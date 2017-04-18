package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-04-12.
 */

public class TempOutFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempout_details);

        Bundle data = this.getIntent().getExtras();

        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        TempOut tempout = new TempOut();
        tempout.setArguments(data);

        fragTrans.replace(R.id.tempoutFrame, tempout);
        fragTrans.commit();
    }
}
