package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class CommonViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;

    public CommonViewHolder(View view) {
        super(view);
        this.context = view.getContext();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener == null) return;
        itemClickListener.onItemClick(getPosition(), v);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongClick(View v) {
        if (longClickListener == null) return false;
        return longClickListener.onLongItemClicked(getPosition(), v);
    }

    void setOnClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    void setOnLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
        itemView.setOnLongClickListener(this);
    }

    protected abstract void bindTo(V data);

    interface OnItemClickListener {
        void onItemClick(final int position, View view);
    }

    interface OnItemLongClickListener {
        boolean onLongItemClicked(final int position, View view);
    }

}
