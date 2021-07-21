package com.jammy.scene.request;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Request;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseRequest;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.RequestRoutes;
import com.jammy.routes.UserRoutes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestReceiver extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    private UserRoutes userRoutes;
    private RequestRoutes requestRoutes;
    RequestAdapter requestAdapter;
    RecyclerView.LayoutManager layoutManager;
    private User user = new User();
    private List<Request> requestList = new ArrayList<>();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_receiver);

        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        Call<ResponseUser> getMe = userRoutes.me(token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    user.setId(responseUser.getResults().getId());
                    getAllRequestByUser(user.getId());


                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(RequestReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(RequestReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(RequestReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAllRequestByUser(int id) {
        requestRoutes = RetrofitClientInstance.getRetrofitInstance().create(RequestRoutes.class);
        Call <ResponseRequest> getAllRequestByUser = requestRoutes.findRequestByPost(id,token);
        getAllRequestByUser.enqueue(new Callback<ResponseRequest>() {
            @Override
            public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                if (response.isSuccessful()){
                    ResponseRequest responseRequest = response.body();
                    populateRequestAdapter(responseRequest.getResults());
                } else {
                    Toast.makeText(RequestReceiver.this, "No requests for this profile", Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(RequestReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRequest> call, Throwable t) {
                Toast.makeText(RequestReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateRequestAdapter(List<Request> body) {
        for (Request request: body){
            requestList.add(request);
            RecyclerView rvRequest = findViewById(R.id.rvRequest);
            rvRequest.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvRequest.setLayoutManager(layoutManager);
            requestAdapter = new RequestAdapter(this, requestList,rvRequest);
            rvRequest.setAdapter(requestAdapter);
        }
    }
}