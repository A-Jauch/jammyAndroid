package com.jammy.scene.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class EditProfilActivity extends AppCompatActivity {

    Button sendEditInfo;
    EditText emailEditText,firstNameEditText, lastNameEditText;
    FileManager fileManager = new FileManager();
    FileManager connectFm = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    private UserRoutes userRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_info);
        sendEditInfo = findViewById(R.id.send_profil_info_btn);
        emailEditText = findViewById(R.id.email_profil_input);
        firstNameEditText = findViewById(R.id.firstname_profil_input);
        lastNameEditText = findViewById(R.id.lasttname_profil_input);


        sendEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                Intent intent = getIntent();
                int userId = intent.getIntExtra(MyProfileActivity.ID_USER_FROM_PROFILE, 0);
                user.setId(userId);
                user.setEmail(emailEditText.getText().toString().trim());
                user.setLastname(lastNameEditText.getText().toString().trim());
                user.setName(firstNameEditText.getText().toString().trim());
                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<Void> loginUser = userRoutes.updateUser(user,user.getId(),token);
                loginUser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "all info has been modified", Toast.LENGTH_SHORT).show();
                            Boolean deletConnect = connectFm.deleteFile("connect.txt");
                            if (deletConnect == true){
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
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
        });

    }
}