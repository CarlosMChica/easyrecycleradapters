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
        ViewHolder viewHolder = inflateViewHolder(viewGroup);
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
        bindListeners(position);
        bindViewHolder(viewHolder, getItem(position));
    }

    private void bindListeners(final int position) {
        bindOnLickListener(position);
        bindOnLongClickListener(position);
    }

    private void bindOnLickListener(final int position) {
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, viewHolder.getView());
                }
            }
        });
    }

    private void bindOnLongClickListener(final int position) {
        viewHolder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener == null) {
                    return false;
                }
                return onItemLongClickListener.onLongItemClicked(position, viewHolder.getView());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
        return dataList.get(position);
    }

    public void clearItems() {
        dataList.clear();
        notifyDataSetChanged();
    }

    protected abstract ViewHolder inflateViewHolder(ViewGroup viewGroup);

    public abstract void bindViewHolder(ViewHolder viewHolder, T item);

    public interface AdapterCallback {
        public void bottomReached();
    }

    public interface OnItemClickListener {
        void onItemClick(final int position, View view);
    }

    public interface OnItemLongClickListener {
        boolean onLongItemClicked(final int position, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            view.setOnClickListener(listener);
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            view.setOnLongClickListener(listener);
        }

        public View getView() {
            return view;
        }

    }

}