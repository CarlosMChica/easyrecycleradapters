package com.carlosdelachica.easyrecycleradapters.adapter.debouncedlisteners;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DebouncedClickHandlerTest {

  DebouncedClickHandler handler = new DebouncedClickHandler(new StubListener());
  int counter;

  @Test public void testWithinThreshold() {
    simulateClick();
    simulateClick();
    Assert.assertEquals(1, counter);
  }

  private void simulateClick() {
    handler.invokeDebouncedClick(0, null);
  }

  @Test public void testOutsideThreshold() throws InterruptedException {
    simulateClick();
    Thread.sleep(DebouncedClickHandler.MINIMUM_INTERVAL_MILLIS + 50);
    simulateClick();
    Assert.assertEquals(2, counter);
  }

  private class StubListener extends DebouncedOnClickListener {

    @Override public boolean onDebouncedClick(View v, int position) {
      counter++;
      return true;
    }
  }
}