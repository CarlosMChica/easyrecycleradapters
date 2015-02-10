package com.carlosdelachica.sample.adapter.view_holders;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.data.ImageData;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageEasyViewHolder extends EasyViewHolder<ImageData> {

    @InjectView(R.id.image) ImageView image;

    private Context context;
    
    public ImageEasyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.image_item);
        this.context = context;
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void bindTo(ImageData item) {
        Picasso.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(image);
    }

}
