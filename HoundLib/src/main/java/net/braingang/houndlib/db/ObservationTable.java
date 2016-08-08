package net.braingang.houndlib.db;

import android.net.Uri;
import android.provider.BaseColumns;
import net.braingang.houndlib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ObservationTable implements DataBaseTable {
    public static final String TABLE_NAME = "observation";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.braingang." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.braingang." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns._ID;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NETWORK_NAME + " TEXT NOT NULL,"
            + Columns.NETWORK_OPERATOR + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NETWORK_NAME = "network_name";
        public static final String NETWORK_OPERATOR = "network_operator";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ObservationTable.Columns._ID, ObservationTable.Columns._ID);
        PROJECTION_MAP.put(ObservationTable.Columns.NETWORK_NAME, ObservationTable.Columns.NETWORK_NAME);
        PROJECTION_MAP.put(ObservationTable.Columns.NETWORK_OPERATOR, ObservationTable.Columns.NETWORK_OPERATOR);
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
        Set<String> keySet = ObservationTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}