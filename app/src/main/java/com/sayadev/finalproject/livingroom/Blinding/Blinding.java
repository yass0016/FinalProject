package com.sayadev.finalproject.livingroom.Blinding;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

/**
 * Created by saleh on 3/26/2017.
 */

public class Blinding extends BaseActivity {
    private ImageView image;
    private TextView blindingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blinding);

        getSupportActionBar().setTitle("House Blindings");

        final Bundle data = this.getIntent().getExtras();


        image = (ImageView) findViewById(R.id.blindingImage);

        image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getPackageName())));

        blindingText = (TextView) findViewById(R.id.blindingText);

        blindingText.setText("ID: " + data.getString("id"));

    }
}
