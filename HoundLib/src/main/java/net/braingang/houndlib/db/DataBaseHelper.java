package net.braingang.houndlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE_NAME = "hound.db";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_FILE_NAME, null, DATABASE_VERSION);
//        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CellularTable.CREATE_TABLE);
        db.execSQL(GeoLocTable.CREATE_TABLE);
        db.execSQL(ObservationTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }
}
