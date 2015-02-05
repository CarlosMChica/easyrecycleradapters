package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Set;

public abstract class VariousTypesEasyViewHolderFactory implements EasyViewHolderFactory {

    private HashMap<Integer, Object> bindingHolders = new HashMap<>();
    
    public void bindViewHolder(int viewType, Object object) {
        bindingHolders.put(viewType, object);
    }
    
    public EasyViewHolder getViewHolder(int viewType, Context context, ViewGroup parent) {
        Set<Integer> viewTypes = bindingHolders.keySet();
        for (Integer bindingViewType : viewTypes) {
            if (viewType == bindingViewType) {
                return createViewHolder(bindingHolders.get(viewType), context, parent);
            }
        }
        return null;
    }

}
