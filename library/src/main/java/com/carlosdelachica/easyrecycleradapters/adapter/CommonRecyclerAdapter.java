package com.carlosdelachica.easyrecycleradapters.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.carlosdelachica.easyrecycleradapters.R;
import com.carlosdelachica.easyrecycleradapters.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerAdapter.ViewHolder> {

    protected Context context;
    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;
    private AdapterCallback bottomReachedCallback;
    private final List<T> dataList;
    private ViewHolder viewHolder;

    public CommonRecyclerAdapter(Context context) {
        this(new ArrayList<T>(), context);
    }

    public CommonRecyclerAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setBottomReachedCallback(AdapterCallback callback) {
        this.bottomReachedCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        ViewHolder viewHolder = inflateViewHolder(viewGroup, getItemViewType(position));
        applySelector(viewHolder.getView());
        return viewHolder;
    }

    private void applySelector(View view) {
        Drawable selectorDrawable = generateSelectorDrawable();
        if (view instanceof FrameLayout) {
            ((FrameLayout) view).setForeground(selectorDrawable);
        } else {
            ViewUtils.setBackground(view, selectorDrawable);
        }
    }

    private Drawable generateSelectorDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(context, R.drawable.default_selector);
        } else {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.selectorDrawable, typedValue, true);
            return ContextCompat.getDrawable(context, typedValue.resourceId);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        this.viewHolder = viewHolder;
        bindListeners();
        bindViewHolder(viewHolder, getItem(position), position);
    }

    private void bindListeners() {
        bindOnLickListener();
        bindOnLongClickListener();
    }

    private void bindOnLickListener() {
        viewHolder.setOnClickListener(onItemClickListener);
    }

    private void bindOnLongClickListener() {
        viewHolder.setOnLongClickListener(onItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public boolean updateItem(T data) {
        int indexOfData = dataList.indexOf(data);
        if (indexOfData == -1) return false;
        return updateItem(data, indexOfData);
    }

    public boolean updateItem(T data, int position) {
        T oldData = dataList.set(position, data);
        notifyItemChanged(position);
        return oldData != null;
    }

    public void updateItems(List<T> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void add(T item) {
        dataList.add(item);
        notifyItemInserted(dataList.indexOf(item));
    }

    public void add(T item, int position) {
        dataList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(T data) {
        if (dataList.contains(data)) {
            remove(dataList.indexOf(data));
        }
    }

    public void remove(int position) {
        if (position >= 0 && position < getItemCount()) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getItem(int position) {
        if (position >= dataList.size()) {
            if (bottomReachedCallback != null) {
                bottomReachedCallback.bottomReached();
            }
        }
        if (position < 0 || position >= dataList.size()) return null;
        return dataList.get(position);
    }

    public int getItemIndex(T item) {
        return dataList.indexOf(item);
    }

    public void clearItems() {
        dataList.clear();
        notifyDataSetChanged();
    }

    protected abstract ViewHolder inflateViewHolder(ViewGroup viewGroup, int viewType);

    public abstract void bindViewHolder(ViewHolder viewHolder, T item, int position);

    public interface AdapterCallback {
        public void bottomReached();
    }

    public interface OnItemClickListener {
        void onItemClick(final int position, View view);
    }

    public interface OnItemLongClickListener {
        boolean onLongItemClicked(final int position, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        private View view;
        private OnItemClickListener itemClickListener;
        private OnItemLongClickListener longClickListener;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setOnClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
            view.setOnClickListener(this);
        }

        public void setOnLongClickListener(OnItemLongClickListener listener) {
            this.longClickListener = listener;
            view.setOnLongClickListener(this);
        }

        public View getView() {
            return view;
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getPosition(), v);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null) {
                return longClickListener.onLongItemClicked(getPosition(), v);
            }
            return false;
        }
    }

}