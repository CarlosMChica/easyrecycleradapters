package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

public class RecyclerStandalone<T> implements CommonRecyclerAdapter.OnItemClickListener,
        CommonRecyclerAdapter.OnItemLongClickListener {

    private RecyclerView recyclerView;
    private CommonRecyclerAdapter<T> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private RecyclerFragmentStandaloneCallback callback;
    private DividerItemDecoration dividerItemDecoration;

    public void attachToRecyclerView(RecyclerView recyclerView, CommonRecyclerAdapter<T> adapter) {
        this.attachToRecyclerView(recyclerView, adapter, new LinearLayoutManager(recyclerView.getContext()));
    }

    public void attachToRecyclerView(RecyclerView recyclerView, CommonRecyclerAdapter<T> adapter, RecyclerView.LayoutManager layoutManager) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.context = recyclerView.getContext();
        initRecyclerView();
    }

    public void setCallback(RecyclerFragmentStandaloneCallback callback) {
        this.callback = callback;
    }

    public void setDivider(DividerItemDecoration dividerItemDecoration) {
        recyclerView.removeItemDecoration(this.dividerItemDecoration);
        initDivider(dividerItemDecoration);
    }

    private void initDivider(DividerItemDecoration dividerItemDecoration) {
        this.dividerItemDecoration = dividerItemDecoration;
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        initAdapter();
        initLayoutManager();
        initItemDecorations();
    }

    private void initAdapter() {
        recyclerView.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    private void initLayoutManager() {
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initItemDecorations() {
        int dividerRes;
        if (layoutManager instanceof GridLayoutManager) {
            dividerRes = R.drawable.grid_divider;
        } else {
            dividerRes = R.drawable.list_divider;
        }
        initDivider(new DividerItemDecoration(context, context.getResources().getDrawable(dividerRes)));
    }

    public void updateItems(List<T> data) {
        adapter.updateItems(data);
    }

    public void addItem(T data) {
        adapter.add(data);
    }

    public void removeItem (T data) {
        adapter.remove(data);
    }

    public void removeItem (int position) {
        adapter.remove(position);
    }

    public void onRefresh() {
        adapter.clearItems();
    }

    @Override
    public void onItemClick(int position, View view) {
        if (callback != null) {
            callback.onItemClick(position, view);
        }
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        if (callback != null) {
            return callback.onLongItemClicked(position, view);
        }
        return false;
    }

    public interface RecyclerFragmentStandaloneCallback {
        void onItemClick(int position, View view);
        boolean onLongItemClicked(int position, View view);
    }

}
