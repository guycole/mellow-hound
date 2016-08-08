package net.braingang.houndlib;

import android.app.PendingIntent;
import android.location.Location;
import android.net.wifi.ScanResult;

import java.util.ArrayList;

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
    public static ArrayList<ScanResult> wifiScanList;

    /**
     *
     */
    public static Integer counter = 0;

    /**
     *
     */
    public static ModeEnum runMode;
}
