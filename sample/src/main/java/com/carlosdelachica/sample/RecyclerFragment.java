package com.carlosdelachica.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.fragment.BaseRecyclerFragment;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.adapter.ImageForegroundSelectorAdapter;
import com.carlosdelachica.sample.data.DataGenerator;

public class RecyclerFragment extends BaseRecyclerFragment<ImageData> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDivider(getResources().getDrawable(R.drawable.custom_divider));
        updateItems(DataGenerator.generateRandomData());
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

}
