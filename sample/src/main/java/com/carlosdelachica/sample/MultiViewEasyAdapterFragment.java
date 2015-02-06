package com.carlosdelachica.sample;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.adapter.ImageEasyViewHolder;
import com.carlosdelachica.sample.adapter.TextData;
import com.carlosdelachica.sample.adapter.TextDataEasyViewHolder;
import com.carlosdelachica.sample.data.DataGenerator;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemLongClickListener;

public class MultiViewEasyAdapterFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.empty_list)
    TextView emptyList;

    private Handler handler = new Handler();
    private EasyRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.standalone_fragment, container, false);
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
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int grid_columns = getResources().getInteger(R.integer.grid_columns);
                adapter.addAll(DataGenerator.generateRandomDataList(width / grid_columns));
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
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
