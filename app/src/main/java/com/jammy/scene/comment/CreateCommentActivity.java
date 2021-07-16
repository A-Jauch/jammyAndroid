package com.jammy.scene.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.CreateComment;
import com.jammy.responseModel.ResponseCreateComment;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.CommentRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCommentActivity extends AppCompatActivity {

    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    Button sendCommentBtn;
    EditText contentEditText;
    private CommentRoutes commentRoutes;
    private String userName;
    private String postContent;

    public static final String ID_POST_FROM_CREATED_COM = "com.jammy.scene.comment.ID_POST_FROM_CREATED_COM";
    public static final String POST_CONTENT_FROM_CREATED_COM = "com.jammy.scene.comment.POST_CONTENT_FROM_CREATED_COM";
    public static final String INFO_USER_FROM_CREATED_COM_ = "com.jammy.scene.comment.INFO_USER_FROM_CREATED_COM";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        sendCommentBtn = findViewById(R.id.send_comment_btn);
        contentEditText = findViewById(R.id.comment_content_input);
        Intent intent = getIntent();
        int postId = intent.getIntExtra(CommentReceiver.ID_POST_FROM_COM_RECEIVER, 0);
        int userId = intent.getIntExtra(CommentReceiver.ID_USER_FROM_COM_RECEIVER, 0);
        postContent = intent.getStringExtra(CommentReceiver.POST_CONTENT_FROM_COM_RECEIVER);
        userName = intent.getStringExtra(CommentReceiver.INFO_USER_FROM_COM_RECEIVER);

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                CreateComment comment = new CreateComment(content, userId, postId);
                sendComment(comment);
            }
        });
    }

    private void sendComment(CreateComment comment) {
        commentRoutes = RetrofitClientInstance.getRetrofitInstance().create(CommentRoutes.class);
        Call<ResponseCreateComment> sendComment = commentRoutes.postComment(comment,token);
        sendComment.enqueue(new Callback<ResponseCreateComment>() {
            @Override
            public void onResponse(Call<ResponseCreateComment> call, Response<ResponseCreateComment> response) {
                if (response.isSuccessful()){
                    ResponseCreateComment responseComment = response.body();
                    Toast.makeText(CreateCommentActivity.this,   getString(R.string.comment_created), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCommentActivity.this, CommentReceiver.class);
                    intent.putExtra(ID_POST_FROM_CREATED_COM, responseComment.getResults().getPost_id());
                    intent.putExtra(INFO_USER_FROM_CREATED_COM_, userName);
                    intent.putExtra(POST_CONTENT_FROM_CREATED_COM, postContent);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(intent);
                            finish();
                        }
                    }, 2300);
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(CreateCommentActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CreateCommentActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateComment> call, Throwable t) {
                Toast.makeText(CreateCommentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}