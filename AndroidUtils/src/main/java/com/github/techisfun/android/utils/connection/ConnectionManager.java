package com.github.techisfun.android.utils.connection;

import android.content.Context;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
interface ConnectionManager {

    public void enable(Context context) throws Exception;

    public void disable(Context context) throws Exception;

    public boolean isEnabled(Context context) throws Exception;

}
