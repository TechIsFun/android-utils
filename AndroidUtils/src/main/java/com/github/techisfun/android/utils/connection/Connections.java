package com.github.techisfun.android.utils.connection;

import com.github.techisfun.android.utils.R;

import android.content.Context;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public enum Connections {

    WIFI(new WifiConnection(), R.string.wifi),

    DATA(new DataConnection(), R.string.data);

    private ConnectionManager connectionManager;

    private int stringResourceId;

    Connections(ConnectionManager connectionManager, int stringResourceId) {
        this.connectionManager = connectionManager;
        this.stringResourceId = stringResourceId;
    }

    public boolean isEnabled(Context context) throws Exception {
        return connectionManager.isEnabled(context);
    }

    public void enable(Context context) throws Exception {
        connectionManager.enable(context);
    }

    public void disable(Context context) throws Exception {
        connectionManager.disable(context);
    }

    public static Connections lookup(String value) {
        if (DATA.toString().equals(value)) {
            return DATA;
        } else if (WIFI.toString().equals(value)) {
            return WIFI;
        }

        throw new IllegalArgumentException("Unknown value " + value);
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

}
