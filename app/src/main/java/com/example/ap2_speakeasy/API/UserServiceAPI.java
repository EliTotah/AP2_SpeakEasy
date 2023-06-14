package com.example.ap2_speakeasy.API;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServiceAPI {
    @POST("Tokens")
    Call<Map<String, String>> login(@Body Map<String, String> user);

    @POST("Users")
    Call<ResponseBody> signup(@Body Map<String, String> user);

//    @GET("Users/")
//    Call<List<User>> getUsers();
//
//    @POST("Users/")
//    Call<Void> createUser(@Body User user);
//
//    @GET("Users/{id}")
//    Call<Void> getUser (@Path("id") int id);
}