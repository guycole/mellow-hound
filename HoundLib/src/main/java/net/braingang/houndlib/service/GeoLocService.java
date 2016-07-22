package net.braingang.houndlib.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import net.braingang.houndlib.Personality;

/**
 *
 */
public class GeoLocService extends IntentService {
    public static final String LOG_TAG = GeoLocService.class.getName();

    public static final int REQUEST_CODE = 6789;

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    public GeoLocService() {
        super("GeoLocService");
    }

    /**
     *
     * @param context
     */
    public static void startGeoLoc(Context context) {
        Intent intent = new Intent(context, GeoLocService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG, "handle handle handle");
        Log.i(LOG_TAG, "handle handle handle");
        Log.i(LOG_TAG, "handle handle handle");

        Bundle bundle = intent.getExtras();
        if ((bundle != null) && (bundle.containsKey(LocationManager.KEY_LOCATION_CHANGED))) {
            Location location = (Location) bundle.get(LocationManager.KEY_LOCATION_CHANGED);
            Log.i(LOG_TAG, "updated location:" + location.getTime());
            playAlert();
        }

        if (Personality.flag == false) {

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);

            PendingIntent pendingIntent = PendingIntent.getService(this, REQUEST_CODE, new Intent(this, GeoLocService.class), PendingIntent.FLAG_UPDATE_CURRENT);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(GEO_MIN_TIME, GEO_MIN_DISTANCE, criteria, pendingIntent);

            Personality.flag = true;
        } else {
            Log.i(LOG_TAG, "skipping skipping");
        }
    }

    private void playAlert() {
        try {
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);
            ringtone.play();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "xxx xxx onDestroy xxx xxx");
    }
}
