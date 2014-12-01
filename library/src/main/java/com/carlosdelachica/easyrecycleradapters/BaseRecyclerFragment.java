package com.carlosdelachica.easyrecycleradapters;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

public abstract class BaseRecyclerFragment<T> extends Fragment implements CommonRecyclerAdapter.OnItemClickListener,
        CommonRecyclerAdapter.OnItemLongClickListener, RecyclerStandalone.RecyclerFragmentStandaloneCallback {

    private RecyclerStandalone<T> recyclerStandalone;

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
        initStandalone(view);
    }

    private void initStandalone(View view) {
        recyclerStandalone.attachToRecyclerView(
                ((RecyclerView) view.findViewById(R.id.recyclerView)),
                createAdapter(),
                createLayoutManager());
        recyclerStandalone.setCallback(this);
    }

    private void setDivider(Drawable divider) {
        recyclerStandalone.setDivider(new DividerItemDecoration(getActivity(), divider));
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
