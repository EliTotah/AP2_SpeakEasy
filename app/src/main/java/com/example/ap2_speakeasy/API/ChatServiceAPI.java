package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.entities.ChatUserAdd;
import com.example.ap2_speakeasy.entities.Contact;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatServiceAPI {

        @POST("Chats/")
        Call<ChatUserAdd> createChat(@Header("Authorization")String token, @Body Map<String,String> request);

        @GET("Chats/")
        Call<List<Contact>> getChats(@Header("Authorization")String token);

        @GET("Chats/{id}")
        Call<Contact> getChat(@Header("Authorization")String token,@Path("id") String id);

        @DELETE("Chats/{id}")
        Call<Void> deleteChat (@Header("Authorization")String token,@Path("id") String id);
}
