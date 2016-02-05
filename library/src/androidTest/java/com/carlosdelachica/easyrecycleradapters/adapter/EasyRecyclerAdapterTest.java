package com.carlosdelachica.easyrecycleradapters.adapter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class) public class EasyRecyclerAdapterTest {

  public static final List<Object> LIST = singletonList(new Object());
  public static final List<Object> LIST_WITH_3_ITEMS =
      asList(new Object(), new Object(), new Object());

  Context context;
  EasyRecyclerAdapter adapter;
  public static final Object ITEM = new Object();
  public static final Object ITEM_AT_INDEX_3 = new Object();
  public static final List<Object> LIST_WITH_4_ITEMS =
      asList(new Object(), new Object(), new Object(), ITEM_AT_INDEX_3);

  @Before public void setup() {
    context = InstrumentationRegistry.getInstrumentation().getContext();
    adapter = new EasyRecyclerAdapter(context);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAllNullList_throwsIllegalArgumentException() throws Exception {
    adapter.addAll(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAppendAllNullList_throwsIllegalArgumentException() throws Exception {
    adapter.appendAll(null);
  }

  @Test public void testAddAll_returnsItemCountEqualsListSize() throws Exception {
    adapter.addAll(LIST_WITH_3_ITEMS);

    assertEquals(3, adapter.getItemCount());
  }

  @Test public void testUpdateExistingItem_returnTrueAndReturnUpdatedItemAndDoNotChangeItemCount()
      throws Exception {
    Object updatedItem = new Object();

    adapter.add(ITEM);
    boolean updated = adapter.update(updatedItem, 0);

    assertTrue(updated);
    assertEquals(1, adapter.getItemCount());
    assertEquals(updatedItem, adapter.get(0));
  }

  @Test public void testRemoveWithNullParameter_returnsFalse() throws Exception {
    boolean remove = adapter.remove(null);

    assertFalse(remove);
  }

  @Test public void testRemoveValidItem_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne()
      throws Exception {
    adapter.add(ITEM);

    boolean remove = adapter.remove(ITEM);

    assertTrue(remove);
    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testRemovedInvalidIndex_returnsFalse() throws Exception {
    boolean remove = adapter.remove(-1);

    assertFalse(remove);
  }

  @Test public void testGetItem_returnsCorrectItem() throws Exception {
    adapter.addAll(LIST_WITH_4_ITEMS);

    Object item = adapter.get(3);

    assertEquals(ITEM_AT_INDEX_3, item);
  }

  @Test public void testGetIndex_returnsCorrectIndex() throws Exception {
    adapter.addAll(LIST_WITH_4_ITEMS);

    int adapterIndex = adapter.getIndex(ITEM_AT_INDEX_3);

    assertEquals(3, adapterIndex);
  }

  @Test public void testRemoveValidIndex_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne()
      throws Exception {
    adapter.add(ITEM);

    boolean remove = adapter.remove(0);

    assertTrue(remove);
    assertEquals(0, adapter.getItemCount());
  }

  @Test public void testClear_emptyDataSet() throws Exception {
    adapter.addAll(LIST);

    adapter.clear();

    assertIsEmpty();
  }

  @Test public void testIsEmptyByDefault() throws Exception {
    assertIsEmpty();
  }

  @Test public void testIsNotEmptyAfterAddingItem() throws Exception {
    adapter.add(ITEM);

    assertFalse(adapter.isEmpty());
    assertEquals(1, adapter.getItemCount());
  }

  private void assertIsEmpty() {
    assertEquals(0, adapter.getItemCount());
    assertTrue(adapter.isEmpty());
  }
}
