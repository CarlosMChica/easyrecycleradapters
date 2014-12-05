# EasyRecyclerAdapters

The code brings up an easy way of using recyclerView, with the new recycler adapters. It also contains a BaseRecyclerFragment and a RecyclerStandalone that removes a lot of boiler plate for fragments that contains either grids or lists.
It also offers out of the box a DividerItemDecoration that handles the drawing of the divider in the recycler view. As well selectors for the items in your lists are handled.

---

### Gradle Dependency (Coming soon)

```Groovy
dependencies {
    compile 'com.github.cmc00022:easyrecycleradapters:0.0.2'
}
```

---

### Basic use

You can find a sample project that shows up how to use.

The sample has two fragments, each one illustrates how to use the two variations of the lib (Extending BaseRecyclerFragment or composing with RecyclerStandalone.
Customizations can also be found on the sample code (custom divider, foreground and background selectors)

Here's an example of basic use:

Extend from CommonRecyclerAdapter and provide a type for the data used on this particular adapter.
Inflate the custom view for each item that is contained on the adapter.
Bind each custom view with its data.

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
---

### Define your selectors

You need to define two items in your styles

```java
    <item name="rippleColor">@color/primaryDark</item>
    <item name="selectorDrawable">@drawable/custom_selector</item>
```

rippleColor: Color reference (Defines the color of the ripple that is used as selector). API 21+
selectorDrawable: Drawable reference that defines the states and values for your selector. API 21-

Find an example on the sample project

---

### 1.- Extend From BaseRecyclerFragment

Create your fragment that will contain a recyclerview and extend it from BaseRecylcerFragment.

Implement the methods to create your custom adapter and define the layout manager you want to use.


```java

    @Override
    protected CommonRecyclerAdapter<ImageData> createAdapter() {
        return new ImageAdapter(getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns));
    }
```

Override clicks methods for callbacks on items clicks and long clicks
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

Call the methods provided by BaseRecyclerFragment to interact with the items contained in the adapter
```java

    public void updateItems(List<T> data);

    public void addItem(T data);

    public void removeItem (T data);

    public void removeItem (int position);

    public void onRefresh();

```

### 2.- Standalone version


If you can't extend from BaseRecyclerFragment, there is a standalone class that offers the same features.

Just attach the standalone object to the recyclerView. Define an adapter and layout manager, set callback if needed:

```java
        standalone.attachToRecyclerView(recyclerView,
                new ImageAdapter(getActivity()),
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)));
        standalone.setCallback(this);

```

Use the same methods defined for BaseRecyclerFragment to interact with the data set

---

### Misc

Call for custom divider drawable

```java
    public void setDivider(Drawable divider);
```

---

Extend your custom view from FrameLayout for foreground selector (The selector will be drawn above your view)

```java
public class ImageForegroundSelectorItem extends FrameLayout {
.
.
}
```
---

Extend from any other view type for background selector (The selector will be drawn under your view)

```java
public class ImageBackgroundSelectorItem extends LinearLayout {
.
.
}
```
---

Call for empty text customisations

```java
    public void setEmptyListText(@StringRes int messageStringRes);
    public void setEmptyListTextColor(@ColorRes int colorRes);
```

First attach empty TextView to standalone object

```java
    recyclerStandalone.attachToEmptyList(emptyListTextView);
```




