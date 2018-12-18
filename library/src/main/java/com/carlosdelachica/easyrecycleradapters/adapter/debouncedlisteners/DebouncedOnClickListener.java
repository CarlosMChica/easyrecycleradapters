package com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners;

import android.view.View;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;

public abstract class DebouncedOnClickListener
        implements EasyViewHolder.OnItemClickListener, DebouncedListener {
    private final DebouncedClickHandler debouncedClickHandler;

    protected DebouncedOnClickListener() {
        this.debouncedClickHandler = new DebouncedClickHandler(this);
    }

    @Override
    public void onItemClick(int position, View view) {
        debouncedClickHandler.invokeDebouncedClick(position, view);
    }

}



