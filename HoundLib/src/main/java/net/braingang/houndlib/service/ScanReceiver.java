package net.braingang.houndlib.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import net.braingang.houndlib.Personality;


public class ScanReceiver extends BroadcastReceiver {
    public static final String LOG_TAG = ScanReceiver.class.getName();

    public ScanReceiver() {
        // empty
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (Personality.wifiScanList.size() > 3333) {
            Log.i(LOG_TAG, "scan list limit noted");
        } else {
            Personality.wifiScanList.addAll(wifiManager.getScanResults());
        }

        Log.i(LOG_TAG, "scan list length:" + Personality.wifiScanList.size());
    }
}