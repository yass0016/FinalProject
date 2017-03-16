package com.sayadev.finalproject.kitchen;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sayadev.finalproject.R;

public class KitchenMainActivity extends AppCompatActivity {

    protected FloatingActionButton addDeviceFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);

        addDeviceFAB = (FloatingActionButton) findViewById(R.id.add_device_fab);
    }
}
