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

    private static final String CREATE_HOUSE_TEMPIN_TABLE = "create table "
            + TABLE_HOUSE_TEMPIN + "( "
            + COLUMN_HOUSE_TEMPIN_ID + " integer primary key autoincrement, "
            + COLUMN_HOUSE_TEMPIN_DEVICE_ID + " integer, "
            + COLUMN_HOUSE_TEMPIN_CURRENT_TEMP + " integer);";

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
        db.execSQL("DROP TABLE IF EXISTS messages");
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

        ContentValues house_tempinsched_values = new ContentValues();
        house_tempinsched_values.put(COLUMN_HOUSE_TEMPIN_SCHED_DEVICE_ID, _id);
        db.insert(TABLE_HOUSE_TEMPIN_SCHED, null, house_tempinsched_values);

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

    /*
    //save automobile temperature
    public void saveAutomobileTemperature(String auto_temp, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_temp_values = new ContentValues();
        automobile_temp_values.put(COLUMN_AUTOMOBILE_AIR_TEMPERATURE, auto_temp);
        db.update(TABLE_AUTOMOBILE_AIR_CONDITION, automobile_temp_values, COLUMN_AUTOMOBILE_AIR_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    public Cursor getAutomobileTemperature(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_AUTOMOBILE_AIR_TEMPERATURE + " FROM " + TABLE_AUTOMOBILE_AIR_CONDITION + " WHERE " +
                COLUMN_AUTOMOBILE_AIR_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }

    //save automobile lights
    public void saveAutomobileLowBeam(int auto_low_beam, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_low_beam_values = new ContentValues();
        automobile_low_beam_values.put(COLUMN_AUTOMOBILE_LIGHTS_LOW, auto_low_beam);
        db.update(TABLE_AUTOMOBILE_LIGHTS, automobile_low_beam_values, COLUMN_AUTOMOBILE_LIGHTS_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    public void saveAutomobileHighBeam(int auto_high_beam, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_high_beam_values = new ContentValues();
        automobile_high_beam_values.put(COLUMN_AUTOMOBILE_LIGHTS_HIGH, auto_high_beam);
        db.update(TABLE_AUTOMOBILE_LIGHTS, automobile_high_beam_values, COLUMN_AUTOMOBILE_LIGHTS_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    public void saveAutomobileFog(int auto_fog, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_fog_values = new ContentValues();
        automobile_fog_values.put(COLUMN_AUTOMOBILE_LIGHTS_FOG, auto_fog);
        db.update(TABLE_AUTOMOBILE_LIGHTS, automobile_fog_values, COLUMN_AUTOMOBILE_LIGHTS_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    public void saveAutomobileInterior(int auto_interior, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_interior_values = new ContentValues();
        automobile_interior_values.put(COLUMN_AUTOMOBILE_LIGHTS_INTERIOR, auto_interior);
        db.update(TABLE_AUTOMOBILE_LIGHTS, automobile_interior_values, COLUMN_AUTOMOBILE_LIGHTS_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    //save automobile radio channels
    public void saveAutomobileRadioChannelNumber(int channel_number, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_channel_values = new ContentValues();
        automobile_channel_values.put(COLUMN_AUTOMOBILE_RADIO_CHANNEL_NUMBER, channel_number);
        db.update(TABLE_AUTOMOBILE_RADIO, automobile_channel_values, COLUMN_AUTOMOBILE_RADIO_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    //save automobile radio frequency
    public void saveAutomobileRadioChannelFreq(float frequency, long device_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues automobile_freq_values = new ContentValues();
        automobile_freq_values.put(COLUMN_AUTOMOBILE_RADIO_FREQUENCY, frequency);
        db.update(TABLE_AUTOMOBILE_RADIO, automobile_freq_values, COLUMN_AUTOMOBILE_RADIO_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
    }

    public Cursor getAutomobileLights(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_AUTOMOBILE_LIGHTS + " WHERE " +
                COLUMN_AUTOMOBILE_LIGHTS_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }

    public Cursor getAutomobileRadio(long device_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_AUTOMOBILE_RADIO + " WHERE " +
                COLUMN_AUTOMOBILE_RADIO_DEVICE_ID + "=?", new String[]{Long.toString(device_id)});
        return res;
    }
*/
}