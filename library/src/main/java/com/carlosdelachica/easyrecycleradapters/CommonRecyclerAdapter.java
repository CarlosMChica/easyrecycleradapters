package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public abstract class CommonRecyclerAdapter<V, VH extends CommonViewHolder<V>> extends RecyclerView.Adapter<VH> {

    protected Context context;

    private CommonViewHolder.OnItemClickListener onItemClickListener;
    private CommonViewHolder.OnItemLongClickListener onItemLongClickListener;
    private final List<V> dataList;
    private VH viewHolder;

    public CommonRecyclerAdapter(Context context) {
        this(new ArrayList<V>(), context);
    }

    public CommonRecyclerAdapter(List<V> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    void setOnItemClickListener(CommonViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void setOnItemLongClickListener(CommonViewHolder.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return inflateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH viewHolder, final int position) {
        this.viewHolder = viewHolder;
        bindListeners();
        viewHolder.bindTo(dataList.get(position));
    }

    private void bindListeners() {
        bindOnClickListener();
        bindOnLongClickListener();
    }

    private void bindOnClickListener() {
        viewHolder.setOnClickListener(onItemClickListener);
    }

    private void bindOnLongClickListener() {
        viewHolder.setOnLongClickListener(onItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public boolean update(V data) {
        int indexOfData = dataList.indexOf(data);
        if (indexOfData == -1) return false;
        return update(data, indexOfData);
    }

    public boolean update(V data, int position) {
        V oldData = dataList.set(position, data);
        notifyItemChanged(position);
        return oldData != null;
    }

    public void add(List<V> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void add(V item) {
        dataList.add(item);
        notifyItemInserted(dataList.indexOf(item));
    }

    public void add(V item, int position) {
        dataList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(V data) {
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

    public V getItem(int position) {
        if (position < 0 || position >= dataList.size()) return null;
        return dataList.get(position);
    }

    public int getItemIndex(V item) {
        return dataList.indexOf(item);
    }

    public void clearItems() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public abstract VH inflateViewHolder(ViewGroup parent, int viewType);

}