package com.sayadev.finalproject.kitchen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.R;

import java.util.ArrayList;

public class KitchenMainActivity extends AppCompatActivity {

    final static String ACTIVITY_NAME = "KitchenMainActivity";
    final Context context = this;

    protected FloatingActionButton addDeviceFAB;
    protected ListView deviceListView;

    protected ArrayList<KitchenDevice> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);

        getSupportActionBar().setTitle("Kitchen");

        addDeviceFAB = (FloatingActionButton) findViewById(R.id.add_device_fab);
        deviceListView = (ListView) findViewById(R.id.device_list);

        deviceList = new ArrayList<>();

        final KitchenDeviceAdapter deviceAdapter = new KitchenDeviceAdapter(this);
        deviceListView.setAdapter(deviceAdapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KitchenDevice clickedDevice = (KitchenDevice) parent.getItemAtPosition(position);
                showDeviceInfoDialog(clickedDevice, position);
            }
        });

        addDeviceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog_kitchen_add_device, null);

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

                                String name = nameEditText.getText().toString();
                                String model = modelEditText.getText().toString();

                                if (name.matches("") || model.matches(""))
                                {
                                    Toast.makeText(context,
                                            R.string.kitchen_add_device_empty_field,
                                            Toast.LENGTH_SHORT).show();

                                    return;
                                }

                                KitchenDevice device = new KitchenDevice(
                                        KitchenDeviceType.MICROWAVE,
                                        name, model);

                                deviceList.add(device);
                                deviceAdapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog addDeviceDialog = builder.create();
                addDeviceDialog.show();
            }
        });
    }

    private void showDeviceInfoDialog(KitchenDevice device, final int position) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_kitchen_about_device, null);

        TextView deviceModel = (TextView) dialogView.findViewById(R.id.about_kitchen_device_model);
        TextView deviceName = (TextView) dialogView.findViewById(R.id.about_kitchen_device_name);

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
                .setNegativeButton(R.string.kitchen_show_device_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDevice(position);
                    }
                });

        AlertDialog showDeviceDialog = builder.create();
        showDeviceDialog.show();
    }

    protected void deleteDevice(int position) {
        deviceList.remove(position);
        ((KitchenDeviceAdapter) deviceListView.getAdapter()).notifyDataSetChanged();
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
}
