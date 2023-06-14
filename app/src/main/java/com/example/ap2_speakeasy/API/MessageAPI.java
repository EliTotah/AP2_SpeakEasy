package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.Message;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;
    private MessageServiceAPI messageServiceAPI;

    public MessageAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageServiceAPI = retrofit.create(MessageServiceAPI.class);
    }

    public Call<Void> createMessage(int id, String username, String content) {
        Call<Void> call = messageServiceAPI.createMessage(id, username,content);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Message created successfully
                } else {
                    // Handle the error
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure
            }
        });

        return call;
    }

    public Call<List<Message>> getMessages(int id) {
        Call<List<Message>> call = messageServiceAPI.getMessages(id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
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
