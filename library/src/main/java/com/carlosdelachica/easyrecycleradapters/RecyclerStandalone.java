package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

public class RecyclerStandalone<T> implements CommonRecyclerAdapter.OnItemClickListener,
        CommonRecyclerAdapter.OnItemLongClickListener {

    private RecyclerView recyclerView;
    private TextView emptyListTextView;
    private CommonRecyclerAdapter<T> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private RecyclerStandaloneCallback callback;
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

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        initRecyclerViewPadding();
        initAdapter();
        initLayoutManager();
        initItemDecorations();
    }

    private void initRecyclerViewPadding() {
        //Apply padding as described in Material Design specs
        int topBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.material_design_list_top_bottom_padding);
        setRecyclerViewPadding(0, topBottomPadding, 0, topBottomPadding);
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
        initDivider();
    }

    private void initDivider() {
        int dividerRes;
        if (layoutManager instanceof GridLayoutManager) {
            dividerRes = R.drawable.grid_divider;
        } else {
            dividerRes = R.drawable.list_divider;
        }
        this.dividerItemDecoration = new DividerItemDecoration(context, context.getResources().getDrawable(dividerRes));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void setDivider(@DrawableRes int dividerDrawableRes) {
        dividerItemDecoration.setDivider(dividerDrawableRes);
    }

    public void setEmptyListTextColor(@ColorRes int colorRes) {
        if (emptyListTextView != null) {
            emptyListTextView.setTextColor(context.getResources().getColor(colorRes));
        }
    }

    public void setEmptyListText(@StringRes int messageStringRes) {
        if (emptyListTextView != null) {
            emptyListTextView.setText(messageStringRes);
        }
    }

    public void attachToEmptyList(TextView emptyListTextView) {
        this.emptyListTextView = emptyListTextView;
    }

    public void setCallback(RecyclerStandaloneCallback callback) {
        this.callback = callback;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerView.setPadding(left, top, right, bottom);
    }

    public T getItem(int position) {
        return adapter.getItem(position);
    }

    public void updateItems(List<T> data) {
        setEmptyListVisible(false);
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
        setEmptyListVisible(true);
        adapter.clearItems();
    }

    private void setEmptyListVisible(boolean visible) {
        if (emptyListTextView != null) {
            emptyListTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
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

    public interface RecyclerStandaloneCallback {
        void onItemClick(int position, View view);
        boolean onLongItemClicked(int position, View view);
    }

}
