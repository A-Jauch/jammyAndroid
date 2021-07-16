package com.jammy.scene.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.scene.dashboard.DashboardActivity;
import com.jammy.scene.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCRENN = 3000;
    FileManager fileManager = new FileManager();
    String connect = fileManager.readFile("connect.txt").trim();


    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView appName, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.logo_imageview);
        appName = findViewById(R.id.app_name_textview);
        slogan = findViewById(R.id.slogan_textview);

        imageView.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (connect.contentEquals("isConnected")){
                    Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCRENN);

    }
}