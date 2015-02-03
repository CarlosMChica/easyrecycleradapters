package com.carlosdelachica.sample;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.RecyclerStandalone;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageBackgroundSelectorAdapter;
import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.data.DataGenerator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StandaloneFragment extends Fragment implements RecyclerStandalone.RecyclerStandaloneCallback {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.empty_list)
    TextView emptyList;

    private RecyclerStandalone<ImageData> standalone = new RecyclerStandalone<>();
    private Handler handler;

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
        initEmptyList();
        initData();
    }

    private void initStandalone() {
        standalone.attachToRecyclerView(recyclerView,
                new ImageBackgroundSelectorAdapter(getActivity()));
        standalone.setCallback(this);
    }

    private void initEmptyList() {
        standalone.attachToAuxTextView(emptyList);
        standalone.setLoadingText(R.string.loading);
        standalone.setLoadingTextColor(R.color.accentColor);
        standalone.setAuxTextViewEnabled(true);
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
                standalone.add(DataGenerator.generateRandomData(width, width / 2));
            }
        }, 2000);
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
