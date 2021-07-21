package com.jammy.scene.comment;

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
import com.jammy.model.Comment;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.CommentRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCommentActivity extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    private CommentRoutes commentRoutes;
    private int commentId;
    private int postId;
    private int userId;
    private String postContent;
    private String userInfo;
    Button sendCommentUpdateBtn;
    EditText contentEditText;
    public static final String ID_POST_FROM_COM_UPDATE = "com.jammy.scene.comment.ID_POST_FROM_COM_UPDATE";
    public static final String INFO_POST_FROM_COM_UPDATE = "com.jammy.scene.comment.INFO_POST_FROM_COM_UPDATE";
    public static final String INFO_USER_FROM_COM_UPDATE = "com.jammy.scene.comment.INFO_USER_FROM_COM_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comment);
        sendCommentUpdateBtn = findViewById(R.id.send_comment_update_btn);
        contentEditText = findViewById(R.id.comment_update_content_input);

        Intent intent = getIntent();
        commentId = intent.getIntExtra(CommentAdapter.ID_COMMENT_ADAPTER, 0);
        postId = intent.getIntExtra(CommentAdapter.ID_POST_FROM_COM_ADAPTER, 0);
        userId = intent.getIntExtra(CommentAdapter.ID_USER_FROM_COM_ADAPTER, 0);
        postContent = intent.getStringExtra(CommentAdapter.INFO_POST_CONTENT);
        userInfo = intent.getStringExtra(CommentAdapter.INFO_POST_USER);

        sendCommentUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                Comment comment = new Comment(content, userId,postId);
                Comment comment2 = new Comment();
                comment2.setContent(content);
                updateComment(comment2);
            }
        });
    }

    private void updateComment(Comment comment) {
        commentRoutes = RetrofitClientInstance.getRetrofitInstance().create(CommentRoutes.class);
        Call<Void> updatePost = commentRoutes.updateComment(comment,commentId,token);
        updatePost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),   getString(R.string.comment_updated), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateCommentActivity.this, CommentReceiver.class);
                    intent.putExtra(ID_POST_FROM_COM_UPDATE, postId);
                    intent.putExtra(INFO_POST_FROM_COM_UPDATE, postContent);
                    intent.putExtra(INFO_USER_FROM_COM_UPDATE, userInfo);
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