package com.github.techisfun.android.utils.testing;

import com.github.techisfun.android.utils.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class TestFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_test_fragment);
    }
}
