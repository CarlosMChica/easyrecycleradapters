package com.carlosdelachica.easyrecycleradapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import carlosdelachica.com.easyrecycleradapters.R;

public abstract class BaseRecyclerFragment<T> extends Fragment implements CommonRecyclerAdapter.OnItemClickListener,
        CommonRecyclerAdapter.OnItemLongClickListener {

    RecyclerView recyclerView;

    private CommonRecyclerAdapter<T> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_recycler_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        initItemDecorations();
        initAdapter();
        initLayoutManager();
        initDivider();
    }

    private void initItemDecorations() {
        addRecyclerViewDivider();
    }

    private void addRecyclerViewDivider() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void initAdapter() {
        adapter = createAdapter();
        recyclerView.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    private void initLayoutManager() {
        recyclerView.setLayoutManager(createLayoutManager());
    }

    private void initDivider() {
        DividerItemDecoration dividerItemDecoration = getDivider();
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    protected DividerItemDecoration getDivider() {
        return new DividerItemDecoration(getActivity());
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
