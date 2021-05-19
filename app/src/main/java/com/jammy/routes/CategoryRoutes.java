package com.jammy.routes;

import com.jammy.responseModel.ResponseCategory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoryRoutes {
    @GET("categories")

    Call<ResponseCategory> getAllCategories(@Header("Authorization") String token);
}
