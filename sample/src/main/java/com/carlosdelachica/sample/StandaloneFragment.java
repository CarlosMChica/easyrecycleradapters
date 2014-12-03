package com.carlosdelachica.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.easyrecycleradapters.standalone.RecyclerStandalone;
import com.carlosdelachica.sample.adapter.ImageAdapter;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.data.DataGenerator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StandaloneFragment extends Fragment implements RecyclerStandalone.RecyclerFragmentStandaloneCallback {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerStandalone<ImageData> standalone = new RecyclerStandalone<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.standalone_fragment, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStandalone();
        standalone.updateItems(DataGenerator.generateRandomData());
    }

    private void initStandalone() {
        standalone.attachToRecyclerView(recyclerView,
                new ImageAdapter(getActivity()),
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)));
        standalone.setCallback(this);
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
