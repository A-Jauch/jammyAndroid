package com.jammy.routes;

import com.jammy.responseModel.ResponseJam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JamRoutes {

    @GET("jams")
    Call<ResponseJam> findAllJam (@Header("Authorization") String token);

}
