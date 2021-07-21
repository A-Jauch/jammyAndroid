package com.jammy.scene.jam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.CreateQuery;
import com.jammy.model.Query;
import com.jammy.model.Session;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseCreateQueries;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseQueries;
import com.jammy.responseModel.ResponseSession;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.QueriesRoutes;
import com.jammy.routes.SessionRoutes;
import com.jammy.routes.UserRoutes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JamDetails extends AppCompatActivity {
    private List<Session> sessionList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    private int jamId;
    private String jam_description;
    private String jam_creator;
    TextView jamDescriptionText, jamCreatorTextView, jammyLabelText;
    SessionAdapter sessionAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button joinJamBtn;
    private SessionRoutes sessionRoutes;
    private UserRoutes userRoutes;
    private QueriesRoutes queriesRoutes;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_details);
        Intent intent = getIntent();
        // get thread id from last activity
        jamDescriptionText = findViewById(R.id.jam_description_cardview);
        jamCreatorTextView = findViewById(R.id.jam_user_cardview);
        jammyLabelText = findViewById(R.id.jammy_session_label);
        joinJamBtn = findViewById(R.id.join_jam_btn);
        jamId = intent.getIntExtra(JamAdapter.ID_JAM_FROM_ADAPTER,0);
        jam_description = intent.getStringExtra(JamAdapter.INFO_JAM_DESCRIPTION);
        jam_creator = intent.getStringExtra(JamAdapter.INFO_JAM_USER);

        jamDescriptionText.setText(getString(R.string.description) + ": " + jam_description);
        jamCreatorTextView.setText(getString(R.string.creer_par) + ": " + jam_creator);
        jammyLabelText.setText("Jammy Session " + jamId);
        getMe();
        getSessionByJamId(jamId);
        getQueriesforJam(jamId);

        joinJamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendQuery(jamId,user.getId());
            }
        });

    }

    private void sendQuery(int jamId, int userId) {
        queriesRoutes = RetrofitClientInstance.getRetrofitInstance().create(QueriesRoutes.class);
        CreateQuery createQuery = new CreateQuery(jamId,userId);
        Call<ResponseCreateQueries> sendQuery = queriesRoutes.postQuery(createQuery,token);
        sendQuery.enqueue(new Callback<ResponseCreateQueries>() {
            @Override
            public void onResponse(Call<ResponseCreateQueries> call, Response<ResponseCreateQueries> response) {
                if (response.isSuccessful()){
                    Toast.makeText(JamDetails.this, "Demande envoyer", Toast.LENGTH_SHORT).show();
                    //    Intent intent = new Intent(getApplicationContext(), FriendsReceiver.class);
                    //     startActivity(intent);
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(JamDetails.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(JamDetails.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateQueries> call, Throwable t) {
                Toast.makeText(JamDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getQueriesforJam(int jamId) {
        queriesRoutes = RetrofitClientInstance.getRetrofitInstance().create(QueriesRoutes.class);
       //  createQuery = new CreateQuery(jamId);
        Call<ResponseQueries> sendQuery = queriesRoutes.findQueryByJam(jamId,token);
        sendQuery.enqueue(new Callback<ResponseQueries>() {
            @Override
            public void onResponse(Call<ResponseQueries> call, Response<ResponseQueries> response) {
                if (response.isSuccessful()){
                    for (Query query: response.body().getResults()){
                        if (query.getUser().getId() == user.getId()){
                            joinJamBtn.setVisibility(View.INVISIBLE);
                            return;
                        }
                    }
                    //    Intent intent = new Intent(getApplicationContext(), FriendsReceiver.class);
                    //     startActivity(intent);
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                //    Toast.makeText(JamDetails.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(JamDetails.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseQueries> call, Throwable t) {
                Toast.makeText(JamDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMe() {
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        Call<ResponseUser> getMe = userRoutes.me(token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    user.setId(responseUser.getResults().getId());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(JamDetails.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(JamDetails.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(JamDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSessionByJamId(int jamId) {
        sessionRoutes = RetrofitClientInstance.getRetrofitInstance().create(SessionRoutes.class);
        Call<ResponseSession> getPostByThread = sessionRoutes.findSessionByJam(jamId,token);
        getPostByThread.enqueue(new Callback<ResponseSession>() {
            @Override
            public void onResponse(Call<ResponseSession> call, Response<ResponseSession> response) {
                if (response.isSuccessful()){
                    ResponseSession responseSession = response.body();
                    populateSessionAdapter(responseSession.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(JamDetails.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(JamDetails.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSession> call, Throwable t) {
                Toast.makeText(JamDetails.this, t.getCause().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSessionAdapter(List<Session> results) {
        for (Session session: results){
            sessionList.add(session);
            RecyclerView rvSession = findViewById(R.id.rvSession);
            rvSession.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvSession.setLayoutManager(layoutManager);
            sessionAdapter = new SessionAdapter(this, sessionList, rvSession);
            rvSession.setAdapter(sessionAdapter);
        }
    }
}