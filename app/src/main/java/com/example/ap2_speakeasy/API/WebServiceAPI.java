package com.example.ap2_speakeasy.API;



import com.example.ap2_speakeasy.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
        @GET("users")
        Call<List<Contact>> getUsers();

        @POST("users")
        Call<Void> createUser(@Body Contact contact);

        @GET("users/{id}")
        Call<Void> getUser (@Path("id") int id);

}