package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.view.ViewGroup;

public interface EasyViewHolderFactory<T> {

    EasyViewHolder<T> onCreateViewHolder(int viewType, Context context, ViewGroup parent);

    int getItemViewType(int position);

}
