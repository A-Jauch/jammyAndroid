package com.jammy.scene.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.model.Instrument;
import com.jammy.model.User;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseInstrument;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.InstrumentRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.login.LoginActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment  extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText firstname_edit_text, lastname_edit_text, birthdate_edit_text, password_edit_text, email_edit_text;
    Button signup_btn;
    Spinner instrumentSpinner;
    Instrument instrumentForSub = new Instrument();
    private List<String> instrumentStringList = new ArrayList<String>();
    private List<Instrument> instrumentList = new ArrayList<Instrument>();
    float v = 0;
    UserRoutes userRoutes;
    InstrumentRoutes instrumentRoutes;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        email_edit_text = root.findViewById(R.id.signup_email);
        password_edit_text = root.findViewById(R.id.signup_password);
        firstname_edit_text = root.findViewById(R.id.signup_firstname);
        lastname_edit_text = root.findViewById(R.id.signup_lastname);
        birthdate_edit_text = root.findViewById(R.id.signup_birthdate);
        instrumentSpinner = root.findViewById(R.id.spinner_instrument);
        instrumentSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
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
        instrumentSpinner.setTranslationX(800);

        email_edit_text.setAlpha(v);
        password_edit_text.setAlpha(v);
        firstname_edit_text.setAlpha(v);
        lastname_edit_text.setAlpha(v);
        birthdate_edit_text.setAlpha(v);
        signup_btn.setAlpha(v);
        instrumentSpinner.setAlpha(v);

        email_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        firstname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        lastname_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        birthdate_edit_text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        instrumentSpinner.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        signup_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        getInstrument();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_date = birthdate_edit_text.getText().toString();
                if (TextUtils.isEmpty(str_date)){
                    Toast.makeText(getContext(), "La date ne peut pas être vide", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                Date birthday = null;
                try {
                    birthday = new Date(simpleDateFormat.parse(str_date).getTime()) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if ( birthday.compareTo(calendar.getTime()) > 0 ){
                    Toast.makeText(getContext(), "La date ne peut pas être superieur a celle d'aujourd'hui", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = new User(email_edit_text.getText().toString(),
                        password_edit_text.getText().toString().trim(),
                        firstname_edit_text.getText().toString().trim(),
                        lastname_edit_text.getText().toString().trim(),
                        birthday,
                        instrumentForSub.getId()
                );

                userRoutes = RetrofitClientInstance.getRetrofitInstance().create(UserRoutes.class);
                Call<User> loginUser = userRoutes.signup(user);
                loginUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if (response.isSuccessful()){
                            User user = response.body();
                            //Toast.makeText(getContext(), user.getName().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Votre utilisateur a bien été creer", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else if (!response.isSuccessful()){
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_instrument:
                String name =  parent.getSelectedItem().toString();

                for (Instrument instrument: instrumentList){
                    if (name.equalsIgnoreCase(instrument.getName())){
                        instrumentForSub.setId(instrument.getId());
                        return;
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getInstrument(){
        instrumentRoutes = RetrofitClientInstance.getRetrofitInstance().create(InstrumentRoutes.class);
        Call<ResponseInstrument> getAllInstrument = instrumentRoutes.findAllIndstrument();
        ArrayAdapter<String> instrumentArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, instrumentStringList);


        getAllInstrument.enqueue(new Callback<ResponseInstrument>() {
            @Override
            public void onResponse(Call<ResponseInstrument> call, Response<ResponseInstrument> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseInstrument instrumentResponse = response.body();
                for (Instrument instrument : instrumentResponse.getResults()){
                    String name = instrument.getName();
                    instrumentStringList.add(name);
                    instrumentList.add(instrument);
                    instrumentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    instrumentSpinner.setAdapter(instrumentArrayAdapter);
                }
            }
            @Override
            public void onFailure(Call<ResponseInstrument> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
