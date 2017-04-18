package com.sayadev.finalproject.House;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class TempIn extends Fragment {

    private ProjectDatabaseHelper dbHelper;
    private Bundle data;

    private ArrayList<HouseTempInData> tempInItems;
    private TempInAdapter adapter;

    private ListView tempInList;
    private Button addSched;

    private TextView tempText;
    private Button addTemperature;
    private EditText tempData;

    private int mHour, mMinute, temperature;
    private long device_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_temprature_inside, container, false);

        data = this.getArguments();

        device_id = Long.parseLong(data.getString("id"));

        dbHelper = new ProjectDatabaseHelper(getActivity());

        tempInList = (ListView) view.findViewById(R.id.tempInSched);
        addSched = (Button) view.findViewById(R.id.addTempInShed);


        addTemperature = (Button) view.findViewById(R.id.addTemperature);
        tempText = (TextView) view.findViewById(R.id.tempInTitle);
        tempData = (EditText) view.findViewById(R.id.tempInData);

        tempInItems = new ArrayList<>();

        adapter = new TempInAdapter(getActivity());
        tempInList.setAdapter(adapter);

        Cursor cursor = dbHelper.getHouseTempIn(device_id);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            temperature = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_TEMPIN_CURRENT_TEMP));

            cursor.moveToNext();
        }

        tempText.setText("Current Set Temperature: " + temperature + " \u2103");

        getSchedItems();
        adapter.notifyDataSetChanged();

        addSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchedDialog().show();
            }
        });

        addTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    temperature = Integer.parseInt(tempData.getText().toString());
                    tempText.setText("Current Set Temperature: " + temperature + " \u2103");
                    dbHelper.setHouseTempInTemp(device_id, temperature);
                } catch (NumberFormatException e) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Remember to Enter Temperature", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        tempInList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Object o = tempInList.getItemAtPosition(position);
                HouseTempInData tempInData = (HouseTempInData) o;
                updateSchedDialog(position, id).show();
            }
        });

        tempInList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                final long deviceId = id;
                final int itemPosition = position;

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Delete Item")
                        .setMessage("Are you sure you would like to delete this item?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                dbHelper.deleteHouseTempInSched(device_id, deviceId);
                                tempInItems.remove(itemPosition);
                                adapter.notifyDataSetChanged();

                            }
                        }).show();

                return false;
            }
        });

        return view;
    }

    private void getSchedItems() {
        tempInItems = new ArrayList<>();

        Cursor cursor = dbHelper.getHouseTempInSched(device_id);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_TEMPIN_SCHED_ID));
            long datetime = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_TEMPIN_S_DATE));
            int temp = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_TEMPIN_S_TEMP));

            HouseTempInData data = new HouseTempInData(_id, temp, datetime);

            tempInItems.add(data);

            cursor.moveToNext();
        }

        cursor.close();
    }

    /*
        Source: http://www.journaldev.com/9976/android-date-time-picker-dialog
    */
    private Dialog updateSchedDialog(final int position, final long itemId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_add_tempin_shed, null);

        final TextView txtTime = (TextView) v.findViewById(R.id.in_time);
        final EditText temp = (EditText) v.findViewById(R.id.in_temp);

        temp.setText(Integer.toString(tempInItems.get(position).getTemp()));
        Date d = new Date(tempInItems.get(position).getDatetime());
        SimpleDateFormat df = new SimpleDateFormat("H:mm:ss");
        txtTime.setText(df.format(d));

        Button addTime = (Button) v.findViewById(R.id.btn_time);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                txtTime.setText(mHour + ":" + mMinute + ":00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Calendar c = Calendar.getInstance();
                        c.set(0, 0, 0, mHour, mMinute);

                        try {
                            dbHelper.setHouseTempInSchedDate(device_id, itemId, c.getTime().getTime());
                            dbHelper.setHouseTempInSchedTemp(device_id, itemId, Integer.parseInt(temp.getText().toString()));

                            tempInItems.set(position, new HouseTempInData(itemId, Integer.parseInt(temp.getText().toString()), c.getTime().getTime()));
                            adapter.notifyDataSetChanged();
                        } catch (NumberFormatException e) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Sheduale was not created. Remember to Enter Temperature!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    /*
        Source: http://www.journaldev.com/9976/android-date-time-picker-dialog
     */
    private Dialog addSchedDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_add_tempin_shed, null);

        final TextView txtTime = (TextView) v.findViewById(R.id.in_time);
        final EditText temp = (EditText) v.findViewById(R.id.in_temp);

        Button addTime = (Button) v.findViewById(R.id.btn_time);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                txtTime.setText(mHour + ":" + mMinute + ":00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Calendar c = Calendar.getInstance();
                        c.set(0, 0, 0, mHour, mMinute);

                        try {
                            long _id = dbHelper.insertHouseTempInSched(device_id, c.getTime().getTime(), Integer.parseInt(temp.getText().toString()));
                            tempInItems.add(new HouseTempInData(_id, Integer.parseInt(temp.getText().toString()), c.getTime().getTime()));
                            adapter.notifyDataSetChanged();
                        } catch (NumberFormatException e) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Sheduale was not created. Remember to Enter Temperature!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    private class TempInAdapter extends ArrayAdapter<HouseTempInData> {

        public TempInAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return tempInItems.size();
        }

        public long getItemId(int position) {
            return tempInItems.get(position).get_id();
        }

        public HouseTempInData getItem(int position) {
            return tempInItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = null;

            result = inflater.inflate(R.layout.house_tempin_list_row, null);

            TextView tempInDate = (TextView) result.findViewById((R.id.houseTempInTimeDate));
            Date d = new Date(getItem(position).getDatetime());
            SimpleDateFormat df = new SimpleDateFormat("h:mm a");
            tempInDate.setText(df.format(d));

            TextView tempInTemp = (TextView) result.findViewById((R.id.houseTempInTemp));
            tempInTemp.setText(Integer.toString(getItem(position).getTemp()) + " \u2103");

            return result;
        }
    }
}
