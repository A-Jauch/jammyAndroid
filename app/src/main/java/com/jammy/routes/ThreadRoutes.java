package com.jammy.routes;

import com.jammy.model.PostThread;
import com.jammy.responseModel.ResponsePostThread;
import com.jammy.responseModel.ResponseThread;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ThreadRoutes {


    @GET("thread/category/{id}")
    Call<ResponseThread> findThreadByCategory (@Path("id") int id, @Header("Authorization") String token);

    @POST("thread")
    Call<ResponsePostThread> postThread (@Body PostThread thread, @Header("Authorization") String token);

}
