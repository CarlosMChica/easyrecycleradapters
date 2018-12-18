package com.carlosdelachica.easyrecycleradapters.adapter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EasyRecyclerAdapterTest {

    public static final List<Object> LIST = singletonList(new Object());
    public static final List<Object> LIST_WITH_3_ITEMS =
            asList(new Object(), new Object(), new Object());
    public static final Object ITEM = new Object();
    public static final Object ITEM_AT_INDEX_3 = new Object();
    public static final List<Object> LIST_WITH_4_ITEMS =
            asList(new Object(), new Object(), new Object(), ITEM_AT_INDEX_3);
    Context context;
    EasyRecyclerAdapter adapter;

    @Before
    public void setup() {
        context = getInstrumentation().getContext();
        adapter = new EasyRecyclerAdapter(context);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAllNullList_throwsIllegalArgumentException() {
        adapter.addAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppendAllNullList_throwsIllegalArgumentException() {
        adapter.appendAll(null);
    }

    @Test
    public void testAddAll_returnsItemCountEqualsListSize() {
        adapter.addAll(LIST_WITH_3_ITEMS);

        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void testUpdateExistingItem_returnTrueAndReturnUpdatedItemAndDoNotChangeItemCount() {
        Object updatedItem = new Object();

        adapter.add(ITEM);
        boolean updated = adapter.update(updatedItem, 0);

        assertTrue(updated);
        assertEquals(1, adapter.getItemCount());
        assertEquals(updatedItem, adapter.get(0));
    }

    @Test
    public void testRemoveWithNullParameter_returnsFalse() {
        boolean remove = adapter.remove(null);

        assertFalse(remove);
    }

    @Test
    public void testRemoveValidItem_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne() {
        adapter.add(ITEM);

        boolean remove = adapter.remove(ITEM);

        assertTrue(remove);
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void testRemovedInvalidIndex_returnsFalse() {
        boolean remove = adapter.remove(-1);

        assertFalse(remove);
    }

    @Test
    public void testGetItem_returnsCorrectItem() {
        adapter.addAll(LIST_WITH_4_ITEMS);

        Object item = adapter.get(3);

        assertEquals(ITEM_AT_INDEX_3, item);
    }

    @Test
    public void testGetIndex_returnsCorrectIndex() {
        adapter.addAll(LIST_WITH_4_ITEMS);

        int adapterIndex = adapter.getIndex(ITEM_AT_INDEX_3);

        assertEquals(3, adapterIndex);
    }

    @Test
    public void testRemoveValidIndex_returnsTrueAndRemovesObjectAndDecreaseItemCountByOne() {
        adapter.add(ITEM);

        boolean remove = adapter.remove(0);

        assertTrue(remove);
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void testClear_emptyDataSet() {
        adapter.addAll(LIST);

        adapter.clear();

        assertIsEmpty();
    }

    @Test
    public void testIsEmptyByDefault() {
        assertIsEmpty();
    }

    @Test
    public void testIsNotEmptyAfterAddingItem() {
        adapter.add(ITEM);

        assertFalse(adapter.isEmpty());
        assertEquals(1, adapter.getItemCount());
    }

    private void assertIsEmpty() {
        assertEquals(0, adapter.getItemCount());
        assertTrue(adapter.isEmpty());
    }
}
