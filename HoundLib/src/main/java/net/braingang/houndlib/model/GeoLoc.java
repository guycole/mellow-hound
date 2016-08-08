package net.braingang.houndlib.model;

import android.location.Location;

import net.braingang.houndlib.db.GeoLocModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 *
 */
public class GeoLoc {
    private Date timeStamp;

    private Float accuracy;
    private Double altitude;
    private Double latitude;
    private Double longitude;
    private String provider;

    private Location rawLocation;

    public GeoLoc(Location location) {
        rawLocation = location;

        accuracy = location.getAccuracy();
        altitude = location.getAltitude();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        provider = location.getProvider();
        timeStamp = new Date(location.getTime());
    }

    public Location getRawLocation() {
        return rawLocation;
    }

    public GeoLocModel toModel() {
        GeoLocModel geoLocModel = new GeoLocModel();
        geoLocModel.setDefault();
        geoLocModel.fromLocation(rawLocation);
        return geoLocModel;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("fixTime", timeStamp);
        jsonObject.put("accuracy", accuracy);
        jsonObject.put("altitude", altitude);
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("provider", provider);

        return jsonObject;
    }
}