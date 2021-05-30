package com.jammy.scene.thread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jammy.R;
import com.jammy.model.Thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Thread> threadList;

    List<Thread> threadFullList;
    RecyclerView rvThread;
    final View.OnClickListener onClickListener = new MyOnClickListener();




    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName, rowCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.item_thread_name);
            rowCategory = itemView.findViewById(R.id.item_thread_category);
        }
    }

    public ThreadAdapter(Context context, List<Thread> threadList, RecyclerView rvThread) {
        this.context = context;
        this.threadList = threadList;
        this.rvThread = rvThread;
        threadFullList = new ArrayList<>(threadList);
    }


    @NonNull
    @Override
    public ThreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_thread, parent, false);
        view.setOnClickListener(onClickListener);
        ThreadAdapter.ViewHolder viewHolder = new ThreadAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ThreadAdapter.ViewHolder holder, int position) {
        Thread thread = threadList.get(position);
        holder.rowName.setText("Name: " + thread.getName());
        holder.rowCategory.setText("Category: " + thread.getCategory().getName());
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public Filter getFilter() {
        return threadFilter;
    }

    private Filter threadFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Thread> filteredThreadList = new ArrayList<>();

            if (constraint == null | constraint.length() == 0){
                filteredThreadList.addAll(threadFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Thread thread: threadFullList){
                    if (thread.getName().toLowerCase().contains(filterPattern)) {
                        filteredThreadList.add(thread);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredThreadList;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            threadList.clear();
            threadList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
