package com.github.techisfun.android.utils;

import android.content.Context;
import android.location.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by Andrea Maglie on 24/02/14.
 *
 * Mixed utilities
 */
public abstract class Utils {

    private static final String TAG = Utils.class.getCanonicalName();

    /**
     * Sets left padding on a view
     * @param view
     * @param leftPadding
     * @param <T>
     * @return
     */
    public static <T extends View> T setLeftPadding(T view, int leftPadding) {
        if (view != null) {
            view.setPadding(leftPadding, view.getPaddingTop(),
                    view.getPaddingRight(), view.getPaddingBottom());
        }

        return view;
    }

    /**
     * Adds left padding to a view
     * @param view
     * @param leftPadding
     * @param <T>
     * @return
     */
    public static <T extends View> T addLeftPadding(T view, int leftPadding) {
        if (view != null) {
            view.setPadding(view.getPaddingLeft() + leftPadding,
                    view.getPaddingTop(), view.getPaddingRight(),
                    view.getPaddingBottom());
        }

        return view;
    }

    /**
     * Calculate scaled dimension
     * @param context
     * @param dimen
     * @return
     */
    public static int getScaledDimension(Context context, int dimen) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dimen * scale);
    }

    /**
     * Check if the device is connected Or connecting to the netowork
     * @param context
     * @return
     */
    public static boolean isConnectedOrConnecting(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnectedOrConnecting();
        }

        return false;
    }

    /**
     * Check if the device is connected to the netowork
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }

        return false;
    }

    /**
     * Get address from a Location object
     * @param context
     * @param location
     * @return
     */
    public static String getLocality(Context context, Location location) {
        if (isConnected(context)) {
            try {
                Geocoder geocoder = new Geocoder(context);

                List<Address> list = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);

                if (list != null && list.size() > 0) {
                    Address address = list.get(0);
                    return address.getThoroughfare() + ", "
                            + address.getLocality();
                }
            } catch (Exception e) {
                Log.d(TAG, "ignored", e);
            }
        }

        return null;
    }

    /**
     * Get city from a Location object
     * @param context
     * @param location
     * @return
     */
    public static String getCity(Context context, Location location) {
        try {
            Geocoder geocoder = new Geocoder(context);

            List<Address> list = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);

            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                String result = address.getLocality();
                return result;
            }
        } catch (Exception e) {
            // ok
        }

        return null;
    }

    /**
     * Tell me if wi-fi is activated
     * @param context
     */
    public static boolean isWifiOn(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        return wifiManager.isWifiEnabled();
    }

    /**
     * Tell me if gps is activated
     * @param context
     */
    public static boolean isGpsOn(Context context) {
        final LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Remove all views with a particular tag inside a given view
     * @param rootView
     * @param tag
     */
    public static void removeViewsWithTag(View rootView, String tag) {
        View view = rootView.findViewWithTag(tag);

        while (view != null) {
            view.setVisibility(View.GONE);

            view = rootView.findViewWithTag(tag);
        }
    }

    private Utils() {
        // nothing
    }
}
