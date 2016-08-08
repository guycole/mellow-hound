package net.braingang.houndlib;

import android.content.Context;
import android.util.Log;
import net.braingang.houndlib.service.GeoLocService;

/**
 *
 */
public class ModeManager {
    public static final String LOG_TAG = ModeManager.class.getName();

    public void setRunMode(boolean startFlag, Context context) {
        Log.i(LOG_TAG, "setRunMode:" + startFlag);

        if (startFlag) {

        } else {
            Personality.runMode = ModeEnum.STOPPED;
        }

    }

    public void setSampleMode(Context context) {
        Log.i(LOG_TAG, "setSampleMode");

        setRunMode(false, context);

        GeoLocService.startGeoLoc(context, true);
    }
}
