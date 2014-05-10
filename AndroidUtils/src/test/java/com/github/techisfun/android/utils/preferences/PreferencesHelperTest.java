package com.github.techisfun.android.utils.preferences;

import android.test.InstrumentationTestCase;

import java.lang.String;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import android.content.Context;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.robolectric.*;

import com.github.techisfun.android.utils.RobolectricGradleTestRunner;

@RunWith(RobolectricGradleTestRunner.class)
public class PreferencesHelperTest {

    private PreferencesHelper mPreferencesHelper;

    @Before
    public void setUp() throws Exception {
        Context context = Robolectric.getShadowApplication().getApplicationContext();

        mPreferencesHelper = new PreferencesHelper(context);
    }

    @Test
    public void testPutStringList() {
        ArrayList<String> items = new ArrayList(Arrays.asList("batman", "superman", "pippo"));

        mPreferencesHelper.putStringList("test-key", items);

        ArrayList<String> savedItems = mPreferencesHelper.getStringList("test-key");

        assertNotNull(savedItems);
        assertEquals(3, savedItems.size());
        assertEquals("batman", savedItems.get(0));
        assertEquals("superman", savedItems.get(1));
        assertEquals("pippo", savedItems.get(2));
    }

    @Test
    public void testGetStringList_default() {
        ArrayList<String> defaultItems = new ArrayList(Arrays.asList("batman", "superman", "pippo"));

        assertSame(defaultItems, mPreferencesHelper.getStringList("not-exists", defaultItems));
    }

    @Test
    public void testPutStringSet() {
        HashSet<String> items = new HashSet<String>(Arrays.asList("batman", "superman", "pippo"));

        mPreferencesHelper.putStringSet("test-key", items);

        Set<String> savedItems = mPreferencesHelper.getStringSet("test-key");

        assertNotNull(savedItems);
        assertEquals(3, savedItems.size());
        assertTrue(savedItems.contains("batman"));
        assertTrue(savedItems.contains("superman"));
        assertTrue(savedItems.contains("pippo"));
    }

    @Test
    public void testGetStringSet_default() {
        HashSet<String> defaultItems = new HashSet<String>(Arrays.asList("batman", "superman", "pippo"));

        assertSame(defaultItems, mPreferencesHelper.getStringSet("not-exists", defaultItems));
    }
}