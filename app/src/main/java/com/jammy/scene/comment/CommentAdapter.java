package com.jammy.scene.comment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Comment;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements Filterable {
    private User user = new User();
    Context context;
    List<Comment> commentList;
    private UserRoutes userRoutes;
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    List<Comment> commentListFull;
    RecyclerView rvComment;
    public static final String ID_COMMENT_ADAPTER = "com.jammy.scene.comment.ID_COMMENT_ADAPTER";
    public static final String ID_POST_FROM_COM_ADAPTER = "com.jammy.scene.comment.ID_POST_FROM_COM_ADAPTER";
    public static final String ID_USER_FROM_COM_ADAPTER = "com.jammy.scene.comment.ID_USER_FROM_COM_ADAPTER";
    public static final String INFO_POST_CONTENT = "com.jammy.scene.comment.INFO_POST_CONTENT";
    public static final String INFO_POST_USER = "com.jammy.scene.comment.INFO_POST_USER";
 //   final View.OnClickListener onClickListener = new PostAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowContent,  rowUser, rowDate;
        ImageView modifyCommentImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowContent = itemView.findViewById(R.id.item_comment_content);
            rowUser = itemView.findViewById(R.id.item_comment_user);
            rowDate = itemView.findViewById(R.id.item_comment_date);
            modifyCommentImage = itemView.findViewById(R.id.modifycomment_image_view);
        }
    }

    public CommentAdapter(Context context, List<Comment> commentList, RecyclerView rvComment) {
        this.context = context;
        this.commentList = commentList;
        this.rvComment = rvComment;
        commentListFull = new ArrayList<>(commentList);
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_comment, parent, false);
       // view.setOnClickListener(onClickListener);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.rowContent.setText(comment.getContent());
        holder.rowUser.setText(context.getString(R.string.posted_by) + ": " + comment.getUser().getName() + " " + comment.getUser().getLastname());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // String formatDate = simpleDateFormat.format(ts);
        holder.rowDate.setText(comment.getCreatedAt());
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        Call<ResponseUser> getMe = userRoutes.me("Bearer " + token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    user.setId(responseUser.getResults().getId());
                    if (comment.getUser().getId() == user.getId()){
                        holder.modifyCommentImage.setVisibility(View.VISIBLE);
                        holder.modifyCommentImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CommentAdapter.this.context, UpdateCommentActivity.class);
                                intent.putExtra(ID_COMMENT_ADAPTER, comment.getId());
                                intent.putExtra(ID_POST_FROM_COM_ADAPTER, comment.getPost().getId());
                                intent.putExtra(ID_USER_FROM_COM_ADAPTER, comment.getUser().getId());
                                intent.putExtra(INFO_POST_CONTENT, comment.getPost().getContent());
                                intent.putExtra(INFO_POST_USER, comment.getUser().getName() + " " + comment.getUser().getLastname());
                                context.startActivity(intent);
                            }
                        });
                    }
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(context.getApplicationContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(context.getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
        /*    int itemPosition = rvComment.getChildLayoutPosition(v);
            Post post = commentList.get(itemPosition);
            Intent intent = new Intent(PostAdapter.this.context, CommentReceiver.class);
            intent.putExtra(ID_POST_ADAPTER, post.getId());
            intent.putExtra(INFO_POST_USER, post.getUser().getName() + " " + post.getUser().getLastname());
            intent.putExtra(ID_POST_USER, post.getUser().getId());
            intent.putExtra(INFO_POST_CONTENT, post.getContent());
            context.startActivity(intent);*/
        }
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
