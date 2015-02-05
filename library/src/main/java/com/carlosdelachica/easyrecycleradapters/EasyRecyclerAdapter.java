package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> {

    private List<Object> dataList = new ArrayList<>();
    private Context context;
    private VariousTypesEasyViewHolderFactory factory;

    public EasyRecyclerAdapter(Context context, VariousTypesEasyViewHolderFactory factory) {
        this.context = context;
        this.factory = factory;
    }
    
    @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return factory.getViewHolder(viewType, context, parent);
    }

    @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override public int getItemCount() {
        return dataList.size();
    }
    
    public void add(Object object) {
        dataList.add(object);
    }

    public Object getItem(int position) {
        return dataList.get(position);
    }

}
