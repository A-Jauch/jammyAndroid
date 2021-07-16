package com.jammy.scene.jam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Jam;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseJam;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.JamRoutes;
import com.jammy.routes.UserRoutes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JamReceiver extends AppCompatActivity {
    private List<Jam> jamList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    JamAdapter jamAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button newPostBtn;
    private JamRoutes jamRoutes;
    private UserRoutes userRoutes;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_receiver);

        getAllJam();
    }

    private void getAllJam() {
        jamRoutes = RetrofitClientInstance.getRetrofitInstance().create(JamRoutes.class);
        Call<ResponseJam> getPostByThread = jamRoutes.findAllJam(token);
        getPostByThread.enqueue(new Callback<ResponseJam>() {
            @Override
            public void onResponse(Call<ResponseJam> call, Response<ResponseJam> response) {
                if (response.isSuccessful()){
                    ResponseJam responseJam = response.body();
                    populateJamAdapter(responseJam.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(JamReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(JamReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJam> call, Throwable t) {
                Toast.makeText(JamReceiver.this, t.getCause().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateJamAdapter(List<Jam> results) {
        for (Jam jam: results){
            jamList.add(jam);
            RecyclerView rvJam = findViewById(R.id.rvJam);
            rvJam.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvJam.setLayoutManager(layoutManager);
            jamAdapter = new JamAdapter(this, jamList, rvJam);
            rvJam.setAdapter(jamAdapter);
        }
    }
}