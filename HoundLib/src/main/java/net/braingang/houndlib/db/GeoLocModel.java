package net.braingang.houndlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class GeoLocModel implements DataBaseModel {
    private Long id;
    private Date timeStamp;
    private String note;

    private Float accuracy;
    private Double altitude;
    private Double latitude;
    private Double longitude;
    private String provider;

    @Override
    public void setDefault() {
        id = 0L;
        timeStamp = new Date();
        note = "No Note";

        accuracy = 99999.9f;
        altitude = -1.0d;
        latitude = 99.9d;
        longitude = 181.81d;
        provider = "bogus";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(GeoLocTable.Columns.TIMESTAMP, formatter(timeStamp));
        cv.put(GeoLocTable.Columns.TIMESTAMP_MS, timeStamp.getTime());
        cv.put(GeoLocTable.Columns.NOTE, note);

        cv.put(GeoLocTable.Columns.ACCURACY, accuracy.toString());
        cv.put(GeoLocTable.Columns.ALTITUDE, altitude.toString());
        cv.put(GeoLocTable.Columns.LATITUDE, latitude.toString());
        cv.put(GeoLocTable.Columns.LONGITUDE, longitude.toString());
        cv.put(GeoLocTable.Columns.PROVIDER, provider);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(GeoLocTable.Columns._ID));
        timeStamp.setTime(cursor.getLong(cursor.getColumnIndex(GeoLocTable.Columns.TIMESTAMP_MS)));
        note = cursor.getString(cursor.getColumnIndex(GeoLocTable.Columns.NOTE));

        accuracy = cursor.getFloat(cursor.getColumnIndex(GeoLocTable.Columns.ACCURACY));
        altitude = cursor.getDouble(cursor.getColumnIndex(GeoLocTable.Columns.ALTITUDE));
        latitude = cursor.getDouble(cursor.getColumnIndex(GeoLocTable.Columns.LATITUDE));
        longitude = cursor.getDouble(cursor.getColumnIndex(GeoLocTable.Columns.LONGITUDE));
        provider = cursor.getString(cursor.getColumnIndex(GeoLocTable.Columns.PROVIDER));
    }

    @Override
    public String getTableName() {
        return GeoLocTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return GeoLocTable.CONTENT_URI;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long arg) {
        id = arg;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Date arg) {
        timeStamp = arg;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String arg) {
        note = arg;
    }

    public void fromLocation(Location arg) {
        accuracy = arg.getAccuracy();
        altitude = arg.getAltitude();
        latitude = arg.getLatitude();
        longitude = arg.getLongitude();
        provider = arg.getProvider();
        timeStamp = new Date(arg.getTime());
    }

    private String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
