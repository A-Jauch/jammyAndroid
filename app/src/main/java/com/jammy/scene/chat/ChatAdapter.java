package com.jammy.scene.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{


    Context context;
    List<Message> messageList;
    List<Message> testList = new ArrayList<>();

    RecyclerView rvMessage;
    //   public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
    //   public static final String INFO_POST_USER = "com.jammy.scene.post.INFO_POST_USER";
    //   public static final String INFO_POST_CONTENT = "com.jammy.scene.post.INFO_POST_CONTENT";
    //   public static final String ID_POST_USER = "com.jammy.scene.post.ID_POST_USER";
    //   final View.OnClickListener onClickListener = new PostAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowMyMessage,  rowOtherMessage;
        ImageView myProfile, otherProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowMyMessage = itemView.findViewById(R.id.my_message);
            rowOtherMessage = itemView.findViewById(R.id.other_message);
            myProfile = itemView.findViewById(R.id.my_profile);
            otherProfile = itemView.findViewById(R.id.other_profile);
        }
    }

    public ChatAdapter(Context context, List<Message> messageList, RecyclerView rvMessage) {
        this.context = context;
        this.messageList = messageList;
        this.rvMessage = rvMessage;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_view_message, parent, false);
        // view.setOnClickListener(onClickListener);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }

    public void submitList(List<Message> newMessageList){
        MyDiffUtilCallBack diffUtilCallBack = new MyDiffUtilCallBack(testList,newMessageList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallBack);

        testList.clear();
        testList.addAll(newMessageList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getSender().getId() == 20){
            holder.myProfile.setVisibility(View.VISIBLE);
            holder.rowMyMessage.setVisibility(View.VISIBLE);
            holder.otherProfile.setVisibility(View.GONE);
            holder.rowOtherMessage.setVisibility(View.GONE);

            holder.rowMyMessage.setText(message.getContent());
        } else {
            holder.myProfile.setVisibility(View.GONE);
            holder.rowMyMessage.setVisibility(View.GONE);
            holder.otherProfile.setVisibility(View.VISIBLE);
            holder.rowOtherMessage.setVisibility(View.VISIBLE);

            holder.rowOtherMessage.setText(message.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


}
