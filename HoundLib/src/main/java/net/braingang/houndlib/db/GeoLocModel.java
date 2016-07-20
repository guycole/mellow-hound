package net.braingang.houndlib.db;

import android.content.ContentValues;
import android.database.Cursor;
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

    @Override
    public void setDefault() {
        id = 0L;
        timeStamp = new Date();
        note = "No Note";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(GeoLocTable.Columns.TIMESTAMP, formatter(timeStamp));
        cv.put(GeoLocTable.Columns.TIMESTAMP_MS, timeStamp.getTime());
        cv.put(GeoLocTable.Columns.NOTE, note);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(GeoLocTable.Columns._ID));
        timeStamp.setTime(cursor.getLong(cursor.getColumnIndex(GeoLocTable.Columns.TIMESTAMP_MS)));
        note = cursor.getString(cursor.getColumnIndex(GeoLocTable.Columns.NOTE));
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
    public void setId(Long id) {
        id = id;
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

    private String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
