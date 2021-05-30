package com.jammy.scene.thread;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Thread;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseThread;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.ThreadRoutes;
import com.jammy.routes.UserRoutes;
import com.jammy.scene.category.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreadReceiver extends AppCompatActivity {

    private List<Thread> threadList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    ThreadAdapter threadAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button newThreadBtn;
    private ThreadRoutes threadRoutes;
    private UserRoutes userRoutes;
    private int categoryId;
    public static final String ID_CATEGORY_THREAD = "com.jammy.scene.thread.ID_CATEGORY_THREAD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_receiver);
        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CategoryAdapter.ID_CATEGORY_ADAPTER,0);
        if (categoryId == 0){
            categoryId = intent.getIntExtra(CreateThreadActivity.ID_CATEGORY_THREAD,0);
        }
        getAllThreadByCategory(categoryId);
        newThreadBtn = findViewById(R.id.create_thread_btn);
        newThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreadReceiver.this, CreateThreadActivity.class);
                intent.putExtra(ID_CATEGORY_THREAD, categoryId);
               startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                threadAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getAllThreadByCategory(int categoryId){
        threadRoutes = RetrofitClientInstance.getRetrofitInstance().create(ThreadRoutes.class);
        Call<ResponseThread> getThreadByCategories = threadRoutes.findThreadByCategory(categoryId,"Bearer "+token);
        getThreadByCategories.enqueue(new Callback<ResponseThread>() {
            @Override
            public void onResponse(Call<ResponseThread> call, Response<ResponseThread> response) {
                if (response.isSuccessful()){
                    ResponseThread responseThread = response.body();
                    populateThreadAdapter(responseThread.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(ThreadReceiver.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(ThreadReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseThread> call, Throwable t) {
                Toast.makeText(ThreadReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void populateThreadAdapter(List<Thread> body) {
        for (Thread thread: body){
            threadList.add(thread);
            RecyclerView rvThread = findViewById(R.id.rvThread);
            rvThread.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvThread.setLayoutManager(layoutManager);
            threadAdapter = new ThreadAdapter(this, threadList,rvThread);
            rvThread.setAdapter(threadAdapter);
        }
    }
}