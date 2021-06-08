package net.braingang.mellow_hound;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import java.util.List;

/**
 * Receiver for handling location updates.
 *
 * For apps targeting API level O
 * {@link android.app.PendingIntent#getBroadcast(Context, int, Intent, int)} should be used when
 * requesting location updates. Due to limits on background services,
 * {@link android.app.PendingIntent#getService(Context, int, Intent, int)} should not be used.
 *
 *  Note: Apps running on "O" devices (regardless of targetSdkVersion) may receive updates
 *  less frequently than the interval specified in the
 *  {@link com.google.android.gms.location.LocationRequest} when the app is no longer in the
 *  foreground.
 *
 * https://github.com/android/location-samples/blob/432d3b72b8c058f220416958b444274ddd186abd/LocationUpdatesPendingIntent/app/src/main/java/com/google/android/gms/location/sample/locationupdatespendingintent/LocationUpdatesBroadcastReceiver.java
 */
public class LocationUpdates extends BroadcastReceiver {
    public static final String LOG_TAG = LocationUpdates.class.getName();

    static final String ACTION_PROCESS_UPDATES = "net.braingang.mellow_hound.PROCESS_UPDATES";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "xxx xxx onReceive xxx xxx");

        if (intent == null) {
            Log.i(LOG_TAG, "null intent");
        } else {
            Log.i(LOG_TAG, "not null intent");

            final String action = intent.getAction();
            Log.i(LOG_TAG, "action:" + action);
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result == null) {
                    Log.i(LOG_TAG, "result null");
                } else {
                    Log.i(LOG_TAG, "result not null");
                    List<Location> locations = result.getLocations();
                    Log.i(LOG_TAG, "location size:" + locations.size());

                    Personality.locationResult = LocationResult.extractResult(intent);

                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                    if (!wifiManager.isWifiEnabled()) {
                        Log.i(LOG_TAG, "wifi disabled");
                    } else {
                        Log.i(LOG_TAG, "wifi enabled");
                        wifiManager.startScan();
                    }
                }
            }
        }
    }
}
