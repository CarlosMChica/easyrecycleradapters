# EasyRecyclerAdapters

![Maven Central](https://img.shields.io/maven-central/v/com.github.cmc00022/easyrecycleradapters.svg)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EasyRecyclerAdapters-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1417)

The code brings up an easy way of using recyclerView, with the new recycler adapters. It also offers out of the box multi view types inside the same adapter and a DividerItemDecoration that handles the drawing of the divider in the recycler view.

---

### Gradle Dependency

```Groovy
dependencies {
    compile 'com.github.cmc00022:easyrecycleradapters:1.0.8'
}
```

---

Info: The lib is still under development and might suffer changes.

### How to use

You can find a sample project that shows up how to use.

The sample has three fragments, each one illustrates how to use the features included on the lib
Customizations can also be found on the sample code (custom divider, foreground and background selectors(Have a look to item views layouts in the sample code))

Here's an example of basic use:

Extend from EasyViewHolder and provide a type for the data used on this particular EasyViewHolder.
Call super with the layout for your EasyViewHolder
Bind your EasyViewHolder with its data.

```java

    public ImageEasyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.image_item);
        this.context = context;
    }

    @Override
    public void bindTo(ImageData item) {
        Picasso.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(image);
    }
```

### 1.- Create your adapter

Single view EasyRecyclerAdapter

```java
    private EasyRecyclerAdapter adapter;
    adapter = new EasyRecyclerAdapter(getActivity(), ImageData.class, ImageEasyViewHolder.class);
    adapter.setOnClickListener(this);
    adapter.setOnLongClickListener(this);
```

Multi view EasyRecyclerAdapter (bind each EasyViewHolder class to it's data type class)

```java
    private EasyRecyclerAdapter adapter;
    adapter = new EasyRecyclerAdapter(getActivity());
    adapter.bind(ImageData.class, ImageEasyViewHolder.class);
    adapter.bind(TextData.class, TextDataEasyViewHolder.class);

```

Set click listener methods for callbacks on items clicks and long clicks

```java
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);
```

Call the methods provided by EasyRecyclerAdapter to interact with the data set

```java

    public void add(Object object);

    public void addAll(List<?> objects);

    public boolean update(Object data);

    public boolean update(Object data, int position);

    public void remove(Object data);

    public void remove(int position);

    public Object get(int position);

    public int getIndex(Object item);

    public void clear();

```

### 2.- EasyRecyclerManager version

For fully recyclerView customization you have EasyRecyclerManager. It's easy to use and have a lot of features out of the box for controlling your recycler view.

```java
    //Create your EasyRecyclerAdapter
    EasyRecyclerAdapter adapter = new EasyRecyclerAdapter(getActivity());
    adapter.bind(ImageData.class, ImageEasyViewHolder.class);
    adapter.bind(TextData.class, TextDataEasyViewHolder.class);
    //Create your EasyRecyclerViewManager.Builder and pass your recyclerView and your EasyRecyclerAdapter
    easyRecyclerViewManager = new EasyRecyclerViewManager.Builder(recyclerView, adapter)
            //layoutManager (If none, LinearLayoutManager is used)
            .layoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns)))
            //clickListeners
            .clickListener(this)
            .longClickListener(this)
            //divider (If none, default is used)
            .divider(R.drawable.custom_divider)
            //TextView defined on your layout, to show up when loading your data or the recyclerView has not data
            //If none, None of the related builder params are used
            .emptyLoadingListTextView(emptyList)
            //TextView loading text(If none, "Loading..." is used
            .loadingListText(R.string.loading)
            //TextView loading tex color(If none, default is used
            .loadingListTextColor(R.color.accentColor)
            //TextView empty list tex color(If none, "No data" is used
            .emptyListText(R.string.empty_list)
            //TextView empty list tex color(If none, default is used
            .emptyListTextColor(R.color.accentColor)
            //LoadingView
            .loadingView(View loadingView)
            //emptyView
            .emptyView(View emptyView)
            //Misc
            .recyclerViewClipToPadding(false)
            .recyclerViewHasFixedSize(true)
            .build();
```
---

### Custom ViewHolderFactory

If you need to pass extra parameters in order to display the data associated to a ViewHolder,
create a custom ViewHolderFactory and create your ViewHolders as follows.

```java

public class CustomViewHolderFactory extends BaseEasyViewHolderFactory {

    public CustomViewHolderFactory(Context context) {
        super(context);
        //Extra parameters for your ViewHolders
        this.picasso = Picasso.with(context);
    }

    @Override
    public EasyViewHolder create(Class valueClass, ViewGroup parent) {
    //Create ViewHolder for each dataClass
        if (valueClass == ImageData.class) {
            //Using extra parameters as needed
            return new ImageEasyViewHolder(context, parent, picasso);
        } else if (valueClass == TextData.class) {
            return new TextDataEasyViewHolder(context, parent);
        }
        return null;
    }

}

    //Create the adapter and pass the custom ViewHolderFactory
    //Bind the data as normally.
    adapter = new EasyRecyclerAdapter(
            new CustomViewHolderFactory(getActivity()),
            ImageData.class,
            ImageEasyViewHolder.class);

```


Check out the sample layouts to set selectors on each EasyViewHolder view
You need to define two items in your styles

---

### Define your selectors

Check out the sample layouts to set selectors on each EasyViewHolder view

---

### Proguard

```
-keep public class * extends com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder
-keepclassmembers class * extends com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.view.ViewGroup);
}
```

---

Developed by
=======

Christian Panadero Martinez - <a href="http://panavtec.me">http://panavtec.me</a> - <a href="https://github.com/PaNaVTEC">@PaNaVTEC</a>

Carlos Morera de la Chica - <a href="https://github.com/cmc00022">@cmc00022</a>

License
=======

    Copyright 2014 Carlos Morera de la Chica.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
