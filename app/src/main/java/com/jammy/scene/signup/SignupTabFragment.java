package com.jammy.scene.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.login.LoginActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupTabFragment  extends Fragment {
    EditText firstname_edit_text, lastname_edit_text, birthdate_edit_text, password_edit_text, email_edit_text;
    Button signup_btn;
    float v = 0;
    UserRoutes userRoutes;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        email_edit_text = root.findViewById(R.id.signup_email);
        password_edit_text = root.findViewById(R.id.signup_password);
        firstname_edit_text = root.findViewById(R.id.signup_firstname);
        lastname_edit_text = root.findViewById(R.id.signup_lastname);
        birthdate_edit_text = root.findViewById(R.id.signup_birthdate);
        signup_btn = root.findViewById(R.id.signup_btn);
        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        email_edit_text.setTranslationX(800);
        password_edit_text.setTranslationX(800);
        firstname_edit_text.setTranslationX(800);
        lastname_edit_text.setTranslationX(800);
        birthdate_edit_text.setTranslationX(800);
        signup_btn.setTranslationX(800);

        email_edit_text.setAlpha(v);
        password_edit_text.setAlpha(v);
        firstname_edit_text.setAlpha(v);
        lastname_edit_text.setAlpha(v);
        birthdate_edit_text.setAlpha(v);
        signup_btn.setAlpha(v);

        email_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        firstname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        lastname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        birthdate_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        signup_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_date = birthdate_edit_text.getText().toString();
                if (TextUtils.isEmpty(str_date)){
                    Toast.makeText(getContext(), "La date ne peut pas être vide", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date birthday = null;
                try {
                    birthday = new Date(simpleDateFormat.parse(str_date).getTime()) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if ( birthday.compareTo(calendar.getTime()) > 0 ){
                    Toast.makeText(getContext(), "La date ne peut pas être superieur a celle d'ajd", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = new User(email_edit_text.getText().toString(),
                        password_edit_text.getText().toString().trim(),
                        firstname_edit_text.getText().toString().trim(),
                        lastname_edit_text.getText().toString().trim(),
                        birthday
                        );

                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<User> loginUser = userRoutes.signup(user);
                loginUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if (response.isSuccessful()){
                            User user = response.body();
                            //Toast.makeText(getContext(), user.getName().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        } else if ( !response.isSuccessful()){
                            Gson gson = new Gson();
                            ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                            Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return root;
    }
}
