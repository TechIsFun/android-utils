package com.github.techisfun.android.utils.testing;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.robolectric.*;

import com.github.techisfun.android.utils.RobolectricGradleTestRunner;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class TestFragmentActivityTest {

    @Test
    public void testActivityIsNotNull() {
        TestFragmentActivity activity = Robolectric.buildActivity(TestFragmentActivity.class).create().get();

        assertNotNull(activity);
    }
}
