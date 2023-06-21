package com.example.ap2_speakeasy.API;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.ServerUrl;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;
    private MessageServiceAPI messageServiceAPI;
    private MutableLiveData responeAnswer;

    public MessageAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageServiceAPI = retrofit.create(MessageServiceAPI.class);
        responeAnswer = new MutableLiveData<>();
    }

    public void createMessage(String token, int id, String content,MutableLiveData<List<Message>> messages) {
        //content
        Call<JsonObject> call = messageServiceAPI.createMessage(token,id, Map.of("msg", content));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    AppDB db = DatabaseManager.getDatabase();
                    MessageDao messageDao = db.messageDao();
                    JsonObject responseBody = response.body();
                    if (responseBody != null) {
                        String created = responseBody.get("created").getAsString();
                        JsonObject senderJson = responseBody.get("sender").getAsJsonObject();
                        String username = senderJson.get("username").getAsString();
                        Map<String, String> usernameMap = new HashMap<>();
                        usernameMap.put("username", username);
                        String content = responseBody.get("content").getAsString();

                        Message m = new Message(content, created, usernameMap, id);
                        messageDao.insert(m);
                        List<Message> currentMessages = messages.getValue();
                        // Add the new User object to the current list
                        if (currentMessages != null) {
                            currentMessages.add(m);
                        } else {
                            currentMessages = new ArrayList<>();
                            currentMessages.add(m);
                        }
                        messages.postValue(currentMessages);
                        responeAnswer.postValue("ok");
                    } else {
                        Toast.makeText(AP2_SpeakEasy.context,
                                "Error in send message", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    responeAnswer.setValue(response.errorBody().toString());
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                String err = t.getMessage();
                if (err!=null){
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error" + err, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Call<List<Message>> getMessages(MutableLiveData<List<Message>> messagesLiveData, String token, int id) {
        Call<List<Message>> call = messageServiceAPI.getAllMessage(token,id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    messagesLiveData.postValue(response.body());
                }
                else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:" + response.message() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                String err = t.getMessage();
                if (err != null) {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error: " + err, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error in get messages", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return call;
    }
}
