package com.carlosdelachica.sample;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.fragment.BaseRecyclerFragmentV4;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.adapter.ImageForegroundSelectorAdapter;
import com.carlosdelachica.sample.data.DataGenerator;

public class RecyclerFragment extends BaseRecyclerFragmentV4<ImageData> {

    private Handler handler;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        setDivider(R.drawable.custom_divider);
        initEmptyList();
        int customPadding = getResources().getDimensionPixelSize(R.dimen.custom_padding);
        setRecyclerViewPadding(customPadding, customPadding, customPadding, customPadding);
    }

    private void initEmptyList() {
        setLoadingText(R.string.loading);
        setEmptyListText(R.string.empty_list);
        setLoadingTextColor(R.color.accent_material_dark);
        setAuxTextViewEnabled(true);
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
                add(DataGenerator.generateRandomData(width / grid_columns, width / grid_columns * 2));
            }
        }, 2000);
    }

    @Override
    protected CommonRecyclerAdapter<ImageData> createAdapter() {
        return new ImageForegroundSelectorAdapter(getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns));
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        Toast.makeText(getActivity(), "painting long selected " + position, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(), "painting selected " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

}
