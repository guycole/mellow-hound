package net.braingang.houndlib.model;

import android.net.wifi.ScanResult;

import java.io.Serializable;

/**
 *
 */
public class WiFi implements Serializable {
    private String ssid;
    private String bssid;
    private String capabilities;

    private int frequency;
    private int level;

    private long timeStamp;

    public WiFi(ScanResult arg) {
        ssid = arg.SSID;
        bssid = arg.BSSID;
        capabilities = arg.capabilities;

        frequency = arg.frequency;
        level = arg.level;
        timeStamp = arg.timestamp;
    }
}