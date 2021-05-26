package net.braingang.mellow_hound;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellInfo;

import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import net.braingang.mellow_hound.Personality;
//import net.braingang.houndlib.utility.UserPreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * container
 */
public class Observation implements Serializable {
    private ArrayList<WiFi> wiFi = new ArrayList<WiFi>();

    private GeoLoc geoLoc;

    public Observation(LocationResult locationResult, List<ScanResult> scanResults) {
        geoLoc = new GeoLoc(locationResult.getLastLocation());

        for (ScanResult scanResult:scanResults) {
            wiFi.add(new WiFi(scanResult));
        }
    }

    public GeoLoc getGeoLoc() {
        return geoLoc;
    }

    public JSONObject toJson(Context context) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 1);

        jsonObject.put("geoLoc", geoLoc.toJson());

        JSONArray wiFiArray = new JSONArray();
        for (WiFi current:wiFi) {
            wiFiArray.put(current.toJson());
        }
        jsonObject.put("wiFi", wiFiArray);

        return jsonObject;
    }
}