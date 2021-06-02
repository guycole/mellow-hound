package net.braingang.mellow_hound;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.braingang.mellow_hound.databinding.ActivityMainBinding;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HoundListener, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String LOG_TAG = MainActivity.class.getName();

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ControlViewModel controlViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(LOG_TAG, "onCreate onCreate onCreate");

        // not all phones respect manifest android:screenOrientation="portrait"
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // cannot sleep or collection stops
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        controlViewModel = new ViewModelProvider(this).get(ControlViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
        unregisterReceiver(scanReceiver);
    }

    private void getPermissions() {
        int permissionFlag = ContextCompat.checkSelfPermission(this, LocationManager.GPS_PROVIDER);
        if (PackageManager.PERMISSION_DENIED == permissionFlag) {
            Log.i(LOG_TAG, "must ask location permission");

            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_WIFI_STATE
            };

            ActivityCompat.requestPermissions(this, permissions, EclecticService.REQUEST_CODE);
        } else {
            Log.i(LOG_TAG, "location permission granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult:" + requestCode);

        // location manager access granted
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(Constant.ONE_MINUTE);
        locationRequest.setFastestInterval(Constant.THIRTY_SECOND);

        Intent intent = new Intent(this, EclecticService.class);
        pi = PendingIntent.getService(this, EclecticService.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(locationRequest, pi);

        controlViewModel.setRunMode(getString(R.string.running));
    }

    @Override
    public void onAwsUpload() {
        FileFacade fileFacade = new FileFacade();
        File[] files = fileFacade.getObservations(this);

        AwsUpload upload = new AwsUpload();
        upload.writer(this, files);
    }

    @Override
    public void onCollectionStart() {
        getPermissions();
    }

    @Override
    public void onCollectionStop() {
        controlViewModel.setRunMode(getString(R.string.stopped));
        fusedLocationClient.removeLocationUpdates(pi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.i(LOG_TAG, "xxxxxxx onReceive xxxxxxxxxx");

            FileFacade fileFacade = new FileFacade();

            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> scanResults = wifiManager.getScanResults();

            if (scanResults.size() > 0) {
                Log.i(LOG_TAG, "scan results:" + scanResults.size());
                Observation observation = new Observation(Personality.locationResult, scanResults);

                fileFacade.writeObservation(observation, getApplicationContext());
            } else {
                Log.i(LOG_TAG, "scan results empty");
            }

            File[] files = fileFacade.getObservations(getApplicationContext());

            controlViewModel.setCounter(scanResults.size(), files.length);
        }
    };
}