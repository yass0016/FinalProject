package com.sayadev.finalproject.livingroom;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sayadev.finalproject.R;

/**
 * Created by saleh on 4/4/2017.
 */

public class RoomFragment extends Fragment {
    private int deviceType;
    private String deviceOrientation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = this.getArguments();

        deviceOrientation = data.getString("orientation");
        deviceType = Integer.parseInt(data.getString("deviceType"));

        // Inflate the layout for this fragment
        View v = null;

        ImageView roomItemImage = null;
        Log.i("Info", " " + deviceType);
        if(deviceType == RoomData.DEVICE_TV) {
            v = inflater.inflate(R.layout.activity_tv, container, false);
            roomItemImage = (ImageView) v.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));
        } else if(deviceType == RoomData.DEVICE_LAMP1) {
            v = inflater.inflate(R.layout.activity_lamps, container, false);
            roomItemImage = (ImageView) v.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));
        } else if(deviceType == RoomData.DEVICE_LAMP2) {
            v = inflater.inflate(R.layout.activity_lamps, container, false);
            roomItemImage = (ImageView) v.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));
        } else if(deviceType == RoomData.DEVICE_LAMP3) {
            v = inflater.inflate(R.layout.activity_lamps, container, false);
            roomItemImage = (ImageView) v.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));
        } else if(deviceType == RoomData.DEVICE_BLINDING) {
            v = inflater.inflate(R.layout.activity_blinding, container, false);
            roomItemImage = (ImageView) v.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(, null, getPackageName())));
        }

        return v;
    }
}
