package com.sayadev.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sayadev.finalproject.House.House;
import com.sayadev.finalproject.automobile.automobile;
import com.sayadev.finalproject.kitchen.KitchenMainActivity;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
/*
        appOne = (Button) findViewById(R.id.appOne);
        appOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LivingRoom.class);
                startActivity(intent);
            }
        });

        appTwo = (Button) findViewById(R.id.appTwo);
        appTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenMainActivity.class);
                startActivity(intent);
            }
        });

        appThree = (Button) findViewById(R.id.appThree);
        appThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, House.class);
                startActivity(intent);
            }
        });

        appFour = (Button) findViewById(R.id.appFour);
        appFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // replace null with our main application activity
                Intent intent = new Intent(MainActivity.this, automobile.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_one:
                intent = new Intent(MainActivity.this, LivingRoom.class);
                startActivity(intent);
                return true;
            case R.id.action_two:
                intent = new Intent(MainActivity.this, KitchenMainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_three:
                intent = new Intent(MainActivity.this, House.class);
                startActivity(intent);
                return true;
            case R.id.action_four:
                intent = new Intent(MainActivity.this, automobile.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
