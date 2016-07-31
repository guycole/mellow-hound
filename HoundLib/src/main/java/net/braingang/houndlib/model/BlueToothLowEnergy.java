package net.braingang.houndlib.model;

import android.bluetooth.BluetoothDevice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 */
public class BlueToothLowEnergy implements Serializable {
    private String address;
    private int rssi;
    private byte[] scanRecord;

    public BlueToothLowEnergy(BluetoothDevice device, int rssi, byte[] scanRecord) {
        address = device.getAddress();
        this.rssi = rssi;
        this.scanRecord = scanRecord.clone();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("address", address);
        jsonObject.put("rssi", rssi);

        StringBuffer buffer = new StringBuffer();
        for (int ii = 0; ii < scanRecord.length; ii++) {
            buffer.append(Integer.toHexString(0xff & scanRecord[ii]) + ",");
        }
        jsonObject.put("rawScan", buffer.toString());

        /*
        JSONArray scanArray = new JSONArray();
        for (int ii = 0; ii < scanRecord.length; ii++) {
            //scanArray.put(Integer.toHexString(0xff & scanRecord[ii]));
            scanArray.put(0xff & scanRecord[ii]);
        }
        jsonObject.put("rawScan", scanArray);
        */

        return jsonObject;
    }
}
