package com.jammy.scene.thread;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Category;
import com.jammy.model.PostThread;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponsePostThread;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.ThreadRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateThreadActivity extends AppCompatActivity {

    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    Button sendThreadBtn;
    EditText threadNameEditText;
    private ThreadRoutes threadRoutes;
    public static final String ID_CATEGORY_THREAD = "com.jammy.scene.thread.ID_CATEGORY_THREAD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);
        sendThreadBtn = findViewById(R.id.send_thread_btn);
        threadNameEditText = findViewById(R.id.thread_name_input);
        Intent intent = getIntent();
        int categoryId = intent.getIntExtra(ThreadReceiver.ID_CATEGORY_THREAD, 0);



        sendThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(threadNameEditText.getText().toString())){
                    Category category = new Category(categoryId);
                    PostThread thread = new PostThread(threadNameEditText.getText().toString(),categoryId);
                    sendThread(thread);
                } else {
                    Toast.makeText(CreateThreadActivity.this, getString(R.string.thread_vide), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendThread(PostThread thread) {
        threadRoutes = RetrofitClientInstance.getRetrofitInstance().create(ThreadRoutes.class);
        Call<ResponsePostThread> postThread = threadRoutes.postThread(thread,token);
        postThread.enqueue(new Callback<ResponsePostThread>() {
            @Override
            public void onResponse(Call<ResponsePostThread> call, Response<ResponsePostThread> response) {
                if (response.isSuccessful()){
                    ResponsePostThread responseThread = response.body();
                    Intent intent = new Intent(CreateThreadActivity.this, ThreadReceiver.class);
                    intent.putExtra(ID_CATEGORY_THREAD, responseThread.getResults().getCategory_id());
                    startActivity(intent);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(CreateThreadActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CreateThreadActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostThread> call, Throwable t) {
                Toast.makeText(CreateThreadActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}