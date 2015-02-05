package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class EasyViewHolder<V> extends RecyclerView.ViewHolder {

    public EasyViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    public abstract void bindTo(V value);
    
}
