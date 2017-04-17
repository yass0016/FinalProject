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


    //Automobile table and columns
    public static final String TABLE_AUTO_ITEMS = "autoItems";
    public static final String COLUMN_AUTO_ID = "_id";
    public static final String COLUMN_AUTO_NAME = "name";
    public static final String COLUMN_AUTO_DESCRIPTION = "description";

    // Database creation sql statement
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

    private static final String CREATE_AUTO_ITEMS = "create table " + TABLE_AUTO_ITEMS + " ( "
            + COLUMN_AUTO_ID + " integer primary key autoincrement, "
            + COLUMN_AUTO_NAME + " text, " + COLUMN_AUTO_DESCRIPTION + " text);";

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
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProjectDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_ITEMS);
        db.execSQL("drop table if exists " + TABLE_AUTO_ITEMS);
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
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ROOM_ITEMS, null);
        return res;
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
}