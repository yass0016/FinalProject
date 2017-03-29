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

    // Database creation sql statement
    private static final String CREATE_ROOM_ITEMS_TABLE = "create table "
            + TABLE_ROOM_ITEMS + "( " + COLUMN_ROOM_ID
            + " integer primary key autoincrement, " + COLUMN_OTHER
            + " text not null);";

    public ProjectDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROOM_ITEMS_TABLE);

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
}