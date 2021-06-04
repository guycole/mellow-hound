package net.braingang.mellow_hound;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

public class EclecticService extends IntentService {
    public static final String LOG_TAG = EclecticService.class.getName();

    public static final int REQUEST_CODE = 6789;

    public EclecticService() {
        super("EclecticService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG, "-x-x-x-x-x-x-x-x-x- onHandleIntent -x-x-x-x-x-x-x-x-x-");

        if (intent != null) {

        }

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (LocationResult.hasResult(intent)) {
            Personality.locationResult = LocationResult.extractResult(intent);

            if (!wifiManager.isWifiEnabled()) {
                Log.i(LOG_TAG, "wifi disabled");
            } else {
                Log.i(LOG_TAG, "wifi enabled");
                wifiManager.startScan();
            }
        } else {
            Log.i(LOG_TAG, "location result false");
        }
    }
}