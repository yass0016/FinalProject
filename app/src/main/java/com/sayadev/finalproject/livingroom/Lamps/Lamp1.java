package com.sayadev.finalproject.livingroom.Lamps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.LivingRoom;

/**
 * Created by saleh on 3/26/2017.
 */

public class Lamp1 extends Fragment {

    private ProjectDatabaseHelper dbHelper;
    private Bundle data;

    private int lampStatus = 0;
    public long device_id;

    public ImageView lampImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_lamp1, container, false);

        lampImage = (ImageView) v.findViewById(R.id.lampsImage);

        device_id = Long.parseLong(data.getString("dbId"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        Cursor cursor = dbHelper.getRoomLampOne(device_id);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lampStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_LAMP1_STATUS));

            cursor.moveToNext();
        }

        final Button lightStatus = (Button) v.findViewById(R.id.switchLamp);

        if (lampStatus == 0) {
            lightStatus.setText("SWITCH LAMP ON");
            lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_off));
        } else {
            lightStatus.setText("SWITCH LAMP OFF");
            lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_on));
        }

        lightStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lampStatus == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light ON", Toast.LENGTH_SHORT).show();
                    lightStatus.setText("SWITCH LAMP OFF");
                    lampStatus = 1;
                    lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_on));

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light OFF", Toast.LENGTH_SHORT).show();
                    lightStatus.setText("SWITCH LAMP ON");
                    lampStatus = 0;
                    lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_off));
                }
                dbHelper.setLampOneStatus(device_id, lampStatus);

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (data.getString("orientation").equals("port")) {
            inflater.inflate(R.menu.main_activity, menu);
        }

        menu.add("Delete Device").setOnMenuItemClickListener(this.AddDevice)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    // Capture menu item click
    MenuItem.OnMenuItemClickListener AddDevice = new MenuItem.OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem item) {
            createDeleteDialog().show();
            return false;
        }
    };

    public Dialog createCustomDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.lamp1_help_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    public Dialog createDeleteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_delete_room_device, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (data.getString("orientation").equals("port")) {
                            Intent i = new Intent();
                            i.putExtra("id", data.getString("id"));
                            getActivity().setResult(5, i);
                            getActivity().finish();
                        } else {
                            ((LivingRoom) getActivity()).deleteItem(Integer.parseInt(data.getString("id")));
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_one:
                intent = new Intent(getActivity(), LivingRoom.class);
                getActivity().finish();
                startActivity(intent);
                return true;
            case R.id.action_two:
                intent = new Intent(getActivity(), LivingRoom.class);
                getActivity().finish();
                startActivity(intent);
                return true;
            case R.id.action_three:
                intent = new Intent(getActivity(), LivingRoom.class);
                getActivity().finish();
                startActivity(intent);
                return true;
            case R.id.action_four:
                intent = new Intent(getActivity(), LivingRoom.class);
                getActivity().finish();
                startActivity(intent);
                return true;
            case R.id.action_help:
                createCustomDialog().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
