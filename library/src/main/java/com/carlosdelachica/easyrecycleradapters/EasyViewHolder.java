package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.OnClickListener;
import static android.view.View.OnLongClickListener;

public abstract class EasyViewHolder<V> extends RecyclerView.ViewHolder implements OnLongClickListener,
        OnClickListener {

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;

    public EasyViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        bindListeners();
    }

    void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    private void bindListeners() {
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongClick(View v) {
        if (longClickListener == null) { return false; }
        return longClickListener.onLongItemClicked(getPosition(), itemView);
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener == null) return;
        itemClickListener.onItemClick(getPosition(), v);
    }

    public abstract void bindTo(V value);

    public interface OnItemClickListener {
        void onItemClick(final int position, View view);
    }

    public interface OnItemLongClickListener {
        boolean onLongItemClicked(final int position, View view);
    }

}
