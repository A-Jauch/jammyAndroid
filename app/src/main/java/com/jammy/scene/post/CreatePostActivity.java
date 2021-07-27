package com.jammy.scene.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Post;
import com.jammy.responseModel.ResponseCreatePost;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.PostRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    Button sendPostBtn;
    EditText contentEditText;
    private PostRoutes postRoutes;
    public static final String ID_POST_CREATED_THREAD = "com.jammy.scene.post.ID_POST_CREATED_THREAD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        sendPostBtn = findViewById(R.id.send_post_btn);
        contentEditText = findViewById(R.id.post_content_input);
        Intent intent = getIntent();
        int threadId = intent.getIntExtra(PostReceiver.ID_POST_THREAD, 0);
        int userId = intent.getIntExtra(PostReceiver.ID_POST_USER, 0);


        sendPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    Post post = new Post(content, userId, threadId);
                    sendPost(post);
                } else {
                    Toast.makeText(CreatePostActivity.this, getString(R.string.post_vide), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendPost(Post post) {
        postRoutes = RetrofitClientInstance.getRetrofitInstance().create(PostRoutes.class);
        Call<ResponseCreatePost> sendPost = postRoutes.postPost(post,token);
        sendPost.enqueue(new Callback<ResponseCreatePost>() {
            @Override
            public void onResponse(Call<ResponseCreatePost> call, Response<ResponseCreatePost> response) {
                if (response.isSuccessful()){
                    ResponseCreatePost responsePost = response.body();
                    Toast.makeText(CreatePostActivity.this,   getString(R.string.post_created), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreatePostActivity.this, PostReceiver.class);
                    intent.putExtra(ID_POST_CREATED_THREAD, responsePost.getResults().getThread_id());
                    startActivity(intent);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(CreatePostActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CreatePostActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePost> call, Throwable t) {
                Toast.makeText(CreatePostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}