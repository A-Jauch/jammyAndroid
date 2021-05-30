package com.jammy.scene.category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Category;
import com.jammy.scene.thread.ThreadReceiver;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Filterable {
    public static final String ID_CATEGORY_ADAPTER = "com.jammy.scene.category.ID_CATEGORY_ADAPTER";

    Context context;
    List<Category> categoriesList;
    // for filterable
    List<Category> categoriesFullList;
    RecyclerView rvCategory;
    final View.OnClickListener onClickListener = new MyOnClickListener();


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.item_category_name);
        }
    }

    public CategoryAdapter(Context context, List<Category> categoriesList, RecyclerView rvCategory) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.rvCategory = rvCategory;
        categoriesFullList = new ArrayList<>(categoriesList);

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_category, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.rowName.setText("Name: " + category.getName());

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvCategory.getChildLayoutPosition(v);
            Category category = categoriesList.get(itemPosition);
            Intent intent = new Intent(CategoryAdapter.this.context, ThreadReceiver.class);
            intent.putExtra(ID_CATEGORY_ADAPTER, category.getId());
            context.startActivity(intent);
        }
    }

    @Override
    public Filter getFilter() {
        return categoriesFilter;
    }

    private Filter categoriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredCategoriesList = new ArrayList<>();

            if (constraint == null | constraint.length() == 0){
                filteredCategoriesList.addAll(categoriesFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Category category: categoriesFullList){
                    if (category.getName().toLowerCase().contains(filterPattern)) {
                        filteredCategoriesList.add(category);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredCategoriesList;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoriesList.clear();
            categoriesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
