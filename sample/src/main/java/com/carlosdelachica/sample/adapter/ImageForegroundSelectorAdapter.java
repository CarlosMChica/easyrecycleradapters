package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.adapter.CommonRecyclerAdapter;

import java.util.List;

public class ImageForegroundSelectorAdapter extends CommonRecyclerAdapter<ImageData> {

    public ImageForegroundSelectorAdapter(Context context) {
        super(context);
    }

    public ImageForegroundSelectorAdapter(List<ImageData> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    protected ViewHolder inflateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new ImageForegroundSelectorItem(context));
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, ImageData item, int position) {
        ((ImageForegroundSelectorItem) viewHolder.getView()).bindTo(item);
    }

}
