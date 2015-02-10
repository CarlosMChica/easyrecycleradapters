package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

public class RecyclerViewManager<V, VH extends CommonViewHolder<V>> implements CommonViewHolder.OnItemClickListener,
        CommonViewHolder.OnItemLongClickListener {

    private RecyclerView recyclerView;
    private TextView auxTextView;
    private CommonRecyclerAdapter<V, VH> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private RecyclerViewManagerCallback callback;
    private DividerItemDecoration dividerItemDecoration;
    private int loadingTextColor;
    private String loadingText;
    private String emptyListText;
    private int emptyListTextColor;

    public void attachToRecyclerView(RecyclerView recyclerView, CommonRecyclerAdapter<V, VH> adapter) {
        this.attachToRecyclerView(recyclerView, adapter, new LinearLayoutManager(recyclerView.getContext()));
    }

    public void attachToRecyclerView(RecyclerView recyclerView, CommonRecyclerAdapter<V, VH> adapter, RecyclerView.LayoutManager layoutManager) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.context = recyclerView.getContext();
        emptyListTextColor = context.getResources().getColor(R.color.empty_list_text_color);
        loadingTextColor = context.getResources().getColor(R.color.empty_list_text_color);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        initRecyclerViewPadding();
        initAdapter();
        initLayoutManager();
        initItemDecorations();
    }

    private void initRecyclerViewPadding() {
        //Apply padding as described in Material Design specs
        int topBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.material_design_list_top_bottom_padding);
        setRecyclerViewPadding(0, topBottomPadding, 0, topBottomPadding);
    }

    private void initAdapter() {
        recyclerView.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    private void initLayoutManager() {
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initItemDecorations() {
        initDivider();
    }

    private void initDivider() {
        int dividerRes;
        if (layoutManager instanceof GridLayoutManager) {
            dividerRes = R.drawable.default_divider;
        } else {
            dividerRes = R.drawable.list_divider;
        }
        this.dividerItemDecoration = new DividerItemDecoration(context, context.getResources().getDrawable(dividerRes));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void setDivider(@DrawableRes int dividerDrawableRes) {
        dividerItemDecoration.setDivider(dividerDrawableRes);
    }

    public void setLoadingTextColor(@ColorRes int colorRes) {
        loadingTextColor = context.getResources().getColor(colorRes);
    }

    public void setLoadingText(@StringRes int messageStringRes) {
        loadingText = context.getString(messageStringRes);
    }

    public void setEmptyListTextColor(@ColorRes int colorRes) {
        emptyListTextColor = context.getResources().getColor(colorRes);
    }

    public void setEmptyListText(@StringRes int messageStringRes) {
        emptyListText = context.getString(messageStringRes);
    }

    public void attachToAuxTextView(TextView auxTextView) {
        this.auxTextView = auxTextView;
    }

    public void setAuxTextViewEnabled(boolean enabled) {
        if (!enabled) return;
        initAuxTextView();
    }

    private void initAuxTextView() {
        updateAuxTextView(EmptyLoadingListTextViewState.LOADING);
    }

    public void setCallback(RecyclerViewManagerCallback callback) {
        this.callback = callback;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerView.setPadding(left, top, right, bottom);
    }

    public boolean update(V data) {
        return adapter.update(data);
    }

    public boolean update(V data, int position) {
        return adapter.update(data, position);
    }

    public void add(List<V> data) {
        adapter.add(data);
        updateAuxTextView(data.size() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public void add(V data, int position) {
        adapter.add(data, position);
        updateAuxTextView(EmptyLoadingListTextViewState.HIDDEN);
    }

    public void add(V data) {
        adapter.add(data);
        updateAuxTextView(EmptyLoadingListTextViewState.HIDDEN);
    }

    public void remove(V data) {
        adapter.remove(data);
        updateAuxTextView(adapter.getItemCount() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public void remove(int position) {
        adapter.remove(position);
        updateAuxTextView(adapter.getItemCount() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public V getItem(int position) {
        return adapter.getItem(position);
    }

    public int getItemIndex(V item) {
        return adapter.getItemIndex(item);
    }

    public void onRefresh() {
        adapter.clearItems();
        updateAuxTextView(EmptyLoadingListTextViewState.LOADING);
    }

    private void updateAuxTextView(EmptyLoadingListTextViewState state) {
        switch (state) {
            case HIDDEN:
                setAuxTextViewVisible(false);
                break;
            case EMPTY:
                setAuxTextViewVisible(true);
                setAuxTextViewText(emptyListText);
                setAuxTextViewTextColor(emptyListTextColor);
                break;
            case LOADING:
                setAuxTextViewVisible(true);
                setAuxTextViewText(loadingText);
                setAuxTextViewTextColor(loadingTextColor);
                break;
        }
    }

    private void setAuxTextViewText(String text) {
        if (auxTextView == null) return;
        auxTextView.setText(text);
    }

    private void setAuxTextViewTextColor(int color) {
        if (auxTextView == null) return;
        auxTextView.setTextColor(color);
    }

    private void setAuxTextViewVisible(boolean visible) {
        if (auxTextView == null) return;
        auxTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (callback == null) return;
        callback.onItemClick(position, view);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongItemClicked(int position, View view) {
        if (callback == null) return false;
        return callback.onLongItemClicked(position, view);
    }

    public interface RecyclerViewManagerCallback {
        void onItemClick(int position, View view);
        boolean onLongItemClicked(int position, View view);
    }

}
