package net.braingang.houndlib.model;

import android.net.wifi.ScanResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class WiFi {
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

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("ssid", ssid);
        jsonObject.put("bssid", bssid);

        jsonObject.put("capability", capabilities);

        jsonObject.put("frequency", frequency);
        jsonObject.put("level", level);

        return jsonObject;
    }
}