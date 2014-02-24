package com.github.techisfun.android.utils.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class DataConnection implements ConnectionManager {

    private static final String TAG = DataConnection.class.getSimpleName();

    @Override
    public void enable(Context context) throws Exception {
        Log.d(TAG, "activating data connection");

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled",
                boolean.class);

        dataMtd.setAccessible(true);
        dataMtd.invoke(mgr, true);
    }

    @Override
    public void disable(Context context) throws Exception {
        Log.d(TAG, "deactivating data connection");

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled",
                boolean.class);

        dataMtd.setAccessible(true);
        dataMtd.invoke(mgr, false);
    }

    @Override
    public boolean isEnabled(Context context) throws Exception {

        boolean result = false;

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
        if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            result = activeNetworkInfo.isConnectedOrConnecting();
        }

        Log.d(TAG, "mobile data enabled result: " + result);

        return result;
    }

}
