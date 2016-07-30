package net.braingang.houndlib.model;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class GeoLoc implements Serializable {
    private Date timeStamp;

    private Float accuracy;
    private Double altitude;
    private Double latitude;
    private Double longitude;
    private String provider;

    public GeoLoc(Location location) {
        accuracy = location.getAccuracy();
        altitude = location.getAltitude();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        provider = location.getProvider();
        timeStamp = new Date(location.getTime());
    }

    public String toJson() {
        //FIXME
        return "fixme";
    }
}