package com.jammy.scene.category;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Category;
import com.jammy.responseModel.ResponseCategory;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.CategoryRoutes;
import com.jammy.routes.UserRoutes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryReceiver extends AppCompatActivity {

    private List<Category> categoriesList = new ArrayList<>();
    FileManager fileManager = new FileManager();
    String token = fileManager.readFile("token.txt").trim();
    CategoryAdapter categoryAdapter;
    RecyclerView.LayoutManager layoutManager;
    private CategoryRoutes categoryRoutes;
    private UserRoutes userRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_receiver);
        getAllCategories();

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
                categoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getAllCategories(){
        categoryRoutes = RetrofitClientInstance.getRetrofitInstance().create(CategoryRoutes.class);
        Call <ResponseCategory> getAllCategories = categoryRoutes.getAllCategories("Bearer "+token);
        getAllCategories.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
                if (response.isSuccessful()){
                    ResponseCategory responseCategory = response.body();
                    populateCategoryAdapter(responseCategory.getResults());
                } else {
                    Toast.makeText(CategoryReceiver.this, response.code(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(CategoryReceiver.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {
                Toast.makeText(CategoryReceiver.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateCategoryAdapter(List<Category> body) {
        for (Category category: body){
            categoriesList.add(category);
            RecyclerView rvCategory = findViewById(R.id.rvCategory);
            rvCategory.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvCategory.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(this, categoriesList,rvCategory);
            rvCategory.setAdapter(categoryAdapter);
        }
    }


}