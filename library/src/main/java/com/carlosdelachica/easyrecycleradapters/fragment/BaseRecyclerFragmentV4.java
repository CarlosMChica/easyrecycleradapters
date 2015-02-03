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

import com.carlosdelachica.easyrecycleradapters.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.CommonViewHolder;
import com.carlosdelachica.easyrecycleradapters.R;
import com.carlosdelachica.easyrecycleradapters.RecyclerViewManager;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public abstract class BaseRecyclerFragmentV4<V, VH extends CommonViewHolder<V>> extends Fragment implements RecyclerViewManager.RecyclerViewManagerCallback {

    private RecyclerViewManager<V, VH> recyclerViewManager;
    private FrameLayout container;
    private TextView emptyListTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerViewManager = new RecyclerViewManager<>();
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
        recyclerViewManager.attachToRecyclerView(
                ((RecyclerView) view.findViewById(R.id.recyclerView)),
                createAdapter(),
                createLayoutManager());
        recyclerViewManager.setCallback(this);
    }

    public void setLoadingText(@StringRes int messageStringRes) {
        recyclerViewManager.setLoadingText(messageStringRes);
    }

    public void setLoadingTextColor(@ColorRes int colorRes) {
        recyclerViewManager.setLoadingTextColor(colorRes);
    }

    public void setEmptyListTextColor(@ColorRes int colorRes) {
        recyclerViewManager.setEmptyListTextColor(colorRes);
    }

    public void setEmptyListText(@StringRes int messageStringRes) {
        recyclerViewManager.setEmptyListText(messageStringRes);
    }

    public void setAuxTextViewEnabled(boolean enabled) {
        if (emptyListTextView == null) {
            emptyListTextView = (TextView) container.findViewById(R.id.empty_list);
            recyclerViewManager.attachToAuxTextView(emptyListTextView);
            recyclerViewManager.setAuxTextViewEnabled(enabled);
        }
    }

    public void setDivider(@DrawableRes int dividerDrawableRes) {
        recyclerViewManager.setDivider(dividerDrawableRes);
    }

    public RecyclerView getRecyclerView() {
        return recyclerViewManager.getRecyclerView();
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerViewManager.setRecyclerViewPadding(left, top, right, bottom);
    }

    public boolean update(V data) {
        return recyclerViewManager.update(data);
    }

    public boolean update(V data, int position) {
        return recyclerViewManager.update(data, position);
    }

    public void add(List<V> data) {
        recyclerViewManager.add(data);
    }

    public void add(V data, int position) {
        recyclerViewManager.add(data, position);
    }

    public void add(V data) {
        recyclerViewManager.add(data);
    }

    public void remove(V data) {
        recyclerViewManager.remove(data);
    }

    public void remove(int position) {
        recyclerViewManager.remove(position);
    }

    public V getItem(int position) {
        return recyclerViewManager.getItem(position);
    }

    public int getItemIndex(V item) {
        return recyclerViewManager.getItemIndex(item);
    }

    public void onRefresh() {
        recyclerViewManager.onRefresh();
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

    protected abstract CommonRecyclerAdapter<V, VH> createAdapter();

    protected abstract RecyclerView.LayoutManager createLayoutManager();

}
