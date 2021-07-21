package com.jammy.routes;

import com.jammy.model.FcmToken;
import com.jammy.model.ResetPassword;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseLogin;
import com.jammy.responseModel.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserRoutes {

    @POST("login")
    Call<ResponseLogin> login(@Body User user);

    @POST("signup")
    Call<User> signup(@Body User user);

    @POST("reset/mail")
    Call<User> sendResetPassword(@Body User user);

    @GET("me")
    Call<ResponseUser> me(@Header("Authorization") String token);

    @PUT("user/{id}")
    Call<Void> updateUser(@Body User user, @Path("id") int id, @Header("Authorization") String token);

    @PUT("user/{id}")
    Call<Void> updateUserFcm(@Body FcmToken fcmToken, @Path("id") int id, @Header("Authorization") String token);

    @PUT("reset/password")
    Call<Void> updatePassword(@Body ResetPassword resetPassword);


    @GET("user/{id}")
    Call<ResponseUser> getUserById(@Path ("id") int id,@Header("Authorization") String token);

}
