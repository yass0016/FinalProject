package com.sayadev.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;

/**
 * Created by salehyassin on 3/19/17.
 */

public class BaseActivity extends AppCompatActivity {
    ProjectDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new ProjectDatabaseHelper(this);
    }

    public ProjectDatabaseHelper getDbHelper() { return  dbHelper; }

}
