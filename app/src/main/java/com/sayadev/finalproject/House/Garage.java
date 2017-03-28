package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class Garage extends BaseActivity {
    private ImageView image;
    private TextView tempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_garage);

        getSupportActionBar().setTitle("Garage");

        final Bundle data = this.getIntent().getExtras();


        image = (ImageView) findViewById(R.id.garageImage);

        image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getPackageName())));

        tempText = (TextView) findViewById(R.id.garageText);

        tempText.setText("ID: " + data.getString("id"));

    }
}
