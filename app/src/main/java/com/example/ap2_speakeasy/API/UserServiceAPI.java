package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.Contact;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceAPI {
    @POST("Tokens/")
    Call<Map<String, String>> login(@Body Map<String, String> user);

    @POST("Users/")
    Call<Void> signup(@Body Map<String, String> user);

    @GET("Users/")
    Call<List<Contact>> getUsers();

    @POST("Users/")
    Call<Void> createUser(@Body Contact contact);

    @GET("Users/{id}")
    Call<Void> getUser (@Path("id") int id);
}
