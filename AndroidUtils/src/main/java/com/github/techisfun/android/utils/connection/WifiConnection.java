package com.github.techisfun.android.utils.connection;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class WifiConnection implements ConnectionManager {

    private static final String TAG = WifiConnection.class.getSimpleName();

    @Override
    public void enable(Context context) {
        Log.d(TAG, "activating wifi");

        getWifiSystemManager(context).setWifiEnabled(true);
    }

    @Override
    public void disable(Context context) {
        Log.d(TAG, "deactivating wifi");
        getWifiSystemManager(context).setWifiEnabled(false);
    }

    @Override
    public boolean isEnabled(Context context) throws Exception {
        WifiManager wifiSystemManager = getWifiSystemManager(context);
        boolean result = wifiSystemManager.isWifiEnabled();
        Log.d(TAG, "wifi enabled: " + result);
        return result;
    }

    private WifiManager getWifiSystemManager(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager;
    }

}
