package net.braingang.houndlib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import net.braingang.houndlib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class GeoLocTable implements DataBaseTable {
    public static final String TABLE_NAME = "geo_loc";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.braingang." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.braingang." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.TIMESTAMP_MS + " DESC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.OBSERVATION_ID + " INTEGER NOT NULL,"
            + Columns.TIMESTAMP + " TEXT NOT NULL,"
            + Columns.TIMESTAMP_MS + " INTEGER NOT NULL,"
            + Columns.ACCURACY + " REAL NOT NULL,"
            + Columns.ALTITUDE + " REAL NOT NULL,"
            + Columns.LATITUDE + " REAL NOT NULL,"
            + Columns.LONGITUDE + " REAL NOT NULL,"
            + Columns.PROVIDER + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String OBSERVATION_ID = "observation_id";
        public static final String TIMESTAMP = "time_stamp";
        public static final String TIMESTAMP_MS = "time_stamp_ms";
        public static final String ACCURACY = "accuracy";
        public static final String ALTITUDE = "altitude";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PROVIDER = "provider";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns._ID, net.braingang.houndlib.db.GeoLocTable.Columns._ID);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.OBSERVATION_ID, net.braingang.houndlib.db.GeoLocTable.Columns.OBSERVATION_ID);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.ACCURACY, net.braingang.houndlib.db.GeoLocTable.Columns.ACCURACY);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.ALTITUDE, net.braingang.houndlib.db.GeoLocTable.Columns.ALTITUDE);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.LATITUDE, net.braingang.houndlib.db.GeoLocTable.Columns.LATITUDE);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.LONGITUDE, net.braingang.houndlib.db.GeoLocTable.Columns.LONGITUDE);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.PROVIDER, net.braingang.houndlib.db.GeoLocTable.Columns.PROVIDER);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.TIMESTAMP, net.braingang.houndlib.db.GeoLocTable.Columns.TIMESTAMP);
        PROJECTION_MAP.put(net.braingang.houndlib.db.GeoLocTable.Columns.TIMESTAMP_MS, net.braingang.houndlib.db.GeoLocTable.Columns.TIMESTAMP_MS);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getDefaultSortOrder() {
        return DEFAULT_SORT_ORDER;
    }

    @Override
    public String[] getDefaultProjection() {
        Set<String> keySet = net.braingang.houndlib.db.GeoLocTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}