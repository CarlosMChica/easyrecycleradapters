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

import com.carlosdelachica.easyrecycleradapters.RecyclerViewManager;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.ImageBackgroundSelectorAdapter;
import com.carlosdelachica.sample.adapter.ImageBackgroundSelectorViewHolder;
import com.carlosdelachica.sample.adapter.ImageData;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewManagerFragment extends Fragment implements RecyclerViewManager.RecyclerViewManagerCallback {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.empty_list)
    TextView emptyList;

    private RecyclerViewManager<ImageData, ImageBackgroundSelectorViewHolder> recyclerViewManager = new RecyclerViewManager<>();
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.simple_recycler_view_layout, container, false);
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
        recyclerViewManager.attachToRecyclerView(recyclerView,
                new ImageBackgroundSelectorAdapter(getActivity()));
        recyclerViewManager.setCallback(this);
    }

    private void initEmptyList() {
        recyclerViewManager.attachToAuxTextView(emptyList);
        recyclerViewManager.setLoadingText(R.string.loading);
        recyclerViewManager.setLoadingTextColor(R.color.accentColor);
        recyclerViewManager.setAuxTextViewEnabled(true);
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
//                recyclerViewManager.add(DataGenerator.generateRandomImageData(width, width / 2));
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
