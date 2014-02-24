package com.github.techisfun.android.utils.location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class MidPointCalculator {

    /**
     * Calc center position
     *
     * @param items a collection of Localizable items
     * @return
     */
    public LatLng getCenter(List<Localizable> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        if (items.size() == 1) {
            Localizable localizable = items.get(0);
            return new LatLng(localizable.getLatitude(), localizable.getLongitude());
        }

        if (items.size() == 2) {
            return midPoint(items.get(0).getLatitude(),
                    items.get(0).getLongitude(),
                    items.get(1).getLatitude(),
                    items.get(1).getLongitude());
        }

        double maxLatitude, minLatitude, maxLongitude, minLongitude;
        maxLatitude = minLatitude = items.get(0).getLatitude();
        maxLongitude = minLongitude = items.get(0).getLongitude();

        Localizable localizable;
        for (int i = 1; i < items.size(); i++) {
            localizable = items.get(i);
            maxLatitude = Math.max(maxLatitude, localizable.getLatitude());
            minLatitude = Math.min(minLatitude, localizable.getLatitude());
            maxLongitude = Math.max(maxLongitude, localizable.getLongitude());
            minLongitude = Math.min(minLongitude, localizable.getLongitude());
        }

        return midPoint(maxLatitude, maxLongitude, minLatitude,
                minLongitude);
    }

    /**
     * Get the center between two point using Haversine formula
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static LatLng midPoint(double lat1, double lon1, double lat2,
            double lon2) {

        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(
                Math.sin(lat1) + Math.sin(lat2),
                Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By
                        * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }
}
