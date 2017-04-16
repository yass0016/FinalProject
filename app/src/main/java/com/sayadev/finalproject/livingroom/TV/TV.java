package com.sayadev.finalproject.livingroom.TV;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sayadev.finalproject.House.House;
import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.automobile.automobile;
import com.sayadev.finalproject.kitchen.KitchenMainActivity;
import com.sayadev.finalproject.livingroom.LivingRoom;

/**
 * Created by saleh on 3/26/2017.
 */

public class TV extends Fragment {

    private Bundle data;
    private ProjectDatabaseHelper dbHelper;

    private int channelNumber = 0;
    private int volume = 0;
    private int tvStatus = 0;

    public TextView tvInfo;
    public long device_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();

        device_id = Long.parseLong(data.getString("dbId"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_tv, container, false);

        tvInfo = (TextView)v.findViewById(R.id.tvInfo);

        final TextView volumeText = (TextView)v.findViewById(R.id.volumeText);

        final Button tvStatusButton = (Button) v.findViewById(R.id.tvStatus);

        Cursor cursor = dbHelper.getRoomTv(device_id);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tvStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_TV_STATUS));
            channelNumber = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_TV_CHANNEL));
            volume = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_TV_VOLUME));

            cursor.moveToNext();
        }

        if(tvStatus == 0) {
            volumeText.setVisibility(View.INVISIBLE);
            tvInfo.setVisibility(View.INVISIBLE);
            tvStatusButton.setText("TURN TV ON");
        } else {
            volumeText.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.VISIBLE);
            tvStatusButton.setText("TURN TV OFF");

            volumeText.setText("Volume " + volume);
            tvInfo.setText("Playing Channel " + channelNumber);
        }

        Button vDown = (Button) v.findViewById(R.id.volumeDown);
        vDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume > 0)
                    volume -= 1;
                else
                    volume = 0;

                volumeText.setVisibility(View.VISIBLE);
                volumeText.setText("Volume " + volume);

                dbHelper.setTvVolume(device_id, volume);
            }
        });

        Button vUp = (Button) v.findViewById(R.id.volumeUp);
        vUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume < 20)
                    volume += 1;
                else
                    volume = 20;

                volumeText.setVisibility(View.VISIBLE);
                volumeText.setText("Volume " + volume);
                dbHelper.setTvVolume(device_id, volume);
            }
        });

        Button cDown = (Button) v.findViewById(R.id.channelDown);
        cDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(channelNumber > 0)
                    channelNumber -= 1;
                else
                    channelNumber = 0;

                tvInfo.setText("Playing Channel " + channelNumber);
                dbHelper.setTvChannel(device_id, channelNumber);
            }
        });

        Button cUp = (Button) v.findViewById(R.id.channelUp);
        cUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(channelNumber < 10)
                    channelNumber += 1;
                else
                    channelNumber = 10;

                tvInfo.setText("Playing Channel " + channelNumber);
                dbHelper.setTvChannel(device_id, channelNumber);
            }
        });

        Button enter = (Button) v.findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTvChannel().show();
            }
        });

        tvStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvStatus == 1) {
                    tvInfo.setVisibility(View.INVISIBLE);
                    volumeText.setVisibility(View.INVISIBLE);
                    tvStatusButton.setText("TURN TV ON");
                    tvStatus = 0;
                } else {
                    tvInfo.setVisibility(View.VISIBLE);
                    volumeText.setVisibility(View.VISIBLE);
                    tvStatusButton.setText("TURN TV OFF");

                    volumeText.setText("Volume " + volume);
                    tvInfo.setText("Playing Channel " + channelNumber);
                    tvStatus = 1;
                }
                dbHelper.setTvStatus(device_id, tvStatus);
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

        View v = inflater.inflate(R.layout.tv_help_dialog, null);

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

    public Dialog selectTvChannel() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_add_tv_channel, null);

        final EditText channel = (EditText)v.findViewById(R.id.select_tv_channel);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            int number = Integer.parseInt(channel.getText().toString());
                            if(number <= 10 && number >= 0) {
                                channelNumber = number;
                                tvInfo.setText("Playing Channel " + number);
                                dbHelper.setTvChannel(device_id, number);
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Channel can't be less than 0 or larger than 10", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } catch (NumberFormatException e) {

                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
                startActivity(intent);
                return true;
            case R.id.action_two:
                intent = new Intent(getActivity(), KitchenMainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_three:
                intent = new Intent(getActivity(), House.class);
                startActivity(intent);
                return true;
            case R.id.action_four:
                intent = new Intent(getActivity(), automobile.class);
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
