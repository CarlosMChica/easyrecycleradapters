package com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners;

import android.view.View;

public interface DebouncedListener {
    boolean onDebouncedClick(View v, int position);

}
