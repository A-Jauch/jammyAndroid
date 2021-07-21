package com.jammy.scene.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Friends;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseFriend;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.FriendsRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.friends.FriendsAdapter;
import com.jammy.scene.friends.FriendsReceiver;
import com.jammy.scene.post.PostAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfileActivity extends AppCompatActivity {
    private UserRoutes userRoutes;
    private FriendsRoutes friendsRoutes;
    private User user = new User();
    private User me = new User();
    FileManager fileManager = new FileManager();
    ImageView addFriendImage;
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    TextView userName_text, email_text, role_text, instrument_text;
    private int userId;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        userName_text = findViewById(R.id.other_profile_name);
        email_text = findViewById(R.id.other_profile_email);
        role_text = findViewById(R.id.other_profile_role);
        instrument_text = findViewById(R.id.other_profile_instrument);
        addFriendImage = findViewById(R.id.addFriendImage);
        addFriendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friends friend = new Friends(me.getId(),userId,1);
                Call<Void> addFriend = friendsRoutes.postFriend(friend,token);
                addFriend.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(OtherProfileActivity.this, "Demande envoyer", Toast.LENGTH_SHORT).show();
                        //    Intent intent = new Intent(getApplicationContext(), FriendsReceiver.class);
                       //     startActivity(intent);
                        } else {
                            Gson gson = new Gson();
                            ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                            Toast.makeText(OtherProfileActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.code() == 500){
                            Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Friends friend2 = new Friends(userId,me.getId(),0);
                Call<Void> addFriend2 = friendsRoutes.postFriend(friend2,token);
                addFriend2.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(OtherProfileActivity.this, "Demande envoyer", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), FriendsReceiver.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Gson gson = new Gson();
                            ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                            Toast.makeText(OtherProfileActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.code() == 500){
                            Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Intent intent = getIntent();
        userId = intent.getIntExtra(FriendsAdapter.ID_USER_FROM_FRIEND_ADAPTER, 0);
        status = intent.getIntExtra(FriendsAdapter.STATUS_FROM_FRIEND_ADAPTER, -1);


        if (userId ==0 && status == -1){
            userId =  intent.getIntExtra(PostAdapter.ID_USER_FROM_POST_ADAPTER, 0);
        }

        friendsRoutes = RetrofitClientInstance.getRetrofitInstance().create(FriendsRoutes.class);
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);

        Call<ResponseUser> getUserById = userRoutes.getUserById(userId,token);
        getUserById.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    userName_text.setText(responseUser.getResults().getName() + " " + responseUser.getResults().getLastname());
                    email_text.setText(responseUser.getResults().getEmail());
                    role_text.setText(responseUser.getResults().getRole().getName());
                    instrument_text.setText(responseUser.getResults().getInstrument().getName());
                    if (status == 0 || status == 1){
                        addFriendImage.setVisibility(View.GONE);
                        return;
                    } else if (status == 2){
                        addFriendImage.setVisibility(View.VISIBLE);
                    }
                    addFriendImage.setVisibility(View.GONE);
                    user.setId(responseUser.getResults().getId());
                    getFriendForMe();

                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(OtherProfileActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFriendForMe(){

        Call<ResponseUser> getMe = userRoutes.me(token);

        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {

                    me.setId(response.body().getResults().getId());

                    Call<ResponseFriend> getAllFriendsByUserId = friendsRoutes.findAllByUser(me.getId(),token);
                    getAllFriendsByUserId.enqueue(new Callback<ResponseFriend>() {
                        @Override
                        public void onResponse(Call<ResponseFriend> call, Response<ResponseFriend> response) {
                            if (response.isSuccessful()){
                                ResponseFriend friendsResponse = response.body();

                                populateFriendAdapter(friendsResponse.getResults());
                            } else {
                                Toast.makeText(OtherProfileActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFriend> call, Throwable t) {
                            Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

                    Call<ResponseFriend> getAllFriendsByUserId = friendsRoutes.findAllByUser(20,token);
                    getAllFriendsByUserId.enqueue(new Callback<ResponseFriend>() {
                        @Override
                        public void onResponse(Call<ResponseFriend> call, Response<ResponseFriend> response) {
                            if (response.isSuccessful()){
                                ResponseFriend friendsResponse = response.body();

                                populateFriendAdapter(friendsResponse.getResults());
                            } else {
                                Toast.makeText(OtherProfileActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFriend> call, Throwable t) {
                            Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    private void populateFriendAdapter(List<Friends> body) {

        Call<ResponseUser> getMe = userRoutes.getUserById(userId,token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();

                    for (Friends friend: body){
                        if (friend.getId_user2() != responseUser.getResults().getId()){
                            status = -1;
                        } else if (friend.getId_user2() == responseUser.getResults().getId()){
                            status = friend.getStatus();
                            return;
                        }
                    }

                    if (responseUser.getResults().getId() == me.getId()){
                        addFriendImage.setVisibility(View.GONE);
                        return;
                    }

                    if (status == 0 || status == 1){
                        addFriendImage.setVisibility(View.GONE);
                    } else if (status == -1){
                        addFriendImage.setVisibility(View.VISIBLE);
                    }

                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(OtherProfileActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(OtherProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(OtherProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        }
    }

