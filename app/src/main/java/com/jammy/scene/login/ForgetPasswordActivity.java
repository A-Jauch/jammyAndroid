package com.jammy.scene.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button sendResetEmail;
    EditText emailEditText;
    private UserRoutes userRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        sendResetEmail = findViewById(R.id.send_reset_email_btn);
        emailEditText = findViewById(R.id.email_reset_input);

        sendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setEmail(emailEditText.getText().toString().trim());
                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<User> sendEmail = userRoutes.sendResetPassword(user);
                sendEmail.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Email envoyé", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else if ( !response.isSuccessful()){
                            Gson gson = new Gson();
                            ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                            Toast.makeText(getApplicationContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}