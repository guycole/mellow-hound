package net.braingang.houndlib;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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
