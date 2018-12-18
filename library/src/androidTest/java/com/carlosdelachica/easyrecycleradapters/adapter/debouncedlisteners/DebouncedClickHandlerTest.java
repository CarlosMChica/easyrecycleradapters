package com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners;

import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DebouncedClickHandlerTest {

    DebouncedClickHandler handler = new DebouncedClickHandler(new StubListener());
    int counter;

    @Test
    public void testWithinThreshold() {
        simulateClick();
        simulateClick();
        assertEquals(1, counter);
    }

    private void simulateClick() {
        handler.invokeDebouncedClick(0, null);
    }

    @Test
    public void testOutsideThreshold() throws InterruptedException {
        simulateClick();
        Thread.sleep(DebouncedClickHandler.MINIMUM_INTERVAL_MILLIS + 50);
        simulateClick();
        assertEquals(2, counter);
    }

    private class StubListener extends DebouncedOnClickListener {

        @Override
        public boolean onDebouncedClick(View v, int position) {
            counter++;
            return true;
        }
    }
}