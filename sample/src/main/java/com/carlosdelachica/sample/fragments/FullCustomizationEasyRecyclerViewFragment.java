package com.carlosdelachica.sample.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.recycler_view_manager.EasyRecyclerViewManager;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.adapter.view_holders.ImageEasyViewHolder;
import com.carlosdelachica.sample.adapter.view_holders.TextDataEasyViewHolder;
import com.carlosdelachica.sample.data.DataGenerator;
import com.carlosdelachica.sample.data.ImageData;
import com.carlosdelachica.sample.data.TextData;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FullCustomizationEasyRecyclerViewFragment extends Fragment implements EasyViewHolder.OnItemClickListener,
        EasyViewHolder.OnItemLongClickListener {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.empty_list)
    TextView emptyList;
    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private Handler handler;
    private EasyRecyclerViewManager easyRecyclerViewManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.swipe_to_refresh_recycler_view_layout, container, false);
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
        initRefreshLayout();
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
                .loadingListTextColor(R.color.accentColor)
                .emptyListText(R.string.empty_list)
                .emptyListTextColor(R.color.accentColor)
                .clickListener(this)
                .longClickListener(this)
                .recyclerViewClipToPadding(false)
                .recyclerViewHasFixedSize(true)
                .build();
    }

    private void initRefreshLayout() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0)
                                ? 0
                                : recyclerView.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                easyRecyclerViewManager.onRefresh();
                initData();
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
                easyRecyclerViewManager.addAll(DataGenerator.generateRandomDataList(width / grid_columns));
                refreshLayout.setRefreshing(false);
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
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(), "painting selected " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongItemClicked(int position, View view) {
        Toast.makeText(getActivity(), "painting long selected " + position, Toast.LENGTH_LONG).show();
        return true;
    }

}
