package net.braingang.houndlib;

import android.app.Application;
import android.util.Log;

import net.braingang.houndlib.utility.UserPreferenceHelper;

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

        UserPreferenceHelper userPreferenceHelper = new UserPreferenceHelper();
        if (userPreferenceHelper.isEmptyPreferences(this)) {
            userPreferenceHelper.writeDefaults(this);
            userPreferenceHelper.setBleCollection(this, true);
            userPreferenceHelper.setCellularCollection(this, true);
        }
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
