package com.jammy.scene.friends;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Friends;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseFriend;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.FriendsRoutes;
import com.jammy.routes.UserRoutes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsReceiver extends AppCompatActivity {
    private List<Friends> friendsList = new ArrayList<>();
    ArrayList result = new ArrayList();
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    FriendsAdapter friendsAdapter;
    RecyclerView.LayoutManager layoutManager;
    private FriendsRoutes friendsRoutes;
    private UserRoutes userRoutes;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_receiver);
        getFriendForMe();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friend_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void populateFriendAdapter(List<Friends> body) {
        for (Friends friend: body){
            friendsList.add(friend);
            RecyclerView rvFriend = findViewById(R.id.rvFriends);
            rvFriend.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvFriend.setLayoutManager(layoutManager);
            friendsAdapter = new FriendsAdapter(this,friendsList,rvFriend);
            rvFriend.setAdapter(friendsAdapter);
        }
    }

    private void getFriendForMe(){
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        friendsRoutes = RetrofitClientInstance.getRetrofitInstance().create(FriendsRoutes.class);

        Call<ResponseUser> getMe = userRoutes.me("Bearer "+token);

        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    user = response.body().getResults();

                    Call<ResponseFriend> getAllFriendsByUserId = friendsRoutes.findAllByUser(user.getId(),"Bearer "+token);
                    getAllFriendsByUserId.enqueue(new Callback<ResponseFriend>() {
                        @Override
                        public void onResponse(Call<ResponseFriend> call, Response<ResponseFriend> response) {
                            if (response.isSuccessful()){
                                ResponseFriend friendsResponse = response.body();
                                populateFriendAdapter(friendsResponse.getResults());
                            } else {
                                Toast.makeText(FriendsReceiver.this, response.code(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(FriendsReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFriend> call, Throwable t) {
                            Toast.makeText(FriendsReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(FriendsReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}