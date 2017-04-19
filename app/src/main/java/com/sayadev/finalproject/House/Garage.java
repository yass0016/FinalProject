package com.sayadev.finalproject.House;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class Garage extends Fragment {
    private ProjectDatabaseHelper dbHelper;
    private Bundle data;

    private int lampStatus = 0;
    private int garageStatus = 0;
    private long device_id;
    private ImageView garageImage;
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_garage, container, false);

        garageImage = (ImageView) v.findViewById(R.id.garageImage);

        device_id = Long.parseLong(data.getString("id"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        Cursor cursor = dbHelper.getHouseGarage(device_id);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            garageStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_GARAGE_DOOR));
            lampStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_GARAGE_LIGHT));

            cursor.moveToNext();
        }

        final Button openGarage = (Button) v.findViewById(R.id.openGarage);
        final Button openLight = (Button) v.findViewById(R.id.openLamp);
        text = (TextView) v.findViewById(R.id.garageText);

        if (garageStatus == 0) {
            openGarage.setText(getResources().getText(R.string.open_garage));
            garageImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.garageclosed));

        } else {
            openGarage.setText(getResources().getText(R.string.close_garage));
            garageImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.garageopen));
        }


        if (lampStatus == 0) {
            openLight.setText(getResources().getText(R.string.light_on));
        } else {
            openLight.setText(getResources().getText(R.string.light_off));
        }

        text.setText(getResources().getText(R.string.garage_is)
                + " " + ((garageStatus == 1) ? getResources().getText(R.string.open) : getResources().getText(R.string.closed))
                + " " + getResources().getText(R.string.light_is)
                + " " + ((lampStatus == 1) ? getResources().getText(R.string.on) : getResources().getText(R.string.off)));

        openGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (garageStatus == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.opened_garage), Toast.LENGTH_SHORT).show();
                    openGarage.setText(getResources().getText(R.string.close_garage));
                    openLight.setText(getResources().getText(R.string.light_off));
                    garageStatus = 1;
                    lampStatus = 1;
                    garageImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.garageopen));
                    dbHelper.setHouseGarageLightStatus(device_id, lampStatus);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.closed_garage), Toast.LENGTH_SHORT).show();
                    openGarage.setText(getResources().getText(R.string.open_garage));
                    garageStatus = 0;
                    garageImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.garageclosed));

                }
                dbHelper.setHouseGarageDoorStatus(device_id, garageStatus);
                text.setText(getResources().getText(R.string.garage_is)
                        + " " + ((garageStatus == 1) ? getResources().getText(R.string.open) : getResources().getText(R.string.closed))
                        + " " + getResources().getText(R.string.light_is)
                        + " " + ((lampStatus == 1) ? getResources().getText(R.string.on) : getResources().getText(R.string.off)));

            }
        });

        openLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lampStatus == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.turned_light_on), Toast.LENGTH_SHORT).show();
                    openLight.setText(getResources().getText(R.string.light_off));
                    lampStatus = 1;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.turned_light_off), Toast.LENGTH_SHORT).show();
                    openLight.setText(getResources().getText(R.string.light_on));
                    lampStatus = 0;
                }
                dbHelper.setHouseGarageLightStatus(device_id, lampStatus);
                text.setText(getResources().getText(R.string.garage_is)
                        + " " + ((garageStatus == 1) ? getResources().getText(R.string.open) : getResources().getText(R.string.closed))
                        + " " + getResources().getText(R.string.light_is)
                        + " " + ((lampStatus == 1) ? getResources().getText(R.string.on) : getResources().getText(R.string.off)));

            }
        });

        return v;
    }
}
