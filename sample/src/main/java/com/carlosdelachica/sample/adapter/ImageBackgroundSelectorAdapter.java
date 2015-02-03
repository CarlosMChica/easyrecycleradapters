package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.sample.R;

public class ImageBackgroundSelectorAdapter extends CommonRecyclerAdapter<ImageData, ImageBackgroundSelectorViewHolder> {

    public ImageBackgroundSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    public ImageBackgroundSelectorViewHolder inflateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.image_background_item, parent, false);
        return new ImageBackgroundSelectorViewHolder(item);
    }

}
