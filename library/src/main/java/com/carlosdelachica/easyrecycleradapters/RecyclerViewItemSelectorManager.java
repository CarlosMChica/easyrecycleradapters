package com.carlosdelachica.easyrecycleradapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

@SuppressWarnings("UnusedDeclaration")
public class RecyclerViewItemSelectorManager {

    private Drawable selectorDrawable;
    private Context context;

    public RecyclerViewItemSelectorManager(Context context) {
        this.context = context;
        selectorDrawable = generateSelectorDrawable();
    }

    public View applySelector(View view) {
        FrameLayout selectorContainer;
        if (view instanceof FrameLayout) {
            selectorContainer = (FrameLayout) view;
        } else {
            selectorContainer = new FrameLayout(context);
            selectorContainer.addView(view);
        }
        selectorContainer.setForeground(selectorDrawable);
        return selectorContainer;
    }

    private Drawable generateSelectorDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(context, R.drawable.default_selector);
        } else {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.selectorDrawable, typedValue, true);
            return ContextCompat.getDrawable(context, typedValue.resourceId);
        }
    }


}