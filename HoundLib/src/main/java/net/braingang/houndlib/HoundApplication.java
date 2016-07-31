package net.braingang.houndlib;

import android.app.Application;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import net.braingang.houndlib.service.OutboundService;
import net.braingang.houndlib.utility.UserPreferenceHelper;

import java.util.ArrayList;

/**
 *
 */
public class HoundApplication extends Application {
    public static final String LOG_TAG = HoundApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");
        Log.i(LOG_TAG, "xo start start start start start xo");
        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");

        Personality.wifiScanList = new ArrayList<ScanResult>();
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Log.i(LOG_TAG, "wifi disabled");
        } else {
            wifiManager.setWifiEnabled(true);
            wifiManager.startScan();
        }

        UserPreferenceHelper userPreferenceHelper = new UserPreferenceHelper();
        if (userPreferenceHelper.isEmptyPreferences(this)) {
            userPreferenceHelper.writeDefaults(this);
            userPreferenceHelper.setBleCollection(this, true);
            userPreferenceHelper.setCellularCollection(this, true);
            userPreferenceHelper.setWiFiCollection(this, true);
        }

        OutboundService.startOutbound(this, Constant.ONE_HOUR);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(LOG_TAG, "low memory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(LOG_TAG, "terminate");
    }

    @Override
    public void onTrimMemory(int arg) {
        super.onTrimMemory(arg);
        Log.i(LOG_TAG, "trim memory:" + arg);
    }
}