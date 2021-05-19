package com.jammy.routes;

import com.jammy.responseModel.ResponseLogin;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserRoutes {

    @POST("login")
    Call<ResponseLogin> login(@Body User user);

    @POST("signup")
    Call<User> signup(@Body User user);

    @GET("me")
    Call<ResponseUser> me(@Header("Authorization") String token);

}
