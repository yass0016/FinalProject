package com.sayadev.finalproject.livingroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.Blinding.Blinding;
import com.sayadev.finalproject.livingroom.Lamps.Lamp1;
import com.sayadev.finalproject.livingroom.Lamps.Lamp2;
import com.sayadev.finalproject.livingroom.Lamps.Lamp3;
import com.sayadev.finalproject.livingroom.TV.TV;

public class RoomDetails extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        Bundle data = this.getIntent().getExtras();

        int deviceType = Integer.parseInt(data.getString("deviceType"));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment f = null;

        if(deviceType == RoomData.DEVICE_TV) {
            f = new TV();
        } else if(deviceType == RoomData.DEVICE_LAMP1) {
            f = new Lamp1();
        } else if(deviceType == RoomData.DEVICE_LAMP2) {
            f = new Lamp2();
        } else if(deviceType == RoomData.DEVICE_LAMP3) {
            f = new Lamp3();
        } else if(deviceType == RoomData.DEVICE_BLINDING) {
            f = new Blinding();
        }

        f.setArguments(data);

        ft.replace(R.id.emptyFrame, f);
        ft.commit();
    }
}
