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
import com.jammy.model.ResetPassword;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;

import retrofit2.Call;
import retrofit2.Callback;

public class ResetPasswordActivity extends AppCompatActivity {
    Button sendResetInfo;
    EditText tokenEditText,newPasswordEditText, confirmPasswordEditText;
    private UserRoutes userRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        tokenEditText = findViewById(R.id.token_reset_input);
        newPasswordEditText = findViewById(R.id.new_password_input);
        confirmPasswordEditText = findViewById(R.id.confirm_password_input);
        sendResetInfo = findViewById(R.id.reset_password_btn);

        sendResetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token,password,confirm;
                token = tokenEditText.getText().toString().trim();
                password = newPasswordEditText.getText().toString().trim();
                confirm = confirmPasswordEditText.getText().toString().trim();
                ResetPassword resetPassword = new ResetPassword(token,password,confirm);
                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<Void> resetPasswordCall = userRoutes.updatePassword(resetPassword);
                resetPasswordCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "all info has been modified", Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}