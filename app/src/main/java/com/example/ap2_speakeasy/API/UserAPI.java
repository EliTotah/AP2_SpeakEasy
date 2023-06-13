package com.example.ap2_speakeasy.API;

import android.preference.PreferenceManager;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.User;

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
        Call<List<User>> call = userServiceAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        List<User> users = response.body();
    }
    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }

    public Call<Map<String, String>> login(String username, String password, String token) {
        return userServiceAPI.login(Map.of("username", username, "password", password, "firebaseToken", token));
    }

    public Call<Void> signup(String username, String password, String name, String profilePicture) {
        return userServiceAPI.signup(Map.of("username", username, "password", password, "name", name, "profilePicture", profilePicture));
    }
}