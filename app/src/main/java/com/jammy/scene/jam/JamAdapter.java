package com.jammy.scene.jam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Jam;
import com.jammy.model.Post;

import java.text.SimpleDateFormat;
import java.util.List;

public class JamAdapter extends RecyclerView.Adapter<JamAdapter.ViewHolder>{


    Context context;
    List<Jam> jamList;

    List<Post> postListFull;
    RecyclerView rvJam;
    public static final String ID_POST_ADAPTER = "com.jammy.scene.post.ID_POST_ADAPTER";
    public static final String INFO_JAM_USER = "com.jammy.scene.jam.INFO_JAM_USER";
    public static final String INFO_JAM_DESCRIPTION = "com.jammy.scene.jam.INFO_JAM_DESCRIPTION";
    public static final String ID_JAM_FROM_ADAPTER = "com.jammy.scene.jam.ID_JAM_FROM_ADAPTER";
    public static final String ID_USER_FROM_POST_ADAPTER = "com.jammy.scene.post.ID_USER_FROM_POST_ADAPTER";
    final View.OnClickListener onClickListener = new JamAdapter.MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName, rowDescriprion, rowUser, rowDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.item_jam_name);
            rowDescriprion = itemView.findViewById(R.id.item_jam_description);
            rowUser = itemView.findViewById(R.id.item_jam_creator);
            rowDate = itemView.findViewById(R.id.item_jam_date);

        }
    }

    public JamAdapter(Context context, List<Jam> jamList, RecyclerView rvJam) {
        this.context = context;
        this.jamList = jamList;
        this.rvJam = rvJam;
     //   postListFull = new ArrayList<>(jamList);
    }

    @NonNull
    @Override
    public JamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_jam, parent, false);
        view.setOnClickListener(onClickListener);
        JamAdapter.ViewHolder viewHolder = new JamAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JamAdapter.ViewHolder holder, int position) {
        Jam jam = jamList.get(position);
        holder.rowName.setText(context.getString(R.string.name) + ": " + jam.getName());
        holder.rowDescriprion.setText("Description: " + jam.getDescription());
        holder.rowUser.setText(context.getString(R.string.posted_by) + ": " + jam.getCreator().getName() + " " + jam.getCreator().getLastname());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // String formatDate = simpleDateFormat.format(ts);
        holder.rowDate.setText(jam.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return jamList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvJam.getChildLayoutPosition(v);
            Jam jam = jamList.get(itemPosition);
            Intent intent = new Intent(JamAdapter.this.context, JamDetails.class);
            intent.putExtra(ID_JAM_FROM_ADAPTER, jam.getId());
            intent.putExtra(INFO_JAM_USER, jam.getCreator().getName() + " " + jam.getCreator().getLastname());
          //  intent.putExtra(ID_POST_USER, post.getUser().getId());
            intent.putExtra(INFO_JAM_DESCRIPTION, jam.getDescription());
            context.startActivity(intent);
        }
    }

}

