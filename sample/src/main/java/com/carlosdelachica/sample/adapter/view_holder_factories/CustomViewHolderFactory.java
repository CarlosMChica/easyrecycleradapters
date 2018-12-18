package com.carlosdelachica.sample.adapter.view_holder_factories;

import android.content.Context;
import android.view.ViewGroup;

import com.carlosdelachica.easyrecycleradapters.adapter.BaseEasyViewHolderFactory;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import com.carlosdelachica.sample.adapter.view_holders.ImageEasyViewHolder;
import com.carlosdelachica.sample.adapter.view_holders.TextDataEasyViewHolder;
import com.carlosdelachica.sample.data.ImageData;
import com.carlosdelachica.sample.data.TextData;

public class CustomViewHolderFactory extends BaseEasyViewHolderFactory {


    public CustomViewHolderFactory(Context context) {
        super(context);
    }

    @Override
    public EasyViewHolder create(Class valueClass, ViewGroup parent) {
        if (valueClass == ImageData.class) {
            return new ImageEasyViewHolder(context, parent);
        } else if (valueClass == TextData.class) {
            return new TextDataEasyViewHolder(context, parent);
        }
        return null;
    }


}
