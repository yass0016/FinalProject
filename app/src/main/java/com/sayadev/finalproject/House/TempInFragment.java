package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-04-12.
 */

public class TempInFragment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempin_details);

        Bundle data = this.getIntent().getExtras();

        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        TempIn tempin = new TempIn();
        tempin.setArguments(data);

        fragTrans.replace(R.id.tempinFrame, tempin);
        fragTrans.commit();
    }
}
