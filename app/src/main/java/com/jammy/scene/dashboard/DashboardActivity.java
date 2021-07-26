package com.jammy.scene.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.FcmToken;
import com.jammy.model.Friends;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseFriend;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.FriendsRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.category.CategoryReceiver;
import com.jammy.scene.friends.FriendsReceiver;
import com.jammy.scene.jam.JamReceiver;
import com.jammy.scene.profil.MyProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    CardView friendCard, jamCard, profileCard, categoryCard;
    ImageView friendImage, friendNotifImage;
    private FriendsRoutes friendsRoutes;
    private UserRoutes userRoutes;
    User user;
    String token ="Bearer " + fileManager.readFile("token.txt").trim();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        friendCard = findViewById(R.id.friendCard);
        categoryCard = findViewById(R.id.categoryCard);
        profileCard = findViewById(R.id.profilCard);
        jamCard = findViewById(R.id.jamCard);
        friendImage = findViewById(R.id.friendImageView);
        friendNotifImage = findViewById(R.id.friendNotifImageView);
        getMe();
        getFriendForMe();


        friendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendsReceiver.class);
                startActivity(intent);
            }
        });

        categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryReceiver.class);
                startActivity(intent);
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        jamCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JamReceiver.class);
                startActivity(intent);
            }
        });

    }

    private void updateUserToken(String fcMtoken) {
        FcmToken fcmToken = new FcmToken(fcMtoken);
        Call<Void> loginUser = userRoutes.updateUserFcm(fcmToken,user.getId(),token);
        loginUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()){
                } else if ( !response.isSuccessful()){
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(getApplicationContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFriendForMe();
    }

    private void getFriendForMe(){
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        friendsRoutes = RetrofitClientInstance.getRetrofitInstance().create(FriendsRoutes.class);

        Call<ResponseUser> getMe = userRoutes.me(token);

        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    user = response.body().getResults();

                    Call<ResponseFriend> getAllFriendsByUserId = friendsRoutes.findAllByUser(user.getId(),token);
                    getAllFriendsByUserId.enqueue(new Callback<ResponseFriend>() {
                        @Override
                        public void onResponse(Call<ResponseFriend> call, Response<ResponseFriend> response) {
                            if (response.isSuccessful()){
                                ResponseFriend friendsResponse = response.body();
                                if (friendsResponse.getResults().size() == 0){
                                    friendImage.setVisibility(View.VISIBLE);
                                    friendNotifImage.setVisibility(View.INVISIBLE);
                                    return;
                                }
                                for (Friends friends: friendsResponse.getResults()){
                                    if (friends.getStatus() == 0){
                                        friendImage.setVisibility(View.INVISIBLE);
                                        friendNotifImage.setVisibility(View.VISIBLE);
                                        return;
                                    } else if (friends.getStatus() == 1){
                                        friendImage.setVisibility(View.VISIBLE);
                                        friendNotifImage.setVisibility(View.INVISIBLE);
                                    }

                                }
                            } else {
                                Toast.makeText(DashboardActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }

                            if (response.code() == 500){
                                Toast.makeText(DashboardActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFriend> call, Throwable t) {
                            Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMe() {
        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);

        Call<ResponseUser> getMe = userRoutes.me(token);

        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    User resUser = response.body().getResults();
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DashboardActivity.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Get new FCM registration token
                        String FCMtoken = task.getResult();
                        if (resUser.getFcm_token() == null || !resUser.getFcm_token().equals(FCMtoken)){
                            updateUserToken(FCMtoken);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}