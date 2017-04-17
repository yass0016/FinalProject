package com.sayadev.finalproject.livingroom.Blinding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.House.House;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.kitchen.KitchenMainActivity;
import com.sayadev.finalproject.livingroom.LivingRoom;

/**
 * Created by saleh on 3/26/2017.
 */

public class Blinding extends Fragment {
    private ImageView image;
    private TextView blindingText;
    private Bundle data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = this.getArguments();

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_blinding, container, false);

        image = (ImageView) v.findViewById(R.id.blindingImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        blindingText = (TextView) v.findViewById(R.id.blindingText);

        blindingText.setText("ID: " + data.getString("dbId"));

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
                return true;
            case R.id.action_help:
                createCustomDialog().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
