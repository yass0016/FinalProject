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

    /* Living Room Table and Column Names */

    /* items list */
    public static final String TABLE_ROOM_ITEMS = "roomItems";
    public static final String COLUMN_ROOM_ID = "_id";
    public static final String COLUMN_ROOM_DEVICE_TYPE = "deviceType";
    public static final String COLUMN_ROOM_DEVICE_TITLE = "deviceTitle";
    public static final String COLUMN_ROOM_DEVICE_IMAGE = "deviceImage";
    public static final String COLUMN_ROOM_VISIT_COUNT = "lastVisited";
    public static final String COLUMN_ROOM_CREATED = "created";

    /* lamp 1 */
    public static final String TABLE_ROOM_LAMP1 = "roomLampOne";
    public static final String COLUMN_ROOM_LAMP1_ID = "_id";
    public static final String COLUMN_ROOM_LAMP1_DEVICE_ID = "deviceId";
    public static final String COLUMN_ROOM_LAMP1_STATUS = "deviceStatus";

    /* lamp 2 */
    public static final String TABLE_ROOM_LAMP2 = "roomLampTwo";
    public static final String COLUMN_ROOM_LAMP2_ID = "_id";
    public static final String COLUMN_ROOM_LAMP2_DEVICE_ID = "deviceId";
    public static final String COLUMN_ROOM_LAMP2_STATUS = "deviceStatus";
    public static final String COLUMN_ROOM_LAMP2_DIM_LEVEL = "deviceDimLevel";

    /* lamp 3 */
    public static final String TABLE_ROOM_LAMP3 = "roomLampThree";
    public static final String COLUMN_ROOM_LAMP3_ID = "_id";
    public static final String COLUMN_ROOM_LAMP3_DEVICE_ID = "deviceId";
    public static final String COLUMN_ROOM_LAMP3_STATUS = "deviceStatus";
    public static final String COLUMN_ROOM_LAMP3_DIM_LEVEL = "deviceDimLevel";
    public static final String COLUMN_ROOM_LAMP3_COLOR = "deviceColor";

    /* TV */
    public static final String TABLE_ROOM_TV = "roomTV";
    public static final String COLUMN_ROOM_TV_ID = "_id";
    public static final String COLUMN_ROOM_TV_DEVICE_ID = "deviceId";
    public static final String COLUMN_ROOM_TV_STATUS = "deviceStatus";
    public static final String COLUMN_ROOM_TV_CHANNEL = "deviceChannel";
    public static final String COLUMN_ROOM_TV_VOLUME = "deviceVolume";

    /* Blinds */
    public static final String TABLE_ROOM_BLINDS = "roomBlinds";
    public static final String COLUMN_ROOM_BLINDS_ID = "_id";
    public static final String COLUMN_ROOM_BLINDS_DEVICE_ID = "deviceId";
    public static final String COLUMN_ROOM_BLINDS_STATUS = "deviceStatus";
    public static final String COLUMN_ROOM_BLINDS_LEVEL = "deviceLevel";


    // Database creation sql statement for Living Room
    private static final String CREATE_ROOM_ITEMS_TABLE = "create table "
            + TABLE_ROOM_ITEMS + "( "
            + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_DEVICE_TYPE + " INTEGER not null default 0, "
            + COLUMN_ROOM_DEVICE_TITLE + " text, "
            + COLUMN_ROOM_DEVICE_IMAGE + " text, "
            + COLUMN_ROOM_VISIT_COUNT + " INTEGER default 0, "
            + COLUMN_ROOM_CREATED + " INTEGER default 0);";

    private static final String CREATE_ROOM_LAMP1_TABLE = "create table "
            + TABLE_ROOM_LAMP1 + "( "
            + COLUMN_ROOM_LAMP1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_LAMP1_DEVICE_ID + " INTEGER not null, "
            + COLUMN_ROOM_LAMP1_STATUS + " INTEGER not null default 0);";

    private static final String CREATE_ROOM_LAMP2_TABLE = "create table "
            + TABLE_ROOM_LAMP2 + "( "
            + COLUMN_ROOM_LAMP2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_LAMP2_DEVICE_ID + " INTEGER not null, "
            + COLUMN_ROOM_LAMP2_STATUS + " INTEGER not null default 0, "
            + COLUMN_ROOM_LAMP2_DIM_LEVEL + " INTEGER not null default 0);";

    private static final String CREATE_ROOM_LAMP3_TABLE = "create table "
            + TABLE_ROOM_LAMP3 + "( "
            + COLUMN_ROOM_LAMP3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_LAMP3_DEVICE_ID + " INTEGER not null, "
            + COLUMN_ROOM_LAMP3_STATUS + " INTEGER not null default 0, "
            + COLUMN_ROOM_LAMP3_DIM_LEVEL + " INTEGER not null default 0, "
            + COLUMN_ROOM_LAMP3_COLOR + " INTEGER);";

    private static final String CREATE_ROOM_TV_TABLE = "create table "
            + TABLE_ROOM_TV + "( "
            + COLUMN_ROOM_TV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_TV_DEVICE_ID + " INTEGER not null, "
            + COLUMN_ROOM_TV_STATUS + " INTEGER not null default 0, "
            + COLUMN_ROOM_TV_CHANNEL + " INTEGER not null default 0, "
            + COLUMN_ROOM_TV_VOLUME + " INTEGER not null default 0);";

    private static final String CREATE_ROOM_BLINDS_TABLE = "create table "
            + TABLE_ROOM_BLINDS + "( "
            + COLUMN_ROOM_BLINDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_ROOM_BLINDS_DEVICE_ID + " INTEGER not null, "
            + COLUMN_ROOM_BLINDS_STATUS + " INTEGER not null default 0, "
            + COLUMN_ROOM_BLINDS_LEVEL + " INTEGER not null default 0);";


    //Auto programmed buttons tables
    //Automobile table and columns
    public static final String TABLE_AUTO_ITEMS = "autoItems";
    public static final String COLUMN_AUTO_ID = "_id";
    public static final String COLUMN_AUTO_NAME = "name";
    public static final String COLUMN_AUTO_DESCRIPTION = "description";
    public static final String COLUMN_AUTO_TYPE = "type";
    //temp controls
    public static final String TABLE_AUTO_TEMP = "tempSettings";
    public static final String COLUMN_TEMP_ID = "_id";
    public static final String COLUMN_TEMP_TEMP = "temp";
    public static final String COLUMN_TEMP_FAN = "fanSpeed";
    public static final String COLUMN_TEMP_AC = "ac";
    //radio
    public static final String TABLE_AUTO_RADIO = "radioSettings";
    public static final String COLUMN_RADIO_ID = "_id";
    public static final String COLUMN_RADIO_VOLUME = "volume";
    public static final String COLUMN_RADIO_CHANNEL = "channel";
    //cb
    public static final String TABLE_AUTO_CB = "cbSettings";
    public static final String COLUMN_CB_ID = "_id";
    public static final String COLUMN_CB_VOLUME = "volume";
    public static final String COLUMN_CB_CHANNEL = "channel";
    public static final String COLUMN_CB_GAIN = "gain";

    //Auto tables
    private static final String CREATE_AUTO_ITEMS = "create table " + TABLE_AUTO_ITEMS +  " ( "
            + COLUMN_AUTO_ID + " integer primary key autoincrement, "
            +  COLUMN_AUTO_NAME + " text, " + COLUMN_AUTO_DESCRIPTION + " text, " + COLUMN_AUTO_TYPE + " text);";

    private static final String CREATE_AUTO_TEMP = "create table " + TABLE_AUTO_TEMP + " ( "
            + COLUMN_TEMP_ID  + " integer primary key autoincrement, " + COLUMN_TEMP_TEMP + " integer, " + COLUMN_TEMP_FAN + " integer, "
            +COLUMN_TEMP_AC + " integer);";

    private static final String CREATE_AUTO_RADIO = "create table " + TABLE_AUTO_RADIO + " ( "
            + COLUMN_RADIO_ID + " integer primary key autoincrement, " + COLUMN_RADIO_VOLUME + " integer, "
            + COLUMN_RADIO_CHANNEL + " integer);";

    private static final String CREATE_AUTO_CB = "create table " + TABLE_AUTO_CB + " ( " + COLUMN_CB_ID
            + " integer primary key autoincrement, " + COLUMN_CB_VOLUME + " integer, " + COLUMN_CB_CHANNEL
            + " integer, " + COLUMN_CB_GAIN + " integer);";


    /* Kitchen Table and Columns */
    public static final String TABLE_KITCHEN_ITEMS = "kitchenItems";
    public static final String COLUMN_KITCHEN_ID = "_id";
    public static final String COLUMN_KITCHEN_NAME = "name";
    public static final String COLUMN_KITCHEN_MODEL = "model";
    public static final String COLUMN_KITCHEN_TYPE = "type";

    private static final String CREATE_KITCHEN_ITEMS_TABLE = "create table " +
            TABLE_KITCHEN_ITEMS +
            " ( " +
            COLUMN_KITCHEN_ID + " integer primary key autoincrement, " +
            COLUMN_KITCHEN_NAME + " text, " +
            COLUMN_KITCHEN_MODEL + " text, " +
            COLUMN_KITCHEN_TYPE + " text);";

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
        db.execSQL(CREATE_ROOM_ITEMS_TABLE);
        db.execSQL(CREATE_ROOM_LAMP1_TABLE);
        db.execSQL(CREATE_ROOM_LAMP2_TABLE);
        db.execSQL(CREATE_ROOM_LAMP3_TABLE);
        db.execSQL(CREATE_ROOM_TV_TABLE);
        db.execSQL(CREATE_ROOM_BLINDS_TABLE);

        db.execSQL(CREATE_AUTO_ITEMS);
        db.execSQL(CREATE_AUTO_TEMP);
        db.execSQL(CREATE_AUTO_RADIO);
        db.execSQL(CREATE_AUTO_CB);

        db.execSQL(CREATE_KITCHEN_ITEMS_TABLE);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_TV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_BLINDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_LAMP1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_LAMP2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_LAMP3);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_TEMP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_RADIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_CB);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KITCHEN_ITEMS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_TEMPIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_TEMPIN_SCHED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_GARAGE);

        onCreate(db);

        Log.i("ProjectDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    /* insert new room item */
    public long insertRoomItem(String title, String uri, int deviceType, long createdDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_DEVICE_TYPE, deviceType);
        room_item_values.put(COLUMN_ROOM_DEVICE_IMAGE, uri);
        room_item_values.put(COLUMN_ROOM_DEVICE_TITLE, title);
        room_item_values.put(COLUMN_ROOM_CREATED, createdDate);
        long _id = db.insert(TABLE_ROOM_ITEMS, null, room_item_values);

        room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_LAMP1_DEVICE_ID, _id);
        db.insert(TABLE_ROOM_LAMP1, null, room_item_values);

        room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_LAMP2_DEVICE_ID, _id);
        db.insert(TABLE_ROOM_LAMP2, null, room_item_values);

        room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_LAMP3_DEVICE_ID, _id);
        db.insert(TABLE_ROOM_LAMP3, null, room_item_values);

        room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_TV_DEVICE_ID, _id);
        db.insert(TABLE_ROOM_TV, null, room_item_values);

        room_item_values = new ContentValues();
        room_item_values.put(COLUMN_ROOM_BLINDS_DEVICE_ID, _id);
        db.insert(TABLE_ROOM_BLINDS, null, room_item_values);

        return _id;
    }

    /* delete room item */
    public void deleteRoomItem(long deviceId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ROOM_ITEMS, COLUMN_ROOM_ID + "=?",
                new String[]{Long.toString(deviceId)});

        db.delete(TABLE_ROOM_LAMP1, COLUMN_ROOM_LAMP1_DEVICE_ID + "=?",
                new String[]{Long.toString(deviceId)});

        db.delete(TABLE_ROOM_LAMP2, COLUMN_ROOM_LAMP2_DEVICE_ID + "=?",
                new String[]{Long.toString(deviceId)});

        db.delete(TABLE_ROOM_LAMP3, COLUMN_ROOM_LAMP3_DEVICE_ID + "=?",
                new String[]{Long.toString(deviceId)});

        db.delete(TABLE_ROOM_TV, COLUMN_ROOM_TV_DEVICE_ID + "=?",
                new String[]{Long.toString(deviceId)});

        db.delete(TABLE_ROOM_BLINDS, COLUMN_ROOM_BLINDS_DEVICE_ID + "=?",
                new String[]{Long.toString(deviceId)});
    }

    /* get room items */
    public Cursor getRoomItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_ITEMS + " ORDER BY " + COLUMN_ROOM_VISIT_COUNT + " DESC", null);

        return res;
    }

    /* set room item click count */
    public void addDeviceCount(long deviceId) {
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROOM_VISIT_COUNT + " FROM " + TABLE_ROOM_ITEMS + " WHERE " + COLUMN_ROOM_ID + "=" + deviceId, null);

        cursor.moveToFirst();
        count = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_VISIT_COUNT));

        count += 1;

        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.execSQL("UPDATE " + TABLE_ROOM_ITEMS + " SET " + COLUMN_ROOM_VISIT_COUNT + "=" + count + " WHERE " + COLUMN_ROOM_ID + "=" + deviceId);
    }

    /* set lamp 1's status */
    public void setLampOneStatus(long deviceId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP1_STATUS, status);
        db.update(TABLE_ROOM_LAMP1, values, COLUMN_ROOM_LAMP1_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* get lamp 1's table */
    public Cursor getRoomLampOne(long deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_LAMP1 + " WHERE " + COLUMN_ROOM_LAMP1_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});

        return res;
    }

    /* set lamp 2's status */
    public void setLampTwoStatus(long deviceId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP2_STATUS, status);
        db.update(TABLE_ROOM_LAMP2, values, COLUMN_ROOM_LAMP2_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set lamp 2's dim level */
    public void setLampTwoDimLevel(long deviceId, int dim_level) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP2_DIM_LEVEL, dim_level);
        db.update(TABLE_ROOM_LAMP2, values, COLUMN_ROOM_LAMP2_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* get lamp 2's table */
    public Cursor getRoomLampTwo(long deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_LAMP2 + " WHERE " + COLUMN_ROOM_LAMP2_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});

        return res;
    }

    /* set lamp 3's status */
    public void setLampThreeStatus(long deviceId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP3_STATUS, status);
        db.update(TABLE_ROOM_LAMP3, values, COLUMN_ROOM_LAMP3_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set lamp 3's dim level */
    public void setLampThreeDimLevel(long deviceId, int dim_level) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP3_DIM_LEVEL, dim_level);
        db.update(TABLE_ROOM_LAMP3, values, COLUMN_ROOM_LAMP3_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set lamp 3's color */
    public void setLampThreeColor(long deviceId, int color) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_LAMP3_COLOR, color);
        db.update(TABLE_ROOM_LAMP3, values, COLUMN_ROOM_LAMP3_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* get lamp 3's table */
    public Cursor getRoomLampThree(long deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_LAMP3 + " WHERE " + COLUMN_ROOM_LAMP3_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});

        return res;
    }

    /* set TV status */
    public void setTvStatus(long deviceId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TV_STATUS, status);
        db.update(TABLE_ROOM_TV, values, COLUMN_ROOM_TV_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set TV channel number */
    public void setTvChannel(long deviceId, int channel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TV_CHANNEL, channel);
        db.update(TABLE_ROOM_TV, values, COLUMN_ROOM_TV_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set TV volume */
    public void setTvVolume(long deviceId, int volume) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TV_VOLUME, volume);
        db.update(TABLE_ROOM_TV, values, COLUMN_ROOM_TV_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* get TV table */
    public Cursor getRoomTv(long deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_TV + " WHERE " + COLUMN_ROOM_TV_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});

        return res;
    }

    /* set Blinds status */
    public void setBlindsStatus(long deviceId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_BLINDS_STATUS, status);
        db.update(TABLE_ROOM_BLINDS, values, COLUMN_ROOM_BLINDS_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* set Blinds level */
    public void setBlindsLevel(long deviceId, int level) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_BLINDS_LEVEL, level);
        db.update(TABLE_ROOM_BLINDS, values, COLUMN_ROOM_BLINDS_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});
    }

    /* get blinds table */
    public Cursor getRoomBlinds(long deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_BLINDS + " WHERE " + COLUMN_ROOM_BLINDS_DEVICE_ID + "=?", new String[]{Long.toString(deviceId)});

        return res;
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