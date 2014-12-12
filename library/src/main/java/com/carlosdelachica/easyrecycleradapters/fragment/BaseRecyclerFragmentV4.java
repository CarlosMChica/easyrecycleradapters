package com.carlosdelachica.easyrecycleradapters.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.R;
import com.carlosdelachica.easyrecycleradapters.RecyclerStandalone;
import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;

import java.util.List;

public abstract class BaseRecyclerFragmentV4<T> extends Fragment implements RecyclerStandalone.RecyclerStandaloneCallback {

    private RecyclerStandalone<T> recyclerStandalone;
    private FrameLayout container;
    private TextView emptyListTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerStandalone = new RecyclerStandalone<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (FrameLayout) view.findViewById(R.id.recycler_view_container);
        initStandalone(view);
    }

    private void initStandalone(View view) {
        recyclerStandalone.attachToRecyclerView(
                ((RecyclerView) view.findViewById(R.id.recyclerView)),
                createAdapter(),
                createLayoutManager());
        recyclerStandalone.setCallback(this);
    }

    public void setEmptyListText(@StringRes int messageStringRes) {
        initEmptyListTextView();
        recyclerStandalone.setEmptyListText(messageStringRes);
    }

    public void setEmptyListTextColor(@ColorRes int colorRes) {
        initEmptyListTextView();
        recyclerStandalone.setEmptyListTextColor(colorRes);
    }

    private void initEmptyListTextView() {
        if (emptyListTextView == null) {
            emptyListTextView = (TextView) container.findViewById(R.id.empty_list);
            recyclerStandalone.attachToEmptyList(emptyListTextView);
        }
    }

    public void setDivider(@DrawableRes int dividerDrawableRes) {
        recyclerStandalone.setDivider(dividerDrawableRes);
    }

    public RecyclerView getRecyclerView() {
        return recyclerStandalone.getRecyclerView();
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerStandalone.setRecyclerViewPadding(left, top, right, bottom);
    }

    public T getItem(int position) {
        return recyclerStandalone.getItem(position);
    }

    public void updateItems(List<T> data) {
        recyclerStandalone.updateItems(data);
    }

    public void addItem(T data) {
        recyclerStandalone.addItem(data);
    }

    public void removeItem (T data) {
        recyclerStandalone.removeItem(data);
    }

    public void removeItem (int position) {
        recyclerStandalone.removeItem(position);
    }

    public void onRefresh() {
        recyclerStandalone.onRefresh();
    }

    @Override
    public void onItemClick(int position, View view) {
        //Empty Impl
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        //Empty Impl
        return false;
    }

    protected abstract CommonRecyclerAdapter<T> createAdapter();

    protected abstract RecyclerView.LayoutManager createLayoutManager();

}
