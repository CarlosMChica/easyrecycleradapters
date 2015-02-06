package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EasyViewHolderFactory {

    private List<Class> classViewTypes = new ArrayList<>();
    private Map<Class, Class<? extends EasyViewHolder>> boundViewHolders = new HashMap<>();

    public EasyViewHolder onCreateViewHolder(int viewType, Context context, ViewGroup parent) {
        return createViewHolder(viewType, context, parent);
    }

    private EasyViewHolder createViewHolder(int viewType, Context context, ViewGroup parent) {
        try {
            Class valueClass = classViewTypes.get(viewType);
            Class<? extends EasyViewHolder> easyViewHolderClass = boundViewHolders.get(valueClass);
            Constructor<? extends EasyViewHolder> constructor = easyViewHolderClass.getDeclaredConstructor(Context.class, ViewGroup.class);
            return constructor.newInstance(context, parent);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder) {
        classViewTypes.add(valueClass);
        boundViewHolders.put(valueClass, viewHolder);
    }

    public int getItemViewType(Object data) {
        if (classViewTypes == null) {  return 0; }
        return classViewTypes.indexOf(data.getClass());
    }

}
