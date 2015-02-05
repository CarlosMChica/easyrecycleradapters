package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> {

    private List<Object> dataList = new ArrayList<>();
    private Context context;
    private EasyViewHolderFactory factory;
    private List<Class> classViewTypes;

    public EasyRecyclerAdapter(Context context, EasyViewHolderFactory factory, Class[] classViewTypes) {
        this.context = context;
        this.factory = factory;
        this.classViewTypes = Arrays.asList(classViewTypes);
    }
    
    @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return factory.onCreateViewHolder(viewType, context, parent);
    }

    @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
        holder.bindTo(dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        Object objectClass = dataList.get(position);
        return classViewTypes.indexOf(objectClass.getClass());
    }

    @Override public int getItemCount() {
        return dataList.size();
    }
    
    public void add(Object object) {
        dataList.add(object);
    }

    public void addAll(List<? extends Object> objects) {
        dataList.addAll(objects);
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return dataList.get(position);
    }

}
