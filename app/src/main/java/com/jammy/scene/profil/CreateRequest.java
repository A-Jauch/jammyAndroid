package com.jammy.scene.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Request;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.RequestRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRequest extends AppCompatActivity {

    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    Button sendRequestBtn;
    EditText contentEditText;
    private RequestRoutes requestRoutes;
    public static final String ID_CATEGORY_THREAD = "com.jammy.scene.thread.ID_CATEGORY_THREAD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        sendRequestBtn = findViewById(R.id.send_request_btn);
        contentEditText = findViewById(R.id.request_content_input);
        Intent intent = getIntent();
        int jam_or_cat = intent.getIntExtra(MyProfileActivity.ID_JAM_OR_CAT, 0);
        int userId = intent.getIntExtra(MyProfileActivity.ID_USER_FROM_PROFILE, 0);



        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request(jam_or_cat,userId,contentEditText.getText().toString());
                sendRequest(request);
            }
        });
    }

    private void sendRequest(Request request) {
        requestRoutes = RetrofitClientInstance.getRetrofitInstance().create(RequestRoutes.class);
        Call<Request> postRequest = requestRoutes.postRequest(request,token);
        postRequest.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()){
                    Request Request = response.body();
                    Toast.makeText(CreateRequest.this,  "Demande envoyé", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(CreateRequest.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CreateRequest.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(CreateRequest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}