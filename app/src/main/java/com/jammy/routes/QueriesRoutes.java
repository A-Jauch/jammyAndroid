package com.jammy.routes;

import com.jammy.model.CreateQuery;
import com.jammy.responseModel.ResponseCreateQueries;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface QueriesRoutes {

    @POST("query")
    Call<ResponseCreateQueries> postQuery (@Body CreateQuery query, @Header("Authorization") String token);
}
