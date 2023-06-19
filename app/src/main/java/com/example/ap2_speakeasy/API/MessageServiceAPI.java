package com.example.ap2_speakeasy.API;

import com.example.ap2_speakeasy.entities.Message;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServiceAPI {
    @POST("Chats/{id}/Messages")
    Call<JsonObject> createMessage (@Header("Authorization")String token, @Path("id") int id, @Body Map<String, String> map);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getAllMessage (@Header("Authorization")String token, @Path("id") int id);
}
