package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;

import java.util.List;

public class ImageBackgroundSelectorAdapter extends CommonRecyclerAdapter<ImageData> {

    public ImageBackgroundSelectorAdapter(Context context) {
        super(context);
    }

    public ImageBackgroundSelectorAdapter(List<ImageData> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    protected ViewHolder inflateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new ImageBackgroundSelectorItem(context));
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, ImageData item) {
        ((ImageBackgroundSelectorItem) viewHolder.getView()).bindTo(item);
    }

}
