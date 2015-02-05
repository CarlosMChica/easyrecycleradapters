package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageEasyViewHolder extends EasyViewHolder<ImageData> {

    @InjectView(R.id.image) ImageView image;
    @InjectView(R.id.text) TextView title;
    
    private Context context;
    
    public ImageEasyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.image_foreground_item);
        this.context = context;
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void bindTo(ImageData item) {
        title.setText(item.getTitle());
        Picasso.with(context).load(item.getImageUrl()).placeholder(R.drawable.ic_launcher).into(image);
    }

}
