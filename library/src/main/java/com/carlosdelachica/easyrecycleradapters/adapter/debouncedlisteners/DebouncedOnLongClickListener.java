package com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners;

import android.view.View;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;

public abstract class DebouncedOnLongClickListener
        implements DebouncedListener, EasyViewHolder.OnItemLongClickListener {

    private final DebouncedClickHandler debouncedClickHandler;

    protected DebouncedOnLongClickListener() {
        this.debouncedClickHandler = new DebouncedClickHandler(this);
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        return debouncedClickHandler.invokeDebouncedClick(position, view);
    }

}



