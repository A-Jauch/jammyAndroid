package com.jammy.scene.jam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Session;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    Context context;
    List<Session> sessionList;

    RecyclerView rvSession;
    public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
    public static final String INFO_POST_USER = "com.jammy.scene.post.INFO_POST_USER";
    public static final String INFO_POST_CONTENT = "com.jammy.scene.post.INFO_POST_CONTENT";
    public static final String ID_JAM_FROM_ADAPTER = "com.jammy.scene.post.ID_";
    public static final String ID_USER_FROM_POST_ADAPTER = "com.jammy.scene.post.ID_USER_FROM_POST_ADAPTER";
   // final View.OnClickListener onClickListener = new JamAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName, rowEmail, rowUser, rowDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.item_session_name);
            rowEmail = itemView.findViewById(R.id.item_session_email);
        //    rowUser = itemView.findViewById(R.id.item_jam_creator);
        //    rowDate = itemView.findViewById(R.id.item_jam_date);

        }
    }

    public SessionAdapter(Context context, List<Session> sessionList, RecyclerView rvSession) {
        this.context = context;
        this.sessionList = sessionList;
        this.rvSession = rvSession;
        //   postListFull = new ArrayList<>(jamList);
    }

    @NonNull
    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_session, parent, false);
        //view.setOnClickListener(onClickListener);
        SessionAdapter.ViewHolder viewHolder = new SessionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder holder, int position) {
        Session session = sessionList.get(position);
        holder.rowName.setText(context.getString(R.string.participant) + ": " + session.getUser().getName() + " " + session.getUser().getLastname());
        holder.rowEmail.setText(context.getString(R.string.email) + ": " + session.getUser().getEmail());
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvSession.getChildLayoutPosition(v);
            Session session = sessionList.get(itemPosition);
          //  Intent intent = new Intent(JamAdapter.this.context, CommentReceiver.class);
          //  intent.putExtra(ID_JAM_FROM_ADAPTER, jam.getId());
            // intent.putExtra(INFO_POST_USER, post.getUser().getName() + " " + post.getUser().getLastname());
            //  intent.putExtra(ID_POST_USER, post.getUser().getId());
            //  intent.putExtra(INFO_POST_CONTENT, post.getContent());
           // context.startActivity(intent);
        }
    }

}
