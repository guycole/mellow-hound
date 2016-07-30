package net.braingang.houndlib.model;

import android.bluetooth.BluetoothDevice;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellInfo;

import net.braingang.houndlib.Personality;

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
    }

    public void addBleObservation(BluetoothDevice device, int rssi, byte[] scanRecord) {
        ble.add(new BlueToothLowEnergy(device, rssi, scanRecord));
    }

    public void addCellular(String operator, String name, List<CellInfo> cellList) {
        System.out.println("operator:" + operator);
        System.out.println("operator:" + name);

        for (CellInfo cellInfo:cellList) {
            cellular.add(new Cellular(cellInfo));
        }
    }

    public void setLocation(Location location) {
        setGeoLoc(new GeoLoc(location));
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