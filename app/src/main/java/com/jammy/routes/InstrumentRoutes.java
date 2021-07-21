package com.jammy.routes;

import com.jammy.responseModel.ResponseInstrument;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InstrumentRoutes {
    @GET("instruments")
    Call<ResponseInstrument> findAllIndstrument ();
}
