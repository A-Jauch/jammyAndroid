package com.jammy.routes;

import com.jammy.model.Post;
import com.jammy.responseModel.ResponseCreatePost;
import com.jammy.responseModel.ResponsePost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostRoutes {

    @GET("post/thread/{id}")
    Call<ResponsePost> findPostByThread (@Path("id") int id, @Header("Authorization") String token);

    @POST("post")
    Call<ResponseCreatePost> postPost (@Body Post post, @Header("Authorization") String token);

    @PUT("post/{id}")
    Call<Void> updatePost(@Body Post post, @Path("id") int id, @Header("Authorization") String token);


}
