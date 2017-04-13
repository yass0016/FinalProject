package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-04-12.
 */

public class TempOutFragment extends BaseActivity {

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
