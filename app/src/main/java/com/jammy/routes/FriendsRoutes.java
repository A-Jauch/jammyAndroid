package com.jammy.routes;

import com.jammy.responseModel.ResponseFriend;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FriendsRoutes {

    @GET("friends/{id}")
    Call<ResponseFriend> findAllByUser (@Path("id") int id, @Header("Authorization") String token);
}
