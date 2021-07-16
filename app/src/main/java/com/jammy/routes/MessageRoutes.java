package com.jammy.routes;

import com.jammy.model.Message;
import com.jammy.responseModel.ResponseCreateMessage;
import com.jammy.responseModel.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageRoutes {

    @GET("messages/{receiver}/{sender}")
    Call<ResponseMessage> findByReceiverAndSender (@Path("receiver") int receiver, @Path("sender") int sender, @Header("Authorization") String token);

    @POST("message")
    Call<ResponseCreateMessage> postMessage (@Body Message message, @Header("Authorization") String token);


}
