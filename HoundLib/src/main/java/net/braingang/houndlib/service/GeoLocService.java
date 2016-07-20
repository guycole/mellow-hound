package net.braingang.houndlib.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class GeoLocService extends Service {
    public static final String LOG_TAG = GeoLocService.class.getName();

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    private LocationListener locationListener1;
    private LocationListener locationListener2;

    public GeoLocService() {
        // empty
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "xxx xxx OnStartCommand" + startId + " xxx xxx");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "xxx xxx onCreate xxx xxx ");

        locationListener1 = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged1:" + location);
                freshLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStatusChanged1:" + status + ":" + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(LOG_TAG, "onProviderEnabled1:" + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(LOG_TAG, "onProviderDisabled1:" + provider);
            }
        };

        locationListener2 = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(LOG_TAG, "onLocationChanged2:" + location);
                freshLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(LOG_TAG, "onStatusChanged2:" + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(LOG_TAG, "onProviderEnabled2:" + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(LOG_TAG, "onProviderDisabled2:" + provider);
            }
        };

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GEO_MIN_TIME, GEO_MIN_DISTANCE, locationListener1);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GEO_MIN_TIME, GEO_MIN_DISTANCE, locationListener2);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "xxx xxx onDestroy xxx xxx");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void freshLocation(Location location) {
        cueTone();
        writeTelephony();
    }

    private void cueTone() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
    }

    private void writeTelephony() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        System.out.println("operator:" + telephonyManager.getNetworkOperator());
        System.out.println("operator:" + telephonyManager.getNetworkOperatorName());

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();
        System.out.println("cell info:" + cellList.size());

        for (CellInfo cellInfo:cellList) {
            System.out.println(cellInfo);
        }
    }
}
