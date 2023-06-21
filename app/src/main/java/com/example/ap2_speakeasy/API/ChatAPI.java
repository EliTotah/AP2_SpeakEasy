package com.example.ap2_speakeasy.API;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.ServerUrl;
import com.example.ap2_speakeasy.entities.ChatUserAdd;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit2.Response;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    Retrofit retrofit;
    ChatServiceAPI chatServiceAPI;

    private MutableLiveData responeAnswer;

    public ChatAPI() {

        /////////////
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        /////////////////

        retrofit = new Retrofit.Builder()
                .baseUrl(ServerUrl.getInstance().getUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatServiceAPI = retrofit.create(ChatServiceAPI.class);
        responeAnswer = new MutableLiveData<>();
    }

    public void createChat(String token, String username, MutableLiveData<List<Contact>> contacts) {
        Call<JsonObject> call = chatServiceAPI.createChat(token, Map.of("username", username));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    AppDB db = DatabaseManager.getDatabase();
                    ContactDao contactDao = db.contactDao();
                    JsonObject responseBody = response.body(); // Assuming the response body is a JSON string
                    if (responseBody != null) {
                        String id = responseBody.get("id").getAsString();
                        int id2 = Integer.parseInt(id);
                        JsonObject userJson = responseBody.get("user").getAsJsonObject();
                        User user = new Gson().fromJson(userJson, User.class);
                        Contact c = new Contact(id2, user, null);
                        contactDao.insert(c);
                        List<Contact> currentUsers = contacts.getValue();
                        // Add the new User object to the current list
                        if (currentUsers != null) {
                            currentUsers.add(c);
                        } else {
                            currentUsers = new ArrayList<>();
                            currentUsers.add(c);
                        }
                        contacts.setValue(currentUsers);
                        responeAnswer.setValue("ok");
                    } else {
                        Toast.makeText(AP2_SpeakEasy.context,
                                "Error with add contact", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //responeAnswer.setValue(response.errorBody().toString());
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                String err = t.getMessage();
                if (err != null) {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error" + err, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getAllChats(MutableLiveData<List<Contact>> contactListData, String token) {
        Call<List<Contact>> call = chatServiceAPI.getChats(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    contactListData.setValue(response.body());
                } else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                String err = t.getMessage();
                if (err != null) {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:" + err, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error:", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}