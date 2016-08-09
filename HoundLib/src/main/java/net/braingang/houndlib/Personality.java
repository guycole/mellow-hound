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
     * Accumulated WiFi observations
     */
    public static ArrayList<ScanResult> wifiScanList;

    /**
     * Total quantity of files uploaded
     */
    public static Integer uploadCounter = 0;

    /**
     * Current Run Mode
     */
    public static ModeEnum runMode;
}
