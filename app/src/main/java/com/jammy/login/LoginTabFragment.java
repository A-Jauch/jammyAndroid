package com.jammy.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jammy.R;

public class LoginTabFragment extends Fragment {
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
                Toast.makeText(getContext(), "login", Toast.LENGTH_SHORT).show();
            }
        });

        return root;

    }
}
