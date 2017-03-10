package com.sayadev.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sayadev.finalproject.livingroom.LivingRoom;

public class MainActivity extends AppCompatActivity {

    private Button appOne;
    private Button appTwo;
    private Button appThree;
    private Button appFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appOne = (Button) findViewById(R.id.appOne);
        appOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, LivingRoom.class);
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
}
