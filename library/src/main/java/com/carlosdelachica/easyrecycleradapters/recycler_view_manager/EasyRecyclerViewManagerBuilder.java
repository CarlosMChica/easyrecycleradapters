package com.carlosdelachica.easyrecycleradapters.recycler_view_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.R;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EasyRecyclerViewManagerBuilder {

    private Context context;
    private EasyViewHolder.OnItemClickListener clickListener;
    private EasyViewHolder.OnItemLongClickListener longClickListener;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EasyRecyclerAdapter adapter;
    private Drawable dividerDrawable;
    private String loadingText;
    private String emptyText;
    private TextView emptyLoadingListTextView;
    private int emptyTextColor;
    private int loadingTextColor;
    private boolean clipToPadding = false;
    private boolean hasFixedSize = true;
    private View loadingView;
    private View emptyView;

    public EasyRecyclerViewManagerBuilder(RecyclerView recyclerView, EasyRecyclerAdapter adapter) {
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
    }

    public EasyRecyclerViewManagerBuilder layoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager == null) {
            throw new IllegalArgumentException("LayoutManager must not be null.");
        }
        if (this.layoutManager != null) {
            throw new IllegalStateException("LayoutManager already set.");
        }
        this.layoutManager = layoutManager;
        return this;
    }

    public EasyRecyclerViewManagerBuilder clickListener(EasyViewHolder.OnItemClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null.");
        }
        if (this.clickListener != null) {
            throw new IllegalStateException("Listener already set.");
        }
        this.clickListener = listener;
        return this;
    }

    public EasyRecyclerViewManagerBuilder longClickListener(EasyViewHolder.OnItemLongClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null.");
        }
        if (this.longClickListener != null) {
            throw new IllegalStateException("Listener already set.");
        }
        this.longClickListener = listener;
        return this;
    }

    public EasyRecyclerViewManagerBuilder divider(@DrawableRes int dividerDrawableRes) {
        this.dividerDrawable = context.getResources().getDrawable(dividerDrawableRes);
        return this;
    }

    public EasyRecyclerViewManagerBuilder emptyListTextColor(int emptyTextColor) {
        this.emptyTextColor = context.getResources().getColor(emptyTextColor);
        return this;
    }

    public EasyRecyclerViewManagerBuilder emptyListText(@StringRes int emptyListTextRes) {
        this.emptyText = context.getResources().getString(emptyListTextRes);
        return this;
    }

    public EasyRecyclerViewManagerBuilder loadingListTextColor(int loadingTextColor) {
        this.loadingTextColor = context.getResources().getColor(loadingTextColor);
        return this;
    }

    public EasyRecyclerViewManagerBuilder loadingListText(@StringRes int loadingTextRes) {
        this.loadingText = context.getResources().getString(loadingTextRes);
        return this;
    }

    public EasyRecyclerViewManagerBuilder emptyLoadingListTextView(TextView emptyLoadingListTextView) {
        this.emptyLoadingListTextView = emptyLoadingListTextView;
        return this;
    }

    public EasyRecyclerViewManagerBuilder recyclerViewClipToPadding(boolean clipToPadding) {
        this.clipToPadding = clipToPadding;
        return this;
    }

    public EasyRecyclerViewManagerBuilder recyclerViewHasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
        return this;
    }

    public EasyRecyclerViewManagerBuilder loadingView(View loadingView) {
        this.loadingView = loadingView;
        return this;
    }

    public EasyRecyclerViewManagerBuilder emptyView(View emptyView) {
        this.emptyView = emptyView;
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(divider);
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
                dividerItemDecoration,
                clipToPadding,
                hasFixedSize,
                loadingView,
                emptyView
        );
    }

}
