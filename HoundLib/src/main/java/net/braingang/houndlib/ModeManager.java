package net.braingang.houndlib;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import net.braingang.houndlib.service.GeoLocService;

/**
 *
 */
public class ModeManager {
    public static final String LOG_TAG = ModeManager.class.getName();

    public void setRunMode(boolean startFlag, Context context) {
        Log.i(LOG_TAG, "setRunMode:" + startFlag);

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (startFlag) {
            Personality.runMode = ModeEnum.RUNNING;

            if (!wifiManager.isWifiEnabled()) {
                Log.i(LOG_TAG, "wifi disabled");
            } else {
                wifiManager.setWifiEnabled(true);
                wifiManager.startScan();
            }

            GeoLocService.startGeoLoc(context, false);
        } else {
            Personality.runMode = ModeEnum.STOPPED;

            GeoLocService.stopGeoLoc(context);
        }
    }

    public void setSampleMode(Context context) {
        Log.i(LOG_TAG, "setSampleMode");

        setRunMode(false, context);

        GeoLocService.startGeoLoc(context, true);
    }
}
