package com.example.ap2_speakeasy.API;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.Message;


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
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageServiceAPI = retrofit.create(MessageServiceAPI.class);
    }

    public void createMessage(String token, int id, String content) {
        //content
        Call<Map<String, String>> call = messageServiceAPI.createMessage(token,id, Map.of("msg", content));
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    AppDB db = DatabaseManager.getDatabase();
                    MessageDao messageDao = db.messageDao();
                    Map<String, String> map = response.body();
                    if (map != null) {
                        String created = map.get("created");
                        String userName = map.get("sender");
                        Map<String,String> username= Map.of("username",userName);
                        String content = map.get("content");
                        Message m = new Message(content, created, username, id);
                        messageDao.insert(m);
                        Log.e("call message", response.body().toString());
                        responeAnswer.setValue("ok");
                    } else {
                        Log.e("call message", "booooooo");
                    }
                } else {
                    responeAnswer.setValue(response.errorBody().toString());
                    Log.e("call message", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                String err = t.getMessage();
                if (err!=null){
                    Log.e("api1 call","ERROR: " + err );
                }
                else {
                    Log.e("api2 call","Unknown error");
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
                    messagesLiveData.setValue(response.body());
                    Log.e("messages-call",response.body().toString());
                }
                else {
                    Log.e("messages-call","booooooo");
                }
                // Process the retrieved messages
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                // Handle the failure
            }
        });

        return call;
    }
}
