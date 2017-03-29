package com.sayadev.finalproject.kitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sayadev.finalproject.R;

import java.util.ArrayList;

public class KitchenMainActivity extends AppCompatActivity {

    protected FloatingActionButton addDeviceFAB;
    protected ListView deviceListView;

    protected ArrayList<KitchenDevice> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);

        addDeviceFAB = (FloatingActionButton) findViewById(R.id.add_device_fab);
        deviceListView = (ListView) findViewById(R.id.device_list);

        deviceList = new ArrayList<>();

        final KitchenDeviceAdapter deviceAdapter = new KitchenDeviceAdapter(this);
        deviceListView.setAdapter(deviceAdapter);

        addDeviceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing the addition of devices with fake data
                deviceList.add(new KitchenDevice(KitchenDeviceType.MICROWAVE, "My Microwave", "SM17-80"));
                deviceAdapter.notifyDataSetChanged();
            }
        });
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
