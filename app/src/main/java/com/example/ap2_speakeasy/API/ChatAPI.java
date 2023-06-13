package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    Retrofit retrofit;
    ChatServiceAPI chatServiceAPI;

    public ChatAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatServiceAPI = retrofit.create(ChatServiceAPI.class);
    }

    public void getAllChats() {
        Call<List<User>> call = chatServiceAPI.getChats();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }

    public Call<Map<String, String>> createChat(String username) {
        Call<Void> call = chatServiceAPI.createChat(username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Handle the response
                if (response.isSuccessful()) {
                    // The chat was created successfully
                    // You can access the response body if needed
                    // response.body();
                } else {
                    // The chat creation failed
                    // You can handle the error here
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure
            }
        });

        // Return a Call object with the desired response type
        return null; // Replace with the appropriate Call object
    }


    public void getChatById(int id) {
        Call<Void> call = chatServiceAPI.getChat(id);
        call.enqueue(new Callback<Void>() {
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
        Call<Void> call = chatServiceAPI.deleteChat(id);
        call.enqueue(new Callback<Void>() {
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
}
