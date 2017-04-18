package com.sayadev.finalproject.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by salehyassin on 3/19/17.
 */

public class ProjectDatabaseHelper extends SQLiteOpenHelper {

    /* Database name and version number */
    private static final String DATABASE_NAME = "project.db";
    private static final int VERSION_NUM = 1;

    /* House Table and Column Names */
    public static final String TABLE_HOUSE_ITEMS = "houseItems";
    public static final String COLUMN_HOUSE_ID = "_id";
    public static final String COLUMN_HOUSE_DEVICE_TITLE = "device_title";
    public static final String COLUMN_HOUSE_DEVICE_TYPE = "device_type";
    public static final String COLUMN_HOUSE_DEVICE_IMAGE = "device_image";

    // House Garage
    public static final String TABLE_HOUSE_GARAGE = "houseGarage";
    public static final String COLUMN_HOUSE_GARAGE_ID = "_id";
    public static final String COLUMN_HOUSE_GARAGE_DEVICE_ID = "_device_id";
    public static final String COLUMN_HOUSE_GARAGE_DOOR = "garageDoor";
    public static final String COLUMN_HOUSE_GARAGE_LIGHT = "garageLight";

    // TempIn
    public static final String TABLE_HOUSE_TEMPIN = "houseTempIn";
    public static final String COLUMN_HOUSE_TEMPIN_ID = "_id";
    public static final String COLUMN_HOUSE_TEMPIN_DEVICE_ID = "_device_id";
    public static final String COLUMN_HOUSE_TEMPIN_CURRENT_TEMP = "currentTemp";

    // TempIn Scheduales
    public static final String TABLE_HOUSE_TEMPIN_SCHED = "houseTempInSched";
    public static final String COLUMN_HOUSE_TEMPIN_SCHED_ID = "_id";
    public static final String COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID = "_device_id";
    public static final String COLUMN_HOUSE_TEMPIN_S_TEMP = "schedTemp";
    public static final String COLUMN_HOUSE_TEMPIN_S_DATE = "schedDate";

    // Create database sql statements house items
    private static final String CREATE_HOUSE_ITEMS_TABLE = "create table "
            + TABLE_HOUSE_ITEMS + "( "
            + COLUMN_HOUSE_ID + " integer primary key autoincrement, "
            + COLUMN_HOUSE_DEVICE_TITLE + " text, "
            + COLUMN_HOUSE_DEVICE_TYPE + " integer default 0, "
            + COLUMN_HOUSE_DEVICE_IMAGE + " text not null);";

    // Create database sql statements house garage
    private static final String CREATE_HOUSE_GARAGE_TABLE = "create table "
            + TABLE_HOUSE_GARAGE + "( "
            + COLUMN_HOUSE_GARAGE_ID + " integer primary key autoincrement, "
            + COLUMN_HOUSE_GARAGE_DEVICE_ID + " integer, "
            + COLUMN_HOUSE_GARAGE_DOOR + " integer default 0, "
            + COLUMN_HOUSE_GARAGE_LIGHT + " integer default 0);";

    // Create database sql statements house tempin
    private static final String CREATE_HOUSE_TEMPIN_TABLE = "create table "
            + TABLE_HOUSE_TEMPIN + "( "
            + COLUMN_HOUSE_TEMPIN_ID + " integer primary key autoincrement, "
            + COLUMN_HOUSE_TEMPIN_DEVICE_ID + " integer, "
            + COLUMN_HOUSE_TEMPIN_CURRENT_TEMP + " integer);";

    // Create database sql statements house tempin sheduale
    private static final String CREATE_HOUSE_TEMPIN_SCHED_TABLE = "create table "
            + TABLE_HOUSE_TEMPIN_SCHED + "( "
            + COLUMN_HOUSE_TEMPIN_SCHED_ID + " integer primary key autoincrement, "
            + COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + " integer, "
            + COLUMN_HOUSE_TEMPIN_S_TEMP + " integer, "
            + COLUMN_HOUSE_TEMPIN_S_DATE + " integer);";

