package com.jammy.scene.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    Context context;
    List<Request> requestList;

    RecyclerView rvRequest;
    //   public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
    //   public static final String INFO_POST_USER = "com.jammy.scene.post.INFO_POST_USER";
    //   public static final String INFO_POST_CONTENT = "com.jammy.scene.post.INFO_POST_CONTENT";
    //   public static final String ID_POST_USER = "com.jammy.scene.post.ID_POST_USER";
    //   final View.OnClickListener onClickListener = new PostAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowContent,  rowStatus,rowUser,rowAnswer,rowJamOrCat,  rowDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowContent = itemView.findViewById(R.id.item_request_content);
            rowStatus = itemView.findViewById(R.id.item_status_request);
            rowAnswer = itemView.findViewById(R.id.item_answer_request);
            rowJamOrCat = itemView.findViewById(R.id.item_request_jamorcat);
            rowDate = itemView.findViewById(R.id.item_request_date);
            rowUser = itemView.findViewById(R.id.item_request_user);
        }
    }

    public RequestAdapter(Context context, List<Request> requestList, RecyclerView rvRequest) {
        this.context = context;
        this.requestList = requestList;
        this.rvRequest = rvRequest;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_request, parent, false);
        // view.setOnClickListener(onClickListener);
        RequestAdapter.ViewHolder viewHolder = new RequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.rowContent.setText(context.getString(R.string.contenue) + ": " + request.getContent());
        holder.rowUser.setText(context.getString(R.string.posted_by) + ": " + request.getUser().getName() + " " + request.getUser().getLastname());
        if (request.getStatus() == 0){
            holder.rowStatus.setText(context.getString(R.string.status) + ": " + "Pending");
        } else if (request.getStatus() == 1){
            holder.rowStatus.setText(context.getString(R.string.status) + ": " + "Accepted");
        }else if (request.getStatus() == 2){
            holder.rowStatus.setText(context.getString(R.string.status) + ": " + "Denied");
        }
        holder.rowAnswer.setText(context.getString(R.string.answer) + ": " + request.getAnswer());
        if (request.getJam_or_cat() == 1){
            holder.rowJamOrCat.setText(context.getString(R.string.demande) + ": " + "Category request");
        } else if (request.getJam_or_cat() == 2){
            holder.rowJamOrCat.setText(context.getString(R.string.demande) + ": " + "Jam Owner request");
        }
         String formatDate = request.getCreatedAt().substring(0,10) + " " + request.getCreatedAt().substring(11,19);
        holder.rowDate.setText(formatDate);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


}
