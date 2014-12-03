package com.carlosdelachica.easyrecycleradapters;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class ViewUtils {

    public static void setBackground(View v, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }

}
