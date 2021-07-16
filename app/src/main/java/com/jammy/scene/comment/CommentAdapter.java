package com.jammy.scene.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Comment> commentList;

    List<Comment> commentListFull;
    RecyclerView rvComment;
 //   public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
 //   public static final String INFO_POST_USER = "com.jammy.scene.post.INFO_POST_USER";
 //   public static final String INFO_POST_CONTENT = "com.jammy.scene.post.INFO_POST_CONTENT";
 //   public static final String ID_POST_USER = "com.jammy.scene.post.ID_POST_USER";
 //   final View.OnClickListener onClickListener = new PostAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowContent,  rowUser, rowDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowContent = itemView.findViewById(R.id.item_comment_content);
            rowUser = itemView.findViewById(R.id.item_comment_user);
            rowDate = itemView.findViewById(R.id.item_comment_date);
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
        holder.rowContent.setText(context.getString(R.string.contenue) + ": " + comment.getContent());
        holder.rowUser.setText(context.getString(R.string.posted_by) + ": " + comment.getUser().getName() + " " + comment.getUser().getLastname());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // String formatDate = simpleDateFormat.format(ts);
        holder.rowDate.setText(comment.getCreatedAt());
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
