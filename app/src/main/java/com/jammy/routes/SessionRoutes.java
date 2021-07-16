package com.jammy.routes;

import com.jammy.responseModel.ResponseSession;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SessionRoutes {

    @GET("session/jam/{id}")
    Call<ResponseSession> findSessionByJam (@Path("id") int id, @Header("Authorization") String token);

}
