package net.braingang.houndlib.model;

import android.bluetooth.BluetoothDevice;

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
}
