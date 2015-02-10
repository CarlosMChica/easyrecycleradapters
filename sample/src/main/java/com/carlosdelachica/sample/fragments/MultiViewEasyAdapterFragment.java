package com.carlosdelachica.sample.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.view_holders.ImageEasyViewHolder;
import com.carlosdelachica.sample.adapter.view_holders.TextDataEasyViewHolder;
import com.carlosdelachica.sample.data.DataGenerator;
import com.carlosdelachica.sample.data.ImageData;
import com.carlosdelachica.sample.data.TextData;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemLongClickListener;

public class MultiViewEasyAdapterFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private EasyRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.simple_recycler_view_layout, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        adapter = new EasyRecyclerAdapter(getActivity());
        adapter.bind(ImageData.class, ImageEasyViewHolder.class);
        adapter.bind(TextData.class, TextDataEasyViewHolder.class);
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    }

    private void initData() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int grid_columns = getResources().getInteger(R.integer.grid_columns);
        adapter.addAll(DataGenerator.generateRandomDataList(width / grid_columns));
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        Toast.makeText(getActivity(), "item long selected " + position, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(), "item selected " + position, Toast.LENGTH_SHORT).show();
    }

}
