package com.carlosdelachica.sample.adapter.view_holders;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.data.ImageData;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageEasyViewHolder extends EasyViewHolder<ImageData> {

    @BindView(R.id.image)
    ImageView image;

    public ImageEasyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.image_item);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindTo(ImageData item) {
        Picasso.get().load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(image);
    }

}
