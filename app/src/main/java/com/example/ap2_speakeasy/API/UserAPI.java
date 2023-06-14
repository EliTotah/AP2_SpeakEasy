package com.example.ap2_speakeasy.API;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.ChatContactsActivity;
import com.example.ap2_speakeasy.Contact;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.R;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    UserServiceAPI userServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
    }

     public void getAllUsers() {
        Call<List<Contact>> call = userServiceAPI.getUsers();
        call.enqueue(new Callback<List<Contact>>() {
    @Override
    public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
        List<Contact> contacts = response.body();
    }
    @Override
    public void onFailure(Call<List<Contact>> call, Throwable t) {}
        });
    }

    public Call<Map<String, String>> login(String username, String password /*,String token*/) {
        //UserAPI userAPI = new UserAPI();
        Call<Map<String, String>> call = userServiceAPI.login(Map.of("username", username, "password", password));
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                String us = response.body().get("username");

            }
            @Override
            public void onFailure (@NonNull Call < Map < String, String >> call, @NonNull Throwable t){
                // Show error message
                //binding.editTextUsername.setError(("error"));
            }
        });
        return userServiceAPI.login(Map.of("username", username, "password", password/*, "firebaseToken", token*/));
    }

    public Call<Void> signup(String username, String password, String name, String profilePicture) {
        return userServiceAPI.signup(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
    }
}