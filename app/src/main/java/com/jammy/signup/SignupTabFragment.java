package com.jammy.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.jammy.R;
import com.jammy.login.LoginActivity;

public class SignupTabFragment  extends Fragment {
    EditText firstname_edit_text, lastname_edit_text, birthdate_edit_text, password_edit_text, email_edit_text, userRole_edit_text;
    Button signup_btn;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        email_edit_text = root.findViewById(R.id.signup_email);
        password_edit_text = root.findViewById(R.id.signup_password);
        firstname_edit_text = root.findViewById(R.id.signup_firstname);
        lastname_edit_text = root.findViewById(R.id.signup_lastname);
        birthdate_edit_text = root.findViewById(R.id.signup_birthdate);
        userRole_edit_text = root.findViewById(R.id.signup_user_role);
        signup_btn = root.findViewById(R.id.signup_btn);

        email_edit_text.setTranslationX(800);
        password_edit_text.setTranslationX(800);
        firstname_edit_text.setTranslationX(800);
        lastname_edit_text.setTranslationX(800);
        birthdate_edit_text.setTranslationX(800);
        userRole_edit_text.setTranslationX(800);
        signup_btn.setTranslationX(800);

        email_edit_text.setAlpha(v);
        password_edit_text.setAlpha(v);
        firstname_edit_text.setAlpha(v);
        lastname_edit_text.setAlpha(v);
        birthdate_edit_text.setAlpha(v);
        userRole_edit_text.setAlpha(v);
        signup_btn.setAlpha(v);

        email_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        firstname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        lastname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        birthdate_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        userRole_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        signup_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "signup", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
