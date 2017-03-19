package com.sayadev.finalproject.livingroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sayadev.finalproject.R;

public class LivingRoom extends AppCompatActivity {

    private ListView roomItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        roomItems = (ListView)findViewById(R.id.roomItems);


    }
}
