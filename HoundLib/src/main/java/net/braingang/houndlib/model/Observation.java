package net.braingang.houndlib.model;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellInfo;

import net.braingang.houndlib.Personality;

import net.braingang.houndlib.utility.UserPreferenceHelper;
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
    ArrayList<BlueToothLowEnergy> ble = new ArrayList<BlueToothLowEnergy>();
    ArrayList<Cellular> cellular = new ArrayList<Cellular>();
    ArrayList<WiFi> wiFi = new ArrayList<WiFi>();

    GeoLoc geoLoc;

    public ArrayList<BlueToothLowEnergy> getBle() {
        return ble;
    }

    public ArrayList<Cellular> getCellular() {
        return cellular;
    }

    public GeoLoc getGeoLoc() {
        return geoLoc;
    }

    public void setGeoLoc(GeoLoc arg) {
        geoLoc = arg;
    }

    public void addWiFi() {
        for (ScanResult scanResult: Personality.wifiScanList) {
            wiFi.add(new WiFi(scanResult));
        }

        Personality.wifiScanList.clear();
    }

    public void addBleObservation(BluetoothDevice device, int rssi, byte[] scanRecord) {
        ble.add(new BlueToothLowEnergy(device, rssi, scanRecord));
    }

    public void addCellular(List<CellInfo> cellList) {
        for (CellInfo cellInfo:cellList) {
            cellular.add(new Cellular(cellInfo));
        }
    }

    public void setLocation(Location location) {
        setGeoLoc(new GeoLoc(location));
    }

    public JSONObject toJson(Context context) throws JSONException {
        UserPreferenceHelper userPreferenceHelper = new UserPreferenceHelper();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("installation", userPreferenceHelper.getInstallationUuid(context));
        jsonObject.put("networkName", userPreferenceHelper.getNetworkName(context));
        jsonObject.put("networkOperator", userPreferenceHelper.getNetworkOperator(context));
        jsonObject.put("geoLoc", geoLoc.toJson());

        JSONArray bleArray = new JSONArray();
        for (BlueToothLowEnergy current:ble) {
            bleArray.put(current.toJson());
        }
        jsonObject.put("ble", bleArray);

        JSONArray cellArray = new JSONArray();
        for (Cellular current:cellular) {
            cellArray.put(current.toJson());
        }
        jsonObject.put("cellular", cellArray);

        JSONArray wiFiArray = new JSONArray();
        for (WiFi current:wiFi) {
            wiFiArray.put(current.toJson());
        }
        jsonObject.put("wiFi", wiFiArray);

        return jsonObject;
    }

    public String toString() {
        String results = "Observation:";

        if (ble == null) {
            results += ":ble:null";
        } else {
            results += ":ble:" + ble.size();
        }

        if (cellular == null) {
            results += ":cellular:null";
        } else {
            results += ":cellular:" + cellular.size();
        }

        if (wiFi == null) {
            results += ":wifi:null";
        } else {
            results += ":wifi:" + wiFi.size();
        }

        return results;
    }
}