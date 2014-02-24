package com.github.techisfun.android.utils.toast;

import com.github.techisfun.android.utils.RobolectricGradleTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class NoOverlappingToastTest {

    @Test
    public void testSimpleToast() {
        new NoOverlappingToast(Robolectric.application, "a simple toast").show();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("a simple toast"));
    }

    @Test
    public void testTwoNonOverlappingToasts() throws InterruptedException {
        new NoOverlappingToast(Robolectric.application, "a simple toast part 1").show();

        Thread.sleep(NoOverlappingToast.INTERVAL + 1000);

        new NoOverlappingToast(Robolectric.application, "a simple toast part 2").show();

        ShadowHandler.idleMainLooper();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("a simple toast part 2"));
    }

}
