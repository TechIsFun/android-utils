package com.github.techisfun.android.utils.network;


import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network status listener;  usage example:
 * <pre>
 * {@code
 *   class MyClass implements OnNetworkStatusChange
 *
 *   onStart() {
 *     NetworkListener.start(context);
 *   }
 *
 *   onStop() {
 *     NetworkListener.stop();
 *   }
 *
 *   doOnNetworkAvailable() {
 *     do something as soon as network is available
 *   }
 *
 *   doOnNetworkError() {
 *     do something when network is not available
 *   }
 *  }
 *  </pre>
 * @author Andrea Maglie
 *
 */
public class NetworkListener {

    private final OnNetworkStatusChange onNetworkStatusChange;

    private BroadcastReceiver broadcastReceiver;

    public NetworkListener(OnNetworkStatusChange onNetworkAvailable) {
        this.onNetworkStatusChange = onNetworkAvailable;
    }

    public void start(Context context) {
        if (isConnected(context)) {
            onNetworkStatusChange.doOnNetworkAvailable();
        } else {
            registerNetworkStateListener(context);
        }
    }

    public void stop(Context context) {
        unregisterNetworkStateReceiver(context);
    }

    /**
     * Register connectivity state listener
     */
    protected void registerNetworkStateListener(Context context) {

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                    return;
                }

                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if (networkInfo == null) {
                    // Messages.NETWORK_ERROR.toast(context);
                    onNetworkStatusChange.doOnNetworkError();
                } else if (networkInfo.isConnected()) {
                    // areaSpinner.getAreas();
                    // Messages.NETWORK_OK.toast(getApplicationContext());
                    onNetworkStatusChange.doOnNetworkAvailable();
                    unregisterNetworkStateReceiver(context);
                } else if (networkInfo.isConnectedOrConnecting()) {
                    // trying to connect? do nothing
                } else {
                    // Messages.NETWORK_ERROR.toast(getApplicationContext());
                    onNetworkStatusChange.doOnNetworkError();
                }

            }
        };

        context.registerReceiver(broadcastReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Unregister connectivity state listener
     */
    protected void unregisterNetworkStateReceiver(Context context) {
        if (broadcastReceiver != null) {
            // unregister network state receiver
            try {
                context.unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
                // ok
            }
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }

        return false;
    }
}

interface OnNetworkStatusChange {

    public void doOnNetworkAvailable();

    public void doOnNetworkError();
}