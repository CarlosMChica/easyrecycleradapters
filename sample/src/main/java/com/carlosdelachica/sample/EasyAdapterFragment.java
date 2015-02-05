package com.carlosdelachica.sample;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import com.carlosdelachica.easyrecycleradapters.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.VariousTypesEasyViewHolderFactory;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.EasyViewHolderImp;
import com.carlosdelachica.sample.data.DataGenerator;

public class EasyAdapterFragment extends Fragment {

    private Handler handler = new Handler();
    private EasyRecyclerAdapter adapter;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        adapter = new EasyRecyclerAdapter(getActivity(), new VariousTypesEasyViewHolderFactory() {
            @Override public EasyViewHolder createViewHolder(Object object, Context context, ViewGroup parent) {
                return new EasyViewHolderImp(context, parent);
            }
        });
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
                adapter.add(DataGenerator.generateRandomData(width / grid_columns, width / grid_columns * 2));
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
    
}
