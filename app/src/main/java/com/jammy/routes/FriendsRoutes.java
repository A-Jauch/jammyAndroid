package com.jammy.routes;

import com.jammy.model.Friends;
import com.jammy.model.Status;
import com.jammy.responseModel.ResponseFriend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FriendsRoutes {

    @GET("friends/{id}")
    Call<ResponseFriend> findAllByUser (@Path("id") int id, @Header("Authorization") String token);

    @POST("/friend")
    Call<Void> postFriend(@Body Friends friends, @Header("Authorization") String token);

    @PUT("/friend/{id}")
    Call<Void> updateFriend(@Path("id") int id, @Body Status status, @Header("Authorization") String token);


    @DELETE("/friend/{id}")
    Call<Void> deleteFriend(@Path("id") int id, @Header("Authorization") String token);
}
