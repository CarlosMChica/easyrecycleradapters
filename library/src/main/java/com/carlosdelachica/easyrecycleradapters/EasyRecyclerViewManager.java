package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.decorations.DividerItemDecoration;

import java.util.List;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemLongClickListener;

@SuppressWarnings("UnusedDeclaration")
public class EasyRecyclerViewManager implements OnItemClickListener,
        OnItemLongClickListener {

    private RecyclerView recyclerView;
    private TextView emptyLoadingListTextView;
    private EasyRecyclerAdapter adapter;
    private LayoutManager layoutManager;
    private Context context;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener longClickListener;
    private DividerItemDecoration dividerItemDecoration;
    private String loadingListText;
    private String emptyListText;
    private int loadingTextColor;
    private int emptyListTextColor;

    EasyRecyclerViewManager(Context context,
                            RecyclerView recyclerView,
                            LayoutManager layoutManager,
                            EasyRecyclerAdapter adapter,
                            TextView emptyLoadingListTextView,
                            String emptyListText,
                            int emptyTextColor,
                            String loadingListText,
                            int loadingTextColor,
                            ItemLongClickListener longClickListener,
                            ItemClickListener clickListener,
                            DividerItemDecoration dividerDrawableRes) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.adapter = adapter;
        this.emptyLoadingListTextView = emptyLoadingListTextView;
        this.emptyListText = emptyListText;
        this.emptyListTextColor = emptyTextColor;
        this.loadingListText = loadingListText;
        this.loadingTextColor = loadingTextColor;
        this.longClickListener = longClickListener;
        this.itemClickListener = clickListener;
        this.dividerItemDecoration = dividerDrawableRes;
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);
        initRecyclerViewPadding();
        initAdapter();
        initLayoutManager();
        initItemDecorations();
        initEmptyListTextView();
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
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void initEmptyListTextView() {
        updateEmptyListTextView(EmptyLoadingListTextViewState.LOADING);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerViewPadding(int left, int top, int right, int bottom) {
        recyclerView.setPadding(left, top, right, bottom);
    }

    public int getCount() {
        return adapter.getItemCount();
    }

    public void add(Object data, int position) {
        adapter.add(data);
        updateEmptyListTextView(EmptyLoadingListTextViewState.HIDDEN);
    }

    public void addAll(List<Object> data) {
        adapter.addAll(data);
        updateEmptyListTextView(data.size() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public boolean update(Object data) {
        return adapter.update(data);
    }

    public boolean update(Object data, int position) {
        return adapter.update(data, position);
    }

    public void remove(Object data) {
        adapter.remove(data);
        updateEmptyListTextView(adapter.getItemCount() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public void remove(int position) {
        adapter.remove(position);
        updateEmptyListTextView(adapter.getItemCount() > 0 ? EmptyLoadingListTextViewState.HIDDEN : EmptyLoadingListTextViewState.EMPTY);
    }

    public Object getItem(int position) {
        return adapter.get(position);
    }

    public void onRefresh() {
        adapter.clear();
        updateEmptyListTextView(EmptyLoadingListTextViewState.LOADING);
    }

    private void updateEmptyListTextView(EmptyLoadingListTextViewState state) {
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
                setAuxTextViewText(loadingListText);
                setAuxTextViewTextColor(loadingTextColor);
                break;
        }
    }

    private void setAuxTextViewText(String text) {
        if (emptyLoadingListTextView == null) { return; }
        emptyLoadingListTextView.setText(text);
    }

    private void setAuxTextViewTextColor(int color) {
        if (emptyLoadingListTextView == null) { return; }
        emptyLoadingListTextView.setTextColor(color);
    }

    private void setAuxTextViewVisible(boolean visible) {
        if (emptyLoadingListTextView == null) { return; }
        emptyLoadingListTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(position);
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongItemClicked(int position, View view) {
        if (longClickListener == null) { return false; }
        return longClickListener.onLongItemClick(position);
    }

    public static class Builder {

        private Context context;
        private ItemClickListener clickListener;
        private ItemLongClickListener longClickListener;
        private LayoutManager layoutManager;
        private RecyclerView recyclerView;
        private EasyRecyclerAdapter adapter;
        private DividerItemDecoration dividerItemDecoration;
        private Drawable dividerDrawable;
        private String loadingText;
        private String emptyText;
        private TextView emptyLoadingListTextView;
        private int emptyTextColor;
        private int loadingTextColor;

        public Builder(RecyclerView recyclerView, EasyRecyclerAdapter adapter) {
            if (recyclerView == null) {
                throw new IllegalArgumentException("RecyclerView must not be null.");
            }
            if (recyclerView.getContext() == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            if (adapter == null) {
                throw new IllegalArgumentException("Adapter must not be null.");
            }
            this.context = recyclerView.getContext().getApplicationContext();
            this.recyclerView = recyclerView;
            this.adapter = adapter;
            this.emptyTextColor = context.getResources().getColor(R.color.empty_list_text_color);
            this.loadingTextColor = context.getResources().getColor(R.color.empty_list_text_color);
            this.loadingText = context.getResources().getString(R.string.loading);
            this.emptyText = context.getResources().getString(R.string.empty);
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

        public Builder divider(@DrawableRes int dividerDrawableRes) {
            this.dividerDrawable = context.getResources().getDrawable(dividerDrawableRes);
            return this;
        }

        public Builder emptyListTextColor(int emptyTextColor) {
            this.emptyTextColor = context.getResources().getColor(emptyTextColor);
            return this;
        }

        public Builder emptyListText(@StringRes int emptyListTextRes) {
            this.emptyText = context.getResources().getString(emptyListTextRes);
            return this;
        }

        public Builder loadingListTextColor(int loadingTextColor) {
            this.loadingTextColor = context.getResources().getColor(loadingTextColor);
            return this;
        }

        public Builder loadingListText(@StringRes int loadingTextRes) {
            this.loadingText = context.getResources().getString(loadingTextRes);
            return this;
        }

        public Builder emptyLoadingListTextView(TextView emptyLoadingListTextView) {
            this.emptyLoadingListTextView = emptyLoadingListTextView;
            return this;
        }

        public EasyRecyclerViewManager build() {
            if (layoutManager == null) {
                layoutManager = new LinearLayoutManager(context);
            }

            Drawable divider = dividerDrawable;
            if (divider == null) {
                divider = context.getResources().getDrawable(R.drawable.default_divider);
            }
            this.dividerItemDecoration = new DividerItemDecoration(context, divider);

            return new EasyRecyclerViewManager(context,
                    recyclerView,
                    layoutManager,
                    adapter,
                    emptyLoadingListTextView,
                    emptyText,
                    emptyTextColor,
                    loadingText,
                    loadingTextColor,
                    longClickListener,
                    clickListener,
                    dividerItemDecoration
            );
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemLongClickListener {
        boolean onLongItemClick(int position);
    }

}
