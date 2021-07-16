package com.jammy.scene.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.scene.jam.JamReceiver;
import com.jammy.scene.profil.MyProfileActivity;
import com.jammy.scene.category.CategoryReceiver;
import com.jammy.scene.friends.FriendsReceiver;

public class DashboardActivity extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    CardView friendCard, jamCard, profileCard, categoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        friendCard = findViewById(R.id.friendCard);
        categoryCard = findViewById(R.id.categoryCard);
        profileCard = findViewById(R.id.profilCard);
        jamCard = findViewById(R.id.jamCard);


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

}