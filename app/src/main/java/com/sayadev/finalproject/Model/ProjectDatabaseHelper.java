package com.sayadev.finalproject.Model;

import android.content.Context;
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
    public static final String TABLE_ROOM_ITEMS = "roomItems";

    public static final String COLUMN_ROOM_ID = "_id";
    public static final String COLUMN_OTHER = "other";

    //Automobile table and columns
    public static final String TABLE_AUTO_ITEMS = "autoItems";
    public static final String COLUMN_AUTO_ID = "_id";
    public static final String COLUMN_AUTO_NAME = "name";
    public static final String COLUMN_AUTO_DESCRIPTION = "description";
    public static final String COLUMN_AUTO_TYPE = "type";

    //Auto programmed buttons tables
    public static final String TABLE_AUTO_TEMP = "programmedButtons";
    public static final String COLUMN_TEMP_ID = "_id";
    public static final String COLUMN_TEMP_TEMP = "temp";
    public static final String COLUMN_TEMP_FAN = "fanSpeed";
    public static final String COLUMN_TEMP_AC = "ac";


    // Database creation sql statement
    private static final String CREATE_ROOM_ITEMS_TABLE = "create table "
            + TABLE_ROOM_ITEMS + "( " + COLUMN_ROOM_ID
            + " integer primary key autoincrement, " + COLUMN_OTHER
            + " text not null);";

    private static final String CREATE_AUTO_ITEMS = "create table " + TABLE_AUTO_ITEMS +  " ( "
            + COLUMN_AUTO_ID + " integer primary key autoincrement, "
            +  COLUMN_AUTO_NAME + " text, " + COLUMN_AUTO_DESCRIPTION + " text, " + COLUMN_AUTO_TYPE + " text);";

    private static final String CREATE_AUTO_TEMP = "create table " + TABLE_AUTO_TEMP + " ( "
            + COLUMN_TEMP_ID  + " integer primary key autoincrement, " + COLUMN_TEMP_TEMP + " integer, " + COLUMN_TEMP_FAN + " integer, "
            +COLUMN_TEMP_AC + " integer);";

    public ProjectDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROOM_ITEMS_TABLE);
        db.execSQL(CREATE_AUTO_ITEMS);
        db.execSQL(CREATE_AUTO_TEMP);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProjectDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS messages");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO_TEMP);
        onCreate(db);

        Log.i("ProjectDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }
}