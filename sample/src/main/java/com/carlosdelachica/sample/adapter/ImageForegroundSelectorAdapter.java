package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.CommonRecyclerAdapter;
import com.carlosdelachica.easyrecycleradapters.sample.R;

public class ImageForegroundSelectorAdapter extends CommonRecyclerAdapter<ImageData, ImageForegroundSelectorViewHolder> {

    public ImageForegroundSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    public ImageForegroundSelectorViewHolder inflateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.image_foreground_item, parent, false);
        return new ImageForegroundSelectorViewHolder(item);
    }

}
