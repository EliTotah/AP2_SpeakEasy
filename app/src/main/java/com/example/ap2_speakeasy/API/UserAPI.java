package com.example.ap2_speakeasy.API;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.Sign_up.PasswordActivity;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    UserServiceAPI userServiceAPI;

    public UserAPI() {
        /////////////
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        /////////////////

        retrofit = new Retrofit.Builder()
                .baseUrl(AP2_SpeakEasy.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
    }

    public Call<Map<String, String>> login(String username, String password) {
        return userServiceAPI.login(Map.of("username", username, "password", password));
    }

//    public Call<Map<String, String>> login(String username, String password, String token) {
//        return userServiceAPI.login(Map.of("username", username, "password", password, "firebaseToken", token));
//    }
//    public Call<ResponseBody> signup(String username, String password, String name, String profilePicture) {
//        return userServiceAPI.signup(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
//    }

    public void register (String username, String password, String name, String profilePicture, CallBackFlag callBackFlag) {
        Call<ResponseBody> signupCall = userServiceAPI.signup(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
        signupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse( Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callBackFlag.complete(true);
                } else {
                    int statusCode = response.code();
//                    if (statusCode == 409) {
//                        // User already exists, show appropriate message to the user using Toast
//                        //Toast.makeText(getApplicationContext(), "This user already exists and cannot be created.", Toast.LENGTH_SHORT).show();
                        callBackFlag.complete(true);
                    }
                }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //binding.editTextUsername.setError(getString(R.string.connection_error));
            }
        });
    }

}