package com.github.techisfun.android.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class RangeSeekBarFixedMinimum<T extends Number> extends RangeSeekBar<T> {

    public RangeSeekBarFixedMinimum(T absoluteMinValue, T absoluteMaxValue, Context context) throws IllegalArgumentException {
        super(absoluteMinValue, absoluteMaxValue, context);

        notifyWhileDragging = true;
    }

    @Override
    protected void drawMinThumb(Canvas canvas) {
        // do nothing
    }

    /**
     * Handles thumb selection and movement. Notifies listener callback on
     * certain events.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled())
            return false;

        final int action = event.getAction();

        if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {

            int mActivePointerId = event.getPointerId(event.getPointerCount() - 1);
            int pointerIndex = event.findPointerIndex(mActivePointerId);

            float mDownMotionX = event.getX(pointerIndex);

            Thumb pressedThumb = evalPressedThumb(mDownMotionX);

            // Only handle thumb presses.
            if (pressedThumb == null || pressedThumb == Thumb.MIN) {
                return false;
            }
        }

        return super.onTouchEvent(event);
    }

}
