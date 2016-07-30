package net.braingang.houndlib.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.braingang.houndlib.Personality;
import net.braingang.houndlib.model.FileFacade;
import net.braingang.houndlib.model.Observation;
import net.braingang.houndlib.utility.UserPreferenceHelper;

import java.util.List;

/**
 * Write observation for each location update;
 */
public class GeoLocService extends IntentService {
    public static final String LOG_TAG = GeoLocService.class.getName();

    public static final int REQUEST_CODE = 6789;

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    public static final long BLE_SCAN_DURATION = 3333L;

    private Boolean bleScanComplete = false;
    private Handler bleScanHandler = new Handler();

    private FileFacade fileFacade = new FileFacade();
    private Observation observation = new Observation();
    private UserPreferenceHelper userPreferenceHelper = new UserPreferenceHelper();

    public GeoLocService() {
        super("GeoLocService");
    }

    /**
     * start the geoloc service
     * @param context
     * @param singleFlag true, perform only one collection cycle else run until cancelled
     */
    public static void startGeoLoc(Context context, boolean singleFlag) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        if (Personality.geoLocPending != null) {
            Log.i(LOG_TAG, "overwrite non null pending intent");
        }

        Personality.geoLocPending = PendingIntent.getService(context, REQUEST_CODE, new Intent(context, GeoLocService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (singleFlag) {
            locationManager.requestSingleUpdate(criteria, Personality.geoLocPending);
        } else {
            locationManager.requestLocationUpdates(GEO_MIN_TIME, GEO_MIN_DISTANCE, criteria, Personality.geoLocPending);
        }
    }

    /*
    public static void startGeoLoc(Context context, long period) {
        Personality.geoLocPending = PendingIntent.getService(context, REQUEST_CODE, new Intent(context, GeoLocService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), period, Personality.geoLocPending);
    }
    */

    public static void stopGeoLoc(Context context) {
        if (Personality.geoLocPending == null) {
            Log.i(LOG_TAG, "unable to stop w/null pending intent");
        } else {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(Personality.geoLocPending);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG, "onHandleIntent");

        Personality.counter += 1;
        Log.i(LOG_TAG, "counter:" + Personality.counter);

        Bundle bundle = intent.getExtras();
        if ((bundle != null) && (bundle.containsKey(LocationManager.KEY_LOCATION_CHANGED))) {
            Location location = (Location) bundle.get(LocationManager.KEY_LOCATION_CHANGED);
            freshLocation(location);

            if (userPreferenceHelper.isBleCollection(this)) {
                collectBle();
            } else {
                Log.i(LOG_TAG, "BLE collection disabled");
            }

            if (userPreferenceHelper.isCellularCollection(this)) {
                collectCellular();
            } else {
                Log.i(LOG_TAG, "Cellular collection disabled");
            }

            if (userPreferenceHelper.isWiFiCollection(this)) {
                collectWiFi();
            } else {
                Log.i(LOG_TAG, "WiFi collection disabled");
            }

            while (!bleScanComplete) {
                try {
                    Thread.sleep(500L);
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }

            fileFacade.writeObservation(observation, this);
        } else {
            Log.i(LOG_TAG, "skipping intent w/missing location");
            return;
        }
    }

    private BluetoothAdapter.LeScanCallback bleScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            observation.addBleObservation(device, rssi, scanRecord);
        }
    };

    private void collectBle() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter btAdapter = manager.getAdapter();

        if (btAdapter == null) {
            Log.i(LOG_TAG, "null bluetooth adapter");
            return;
        }

        if (btAdapter.isEnabled()) {
            btAdapter.startLeScan(bleScanCallback);

            bleScanHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btAdapter != null) {
                        btAdapter.stopLeScan(bleScanCallback);
                    }

                    bleScanComplete = true;
                }
            }, BLE_SCAN_DURATION);
        } else {
            Log.i(LOG_TAG, "bluetooth adapter disabled");
        }
    }

    private void collectCellular() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String operator = telephonyManager.getNetworkOperator();
        String opName = telephonyManager.getNetworkOperatorName();

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();

        observation.addCellular(operator, opName, cellList);
    }

    private void collectWiFi() {
        observation.addWiFi();
        Personality.wifiScanList.clear();
    }

    private void saveFreshLocation(Location location) {
        Personality.currentLocation = location;
        observation.setLocation(location);
    }

    private void freshLocation(Location location) {
        Log.i(LOG_TAG, "fresh location noted:" + location.getProvider() + ":" + location.getTime());

        if (Personality.currentLocation == null) {
            Log.i(LOG_TAG, "null current");
            saveFreshLocation(location);
        }

        if ((Personality.currentLocation.getTime() == location.getTime()) && (Personality.currentLocation.getProvider().equals(location.getProvider()))) {
            Log.i(LOG_TAG, "current location match");
        } else {
            Log.i(LOG_TAG, "current location fail");
            saveFreshLocation(location);
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
