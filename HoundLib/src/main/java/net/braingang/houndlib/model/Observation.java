package net.braingang.houndlib.model;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellInfo;

import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

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
    private ArrayList<BlueToothLowEnergy> ble = new ArrayList<BlueToothLowEnergy>();
    private ArrayList<AbstractCellular> cellular = new ArrayList<AbstractCellular>();
    private ArrayList<WiFi> wiFi = new ArrayList<WiFi>();

    private GeoLoc geoLoc;

    private String phone1;
    private String operator;
    private String operatorName;

    public Observation(String phone1, String operator, String operatorName) {
        this.phone1 = phone1;
        this.operator = operator;
        this.operatorName = operatorName;
    }

    public ArrayList<BlueToothLowEnergy> getBle() {
        return ble;
    }

    public ArrayList<AbstractCellular> getCellular() {
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
            if (cellInfo instanceof CellInfoCdma) {
                cellular.add(new CellularCdma((CellInfoCdma) cellInfo));
            } else if (cellInfo instanceof CellInfoGsm) {
                cellular.add(new CellularGsm((CellInfoGsm) cellInfo));
            } else if (cellInfo instanceof CellInfoLte) {
                cellular.add(new CellularLte((CellInfoLte) cellInfo));
            } else if (cellInfo instanceof CellInfoWcdma) {
                cellular.add(new CellularWcdma((CellInfoWcdma) cellInfo));
            }
        }
    }

    public void setLocation(Location location) {
        setGeoLoc(new GeoLoc(location));
    }

    public JSONObject toJson(Context context) throws JSONException {
        UserPreferenceHelper userPreferenceHelper = new UserPreferenceHelper();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 1);
        jsonObject.put("installation", userPreferenceHelper.getInstallationUuid(context));
        jsonObject.put("phone1", phone1);
        jsonObject.put("networkName", operatorName);
        jsonObject.put("networkOperator", operator);
        jsonObject.put("geoLoc", geoLoc.toJson());

        JSONArray bleArray = new JSONArray();
        for (BlueToothLowEnergy current:ble) {
            bleArray.put(current.toJson());
        }
        jsonObject.put("ble", bleArray);

        JSONArray cellArray = new JSONArray();
        for (AbstractCellular current:cellular) {
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
        String results = "ObservationTable:";

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