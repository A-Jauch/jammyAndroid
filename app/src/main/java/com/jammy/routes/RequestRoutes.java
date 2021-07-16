package com.jammy.routes;

import com.jammy.model.Request;
import com.jammy.responseModel.ResponseRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestRoutes {

    @POST("request")
    Call<Request> postRequest (@Body Request request, @Header("Authorization") String token);

    @GET("request/user/{id}")
    Call<ResponseRequest> findRequestByPost (@Path("id") int id, @Header("Authorization") String token);
}
