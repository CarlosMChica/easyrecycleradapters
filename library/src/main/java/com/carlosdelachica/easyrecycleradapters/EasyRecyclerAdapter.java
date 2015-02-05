package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> {

    private List<Object> dataList = new ArrayList<>();
    private Context context;
    private EasyViewHolderFactory factory;
    private List<Class> classViewTypes;
    private Map<Class, Class<?extends EasyViewHolder>> bindedHolders = new HashMap<>();

    public EasyRecyclerAdapter(Context context, EasyViewHolderFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    public EasyRecyclerAdapter(Context context, EasyViewHolderFactory factory, Class... classViewTypes) {
        this(context, factory);
        this.classViewTypes = Arrays.asList(classViewTypes);
    }
    
    public EasyRecyclerAdapter(Context context) {
        this.context = context;
        classViewTypes = new ArrayList<>();
        factory = new EasyViewHolderFactory() {
            @Override public EasyViewHolder onCreateViewHolder(int viewType, Context context, ViewGroup parent) {
                try {
                    Class valueClass = classViewTypes.get(viewType);
                    Class<? extends EasyViewHolder> easyViewHolderClass = bindedHolders.get(valueClass);
                    Constructor<? extends EasyViewHolder> constructor = easyViewHolderClass.getDeclaredConstructor(Context.class, ViewGroup.class);
                    return constructor.newInstance(context, parent);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
    
    public void bind(Class valueClass, Class<?extends EasyViewHolder> viewHolder) {
        classViewTypes.add(valueClass);
        bindedHolders.put(valueClass, viewHolder);
    }
    
    @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return factory.onCreateViewHolder(viewType, context, parent);
    }

    @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
        holder.bindTo(dataList.get(position));
    }

    @Override public int getItemViewType(int position) {
        if (classViewTypes == null) { 
            return super.getItemViewType(position);
        }
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
