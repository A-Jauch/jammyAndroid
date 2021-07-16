package com.jammy.scene.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Post;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponsePost;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.PostRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.dashboard.DashboardActivity;
import com.jammy.scene.profil.MyProfileActivity;
import com.jammy.scene.thread.ThreadAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostReceiver extends AppCompatActivity {
    private List<Post> postList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    PostAdapter postAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button newPostBtn;
    private PostRoutes postRoutes;
    private UserRoutes userRoutes;
    private User user = new User();
    private int threadId;
    public static final String ID_POST_THREAD = "com.jammy.scene.thread.ID_POST_THREAD";
    public static final String ID_POST_USER = "com.jammy.scene.thread.ID_POST_USER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_receiver);
        newPostBtn = findViewById(R.id.create_post_btn);
        newPostBtn.setVisibility(View.GONE);
        Intent intent = getIntent();
        // get thread id from last activity
        threadId = intent.getIntExtra(ThreadAdapter.ID_THREAD_ADAPTER,0);

        // to get the good thread id after creation
        if (threadId == 0){
            threadId = intent.getIntExtra(CreatePostActivity.ID_POST_CREATED_THREAD,0);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.createPostItem:
                        Intent intent = new Intent(PostReceiver.this, CreatePostActivity.class);
                        intent.putExtra(ID_POST_THREAD, threadId);
                        intent.putExtra(ID_POST_USER, user.getId());
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profilItem:
                        startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));

                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        Call<ResponseUser> getMe = userRoutes.me("Bearer " + token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    user.setId(responseUser.getResults().getId());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(PostReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(PostReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(PostReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostReceiver.this, CreatePostActivity.class);
                intent.putExtra(ID_POST_THREAD, threadId);
                intent.putExtra(ID_POST_USER, user.getId());
                startActivity(intent);
                finish();
            }
        });
        getAllPostByThread(threadId);


    }

    private void getAllPostByThread(int threadId) {
        postRoutes = RetrofitClientInstance.getRetrofitInstance().create(PostRoutes.class);
        Call<ResponsePost> getPostByThread = postRoutes.findPostByThread(threadId, "Bearer " + token);
        getPostByThread.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                if (response.isSuccessful()){
                    ResponsePost responsePost = response.body();
                    populatePostAdapter(responsePost.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(PostReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(PostReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePost> call, Throwable t) {

            }
        });
    }

    private void populatePostAdapter(List<Post> results) {
        for (Post post: results){
            postList.add(post);
            RecyclerView rvPost = findViewById(R.id.rvPost);
            rvPost.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvPost.setLayoutManager(layoutManager);
            postAdapter = new PostAdapter(this, postList, rvPost);
            rvPost.setAdapter(postAdapter);
        }
    }
}