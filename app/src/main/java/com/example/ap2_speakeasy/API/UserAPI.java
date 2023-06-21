package com.example.ap2_speakeasy.API;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.MainActivity;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.ServerUrl;
import com.example.ap2_speakeasy.Sign_up.PasswordActivity;
import com.example.ap2_speakeasy.Sign_up.SignUpActivity;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.User;

import java.io.IOException;
import java.util.List;
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
    //public String token;
    private MutableLiveData<String> tokenLiveData;
    private MutableLiveData<String> activeUserName;

    private MutableLiveData<User> user;

    private MutableLiveData<String> url;




    public UserAPI() {
        /////////////
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        /////////////////
        String apiAddress = AP2_SpeakEasy.preferences.getString("server", "http://10.0.2.2:5000");
        retrofit = new Retrofit.Builder()
                .baseUrl(apiAddress + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        userServiceAPI = retrofit.create(UserServiceAPI.class);
        tokenLiveData = new MutableLiveData<>();
        activeUserName = new MutableLiveData<>();
        user = new MutableLiveData<>();
    }

    public void setUrl() {

        /////////////
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        /////////////////
        String apiAddress = AP2_SpeakEasy.preferences.getString("server", "http://10.0.2.2:5000");

        retrofit = new Retrofit.Builder()
                .baseUrl(apiAddress + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
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


    public void signIn(String username, String password, String deviceToken, CallBackFlag callBackFlag) {
        Call<ResponseBody> loginCall = userServiceAPI.login(Map.of("username", username, "password", password, "deviceToken", deviceToken));
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String token = response.body().string();
                        setToken(token);
                        setActiveUserName(username);
                    } catch (IOException e) {
                        Toast.makeText(AP2_SpeakEasy.context,
                                "Error with the server", Toast.LENGTH_SHORT).show();;
                    }
                }
                callBackFlag.complete(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                String err = t.getMessage();
                Toast.makeText(AP2_SpeakEasy.context,
                        "Error:" + err, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserDetails(String username, String token, CallBackFlag callBackFlag) {
        Call<User> call = userServiceAPI.getUser(token, username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    //String id = response.body().getId();
                    String display = response.body().getDisplayName();
                    String pic = response.body().getProfilePic();
                    String username = response.body().getUsername();
                    User u = new User(username, display, pic);
                    setUser(u);
                }
                else {
                    Toast.makeText(AP2_SpeakEasy.context,
                            "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
                callBackFlag.complete(response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
    public MutableLiveData<String> getTokenLiveData() {
        return tokenLiveData;
    }

    public void setToken(String token) {
        this.tokenLiveData.setValue(token);
    }

    public MutableLiveData<String> getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName.setValue(activeUserName);
    }

    public User getUser() {
        if (user != null) {
            return user.getValue();
        }
        return null;
    }

    public void setUser(User user) {
        if (user != null) {
            this.user.setValue(user);
        }
        return;
    }
}

