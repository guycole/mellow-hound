package net.braingang.houndlib.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import net.braingang.houndlib.Constant;

public class DataBaseProvider extends ContentProvider {
    public static final String LOG_TAG = DataBaseProvider.class.getName();

    private DataBaseHelper dbHelper;

    private static final int URI_MATCH_CELLULAR = 10;
    private static final int URI_MATCH_CELLULAR_ID = 11;

    private static final int URI_MATCH_GEOLOC = 20;
    private static final int URI_MATCH_GEOLOC_ID = 21;

    private static final int URI_MATCH_OBSERVATION = 30;
    private static final int URI_MATCH_OBSERVATION_ID = 31;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(Constant.AUTHORITY, CellularTable.TABLE_NAME, URI_MATCH_CELLULAR);
        URI_MATCHER.addURI(Constant.AUTHORITY, CellularTable.TABLE_NAME + "/#", URI_MATCH_CELLULAR_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, GeoLocTable.TABLE_NAME, URI_MATCH_GEOLOC);
        URI_MATCHER.addURI(Constant.AUTHORITY, GeoLocTable.TABLE_NAME + "/#", URI_MATCH_GEOLOC_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ObservationTable.TABLE_NAME, URI_MATCH_OBSERVATION);
        URI_MATCHER.addURI(Constant.AUTHORITY, ObservationTable.TABLE_NAME + "/#", URI_MATCH_OBSERVATION_ID);
    }

    public DataBaseProvider() {
        //empty
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_CELLULAR:
                count = db.delete(CellularTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CELLULAR_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(CellularTable.TABLE_NAME, CellularTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_GEOLOC:
                count = db.delete(GeoLocTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_GEOLOC_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(GeoLocTable.TABLE_NAME, GeoLocTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_OBSERVATION:
                count = db.delete(ObservationTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_OBSERVATION_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ObservationTable.TABLE_NAME, ObservationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_CELLULAR:
                return CellularTable.CONTENT_TYPE;
            case URI_MATCH_CELLULAR_ID:
                return CellularTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_GEOLOC:
                return GeoLocTable.CONTENT_TYPE;
            case URI_MATCH_GEOLOC_ID:
                return GeoLocTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_OBSERVATION:
                return ObservationTable.CONTENT_TYPE;
            case URI_MATCH_OBSERVATION_ID:
                return ObservationTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_CELLULAR:
                rowId = db.insert(CellularTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(CellularTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(CellularTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_GEOLOC:
                rowId = db.insert(GeoLocTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(GeoLocTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(GeoLocTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_OBSERVATION:
                rowId = db.insert(ObservationTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ObservationTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ObservationTable.CONTENT_URI, null);
                    return result;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        throw new SQLException("insert failure:" + uri);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = sortOrder;

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_CELLULAR:
                qb.setTables(CellularTable.TABLE_NAME);
                qb.setProjectionMap(CellularTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = CellularTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CELLULAR_ID:
                qb.setTables(CellularTable.TABLE_NAME);
                qb.setProjectionMap(CellularTable.PROJECTION_MAP);
                qb.appendWhere(CellularTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = CellularTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_GEOLOC:
                qb.setTables(GeoLocTable.TABLE_NAME);
                qb.setProjectionMap(GeoLocTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = GeoLocTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_GEOLOC_ID:
                qb.setTables(GeoLocTable.TABLE_NAME);
                qb.setProjectionMap(GeoLocTable.PROJECTION_MAP);
                qb.appendWhere(GeoLocTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = GeoLocTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_OBSERVATION:
                qb.setTables(ObservationTable.TABLE_NAME);
                qb.setProjectionMap(ObservationTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ObservationTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_OBSERVATION_ID:
                qb.setTables(ObservationTable.TABLE_NAME);
                qb.setProjectionMap(ObservationTable.PROJECTION_MAP);
                qb.appendWhere(ObservationTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ObservationTable.DEFAULT_SORT_ORDER;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_CELLULAR:
                count = db.update(CellularTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CELLULAR_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(CellularTable.TABLE_NAME, values, CellularTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_GEOLOC:
                count = db.update(GeoLocTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_GEOLOC_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(GeoLocTable.TABLE_NAME, values, GeoLocTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_OBSERVATION:
                count = db.update(ObservationTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_OBSERVATION_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ObservationTable.TABLE_NAME, values, ObservationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
