package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.EasyViewHolder.OnItemLongClickListener;

@SuppressWarnings("UnusedDeclaration")
public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> implements OnItemLongClickListener,
        OnItemClickListener {

    private List<Object> dataList = new ArrayList<>();
    private EasyViewHolderFactory factory = new EasyViewHolderFactory();
    private Context context;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;

    public EasyRecyclerAdapter(Context context, Class valueClass, Class<? extends EasyViewHolder> easyViewHolderClass) {
        this(context);
        bind(valueClass, easyViewHolderClass);
    }

    public EasyRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder) {
        factory.bind(valueClass, viewHolder);
    }

    @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EasyViewHolder easyViewHolder = factory.onCreateViewHolder(viewType, context, parent);
        bindListeners(easyViewHolder);
        return easyViewHolder;
    }

    private void bindListeners(EasyViewHolder easyViewHolder) {
        if (easyViewHolder != null) {
            easyViewHolder.setItemClickListener(this);
            easyViewHolder.setLongClickListener(this);
        }
    }

    @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
        holder.bindTo(dataList.get(position));
    }

    @Override public int getItemViewType(int position) {
        return factory.getItemViewType(dataList.get(position));
    }

    @Override public int getItemCount() {
        return dataList.size();
    }
    
    public void add(Object object) {
        dataList.add(object);
        notifyItemInserted(getIndex(object));
    }

    public void addAll(List<?> objects) {
        dataList.clear();
        dataList.addAll(objects);
        notifyDataSetChanged();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public boolean update(Object data) {
        int indexOfData = getIndex(data);
        if (indexOfData == -1) return false;
        return update(data, indexOfData);
    }

    public boolean update(Object data, int position) {
        Object oldData = dataList.set(position, data);
        if (oldData != null) {
            notifyItemChanged(position);
        }
        return oldData != null;
    }

    public void remove(Object data) {
        if (dataList.contains(data)) {
            remove(getIndex(data));
        }
    }

    public void remove(int position) {
        if (position >= 0 && position < getItemCount()) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Object get(int position) {
        return dataList.get(position);
    }

    public int getIndex(Object item) {
        return dataList.indexOf(item);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override public boolean onLongItemClicked(int position, View view) {
        if (longClickListener == null) { return false; }
        return longClickListener.onLongItemClicked(position, view);
    }

    @Override public void onItemClick(int position, View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(position, view);
        }
    }

}
