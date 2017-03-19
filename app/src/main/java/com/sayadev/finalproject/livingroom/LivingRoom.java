package com.sayadev.finalproject.livingroom;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

public class LivingRoom extends BaseActivity {

    private ListView roomItems;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        roomItems = (ListView) findViewById(R.id.roomItems);

        db = getDbHelper().getWritableDatabase();

    }
}
