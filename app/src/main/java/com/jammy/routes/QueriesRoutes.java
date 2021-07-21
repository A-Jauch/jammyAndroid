package com.jammy.routes;

import com.jammy.model.CreateQuery;
import com.jammy.responseModel.ResponseCreateQueries;
import com.jammy.responseModel.ResponseQueries;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QueriesRoutes {

    @POST("query")
    Call<ResponseCreateQueries> postQuery (@Body CreateQuery query, @Header("Authorization") String token);

    @GET("query/jam/{id}")
    Call<ResponseQueries> findQueryByJam (@Path("id") int id, @Header("Authorization") String token);

}
