package com.carlosdelachica.sample.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlosdelachica.easyrecycleradapters.EasyViewHolder;
import com.carlosdelachica.easyrecycleradapters.sample.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TextDataEasyViewHolder extends EasyViewHolder<TextData> {

    @InjectView(R.id.text) TextView title;

    public TextDataEasyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.text_item);
        ButterKnife.inject(this, itemView);
    }

    @Override public void bindTo(TextData item) {
        title.setText(item.getTitle());
    }

}
