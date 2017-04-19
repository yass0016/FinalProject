package com.sayadev.finalproject.kitchen;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;


import java.util.ArrayList;

public class KitchenMainActivity extends AppCompatActivity {


    final static String ACTIVITY_NAME = "KitchenMainActivity";
    final Context context = this;

    protected FloatingActionButton addDeviceFAB;
    protected ListView deviceListView;
    protected ProgressBar deviceProgressBar;

    protected ArrayList<KitchenDevice> deviceList;
    protected ProjectDatabaseHelper databaseHelper;
    protected SQLiteDatabase db;

    protected KitchenDeviceAdapter deviceAdapter;

    protected boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);
        getSupportActionBar().setTitle("Kitchen Items");

        databaseHelper = new ProjectDatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();

        deviceAdapter = new KitchenDeviceAdapter(this);

        isTablet = (findViewById(R.id.device_config_frame) != null);

        deviceListView = (ListView) findViewById(R.id.device_list);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KitchenDevice clickedDevice = (KitchenDevice) parent.getItemAtPosition(position);
                showDeviceInfoDialog(clickedDevice, position);
            }
        });

        deviceProgressBar = (ProgressBar) findViewById(R.id.device_list_load_progress);

        new DatabaseQuery().execute();

        addDeviceFAB = (FloatingActionButton) findViewById(R.id.add_device_fab);
        addDeviceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog_kitchen_add_device, null);

                final Spinner spinner =
                        (Spinner) dialogView.findViewById(R.id.add_kitchen_device_type);
                ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                        R.array.kitchen_device_array, android.R.layout.simple_spinner_dropdown_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.kitchen_add_device_title)
                        .setView(dialogView)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText nameEditText =
                                        (EditText) dialogView.findViewById(R.id.add_kitchen_device_name);
                                EditText modelEditText =
                                        (EditText) dialogView.findViewById(R.id.add_kitchen_device_model);
                                String deviceType = (String) spinner.getSelectedItem();

                                String name = nameEditText.getText().toString();
                                String model = modelEditText.getText().toString();
                                KitchenDeviceType type = null;

                                if (deviceType.equals(
                                        getString(R.string.kitchen_device_type_coffee_maker))) {
                                    type = KitchenDeviceType.COFFEE_MAKER;
                                }
                                else if (deviceType.equals(
                                        getString(R.string.kitchen_device_type_refrigerator))) {
                                    type = KitchenDeviceType.REFRIGERATOR;
                                }

                                if (name.matches("") || model.matches(""))
                                {
                                    Toast.makeText(context,
                                            R.string.kitchen_add_device_empty_field,
                                            Toast.LENGTH_SHORT).show();

                                    return;
                                }

                                deviceAdapter.addDevice(name, model, type);
                            }
                        });

                AlertDialog addDeviceDialog = builder.create();
                addDeviceDialog.show();
            }
        });
    }

    private void showDeviceInfoDialog(final KitchenDevice device, final int position) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_kitchen_about_device, null);

        TextView deviceModel = (TextView) dialogView.findViewById(R.id.about_kitchen_device_model);
        TextView deviceName = (TextView) dialogView.findViewById(R.id.about_kitchen_device_name);
        TextView deviceType = (TextView) dialogView.findViewById(R.id.about_kitchen_device_type);

        switch (device.getType()) {
            case REFRIGERATOR:
                deviceType.setText(R.string.kitchen_device_type_refrigerator);
                break;
            case COFFEE_MAKER:
                deviceType.setText(R.string.kitchen_device_type_coffee_maker);
                break;
        }

        deviceModel.setText(device.getModel());
        deviceName.setText(device.getName());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.kitchen_show_device_title)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton(R.string.kitchen_show_device_config, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle newBundle = new Bundle();
                        newBundle.putString("deviceName", device.getName());
                        newBundle.putString("deviceModel", device.getModel());
                        newBundle.putSerializable("deviceType", device.getType());

                        Intent deviceConfigIntent =
                                new Intent(getApplicationContext(), KitchenDeviceConfig.class);
                        deviceConfigIntent.putExtras(newBundle);
                        startActivity(deviceConfigIntent);
                    }
                })
                .setNegativeButton(R.string.kitchen_show_device_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deviceAdapter.deleteDevice(position);
                    }
                });

        AlertDialog showDeviceDialog = builder.create();
        showDeviceDialog.show();
    }

    private class KitchenDeviceAdapter extends ArrayAdapter<KitchenDevice> {

        private KitchenDeviceAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return deviceList.size();
        }

        public KitchenDevice getItem(int position) {
            return deviceList.get(position);
        }

        public void addDevice(String name, String model, KitchenDeviceType type) {
            ContentValues cv = new ContentValues();
            cv.put(ProjectDatabaseHelper.COLUMN_KITCHEN_NAME, name);
            cv.put(ProjectDatabaseHelper.COLUMN_KITCHEN_MODEL, model);
            cv.put(ProjectDatabaseHelper.COLUMN_KITCHEN_TYPE, type.toString());

            long id = db.insert(ProjectDatabaseHelper.TABLE_KITCHEN_ITEMS, null, cv);

            KitchenDevice device = new KitchenDevice(type, name, model, id);
            deviceList.add(device);
            this.notifyDataSetChanged();
        }

        public void deleteDevice(int position) {
            long id = getItem(position).getId();
            String sId = Long.toString(id);

            db.delete(ProjectDatabaseHelper.TABLE_KITCHEN_ITEMS,
                    ProjectDatabaseHelper.COLUMN_KITCHEN_ID + "=" + sId,
                    null);

            deviceList.remove(position);
            this.notifyDataSetChanged();
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = KitchenMainActivity.this.getLayoutInflater();
            View deviceView = inflater.inflate(R.layout.kitchen_device_row, null);

            KitchenDevice device = getItem(position);

            TextView deviceName = (TextView) deviceView.findViewById(R.id.device_name);
            TextView deviceModel = (TextView) deviceView.findViewById(R.id.device_model);

            if (device != null) {
                deviceName.setText(device.getName());
                deviceModel.setText(device.getModel());
            }

            return deviceView;
        }
    }

    private class DatabaseQuery extends AsyncTask<Void, Integer, ArrayList<KitchenDevice>> {

        @Override
        protected ArrayList<KitchenDevice> doInBackground(Void... params) {
            ArrayList<KitchenDevice> newDeviceList = new ArrayList<>();

            Cursor cursor = db.query(ProjectDatabaseHelper.TABLE_KITCHEN_ITEMS,
                    new String[] {ProjectDatabaseHelper.COLUMN_KITCHEN_ID,
                            ProjectDatabaseHelper.COLUMN_KITCHEN_NAME,
                            ProjectDatabaseHelper.COLUMN_KITCHEN_MODEL,
                            ProjectDatabaseHelper.COLUMN_KITCHEN_TYPE},
                    null, null, null, null, null);
            cursor.moveToFirst();

            int rowCount = cursor.getCount();

            while (!cursor.isAfterLast())
            {
                KitchenDeviceType type =
                        KitchenDeviceType.valueOf(cursor.getString(
                                cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_KITCHEN_TYPE)));
                String name = cursor.getString(
                        cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_KITCHEN_NAME));
                String model = cursor.getString(
                        cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_KITCHEN_MODEL));
                long id = cursor.getLong(
                        cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_KITCHEN_ID));

                KitchenDevice newDevice = new KitchenDevice(type, name, model, id);
                newDeviceList.add(newDevice);

                publishProgress(Math.round((cursor.getPosition() / rowCount) * 100));
                cursor.moveToNext();
            }
            cursor.close();

            return newDeviceList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            deviceProgressBar.setVisibility(View.VISIBLE);
            deviceProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<KitchenDevice> result) {
            deviceList = result;
            deviceListView.setAdapter(deviceAdapter);

            deviceProgressBar.setVisibility(View.GONE);
            deviceListView.setVisibility(View.VISIBLE);
            addDeviceFAB.setVisibility(View.VISIBLE);

            Snackbar.make(
                    findViewById(R.id.kitchen_root_view),
                    R.string.kitchen_device_load_msg,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.kitchen_toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        boolean action_performed = false;

        switch(mi.getItemId()) {
            case R.id.action_about:
                AlertDialog.Builder about_builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.kitchen_toolbar_menu_about)
                        .setMessage(R.string.kitchen_about_dialog_msg)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog about_dialog = about_builder.create();
                about_dialog.show();

                action_performed = true;
                break;
            case R.id.action_help:
                HelpDialogFragment help_dialog = HelpDialogFragment.newInstance();
                help_dialog.show(getFragmentManager(), "HelpDialog");

                action_performed = true;
                break;
            case R.id.action_oss:
                LicencesDialogFragment oss_dialog = LicencesDialogFragment.newInstance();
                oss_dialog.show(getFragmentManager(), "LicensesDialog");

                action_performed = true;
                break;
        }

        return action_performed;
    }
}
