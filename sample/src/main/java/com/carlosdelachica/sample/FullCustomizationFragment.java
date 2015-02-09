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
import com.carlosdelachica.easyrecycleradapters.EasyRecyclerViewManager;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.adapter.ImageEasyViewHolder;
import com.carlosdelachica.sample.adapter.TextData;
import com.carlosdelachica.sample.adapter.TextDataEasyViewHolder;
import com.carlosdelachica.sample.data.DataGenerator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FullCustomizationFragment extends Fragment implements EasyRecyclerViewManager.ItemClickListener,
        EasyRecyclerViewManager.ItemLongClickListener {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.empty_list)
    TextView emptyList;

    private Handler handler;
    private EasyRecyclerViewManager easyRecyclerViewManager;

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
        initEasyRecyclerViewManager();
    }

    private void initEasyRecyclerViewManager() {
        EasyRecyclerAdapter adapter = new EasyRecyclerAdapter(getActivity());
        adapter.bind(ImageData.class, ImageEasyViewHolder.class);
        adapter.bind(TextData.class, TextDataEasyViewHolder.class);
        easyRecyclerViewManager = new EasyRecyclerViewManager.Builder(recyclerView, adapter)
                .layoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)))
                .divider(R.drawable.custom_divider)
                .emptyLoadingListTextView(emptyList)
                .loadingListText(R.string.loading)
                .loadingListTextColor(R.color.accent_material_dark)
                .emptyListText(R.string.empty_list)
                .emptyListTextColor(R.color.accent_material_dark)
                .clickListener(this)
                .longClickListener(this)
                .build();
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
                easyRecyclerViewManager.addAll(DataGenerator.generateRandomDataList(width / grid_columns));
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "painting selected " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongItemClick(int position) {
        Toast.makeText(getActivity(), "painting long selected " + position, Toast.LENGTH_LONG).show();
        return true;
    }

}
