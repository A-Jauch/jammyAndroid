package com.jammy.scene.friends;

import android.content.Context;
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

import com.jammy.R;
import com.jammy.model.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Friends> friendsList;
    // for filterable
    List<Friends> friendsFullList;
    RecyclerView rvFriends;
    final View.OnClickListener onClickListener = new MyOnClickListener();


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowEmail;
        ImageView messageImage;
        ImageView deleteImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowEmail = itemView.findViewById(R.id.item_friends_email);
            rowName = itemView.findViewById(R.id.item_friends_name);
            messageImage = itemView.findViewById(R.id.messageImage);
            deleteImage = itemView.findViewById(R.id.deleteFriend_image);
        }
    }

    public FriendsAdapter(Context context, List<Friends> friendsList, RecyclerView rvFriends) {
        this.context = context;
        this.friendsList = friendsList;
        this.rvFriends = rvFriends;
        friendsFullList = new ArrayList<>(friendsList);

    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_friends, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        Friends friend = friendsList.get(position);
        holder.rowName.setText(friend.getUser2().getName() + " " + friend.getUser2().getLastname());
        holder.rowEmail.setText("Email: " + friend.getUser2().getEmail() );
        holder.messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Envoie msg", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete msg", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public Filter getFilter() {
        return friendsFilter;
    }

    private Filter friendsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Friends> filteredFriendList = new ArrayList<>();

            if (constraint == null | constraint.length() == 0){
                filteredFriendList.addAll(friendsFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Friends friend: friendsFullList){
                    if (friend.getUser2().getName().toLowerCase().contains(filterPattern)) {
                        filteredFriendList.add(friend);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredFriendList;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friendsList.clear();
            friendsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
