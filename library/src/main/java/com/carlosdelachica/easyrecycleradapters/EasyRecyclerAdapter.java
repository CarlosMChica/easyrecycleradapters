package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemLongClickListener;

@SuppressWarnings("UnusedDeclaration")
public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> implements OnItemLongClickListener,
        OnItemClickListener {

    private List<Object> dataList = new ArrayList<>();
    private Context context;
    private EasyViewHolderFactory factory;
    private List<Class> classViewTypes = new ArrayList<>();
    private Map<Class, Class<? extends EasyViewHolder>> boundViewHolders = new HashMap<>();
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;

    public EasyRecyclerAdapter(Context context, Class valueClass, Class<? extends EasyViewHolder> easyViewHolderClass) {
        this(context);
        bind(valueClass, easyViewHolderClass);
    }

    public void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder) {
        classViewTypes.add(valueClass);
        boundViewHolders.put(valueClass, viewHolder);
    }

    public EasyRecyclerAdapter(Context context) {
        this.context = context;
        factory = new EasyViewHolderFactory() {
            @Override public EasyViewHolder onCreateViewHolder(int viewType, Context context, ViewGroup parent) {
                return buildViewHolder(viewType, context, parent);
            }
        };
    }

    private EasyViewHolder buildViewHolder(int viewType, Context context, ViewGroup parent) {
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

    @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EasyViewHolder easyViewHolder = factory.onCreateViewHolder(viewType, context, parent);
        bindListeners(easyViewHolder);
        return easyViewHolder;
    }

    private void bindListeners(EasyViewHolder easyViewHolder) {
        easyViewHolder.setItemClickListener(this);
        easyViewHolder.setLongClickListener(this);
    }

    @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
        holder.bindTo(dataList.get(position));
    }

    @Override public int getItemViewType(int position) {
        if (classViewTypes == null) {  return super.getItemViewType(position); }
        Object objectClass = dataList.get(position);
        return classViewTypes.indexOf(objectClass.getClass());
    }

    @Override public int getItemCount() {
        return dataList.size();
    }
    
    public void add(Object object) {
        dataList.add(object);
        notifyItemInserted(dataList.indexOf(object));
    }

    public void addAll(List<?> objects) {
        dataList.clear();
        dataList.addAll(objects);
        notifyDataSetChanged();
    }

    public Object get(int position) {
        return dataList.get(position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongItemClicked(int position, View view) {
        if (longClickListener == null) {
            return false;
        }
        return longClickListener.onLongItemClicked(position, view);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (itemClickListener == null) return;
        itemClickListener.onItemClick(position, view);
    }

}
