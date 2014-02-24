package com.github.techisfun.android.utils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class NoOverlappingToast {

    private static final long INTERVAL = 5*1000; // 5 seconds

    private long lastToastTime;

    private CharSequence mText;

    private Context mContext;

    public NoOverlappingToast(Context context, int resId) {
        mContext = context;
        mText = mContext.getString(resId);
    }

    public NoOverlappingToast(Context context, CharSequence text) {
        mContext = context;
        mText = text;
    }

    public void show() {
        if (!enoughtTimeElapsed(lastToastTime)) {
            // avoid overlapping toasts
            return;
        }

        Toast.makeText(mContext, mText, Toast.LENGTH_LONG).show();

        lastToastTime = System.currentTimeMillis();
    }

    protected static boolean enoughtTimeElapsed(long lastToastTime) {
        if (lastToastTime < 1) {
            return true;
        } else {
            return (System.currentTimeMillis() - lastToastTime) >= INTERVAL;
        }
    }

}
