package com.sayadev.finalproject.livingroom.Lamps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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
import android.widget.SeekBar;
import android.widget.Toast;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.LivingRoom;

public class Lamp3 extends Fragment {

    private ProjectDatabaseHelper dbHelper;
    private Bundle data;

    private int lampStatus = 0;
    private int lampBrightness = 0;
    private int lampColor;

    public long device_id;

    public ImageView lampImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();
        setHasOptionsMenu(true);

        device_id = Long.parseLong(data.getString("dbId"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        View v = inflater.inflate(R.layout.activity_lamp3, container, false);

        lampImage = (ImageView) v.findViewById(R.id.lampsImage);

        final ColorPicker cp = new ColorPicker(getActivity(), 0, 0, 0);

        Cursor cursor = dbHelper.getRoomLampThree(device_id);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lampStatus = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_LAMP3_STATUS));
            lampBrightness = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_LAMP3_DIM_LEVEL));
            lampColor = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_LAMP3_COLOR));
            cursor.moveToNext();
        }

        final Button lightStatus = (Button) v.findViewById(R.id.switchLamp);
        final Button lightColor = (Button) v.findViewById(R.id.changeLampColor);

        final SeekBar dimLevel = (SeekBar) v.findViewById(R.id.brightnessLamp);

        dimLevel.setProgress(lampBrightness);

        dimLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                if(lampStatus == 1)
                    lampImage.setImageBitmap(SetBrightness(BitmapFactory.decodeResource(getResources(), R.drawable.light_on),progresValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dbHelper.setLampThreeDimLevel(device_id, progress);
            }
        });


        if (lampStatus == 0) {
            lightStatus.setText("SWITCH LAMP ON");
            dimLevel.setEnabled(false);
            lightColor.setEnabled(false);
            lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_off));
        } else {
            lightStatus.setText("SWITCH LAMP OFF");
            dimLevel.setEnabled(true);
            lightColor.setEnabled(true);
            lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_on));
            lampImage.setImageBitmap(SetBrightness(BitmapFactory.decodeResource(getResources(), R.drawable.light_on), lampBrightness));
            lampImage.setBackgroundColor(lampColor);
        }

        lightStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lampStatus == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light ON", Toast.LENGTH_SHORT).show();
                    lightStatus.setText("SWITCH LAMP OFF");
                    lampStatus = 1;
                    dimLevel.setEnabled(true);
                    lightColor.setEnabled(true);
                    lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_on));
                    lampImage.setImageBitmap(SetBrightness(BitmapFactory.decodeResource(getResources(), R.drawable.light_on), lampBrightness));
                    lampImage.setBackgroundColor(lampColor);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light OFF", Toast.LENGTH_SHORT).show();
                    lightStatus.setText("SWITCH LAMP ON");
                    lampStatus = 0;
                    dimLevel.setEnabled(false);
                    lightColor.setEnabled(false);
                    lampImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.light_off));
                    lampImage.setBackgroundColor(-1);

                }
                dbHelper.setLampThreeStatus(device_id, lampStatus);

            }
        });

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                cp.hide();
                dbHelper.setLampThreeColor(device_id, color);
                lampImage.setBackgroundColor(color);
            }
        });

        lightColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.show();
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

        View v = inflater.inflate(R.layout.lamp3_help_dialog, null);

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

    /*
        Source: http://shaikhhamadali.blogspot.ca/2013/07/set-brightness-of-imageincreasedecrease.html
     */
    public Bitmap SetBrightness(Bitmap src, int value) {
        // original image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if (R > 255) {
                    R = 255;
                } else if (R < 0) {
                    R = 0;
                }

                G += value;
                if (G > 255) {
                    G = 255;
                } else if (G < 0) {
                    G = 0;
                }

                B += value;
                if (B > 255) {
                    B = 255;
                } else if (B < 0) {
                    B = 0;
                }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }
}
