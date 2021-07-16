package com.jammy.scene.friends;

import android.app.Activity;
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
import com.jammy.model.Friends;
import com.jammy.model.Status;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.FriendsRoutes;
import com.jammy.scene.chat.ChatActivity;
import com.jammy.scene.profil.OtherProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Friends> friendsList;
    // for filterable
    List<Friends> friendsFullList;
    RecyclerView rvFriends;
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    private FriendsRoutes friendsRoutes;


    public static final String ID_USER_RECEIVER = "com.jammy.scene.chat.ID_USER_RECEIVER";
    public static final String ID_USER_SENDER = "com.jammy.scene.chat.ID_USER_SENDER";
    public static final String ID_USER_FROM_FRIEND_ADAPTER = "com.jammy.scene.profil.ID_USER_FROM_FRIEND_ADAPTER";
    public static final String STATUS_FROM_FRIEND_ADAPTER = "com.jammy.scene.profil.STATUS_FROM_FRIEND_ADAPTER";

    final View.OnClickListener onClickListener = new MyOnClickListener();


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowEmail;
        ImageView messageImage;
        ImageView deleteImage;
        ImageView acceptedImage;
        ImageView declineImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowEmail = itemView.findViewById(R.id.item_friends_email);
            rowName = itemView.findViewById(R.id.item_friends_name);
            messageImage = itemView.findViewById(R.id.messageImage);
            deleteImage = itemView.findViewById(R.id.deleteFriend_image);
            acceptedImage = itemView.findViewById(R.id.acceptedImage);
            declineImage = itemView.findViewById(R.id.declineFriend_image);
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
        friendsRoutes = RetrofitClientInstance.getRetrofitInstance().create(FriendsRoutes.class);
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
        if (friend.getStatus() == 0){
            holder.messageImage.setVisibility(View.GONE);
            holder.deleteImage.setVisibility(View.GONE);
            holder.acceptedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Status status = new Status(1);
                    Call<Void> acceptFriend = friendsRoutes.updateFriend(friend.getId(),status,token);
                    acceptFriend.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(FriendsAdapter.this.context, "Demande accepter", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FriendsAdapter.this.context, FriendsReceiver.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            } else {
                                Gson gson = new Gson();
                                ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                                Toast.makeText(FriendsAdapter.this.context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(FriendsAdapter.this.context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(FriendsAdapter.this.context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            holder.declineImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Friends friends = new Friends(friend.getId_user1(),friend.getId_user2(),2);
                    Call<Void> rejectFriend = friendsRoutes.deleteFriend(friend.getId(),token);
                    rejectFriend.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(FriendsAdapter.this.context, "Demande rejeter", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FriendsAdapter.this.context, FriendsReceiver.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            } else {
                                Gson gson = new Gson();
                                ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                                Toast.makeText(FriendsAdapter.this.context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(FriendsAdapter.this.context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(FriendsAdapter.this.context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (friend.getStatus() == 1){
            holder.messageImage.setVisibility(View.VISIBLE);
            holder.deleteImage.setVisibility(View.VISIBLE);
            holder.declineImage.setVisibility(View.GONE);
            holder.acceptedImage.setVisibility(View.GONE);
        }
        holder.messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsAdapter.this.context, ChatActivity.class);
                intent.putExtra(ID_USER_RECEIVER, friend.getId_user2());
                intent.putExtra(ID_USER_SENDER, friend.getId_user1());
                context.startActivity(intent);
                //  Toast.makeText(context, "Envoie msg", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> rejectFriend = friendsRoutes.deleteFriend(friend.getId(),token);
                rejectFriend.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(FriendsAdapter.this.context, "Demande rejeter", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FriendsAdapter.this.context, FriendsReceiver.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        } else {
                            Gson gson = new Gson();
                            ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                            Toast.makeText(FriendsAdapter.this.context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.code() == 500){
                            Toast.makeText(FriendsAdapter.this.context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(FriendsAdapter.this.context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
            int itemPosition = rvFriends.getChildLayoutPosition(v);
            Friends friends = friendsList.get(itemPosition);
            Intent intent = new Intent(FriendsAdapter.this.context, OtherProfileActivity.class);
            intent.putExtra(ID_USER_FROM_FRIEND_ADAPTER, friends.getId_user2());
            intent.putExtra(STATUS_FROM_FRIEND_ADAPTER, friends.getStatus());
            context.startActivity(intent);
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
