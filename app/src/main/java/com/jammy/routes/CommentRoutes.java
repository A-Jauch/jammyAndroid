package com.jammy.routes;

import com.jammy.model.CreateComment;
import com.jammy.responseModel.ResponseComment;
import com.jammy.responseModel.ResponseCreateComment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentRoutes {

    @GET("post/comments/{id}")
    Call<ResponseComment> findCommentByPost (@Path("id") int id, @Header("Authorization") String token);

    @POST("comment")
    Call<ResponseCreateComment> postComment (@Body CreateComment comment, @Header("Authorization") String token);

}
