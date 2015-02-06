package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.view.ViewGroup;

interface IEasyViewHolderFactory<T> {
    EasyViewHolder<T> onCreateViewHolder(int viewType, Context context, ViewGroup parent);
    void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder);
    int getItemViewType(int position, Object o);
}
