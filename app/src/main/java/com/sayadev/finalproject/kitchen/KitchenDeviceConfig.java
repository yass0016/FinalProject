package com.sayadev.finalproject.kitchen;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.sayadev.finalproject.R;

public class KitchenDeviceConfig extends AppCompatActivity {

    protected FrameLayout fl;
    protected Fragment deviceConfigFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getIntent().getExtras();

        fl = new FrameLayout(this);
        fl.setId(R.id.kitchen_device_config_frame);
        fl.setVisibility(View.GONE);
        setContentView(fl);

        getSupportActionBar().setTitle(
                args.getString("deviceName") + " (" + args.getString("deviceModel") + ")"
        );

        switch ((KitchenDeviceType) args.getSerializable("deviceType")) {
            case COFFEE_MAKER:
                deviceConfigFragment = new CoffeeMakerConfigFragment();
                break;
            case REFRIGERATOR:
                deviceConfigFragment = new RefrigeratorConfigFragment();
                break;
        }

        deviceConfigFragment.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.kitchen_device_config_frame, deviceConfigFragment);
        ft.commit();
    }
}
