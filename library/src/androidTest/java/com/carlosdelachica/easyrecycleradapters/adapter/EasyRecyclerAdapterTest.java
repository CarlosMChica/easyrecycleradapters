package com.carlosdelachica.easyrecycleradapters.adapter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class) public class EasyRecyclerAdapterTest {

  Context context;
  EasyRecyclerAdapter adapter;

  @Before public void setup() {
    context = InstrumentationRegistry.getInstrumentation().getContext();
    adapter = new EasyRecyclerAdapter(context);
  }

  @Test public void testSimpleAdapter() {
    assertNotNull(adapter);
    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testAddOneItem_returnsItemCountEquals1() throws Exception {
    adapter.add(new Object());
    assertEquals(1, adapter.getItemCount());
  }

  @Test public void testAddAll_returnsItemCountEqualsListSize() throws Exception {
    adapter.addAll(Arrays.asList(new Object(), new Object(), new Object()));
    assertEquals(3, adapter.getItemCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAllNullList_throwsIllegalArgumentException() throws Exception {
    adapter.addAll(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAppendAllNullList_throwsIllegalArgumentException() throws Exception {
    adapter.appendAll(null);
  }

  @Test public void testUpdateExistingItem_returnTrueAndReturnUpdatedItemAndDoNotChangeItemCount()
      throws Exception {
    Object listing = new Object();
    Object updatedObject = new Object();

    adapter.add(listing);
    boolean updated = adapter.update(updatedObject, 0);

    assertTrue(updated);
    assertEquals(1, adapter.getItemCount());
    assertEquals(updatedObject, adapter.get(0));
  }

  @Test public void testRemoveWithNullParameter_returnsFalse() throws Exception {
    boolean remove = adapter.remove(null);

    assertFalse(remove);
  }

  @Test public void testRemoveValidItem_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne()
      throws Exception {
    Object listing = new Object();
    adapter.add(listing);

    boolean remove = adapter.remove(listing);

    assertTrue(remove);
    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testRemovedInvalidIndex_returnsFalse() throws Exception {
    boolean remove = adapter.remove(-1);

    assertFalse(remove);
  }

  @Test public void testGetItem_returnsCorrectItem() throws Exception {
    Object expectedItem = new Object();
    adapter.addAll(Arrays.asList(new Object(), new Object(), new Object(), expectedItem));

    Object item = adapter.get(3);

    assertEquals(expectedItem, item);
  }

  @Test public void testGetIndex_returnsCorrectIndex() throws Exception {
    Object expectedItem = new Object();
    adapter.addAll(Arrays.asList(new Object(), new Object(), new Object(), expectedItem));

    int adapterIndex = adapter.getIndex(expectedItem);

    assertEquals(3, adapterIndex);
  }

  @Test public void testRemoveValidIndex_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne()
      throws Exception {
    Object listing = new Object();
    adapter.add(listing);

    boolean remove = adapter.remove(0);

    assertTrue(remove);
    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testClear_returnsItemCount0() throws Exception {
    adapter.addAll(Collections.singletonList(new Object()));
    adapter.clear();

    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testIsEmpty() throws Exception {
    assertTrue(adapter.isEmpty());
    adapter.add(new Object());
    assertFalse(adapter.isEmpty());
  }
}
