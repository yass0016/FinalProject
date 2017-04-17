package com.sayadev.finalproject.kitchen;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sayadev.finalproject.R;

import java.math.BigInteger;
import java.security.SecureRandom;


public class CoffeeMakerConfigFragment extends Fragment {

    private static final String ACTIVITY_NAME = "CoffeeMakerConfigFrag";
    protected int tasksToComplete = 2;

    protected LayoutInflater lInflater;

    protected Spinner coffeeSpinner;
    protected Spinner sizeSpinner;
    protected Bundle args;
    protected View view;
    protected FloatingActionButton brewCoffeeFAB;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        args = getArguments();
        lInflater = inflater;
        view = inflater.inflate(R.layout.fragment_kitchen_coffee_maker_config, container, false);

        brewCoffeeFAB = (FloatingActionButton) view.findViewById(R.id.brew_coffee_fab);

        new SyncCoffeeSelection().execute();
        new SyncCoffeeSizeSelection().execute();

        return view;
    }

    private void finishedTask() {
        tasksToComplete--;
        if (tasksToComplete == 0) {
            Toast.makeText(getActivity(),
                    R.string.kitchen_device_config_finished_setup, Toast.LENGTH_SHORT).show();
            getActivity().findViewById(R.id.kitchen_device_config_frame).setVisibility(View.VISIBLE);

            brewCoffeeFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BrewCoffee().execute();
                }
            });
        }
    }

    private class SyncCoffeeSelection extends AsyncTask<Void, Void, ArrayAdapter<CharSequence>> {

        @Override
        protected ArrayAdapter<CharSequence> doInBackground(Void... params) {
            ArrayAdapter<CharSequence> adapter =
                    new ArrayAdapter<CharSequence>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item);

            // Connect to coffee maker and get available coffee types
            adapter.add("Cappuccino");
            adapter.add("Espresso");
            adapter.add("Latte");

            return adapter;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(ArrayAdapter<CharSequence> result) {
            coffeeSpinner =
                    (Spinner) view.findViewById(R.id.kitchen_coffee_maker_config_coffee_spinner);
            coffeeSpinner.setAdapter(result);

            finishedTask();
        }
    }

    private class SyncCoffeeSizeSelection extends AsyncTask<Void, Void, ArrayAdapter<CharSequence>> {

        @Override
        protected ArrayAdapter<CharSequence> doInBackground(Void... params) {
            ArrayAdapter<CharSequence> adapter =
                    new ArrayAdapter<CharSequence>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item);

            // Connect to coffee maker and get available coffee sizes
            adapter.add("Large");
            adapter.add("Medium");
            adapter.add("Small");

            return adapter;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(ArrayAdapter<CharSequence> result) {
            sizeSpinner =
                    (Spinner) view.findViewById(R.id.kitchen_coffee_maker_config_size_spinner);
            sizeSpinner.setAdapter(result);

            finishedTask();
        }
    }

    private class BrewCoffee extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bm = null;

            // Connect to coffee maker and make request

            // Receive UID to retrieve coffee
            SecureRandom random = new SecureRandom();
            String coffeeUID = new BigInteger(128, random).toString(32);

            // Generate QR Code from UID
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(coffeeUID, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bm.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK: Color.WHITE);
                    }
                }
            } catch (WriterException e) {
                Log.e(ACTIVITY_NAME, "Error writing out image");
            }

            return bm;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            final View dialogView = lInflater.inflate(R.layout.dialog_kitchen_brew_coffee, null);

            ((ImageView) dialogView.findViewById(R.id.kitchen_coffee_maker_config_qr))
                    .setImageBitmap(result);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView)
                    .setTitle(R.string.kitchen_device_config_coffee_dialog_title)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog onBrewResultDialog = builder.create();
            onBrewResultDialog.show();
        }
    }
}
