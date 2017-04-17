package com.sayadev.finalproject.livingroom.Blinding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.LivingRoom;

/**
 * Created by saleh on 3/26/2017.
 */

public class Blinding extends Fragment {

    private SeekBar seek;
    private TextView blindingText;

    private ProjectDatabaseHelper dbHelper;
    private Bundle data;

    public long device_id;

    private int blindStatus = 0;
    private int blindLevel = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_blinding, container, false);

        device_id = Long.parseLong(data.getString("dbId"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        Cursor cursor = dbHelper.getRoomBlinds(device_id);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            blindStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_BLINDS_STATUS));
            blindLevel = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_BLINDS_LEVEL));

            cursor.moveToNext();
        }

        blindingText = (TextView) v.findViewById(R.id.blindingText);
        seek = (SeekBar) v.findViewById(R.id.blindingSeek);


        seek.setProgress(blindLevel);
        if(blindStatus == 1) {
            blindingText.setText("Blinds Level is " + blindLevel);
        } else {
            blindingText.setText("Blinds is Closed");
        }

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                blindingText.setText("Blinds Level is " + progresValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dbHelper.setBlindsLevel(device_id, progress);

                if(progress == 0)
                    dbHelper.setBlindsStatus(device_id, 0);
                else
                    dbHelper.setBlindsStatus(device_id, 1);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(data.getString("orientation").equals("port")) {
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

        View v = inflater.inflate(R.layout.blind_help_dialog, null);

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
                        if(data.getString("orientation").equals("port")) {
                            Intent i = new Intent();
                            i.putExtra("id", data.getString("id"));
                            getActivity().setResult(5, i);
                            getActivity().finish();
                        } else {
                            ((LivingRoom)getActivity()).deleteItem(Integer.parseInt(data.getString("id")));
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
