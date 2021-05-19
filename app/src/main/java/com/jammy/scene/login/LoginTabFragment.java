package com.jammy.scene.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jammy.R;
import com.jammy.scene.dashboard.DashboardActivity;
import com.jammy.fileManager.FileManager;
import com.jammy.responseModel.ResponseLogin;
import com.jammy.model.User;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginTabFragment extends Fragment {
    UserRoutes userRoutes;
    EditText emai_edit_text, password_edit_text;
    TextView fortgetPassword_text;
    Button login_btn;
    float v = 0;
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        emai_edit_text = root.findViewById(R.id.login_email);
        password_edit_text = root.findViewById(R.id.login_password);
        fortgetPassword_text = root.findViewById(R.id.forgetPassword_text);
        login_btn = root.findViewById(R.id.login_btn);

        emai_edit_text.setTranslationX(800);
        password_edit_text.setTranslationX(800);
        fortgetPassword_text.setTranslationX(800);
        login_btn.setTranslationX(800);

        emai_edit_text.setAlpha(v);
        password_edit_text.setAlpha(v);
        fortgetPassword_text.setAlpha(v);
        login_btn.setAlpha(v);

        emai_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        fortgetPassword_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(emai_edit_text.getText().toString(),password_edit_text.getText().toString());
             //   user.setEmail(emai_edit_text.getText().toString());
             //   user.setPassword(password_edit_text.getText().toString());
              userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<ResponseLogin> loginUser = userRoutes.login(user);
                loginUser.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                        if (response.isSuccessful()){
                            ResponseLogin responseLogin1 = response.body();
                            //Toast.makeText(getContext(), responseLogin1.getAccessToken().toString(), Toast.LENGTH_SHORT).show();
                            FileManager fileManager = new FileManager();
                            fileManager.writeFile(responseLogin1.getAccessToken(), "token.txt");
                          //  String token = fileManager.readFile("token.txt");
                          //  Toast.makeText(getContext(), token.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return root;

    }
}
