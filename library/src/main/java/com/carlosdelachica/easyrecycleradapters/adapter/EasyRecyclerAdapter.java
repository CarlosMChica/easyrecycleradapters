package com.carlosdelachica.easyrecycleradapters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners.DebouncedOnClickListener;
import com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners.DebouncedOnLongClickListener;
import java.util.ArrayList;
import java.util.List;

import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemClickListener;
import static com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder.OnItemLongClickListener;

public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> {

  private List<Object> dataList = new ArrayList<>();
  private BaseEasyViewHolderFactory viewHolderFactory;
  private List<Class> valueClassTypes = new ArrayList<>();
  private OnItemClickListener itemClickListener;
  private OnItemLongClickListener longClickListener;

  public EasyRecyclerAdapter(Context context, Class valueClass,
      Class<? extends EasyViewHolder> easyViewHolderClass) {
    this(context);
    bind(valueClass, easyViewHolderClass);
  }

  public EasyRecyclerAdapter(Context context) {
    this(new BaseEasyViewHolderFactory(context));
  }

  public EasyRecyclerAdapter(BaseEasyViewHolderFactory easyViewHolderFactory, Class valueClass,
      Class<? extends EasyViewHolder> easyViewHolderClass) {
    this(easyViewHolderFactory);
    bind(valueClass, easyViewHolderClass);
  }

  public EasyRecyclerAdapter(BaseEasyViewHolderFactory easyViewHolderFactory) {
    this.viewHolderFactory = easyViewHolderFactory;
  }

  public void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder) {
    valueClassTypes.add(valueClass);
    viewHolderFactory.bind(valueClass, viewHolder);
  }

  @Override public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    EasyViewHolder easyViewHolder = viewHolderFactory.create(valueClassTypes.get(viewType), parent);
    bindListeners(easyViewHolder);
    return easyViewHolder;
  }

  private void bindListeners(EasyViewHolder easyViewHolder) {
    if (easyViewHolder != null) {
      easyViewHolder.setItemClickListener(itemClickListener);
      easyViewHolder.setLongClickListener(longClickListener);
    }
  }

  @Override public void onBindViewHolder(EasyViewHolder holder, int position) {
    holder.bindTo(dataList.get(position));
  }

  @Override public int getItemViewType(int position) {
    return valueClassTypes.indexOf(dataList.get(position).getClass());
  }

  @Override public int getItemCount() {
    return dataList.size();
  }

  public void add(Object object, int position) {
    dataList.add(position, object);
    notifyItemInserted(position);
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

  @SuppressWarnings("SimplifiableIfStatement") public boolean update(Object data) {
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

  public void clear() {
    dataList.clear();
    notifyDataSetChanged();
  }

  public void setOnClickListener(final OnItemClickListener listener) {
    this.itemClickListener = new DebouncedOnClickListener() {
      @Override public boolean onDebouncedClick(View v, int position) {
        listener.onItemClick(position, v);
        return true;
      }
    };
  }

  public void setOnLongClickListener(final OnItemLongClickListener listener) {
    this.longClickListener = new DebouncedOnLongClickListener() {
      @Override public boolean onDebouncedClick(View v, int position) {
        return listener.onLongItemClicked(position, v);
      }
    };
  }
}
