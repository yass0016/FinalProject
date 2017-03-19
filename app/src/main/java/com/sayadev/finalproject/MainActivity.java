package com.sayadev.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;

public abstract class MainActivity extends AppCompatActivity {

    private Button appOne;
    private Button appTwo;
    private Button appThree;
    private Button appFour;

    private ProjectDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ProjectDatabaseHelper(this);

        appOne = (Button) findViewById(R.id.appOne);
        appOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, null);
                startActivity(intent);
            }
        });

        appTwo = (Button) findViewById(R.id.appTwo);
        appTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, null);
                startActivity(intent);
            }
        });

        appThree = (Button) findViewById(R.id.appThree);
        appThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, null);
                startActivity(intent);
            }
        });

        appFour = (Button) findViewById(R.id.appFour);
        appFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, null);
                startActivity(intent);
            }
        });

    }

    public ProjectDatabaseHelper getDbHelper() {
        return dbHelper;
    }
}
