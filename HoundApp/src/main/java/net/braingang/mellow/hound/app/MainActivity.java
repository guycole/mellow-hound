package net.braingang.mellow.hound.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.braingang.houndlib.ModeManager;
import net.braingang.houndlib.service.NoiseService;
import net.braingang.mellow.hound.R;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    public static final int BASE_REQUEST = 1234;
    public static final int COARSE_LOCATION_REQUEST = BASE_REQUEST;
    public static final int FINE_LOCATION_REQUEST = BASE_REQUEST+1;
    public static final int READ_PHONE_STATE_REQUEST = BASE_REQUEST+2;
    public static final int EXTERNAL_STORAGE_REQUEST = BASE_REQUEST+3;

    private final ModeManager modeManager = new ModeManager();

    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        permissionTest();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissions:" + requestCode + ":" + permissions.length + ":" + grantResults.length);
        for (int ii = 0; ii < permissions.length; ii++) {
            Log.i(LOG_TAG, "permissions[" + ii + "]=" + permissions[ii]);
            Log.i(LOG_TAG, "grant[" + ii + "]=" + grantResults[ii]);

            if (grantResults[ii] < 0) {
                Log.i(LOG_TAG, "skipping " + permissions[ii]);
            } else {
                if (permissions[ii].equals(Manifest.permission.READ_PHONE_STATE)) {
  //                  NoiseService.startActionNotification(this);
                }
            }
        }
    }

    private boolean permissionTest() {
        Log.i(LOG_TAG, "permTest");

        if (canAccessCoarseLocation()) {
            Log.i(LOG_TAG, "perm coarse loc granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_REQUEST);
            return true;
        }

        if (canAccessFineLocation()) {
            Log.i(LOG_TAG, "perm fine loc granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST);
            return true;
        }

        if (canReadPhoneState()) {
            Log.i(LOG_TAG, "perm phone state granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST);
            return true;
        }

        if (canAccessExternalStorage()) {
            Log.i(LOG_TAG, "perm extern storage granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST);
            return true;
        }

        return false;
    }

    private boolean canAccessCoarseLocation() {
        Log.i(LOG_TAG, "accessCoarse");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean canAccessFineLocation() {
        Log.i(LOG_TAG, "accessFine");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean canReadPhoneState() {
        Log.i(LOG_TAG, "phoneState");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean canAccessExternalStorage() {
        Log.i(LOG_TAG, "externalStore");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_run_mode:
                modeManager.setRunMode(true, getApplicationContext());
                break;
            case R.id.action_sample_mode:
                modeManager.setSampleMode(getApplicationContext());
                break;
            case R.id.action_stop_mode:
                modeManager.setRunMode(false, getApplicationContext());
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
