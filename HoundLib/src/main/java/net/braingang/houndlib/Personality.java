package net.braingang.houndlib;

import android.app.PendingIntent;
import android.location.Location;

/**
 *
 */
public class Personality {

    /**
     * most recently reported location
     */
    public static Location currentLocation;

    /**
     * GeoLocService pending intent
     */
    public static PendingIntent geoLocPending;

    /**
     *
     */
    public static Boolean flag = false;
}
