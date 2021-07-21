package com.jammy.scene.post;

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
import com.jammy.model.Post;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.PostRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePostActivity extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    private PostRoutes postRoutes;
    private int postId;
    private int threadId;
    private int userId;
    Button sendPostUpdateBtn;
    EditText contentEditText;
    public static final String ID_THREAD_FROM_POST_UPDATE = "com.jammy.scene.post.ID_THREAD_FROM_POST_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        sendPostUpdateBtn = findViewById(R.id.send_post_update_btn);
        contentEditText = findViewById(R.id.post_update_content_input);
        Intent intent = getIntent();
         threadId = intent.getIntExtra(PostAdapter.ID_THREAD_FROM_POST_ADAPTER, 0);
         postId = intent.getIntExtra(PostAdapter.ID_POST_ADAPTER, 0);
         userId = intent.getIntExtra(PostAdapter.ID_POST_USER, 0);

        sendPostUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                Post post = new Post(content,userId,threadId);
                updatePost(post);
            }
        });

    }

    private void updatePost(Post post) {
        postRoutes = RetrofitClientInstance.getRetrofitInstance().create(PostRoutes.class);
        Call<Void> updatePost = postRoutes.updatePost(post,postId,token);
        updatePost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),   getString(R.string.post_created), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdatePostActivity.this, PostReceiver.class);
                    intent.putExtra(ID_THREAD_FROM_POST_UPDATE, threadId);
                    startActivity(intent);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(getApplicationContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}