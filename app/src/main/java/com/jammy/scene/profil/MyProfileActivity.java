package com.jammy.scene.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseUser;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.login.LoginActivity;
import com.jammy.scene.request.RequestReceiver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    private UserRoutes userRoutes;
    private User user = new User();
    FileManager fileManager = new FileManager();
    FileManager connectFm = new FileManager();
    ImageView LogoutImage;
    Button editProfilButton,showRequestButton;
    CardView newCatCard, jamOwnerCard;
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    TextView userName_text, email_text, role_text,instrument_text;
    public static final String ID_JAM_OR_CAT = "com.jammy.scene.profil.ID_JAM_OR_CAT";
    public static final String ID_USER_FROM_PROFILE = "com.jammy.scene.profil.ID_USER_FROM_PROFILE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName_text = findViewById(R.id.profile_name);
        email_text = findViewById(R.id.profile_email);
        role_text = findViewById(R.id.profile_role);
        LogoutImage = findViewById(R.id.logoutImage);
        newCatCard = findViewById(R.id.categoryProfileCard);
        jamOwnerCard = findViewById(R.id.ProfilejamCard);
        editProfilButton = findViewById(R.id.edit_profile_btn);
        showRequestButton = findViewById(R.id.show_request_btn);
        instrument_text = findViewById(R.id.profile_instrument);

        userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
        Call<ResponseUser> getMe = userRoutes.me(token);
        getMe.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    userName_text.setText(responseUser.getResults().getName() + " " + responseUser.getResults().getLastname());
                    email_text.setText(responseUser.getResults().getEmail());
                    role_text.setText(responseUser.getResults().getRole().getName());
                    instrument_text.setText(responseUser.getResults().getInstrument().getName());
                    user.setId(responseUser.getResults().getId());

                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(MyProfileActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(MyProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(MyProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        newCatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRequest.class);
                intent.putExtra(ID_JAM_OR_CAT, 1);
                intent.putExtra(ID_USER_FROM_PROFILE, user.getId());
                startActivity(intent);
                finish();
            }
        });

        jamOwnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRequest.class);
                intent.putExtra(ID_JAM_OR_CAT, 2);
                intent.putExtra(ID_USER_FROM_PROFILE, user.getId());
                startActivity(intent);
                finish();
            }
        });

        LogoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean deletConnect = connectFm.deleteFile("connect.txt");
                if (deletConnect == true){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        editProfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfilActivity.class);
                intent.putExtra(ID_USER_FROM_PROFILE, user.getId());
                startActivity(intent);
                finish();

            }
        });

        showRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestReceiver.class);
               // intent.putExtra(ID_USER_FROM_PROFILE, user.getId());
                startActivity(intent);
            }
        });
    }
}