package net.braingang.houndlib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import net.braingang.houndlib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class CellularTable implements DataBaseTable {
    public static final String TABLE_NAME = "cellular";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.braingang." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.braingang." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns._ID;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.GEOLOC_ID + " INTEGER NOT NULL,"
            + Columns.CELL_TYPE + " TEXT NOT NULL,"
            + Columns.ASU_LEVEL + " INTEGER NOT NULL,"
            + Columns.CDMA_DBM + " INTEGER NOT NULL,"
            + Columns.CDMA_ECIO + " INTEGER NOT NULL,"
            + Columns.CDMA_LEVEL + " INTEGER NOT NULL,"
            + Columns.DBM + " INTEGER NOT NULL,"
            + Columns.EVDO_DBM + " INTEGER NOT NULL,"
            + Columns.EVDO_ECIO + " INTEGER NOT NULL,"
            + Columns.EVDO_LEVEL + " INTEGER NOT NULL,"
            + Columns.EVDO_SNR + " INTEGER NOT NULL,"
            + Columns.SIGNAL_LEVEL + " INTEGER NOT NULL,"
            + Columns.TIME_ADVANCE + " INTEGER NOT NULL,"
            + Columns.CELL_ID + " INTEGER NOT NULL,"
            + Columns.COUNTRY_CODE + " INTEGER NOT NULL,"
            + Columns.LATITUDE + " INTEGER NOT NULL,"
            + Columns.LONGITUDE + " INTEGER NOT NULL,"
            + Columns.LOCATION_CODE + " INTEGER NOT NULL,"
            + Columns.NETWORK_CODE + " INTEGER NOT NULL,"
            + Columns.PHYSICAL_CELL_ID + " INTEGER NOT NULL,"
            + Columns.RF_CHANNEL + " INTEGER NOT NULL,"
            + Columns.SCRAMBLE_CODE + " INTEGER NOT NULL,"
            + Columns.STATION_ID + " INTEGER NOT NULL,"
            + Columns.SYSTEM_CODE + " INTEGER NOT NULL,"
            + Columns.TRACK_CODE + " INTEGER NOT NULL,"
            + Columns.ACTIVE_FLAG + " INTEGER NOT NULL,"
            + Columns.NOTE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String GEOLOC_ID = "geoloc_id";
        public static final String CELL_TYPE = "cell_type";
        public static final String ASU_LEVEL = "asu_level";
        public static final String CDMA_DBM = "cdma_dbm";
        public static final String CDMA_ECIO = "cdma_ecio";
        public static final String CDMA_LEVEL = "cdma_level";
        public static final String DBM = "dbm";
        public static final String EVDO_DBM = "evdo_dbm";
        public static final String EVDO_ECIO = "evdo_ecio";
        public static final String EVDO_LEVEL = "evdo_level";
        public static final String EVDO_SNR = "evdo_snr";
        public static final String SIGNAL_LEVEL = "signal_level";
        public static final String TIME_ADVANCE = "time_advance";
        public static final String CELL_ID = "cell_id";
        public static final String COUNTRY_CODE = "country_code";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String LOCATION_CODE = "location_code";
        public static final String NETWORK_CODE = "network_code";
        public static final String PHYSICAL_CELL_ID = "physical_cell_id";
        public static final String RF_CHANNEL = "rf_channel";
        public static final String SCRAMBLE_CODE = "scramble_code";
        public static final String STATION_ID = "station_id";
        public static final String SYSTEM_CODE = "system_code";
        public static final String TRACK_CODE = "track_code";
        public static final String ACTIVE_FLAG = "active_flag";
        public static final String NOTE = "note";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(CellularTable.Columns._ID, CellularTable.Columns._ID);
        PROJECTION_MAP.put(CellularTable.Columns.ACTIVE_FLAG, CellularTable.Columns.ACTIVE_FLAG);
        PROJECTION_MAP.put(CellularTable.Columns.ASU_LEVEL, CellularTable.Columns.ASU_LEVEL);
        PROJECTION_MAP.put(CellularTable.Columns.CELL_ID, CellularTable.Columns.CELL_ID);
        PROJECTION_MAP.put(CellularTable.Columns.CDMA_DBM, CellularTable.Columns.CDMA_DBM);
        PROJECTION_MAP.put(CellularTable.Columns.CDMA_ECIO, CellularTable.Columns.CDMA_ECIO);
        PROJECTION_MAP.put(CellularTable.Columns.CDMA_LEVEL, CellularTable.Columns.CDMA_LEVEL);
        PROJECTION_MAP.put(CellularTable.Columns.COUNTRY_CODE, CellularTable.Columns.COUNTRY_CODE);
        PROJECTION_MAP.put(CellularTable.Columns.DBM, CellularTable.Columns.DBM);
        PROJECTION_MAP.put(CellularTable.Columns.EVDO_DBM, CellularTable.Columns.EVDO_DBM);
        PROJECTION_MAP.put(CellularTable.Columns.EVDO_ECIO, CellularTable.Columns.EVDO_ECIO);
        PROJECTION_MAP.put(CellularTable.Columns.EVDO_LEVEL, CellularTable.Columns.EVDO_LEVEL);
        PROJECTION_MAP.put(CellularTable.Columns.EVDO_SNR, CellularTable.Columns.EVDO_SNR);
        PROJECTION_MAP.put(CellularTable.Columns.GEOLOC_ID, CellularTable.Columns.GEOLOC_ID);
        PROJECTION_MAP.put(CellularTable.Columns.LATITUDE, CellularTable.Columns.LATITUDE);
        PROJECTION_MAP.put(CellularTable.Columns.LONGITUDE, CellularTable.Columns.LONGITUDE);
        PROJECTION_MAP.put(CellularTable.Columns.LOCATION_CODE, CellularTable.Columns.LOCATION_CODE);
        PROJECTION_MAP.put(CellularTable.Columns.NETWORK_CODE, CellularTable.Columns.NETWORK_CODE);
        PROJECTION_MAP.put(CellularTable.Columns.NOTE, CellularTable.Columns.NOTE);
        PROJECTION_MAP.put(CellularTable.Columns.PHYSICAL_CELL_ID, CellularTable.Columns.PHYSICAL_CELL_ID);
        PROJECTION_MAP.put(CellularTable.Columns.RF_CHANNEL, CellularTable.Columns.RF_CHANNEL);
        PROJECTION_MAP.put(CellularTable.Columns.SCRAMBLE_CODE, CellularTable.Columns.SCRAMBLE_CODE);
        PROJECTION_MAP.put(CellularTable.Columns.SIGNAL_LEVEL, CellularTable.Columns.SIGNAL_LEVEL);
        PROJECTION_MAP.put(CellularTable.Columns.STATION_ID, CellularTable.Columns.STATION_ID);
        PROJECTION_MAP.put(CellularTable.Columns.SYSTEM_CODE, CellularTable.Columns.SYSTEM_CODE);
        PROJECTION_MAP.put(CellularTable.Columns.TIME_ADVANCE, CellularTable.Columns.TIME_ADVANCE);
        PROJECTION_MAP.put(CellularTable.Columns.TRACK_CODE, CellularTable.Columns.TRACK_CODE);
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
        Set<String> keySet = CellularTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}