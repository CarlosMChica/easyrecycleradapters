package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemLongClickListener;

public class EasyRecyclerViewManager implements OnItemClickListener,
        OnItemLongClickListener {

    private RecyclerView recyclerView;
    private TextView auxTextView;
    private EasyRecyclerAdapter adapter;
    private LayoutManager layoutManager;
    private Context context;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;
    private DividerItemDecoration dividerItemDecoration;
    private int loadingTextColor;
    private String loadingText;
    private String emptyListText;
    private int emptyListTextColor;

    public EasyRecyclerViewManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void attachToRecyclerView(RecyclerView recyclerView, EasyRecyclerAdapter adapter) {
        this.attachToRecyclerView(recyclerView, adapter, new LinearLayoutManager(recyclerView.getContext()));
    }

    public void attachToRecyclerView(RecyclerView recyclerView, EasyRecyclerAdapter adapter, LayoutManager layoutManager) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        context = recyclerView.getContext();
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
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);
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
            dividerRes = R.drawable.grid_divider;
        } else {
            dividerRes = R.drawable.list_divider;
        }
        this.dividerItemDecoration = new DividerItemDecoration(context, context.getResources().getDrawable(dividerRes));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void setDivider(@DrawableRes int dividerDrawableRes) {
        dividerItemDecoration.setDivider(dividerDrawableRes);
    }

    public void setLoadingTextColor(int colorRes) {
        loadingTextColor = context.getResources().getColor(colorRes);
    }

    public void setLoadingText(@StringRes int messageStringRes) {
        loadingText = context.getString(messageStringRes);
    }

    public void setEmptyListTextColor(int colorRes) {
        emptyListTextColor = context.getResources().getColor(colorRes);
    }

    public void setEmptyListText(@StringRes int messageStringRes) {
        emptyListText = context.getString(messageStringRes);
    }

    public void attachToAuxTextView(TextView auxTextView) {
        this.auxTextView = auxTextView;
    }

    private void initEmptyListTextView() {
        updateEmptyListTextView(EmptyListTextViewState.LOADING);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerView.setPadding(left, top, right, bottom);
    }

    public boolean update(Object data) {
//      TODO: Adapter update item
        return false;
    }

    public boolean update(Object data, int position) {
//      TODO: Adapter update item at position
        return false;
    }

    public void addAll(List<Object> data) {
        adapter.addAll(data);
        updateEmptyListTextView(data.size() > 0 ? EmptyListTextViewState.HIDDEN : EmptyListTextViewState.EMPTY);
    }

    public void add(Object data, int position) {
        adapter.add(data);
        updateEmptyListTextView(EmptyListTextViewState.HIDDEN);
    }

    public void remove(Object data) {
//      TODO: Adapter remove data item
        updateEmptyListTextView(adapter.getItemCount() > 0 ? EmptyListTextViewState.HIDDEN : EmptyListTextViewState.EMPTY);
    }

    public void remove(int position) {
//      TODO: Adapter remove item at position
        updateEmptyListTextView(adapter.getItemCount() > 0 ? EmptyListTextViewState.HIDDEN : EmptyListTextViewState.EMPTY);
    }

    public Object getItem(int position) {
        return adapter.get(position);
    }

    public void onRefresh() {
//      TODO: Adapter clear items
        updateEmptyListTextView(EmptyListTextViewState.LOADING);
    }

    private void updateEmptyListTextView(EmptyListTextViewState state) {
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
        if (itemClickListener != null) {
            itemClickListener.onItemClick(position, view);
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongItemClicked(int position, View view) {
        if (longClickListener == null) { return false; }
        return longClickListener.onLongItemClicked(position, view);
    }

    public static class Builder {

        private final Context context;
        private ItemClickListener clickListener;
        private ItemLongClickListener longClickListener;
        private LayoutManager layoutManager;
        private RecyclerView recyclerView;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder layoutManager(LayoutManager layoutManager) {
            if (layoutManager == null) {
                throw new IllegalArgumentException("LayoutManager must not be null.");
            }
            if (this.layoutManager != null) {
                throw new IllegalStateException("LayoutManager already set.");
            }
            this.layoutManager = layoutManager;
            return this;
        }

        public Builder recyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                throw new IllegalArgumentException("RecyclerView must not be null.");
            }
            if (this.recyclerView != null) {
                throw new IllegalStateException("RecyclerView already set.");
            }
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder clickListener(ItemClickListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.clickListener != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.clickListener = listener;
            return this;
        }

        public Builder longClickListener(ItemLongClickListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.longClickListener != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.longClickListener = listener;
            return this;
        }

        //TODO: Look at Picasso builder for ideas.
        //Adds method to set up EasyRecyclerViewManager such as: Divider,
        //emptyListTextView texts, colors, etc...

        public EasyRecyclerViewManager build() {
            Context context = this.context;
            if (layoutManager == null) {
                layoutManager = new LinearLayoutManager(context);
            }
            return new EasyRecyclerViewManager(layoutManager);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemLongClickListener {
        void onLongItemClick(int position);
    }

}
