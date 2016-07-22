package net.braingang.houndlib.service;

import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.braingang.houndlib.Personality;
import net.braingang.houndlib.db.CellularModel;
import net.braingang.houndlib.db.ContentFacade;
import net.braingang.houndlib.db.GeoLocModel;

import java.util.List;

/**
 *
 */
public class GeoLocService extends IntentService {
    public static final String LOG_TAG = GeoLocService.class.getName();

    public static final int REQUEST_CODE = 6789;

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    private ContentFacade contentFacade = new ContentFacade();

    public GeoLocService() {
        super("GeoLocService");
    }

    /**
     *
     * @param context
     */
    public static void startGeoLoc(Context context) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        Personality.geoLocPending = PendingIntent.getService(context, REQUEST_CODE, new Intent(context, GeoLocService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(GEO_MIN_TIME, GEO_MIN_DISTANCE, criteria, Personality.geoLocPending);
    }

    public static void startGeoLoc(Context context, long period) {
        Personality.geoLocPending = PendingIntent.getService(context, REQUEST_CODE, new Intent(context, GeoLocService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), period, Personality.geoLocPending);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG, "handle handle handle");
        Log.i(LOG_TAG, "handle handle handle");
        Log.i(LOG_TAG, "handle handle handle");

        Personality.counter += 1;
        Log.i(LOG_TAG, "counter:" + Personality.counter);

        Bundle bundle = intent.getExtras();
        if ((bundle != null) && (bundle.containsKey(LocationManager.KEY_LOCATION_CHANGED))) {
            Location location = (Location) bundle.get(LocationManager.KEY_LOCATION_CHANGED);
            GeoLocModel geoLocModel = freshLocation(location);
            collectCellular(geoLocModel);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            GeoLocModel geoLocModel = freshLocation(location);
            if (geoLocModel == null) {
                Log.i(LOG_TAG, "null geoloc");
            } else {
                Log.i(LOG_TAG, "id:" + geoLocModel.getId());
                collectCellular(geoLocModel);
            }
        }
    }

    private CellularModel saveFreshCellular(GeoLocModel geoLocModel, CellInfo cellInfo) {
        System.out.println(cellInfo);
        return contentFacade.insertCellular(geoLocModel, cellInfo, this);
    }

    private void collectCellular(GeoLocModel geoLocModel) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        System.out.println("operator:" + telephonyManager.getNetworkOperator());
        System.out.println("operator:" + telephonyManager.getNetworkOperatorName());

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();
        System.out.println("cell info:" + cellList.size());

        for (CellInfo cellInfo:cellList) {
            saveFreshCellular(geoLocModel, cellInfo);
        }
    }

    private GeoLocModel saveFreshLocation(Location location) {
        Personality.currentLocation = location;
        return contentFacade.insertLocation(location, this);
    }

    private GeoLocModel freshLocation(Location location) {
        Log.i(LOG_TAG, "fresh location noted:" + location.getProvider() + ":" + location.getTime());

        if (Personality.currentLocation == null) {
            Log.i(LOG_TAG, "null current");
            return saveFreshLocation(location);
        }

        if ((Personality.currentLocation.getTime() == location.getTime()) && (Personality.currentLocation.getProvider().equals(location.getProvider()))) {
            Log.i(LOG_TAG, "current location match");
            return null;
        } else {
            Log.i(LOG_TAG, "current location fail");
            return saveFreshLocation(location);
        }
    }

    private void playAlert() {
        Log.i(LOG_TAG, "playAlert");

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
