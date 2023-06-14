package com.example.ap2_speakeasy.API;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.MainActivity;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.Sign_up.PasswordActivity;
import com.example.ap2_speakeasy.Sign_up.SignUpActivity;

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
    private Retrofit retrofit;
    private UserServiceAPI userServiceAPI;
    public String token;

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


    public void register(String username, String password, String name, String profilePicture, CallBackFlag callBackFlag) {
        Call<ResponseBody> signupCall = userServiceAPI.signup(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
        signupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callBackFlag.complete(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //binding.editTextUsername.setError(getString(R.string.connection_error));
            }
        });
    }

    public void signIn(String username, String password,CallBackFlag callBackFlag) {
        Call<ResponseBody> loginCall = userServiceAPI.login(Map.of("username", username, "password", password));
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callBackFlag.complete(response.code());
                if (response.code() == 200) {
                    try {
                        token = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //binding.editTextUsername.setError(getString(R.string.connection_error));
            }
        });
    }
}

