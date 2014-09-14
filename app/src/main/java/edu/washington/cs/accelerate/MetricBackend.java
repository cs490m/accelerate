package edu.washington.cs.accelerate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by power on 9/7/14.
 */
public class MetricBackend extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "METRICS";
    private static final String METRICS_TABLE_NAME = "METRICS";

    private static final String COL_ID = "ID";
    private static final String COL_TIME = "TIME";
    private static final String COL_VALUE = "VALUE";
    private static final String COL_NAME = "NAME";

    private static final String METRICS_TABLE_CREATE =
            "CREATE TABLE " + METRICS_TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_TIME + " TEXT, " +
                    COL_NAME + " TEXT, " +
                    COL_VALUE + " BLOB);";

    private SQLiteDatabase db;

    MetricBackend(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(METRICS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        throw new RuntimeException();
    }

    public void addEvent(String name, long timestamp, float[] values) {
        ContentValues cv = new ContentValues();
        String valueStr = "";
        for (float f : values) {
            valueStr = valueStr + String.valueOf(f);
            valueStr = valueStr + ",";
        }

        valueStr = valueStr.substring(0, valueStr.length() - 1);

        cv.put(COL_VALUE, valueStr);
        cv.put(COL_NAME, name);
        cv.put(COL_TIME, timestamp);

        this.getWritableDatabase().insert(METRICS_TABLE_NAME, null, cv);
    }
}
