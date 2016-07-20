package net.braingang.mellow.hound.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.braingang.houndlib.service.GeoLocService;

import net.braingang.mellow.hound.R;

import java.util.List;


public class MainActivity extends Activity {
    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String[] INITIAL_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int BASE_REQUEST = 1234;
    private static final int COARSE_LOCATION_REQUEST = BASE_REQUEST;
    private static final int FINE_LOCATION_REQUEST = BASE_REQUEST+1;
    private static final int EXTERNAL_STORAGE_REQUEST = BASE_REQUEST+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!canAccessCoarseLocation()) {
            requestPermissions(INITIAL_PERMS, COARSE_LOCATION_REQUEST);
        }

        if (!canAccessFineLocation()) {
            requestPermissions(INITIAL_PERMS, FINE_LOCATION_REQUEST);
        }

        if (!canAccessExternalStorage()) {
            requestPermissions(INITIAL_PERMS, EXTERNAL_STORAGE_REQUEST);
        }

    //    writeTelephony();
    //    Intent intent = new Intent(this, GeoLocService.class);
    //    startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissions:" + permissions.length);

        if (canAccessCoarseLocation() && canAccessFineLocation()) {
            Intent intent = new Intent(this, GeoLocService.class);
            startService(intent);
        }

        Log.i(LOG_TAG, "listeners established");
    }

    private boolean canAccessCoarseLocation() {
        boolean flag = hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        return flag;
    }

    private boolean canAccessFineLocation() {
        boolean flag = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        return flag;
    }

    private boolean canAccessExternalStorage() {
        boolean flag = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return flag;
    }

    private boolean hasPermission(String arg) {
        boolean flag = (PackageManager.PERMISSION_GRANTED == checkSelfPermission(arg));
        return true;
    }

    private void writeTelephony() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        System.out.println("operator:" + telephonyManager.getNetworkOperator());
        System.out.println("operator:" + telephonyManager.getNetworkOperatorName());

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();
        System.out.println("cell info:" + cellList.size());

        for (CellInfo cellInfo:cellList) {
            System.out.println(cellInfo);

            if (cellInfo instanceof CellInfoCdma) {
                System.out.println("cdma");
            } else if (cellInfo instanceof CellInfoGsm) {
                System.out.println("gsm");
            } else if (cellInfo instanceof CellInfoLte) {
                System.out.println("lte");
            } else if (cellInfo instanceof CellInfoWcdma) {
                CellInfoWcdma wcdma = (CellInfoWcdma) cellInfo;
                System.out.println("wcdma");
                System.out.println("cid:" + wcdma.getCellIdentity());
            } else {
                Log.e(LOG_TAG, "Unknown cellInfo type");
            }
        }
    }
}
