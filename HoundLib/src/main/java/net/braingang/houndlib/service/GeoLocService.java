package net.braingang.houndlib.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.util.Log;

/**
 *
 */
public class GeoLocService extends IntentService {
    public static final String LOG_TAG = GeoLocService.class.getName();

    public static final float GEO_MIN_DISTANCE = 1000L;
    public static final long GEO_MIN_TIME = 60 * 1000L;

    private static final String ACTION_START = "net.braingang.houndlib.service.action.START";
    private static final String ACTION_UPDATE = "net.braingang.houndlib.service.action.UPDATE";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "net.braingang.houndlib.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "net.braingang.houndlib.service.extra.PARAM2";

    public GeoLocService() {
        super("GeoLocService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startGeoLoc(Context context) {
        Intent intent = new Intent(context, GeoLocService.class);
        intent.setAction(ACTION_START);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GeoLocService.class);
      //  intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                handleActionStart();
            } else if (ACTION_UPDATE.equals(action)) {
                System.out.println("action update update update");
               // final String param1 = intent.getStringExtra(EXTRA_PARAM1);
               // final String param2 = intent.getStringExtra(EXTRA_PARAM2);
               // handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStart() {
        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");
        Log.i(LOG_TAG, "xo start start start start start xo");
        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");

        System.out.println("action start");

        Intent intent = new Intent(this, GeoLocService.class);
        intent.setAction(GeoLocService.ACTION_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(GEO_MIN_TIME, GEO_MIN_DISTANCE, criteria, pendingIntent);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "xxx xxx onDestroy xxx xxx");
    }
}
