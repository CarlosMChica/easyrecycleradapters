package com.carlosdelachica.sample;

import android.content.Context;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.CommonRecyclerAdapter;

import java.util.List;

public class ImageAdapter extends CommonRecyclerAdapter<ImageData> {

    public ImageAdapter(Context context) {
        super(context);
    }

    public ImageAdapter(List<ImageData> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    protected ViewHolder inflateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new ImageItem(context));
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, ImageData item) {
        ((ImageItem) viewHolder.getView()).bindTo(item);
    }
}
