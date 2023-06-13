package com.example.ap2_speakeasy.API;



import com.example.ap2_speakeasy.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
        @GET("users")
        Call<List<User>> getUsers();

        @POST("users")
        Call<Void> createUser(@Body User user);

        @GET("users/{id}")
        Call<Void> getUser (@Path("id") int id);

}