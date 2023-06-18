package com.example.ap2_speakeasy.API;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.User;
import com.google.gson.Gson;

import java.io.IOException;
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
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatServiceAPI = retrofit.create(ChatServiceAPI.class);
        responeAnswer = new MutableLiveData<>();
    }

    public void createChat(String username, String token) {
        Call<Map<String, String>> call = chatServiceAPI.createChat(token,Map.of("username",username));
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    AppDB db = DatabaseManager.getDatabase();
                    ContactDao contactDao = db.contactDao();
                    Map<String, String> map = response.body();
                    if (map != null) {
                        String idString = map.get("id");
                        int id = Integer.parseInt(idString);

                        String userJson = map.get("user");

                        Gson gson = new Gson();
                        User user = gson.fromJson(userJson, User.class);
                        Contact c = new Contact(id,user,null);
                        contactDao.insert(c);
                        Log.e("api call",response.body().toString());
                        responeAnswer.setValue("ok");
                    }
                    else {
                        Log.e("api call","booooooo");
                    }
                }
                else {
                    int a = response.code();
                    responeAnswer.setValue(response.errorBody().toString());
                    Log.e("api call",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                String err = t.getMessage();
                if (err!=null){
                    Log.e("api call","ERROR: " + err );
                }
                else {
                    Log.e("api call","Unknown error");
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
                    Log.e("api call",response.body().toString());
                }
                else {
                    Log.e("api call","booooooo");
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                String err = t.getMessage();
                if (err!=null){
                    Log.e("api call","ERROR: " + err );
                }
                else {
                    Log.e("api call","Unknown error");
                }
            }
        });
    }

/*
    public Call<Contact> getChatById(int id) {
        return chatServiceAPI.getChat(id);
        /*call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response here
                } else {
                    // Handle the error response here
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure here
            }
        });
    }
    public void deleteChat(int id) {
        chatServiceAPI.deleteChat(id);
        /*call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response here
                } else {
                    // Handle the error response here
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure here
            }
        });
    }*/
}
