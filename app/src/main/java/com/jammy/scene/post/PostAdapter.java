package com.jammy.scene.post;

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
import com.jammy.model.Post;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.comment.CommentReceiver;
import com.jammy.scene.profil.OtherProfileActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements Filterable {
    private User user = new User();
    Context context;
    List<Post> postList;
    private UserRoutes userRoutes;
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    List<Post> postListFull;
    RecyclerView rvPost;
    public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
    public static final String ID_THREAD_FROM_POST_ADAPTER = "com.jammy.scene.post.ID_THREAD_FROM_POST_ADAPTER";
    public static final String INFO_POST_USER = "com.jammy.scene.post.INFO_POST_USER";
    public static final String INFO_POST_CONTENT = "com.jammy.scene.post.INFO_POST_CONTENT";
    public static final String ID_POST_USER = "com.jammy.scene.post.ID_POST_USER";
    public static final String ID_USER_FROM_POST_ADAPTER = "com.jammy.scene.post.ID_USER_FROM_POST_ADAPTER";
     final View.OnClickListener onClickListener = new PostAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowContent, rowThread, rowUser, rowDate;
        ImageView profilImage,editPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowContent = itemView.findViewById(R.id.item_post_content);
            rowThread = itemView.findViewById(R.id.item_post_thread);
            rowUser = itemView.findViewById(R.id.item_post_user);
            rowDate = itemView.findViewById(R.id.item_post_date);
            profilImage = itemView.findViewById(R.id.profil_image_view);
            editPostImage = itemView.findViewById(R.id.modifypost_image_view);

        }
    }

    public PostAdapter(Context context, List<Post> postList, RecyclerView rvPost) {
        this.context = context;
        this.postList = postList;
        this.rvPost = rvPost;
        postListFull = new ArrayList<>(postList);
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_post, parent, false);
        view.setOnClickListener(onClickListener);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.rowContent.setText(post.getContent());
        holder.rowThread.setText(post.getThread().getName());
        holder.rowUser.setText(context.getString(R.string.posted_by) + ": " + post.getUser().getName() + " " + post.getUser().getLastname());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
       // String formatDate = simpleDateFormat.format(ts);
        holder.rowDate.setText(post.getCreatedAt());
        holder.profilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostAdapter.this.context, OtherProfileActivity.class);
                intent.putExtra(ID_USER_FROM_POST_ADAPTER, post.getUser().getId());
                context.startActivity(intent);
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
                    if (post.getUser().getId() == user.getId()){
                        holder.profilImage.setVisibility(View.GONE);
                        holder.editPostImage.setVisibility(View.VISIBLE);
                        holder.editPostImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PostAdapter.this.context, UpdatePostActivity.class);
                                intent.putExtra(ID_POST_ADAPTER, post.getId());
                                intent.putExtra(ID_POST_USER, post.getUser_id());
                                intent.putExtra(ID_THREAD_FROM_POST_ADAPTER, post.getThread_id());
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
        return postList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvPost.getChildLayoutPosition(v);
            Post post = postList.get(itemPosition);
            Intent intent = new Intent(PostAdapter.this.context, CommentReceiver.class);
            intent.putExtra(ID_POST_ADAPTER, post.getId());
            intent.putExtra(INFO_POST_USER, post.getUser().getName() + " " + post.getUser().getLastname());
            intent.putExtra(ID_POST_USER, post.getUser().getId());
            intent.putExtra(INFO_POST_CONTENT, post.getContent());
            context.startActivity(intent);
        }
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
