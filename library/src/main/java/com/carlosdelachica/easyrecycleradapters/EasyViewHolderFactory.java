package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.view.ViewGroup;

public interface EasyViewHolderFactory<T> {

    public EasyViewHolder<T> createViewHolder(T object, Context context, ViewGroup parent);
    
}
