# EasyRecyclerAdapters

The code brings up an easy way of using recyclerView, with new recycler adapters. It also contains a BaseRecyclerFragment that removes a lot of boiler plate for fragments that contains either grids or lists.
It also offers out of the box a DividerItemDecoration that handles the drawing of the divider in the recycler view.

---

### What's New

###### Version 0.3.0

> 1. `MaterialDialogCompat` allows easy migration from use of `AlertDialog` (see below).
> 2. Convenience `show()` method in Builder, to skip call to `build()`.
> 3. Various important fixes from pull requests and the maintainer.

###### Version 0.2.0

> 1. Action buttons must be explicitly shown by setting text to them. The buttons will be hidden in any dialog type if no text is passed for them. This also allows you to display neutral or negative action buttons individually without relying on positive text.
> 2. List dialogs now use CharSequence arrays rather than String arrays.
> 3. Other bug fixes are included.

---

### Gradle Dependency

Reference adding to `build.gradle` file:

```Groovy
dependencies {
    compile 'compile depency'
}
```

### Basic use

You can find a sample project that shows up how to use.

Here's an example of basic use:

1. Extend from CommonRecyclerAdapter and provide a type for the data that is used on this particular adapter
2. Inflate the custom view for each item that is contained on this particular adapter
3. Bind each custom view with its data

```java

public class ImageAdapter extends CommonRecyclerAdapter<ImageData> {

    public ImageAdapter(Context context) {
        super(context);
    }

    public ImageAdapter(List<ImageData> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    protected ViewHolder inflateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new ImageItem(context));
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, ImageData item) {
        ((ImageItem) viewHolder.getView()).bindTo(item);
    }
}

```

4. Later create your fragment that will contain a recyclerview and extend it from BaseRecylcerFragment
5. Implement the methods to create your custom adapter and define the layout manager you want to use.


```java

    @Override
    protected CommonRecyclerAdapter<ImageData> createAdapter() {
        return new ImageAdapter(getActivity());
    }

    @Override
    protected GridLayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns));
    }
```

6. Override clicks methods for callbacks on items clicks and long clicks
```java

    @Override
    public boolean onLongItemClicked(int position, View view) {
        Toast.makeText(getActivity(), "painting long selected " + position, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getActivity(), "painting selected " + position, Toast.LENGTH_LONG).show();
    }
```

7. Call the methods provided by BaseRecyclerFragment to interact with the items contained in the adapter
```java

    public void updateItems(List<T> data) {
        adapter.updateItems(data);
    }

    public void addItem(T data) {
        adapter.add(data);
    }

    public void removeItem (T data) {
        adapter.remove(data);
    }

    public void removeItem (int position) {
        adapter.remove(position);
    }

    public void onRefresh() {
        adapter.clearItems();
    }
```

8.- Override for custom divider drawable

    protected DividerItemDecoration getDivider() {
        return new DividerItemDecoration(getActivity());
    }
```java


---

