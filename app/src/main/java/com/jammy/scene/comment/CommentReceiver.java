package com.jammy.scene.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.jammy.model.Comment;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseComment;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.CommentRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.post.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentReceiver extends AppCompatActivity {
    private List<Comment> commentList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    CommentAdapter commentAdapter;
    String token = fileManager.readFile("token.txt").trim();
    RecyclerView.LayoutManager layoutManager;
    Button newCommentBtn;
    TextView postContentTextView, userTextView;
    private int postId;
    private int userId;
    private UserRoutes userRoutes;
    private User user = new User();
    private CommentRoutes  commentRoutes;
    private String userName;
    private String postContent;

    public static final String ID_POST_FROM_COM_RECEIVER = "com.jammy.scene.comment.ID_POST_FROM_COM_RECEIVER";
    public static final String ID_USER_FROM_COM_RECEIVER = "com.jammy.scene.comment.ID_USER_FROM_COM_RECEIVER";
    public static final String POST_CONTENT_FROM_COM_RECEIVER = "com.jammy.scene.comment.POST_CONTENT_FROM_COM_RECEIVER";
    public static final String INFO_USER_FROM_COM_RECEIVER = "com.jammy.scene.comment.INFO_USER_FROM_COM_RECEIVER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_receiver);
        newCommentBtn = findViewById(R.id.create_comment_btn);
        postContentTextView = findViewById(R.id.post_content_cardview);
        userTextView = findViewById(R.id.post_user_cardview);
        Intent intent = getIntent();
        postId = intent.getIntExtra(PostAdapter.ID_POST_ADAPTER,0);
        commentRoutes = RetrofitClientInstance.getRetrofitInstance().create(CommentRoutes.class);

        if (postId == 0){
            postId = intent.getIntExtra(CreateCommentActivity.ID_POST_FROM_CREATED_COM,-1);
        }

        if (postId == -1){
            postId = intent.getIntExtra(UpdateCommentActivity.ID_POST_FROM_COM_UPDATE,-2);
        }

        userName = intent.getStringExtra(PostAdapter.INFO_POST_USER);
        postContent = intent.getStringExtra(PostAdapter.INFO_POST_CONTENT);
        if (userName == null && postContent == null){
            userName = intent.getStringExtra(CreateCommentActivity.INFO_USER_FROM_CREATED_COM_);
            postContent = intent.getStringExtra(CreateCommentActivity.POST_CONTENT_FROM_CREATED_COM);
            if (userName == null && postContent == null){
                postContent = intent.getStringExtra(UpdateCommentActivity.INFO_POST_FROM_COM_UPDATE);
                userName = intent.getStringExtra(UpdateCommentActivity.INFO_USER_FROM_COM_UPDATE);
            }
        }

        postContentTextView.setText(postContent);
        userTextView.setText(userName);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                getAllCommentByPost(postId);

            }
        }, 340);



        newCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<ResponseUser> getMe = userRoutes.me("Bearer " + token);
                getMe.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            ResponseUser responseUser = response.body();
                            user.setId(responseUser.getResults().getId());
                            Intent intent = new Intent(CommentReceiver.this, CreateCommentActivity.class);
                            intent.putExtra(ID_POST_FROM_COM_RECEIVER, postId);
                            intent.putExtra(ID_USER_FROM_COM_RECEIVER, user.getId());
                            intent.putExtra(POST_CONTENT_FROM_COM_RECEIVER, postContent);
                            intent.putExtra(INFO_USER_FROM_COM_RECEIVER, userName);
                            startActivity(intent);
                            finish();
                        } else {
                            Gson gson = new Gson();
                               ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                                Toast.makeText(CommentReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.code() == 500){
                            Toast.makeText(CommentReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        Toast.makeText(CommentReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

    }


    private void getAllCommentByPost(int postId) {
        Call<ResponseComment> getCommentByPost = commentRoutes.findCommentByPost(postId, "Bearer " + token);
        getCommentByPost.enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                if (response.isSuccessful()){
                    ResponseComment responseComment = response.body();
                    populateCommentAdapter(responseComment.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(CommentReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CommentReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseComment> call, Throwable t) {
                Toast.makeText(CommentReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateCommentAdapter(List<Comment> results) {
        for (Comment comment: results){
            commentList.add(comment);
            RecyclerView rvComment = findViewById(R.id.rvComment);
            rvComment.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvComment.setLayoutManager(layoutManager);
            commentAdapter = new CommentAdapter(this, commentList, rvComment);
            rvComment.setAdapter(commentAdapter);
        }
    }
}