    public ProjectDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HOUSE_ITEMS_TABLE);
        db.execSQL(CREATE_HOUSE_GARAGE_TABLE);
        db.execSQL(CREATE_HOUSE_TEMPIN_TABLE);
        db.execSQL(CREATE_HOUSE_TEMPIN_SCHED_TABLE);

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProjectDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_TEMPIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_TEMPIN_SCHED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_GARAGE);

        onCreate(db);

        Log.i("ProjectDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    // insert house item
    public long insertHouseItem(String device_title, int device_type, String uri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues item_values = new ContentValues();
        item_values.put(COLUMN_HOUSE_DEVICE_TITLE, device_title);
        item_values.put(COLUMN_HOUSE_DEVICE_TYPE, device_type);
        item_values.put(COLUMN_HOUSE_DEVICE_IMAGE, uri);
        long _id = db.insert(TABLE_HOUSE_ITEMS, null, item_values);

        ContentValues house_garage_values = new ContentValues();
        house_garage_values.put(COLUMN_HOUSE_GARAGE_DEVICE_ID, _id);
        db.insert(TABLE_HOUSE_GARAGE, null, house_garage_values);

        ContentValues house_tempin_values = new ContentValues();
        house_tempin_values.put(COLUMN_HOUSE_TEMPIN_DEVICE_ID, _id);
        db.insert(TABLE_HOUSE_TEMPIN, null, house_tempin_values);

        return _id;
    }

    //delete house item
    public void deleteHouseItem(long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_HOUSE_ITEMS, COLUMN_HOUSE_ID + "=?",
                new String[]{Long.toString(device_id)});
        db.delete(TABLE_HOUSE_GARAGE, COLUMN_HOUSE_GARAGE_DEVICE_ID + "=?",
                new String[]{Long.toString(device_id)});
        db.delete(TABLE_HOUSE_TEMPIN, COLUMN_HOUSE_TEMPIN_DEVICE_ID + "=?",
                new String[]{Long.toString(device_id)});
        db.delete(TABLE_HOUSE_TEMPIN_SCHED, COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + "=?",
                new String[]{Long.toString(device_id)});
    }

    // get house items
    public Cursor getHouseItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HOUSE_ITEMS, null);
        return res;
    }

    // save garage door status
    public void setHouseGarageDoorStatus(long device_id, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_GARAGE_DOOR, status);
        db.update(TABLE_HOUSE_GARAGE, values, COLUMN_HOUSE_GARAGE_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    // save garage light status
    public void setHouseGarageLightStatus(long device_id, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_GARAGE_LIGHT, status);
        db.update(TABLE_HOUSE_GARAGE, values, COLUMN_HOUSE_GARAGE_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    // get garage table
    public Cursor getHouseGarage(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HOUSE_GARAGE + " WHERE " +
                COLUMN_HOUSE_GARAGE_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }

    // save house tempin temperature
    public void setHouseTempInTemp(long device_id, int temp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_TEMPIN_CURRENT_TEMP, temp);
        db.update(TABLE_HOUSE_TEMPIN, values, COLUMN_HOUSE_TEMPIN_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    // get tempin table
    public Cursor getHouseTempIn(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HOUSE_TEMPIN + " WHERE " +
                COLUMN_HOUSE_TEMPIN_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }

    // save house tempin sched temperature
    public void setHouseTempInSchedTemp(long device_id, long id, int temp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_TEMPIN_S_TEMP, temp);
        db.update(TABLE_HOUSE_TEMPIN_SCHED, values, COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + "=?" + " AND " + COLUMN_HOUSE_TEMPIN_SCHED_ID + "=?", new String[]{Long.toString(device_id), Long.toString(id)});
    }

    // save house tempin sched date and time
    public void setHouseTempInSchedDate(long device_id, long id, long datetime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_TEMPIN_S_DATE, datetime);
        db.update(TABLE_HOUSE_TEMPIN_SCHED, values, COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + "=?" + " AND " + COLUMN_HOUSE_TEMPIN_SCHED_ID + "=?", new String[]{Long.toString(device_id), Long.toString(id)});
    }

    // insert tempin sched item
    public long insertHouseTempInSched(long device_id, long datetime, int temp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID, device_id);
        values.put(COLUMN_HOUSE_TEMPIN_S_DATE, datetime);
        values.put(COLUMN_HOUSE_TEMPIN_S_TEMP, temp);
        long _id = db.insert(TABLE_HOUSE_TEMPIN_SCHED, null, values);

        return _id;
    }

    // delete tempin sched
    public void deleteHouseTempInSched(long device_id, long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_HOUSE_TEMPIN_SCHED, COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + "=?" + " AND " + COLUMN_HOUSE_TEMPIN_SCHED_ID + "=?",
                new String[]{Long.toString(device_id), Long.toString(id)});
    }

    // get tempin scheduale table
    public Cursor getHouseTempInSched(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HOUSE_TEMPIN_SCHED + " WHERE " +
                COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }
}