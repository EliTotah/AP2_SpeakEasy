package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServiceAPI {
    @POST("Chats/{id}/Messages")
    Call<Void> createMessage (@Path("id") int id, @Body String username, String content);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages (@Path("id") int id);
}
