package net.braingang.houndlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 *
 */
public class ObservationModel implements DataBaseModel {
    private Long id;
    private String networkName;
    private String networkOperator;

    @Override
    public void setDefault() {
        id = 0L;
        networkName = "unknown";
        networkOperator = "unknown";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ObservationTable.Columns.NETWORK_NAME, networkName);
        cv.put(ObservationTable.Columns.NETWORK_OPERATOR, networkOperator);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(ObservationTable.Columns._ID));
        networkName = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.NETWORK_NAME));
        networkOperator = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.NETWORK_OPERATOR));
    }

    @Override
    public String getTableName() {
        return ObservationTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ObservationTable.CONTENT_URI;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long arg) {
        id = arg;
    }

    public String getNetworkName() { return networkName; }
    public void setNetworkName(String arg) { networkName = arg; }

    public String getNetworkOperator() { return networkOperator; }
    public void setNetworOperator(String arg) { networkOperator = arg; }
}
