package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class TempIn extends BaseActivity {
    private ImageView image;
    private TextView tempText;
    private Button addTemprature;
    private EditText tempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_temprature_inside);

        getSupportActionBar().setTitle("Temprature Inside House");

        final Bundle data = this.getIntent().getExtras();


        image = (ImageView) findViewById(R.id.tempInImage);

        image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getPackageName())));

        tempText = (TextView) findViewById(R.id.tempInTitle);
        tempData = (EditText) findViewById(R.id.tempInData);
        addTemprature = (Button) findViewById(R.id.addTemprature);

        addTemprature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempData.getText().length() != 0) {
                    tempText.setText("Temprature ID: " + data.getString("id") + " Current Temprature: " + tempData.getText().toString() + " \u2103");
                }
            }
        });

    }
}
