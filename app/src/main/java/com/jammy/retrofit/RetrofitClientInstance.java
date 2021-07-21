package com.jammy.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static Retrofit retrofit2;
    private static final String BASE_URL = "https://api.jammy.ovh/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("MM-dd-yyyy")
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstance2() {
        if (retrofit2 == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy")
                    .create();


            retrofit2 = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.11:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit2;
    }
}
