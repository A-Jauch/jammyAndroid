package com.jammy.scene.chat;

import androidx.recyclerview.widget.DiffUtil;

import com.jammy.model.Message;

import java.util.List;

public class MyDiffUtilCallBack extends DiffUtil.Callback {



    private List<Message> oldList;
    private List<Message> newList;

    public MyDiffUtilCallBack(List<Message> oldList, List<Message> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
