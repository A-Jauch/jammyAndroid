package com.jammy.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.jammy.R;

public class LoginActivity extends AppCompatActivity {

    TabLayout loginTabLayout;
    TabLayout.Tab testTab;
    ViewPager2 viewPager;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTabLayout = findViewById(R.id.login_table_layout);
        viewPager = findViewById(R.id.login_view_pager);
        loginTabLayout.addTab(loginTabLayout.newTab().setText("Login"));
        loginTabLayout.addTab(loginTabLayout.newTab().setText("Signup"));
        loginTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(),getLifecycle(), this, loginTabLayout.getTabCount());
        viewPager.setAdapter(loginAdapter);

        loginTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                loginTabLayout.selectTab(loginTabLayout.getTabAt(position));
            }
        });

       // loginTabLayout.setupWithViewPager(viewPager);

        loginTabLayout.setTranslationY(300);
        loginTabLayout.setAlpha(v);
        loginTabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
    }
}