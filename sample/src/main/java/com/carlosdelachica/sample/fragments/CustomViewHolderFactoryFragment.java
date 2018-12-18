package com.carlosdelachica.sample.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.view_holder_factories.CustomViewHolderFactory;
import com.carlosdelachica.sample.adapter.view_holders.ImageEasyViewHolder;
import com.carlosdelachica.sample.data.DataGenerator;
import com.carlosdelachica.sample.data.ImageData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemLongClickListener;

public class CustomViewHolderFactoryFragment extends Fragment implements OnItemLongClickListener,
        OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private EasyRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.simple_recycler_view_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        adapter = new EasyRecyclerAdapter(
                new CustomViewHolderFactory(getActivity()),
                ImageData.class,
                ImageEasyViewHolder.class);
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL));
    }

    private void initData() {
        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int grid_columns = getResources().getInteger(R.integer.grid_columns);
        adapter.addAll(DataGenerator.generateRandomImageDataList(width / grid_columns));
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
