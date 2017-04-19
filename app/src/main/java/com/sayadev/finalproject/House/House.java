package com.sayadev.finalproject.House;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.automobile.automobile;
import com.sayadev.finalproject.kitchen.KitchenMainActivity;
import com.sayadev.finalproject.livingroom.Blinding.Blinding;
import com.sayadev.finalproject.livingroom.Lamps.Lamp1;
import com.sayadev.finalproject.livingroom.Lamps.Lamp2;
import com.sayadev.finalproject.livingroom.Lamps.Lamp3;
import com.sayadev.finalproject.livingroom.LivingRoom;
import com.sayadev.finalproject.livingroom.RoomData;
import com.sayadev.finalproject.livingroom.TV.TV;

import java.util.ArrayList;

public class House extends AppCompatActivity {

    private ListView HouseList;
    private ArrayList<HouseData> HouseItems;
    private HouseAdapter HouseAdapter;

    private ProjectDatabaseHelper db;

    private boolean isFrameLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_house);

        try{
            getSupportActionBar().setTitle(R.string.house_title);
        } catch (NullPointerException e) {

        }

        HouseItems = new ArrayList<>();

        HouseList = (ListView) findViewById(R.id.houseItems);

        db = new ProjectDatabaseHelper(this);

        HouseAdapter = new HouseAdapter(this);

        HouseList.setAdapter(HouseAdapter);

        getItems();

        Button addHouseItem = (Button) findViewById(R.id.addHouseItem);
        addHouseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemDialog();
            }
        });

        FrameLayout houseFrame;
        houseFrame = (FrameLayout) findViewById(R.id.hosueFrame);

        isFrameLoaded = (houseFrame != null);

        HouseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Object o = HouseList.getItemAtPosition(position);
                HouseData houseInfo = (HouseData) o;
                Bundle data = new Bundle();

                data.putString("id", Long.toString(id));
                data.putString("itemImage", houseInfo.getImageUri());

                Intent intent;

                if (!isFrameLoaded) {
                    if (houseInfo.getItemType() == HouseData.GARAGE) {
                        intent = new Intent(House.this, GarageFragment.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    } else if (houseInfo.getItemType() == HouseData.TEMPIN) {
                        intent = new Intent(House.this, TempInFragment.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    } else if (houseInfo.getItemType() == HouseData.TEMPOUT) {
                        intent = new Intent(House.this, TempOutFragment.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    }
                } else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    Fragment f = null;

                    if (houseInfo.getItemType() == HouseData.GARAGE) {
                        f = new Garage();
                    } else if (houseInfo.getItemType() == HouseData.TEMPIN) {
                        f = new TempIn();
                    } else if (houseInfo.getItemType() == HouseData.TEMPOUT) {
                        f = new TempOut();
                    }

                    try{
                        f.setArguments(data);
                    } catch (NullPointerException e) {

                    }

                    ft.replace(R.id.hosueFrame, f);
                    ft.commit();
                }
            }
        });

        HouseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                final long deviceId = id;
                final int itemPosition = position;

                AlertDialog.Builder dialog = new AlertDialog.Builder(House.this);

                dialog.setTitle(R.string.delete_item)
                        .setMessage(R.string.delete_item_confirm)
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                Fragment f = null;

                                if(isFrameLoaded) {
                                    f = getSupportFragmentManager().findFragmentById(R.id.hosueFrame);
                                }

                                db.deleteHouseItem(deviceId);
                                HouseItems.remove(itemPosition);
                                HouseAdapter.notifyDataSetChanged();

                                if(f != null) {
                                    ft.remove(f);
                                    ft.commit();
                                }
                            }
                        }).show();

                return false;
            }
        });
    }

    public Dialog AddItemDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.add_house_device_dialog, null);
        builder.setView(v);

        final AlertDialog show = builder.show();

        Button garage = (Button) v.findViewById(R.id.addGARAGE);
        garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = db.insertHouseItem(getResources().getText(R.string.garage).toString(), HouseData.GARAGE, "@drawable/garage");
                HouseData garageData = new HouseData(id, getResources().getText(R.string.garage).toString(), "@drawable/garage", HouseData.GARAGE);
                HouseItems.add(garageData);
                HouseAdapter.notifyDataSetChanged();
                show.dismiss();
            }
        });

        Button tempIn = (Button) v.findViewById(R.id.addTempIn);
        tempIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = db.insertHouseItem(getResources().getText(R.string.temperature_inside).toString(), HouseData.TEMPIN, "@drawable/tempin");
                HouseData tempInData = new HouseData(id, getResources().getText(R.string.temperature_inside).toString(), "@drawable/tempin", HouseData.TEMPIN);
                HouseItems.add(tempInData);
                HouseAdapter.notifyDataSetChanged();
                show.dismiss();
            }
        });

        Button tempOut = (Button) v.findViewById(R.id.addTempOut);
        tempOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = db.insertHouseItem(getResources().getText(R.string.temperature_outside).toString(), HouseData.TEMPOUT, "@drawable/tempout");
                HouseData tempOutData = new HouseData(id, getResources().getText(R.string.temperature_outside).toString(), "@drawable/tempout", HouseData.TEMPOUT);
                HouseItems.add(tempOutData);
                HouseAdapter.notifyDataSetChanged();
                show.dismiss();
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v);

        return builder.create();
    }


    private void getItems() {
        HouseItems = new ArrayList<>();

        Cursor cursor = db.getHouseItems();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_ID));
            String title = cursor.getString(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_DEVICE_TITLE));
            String uri = cursor.getString(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_DEVICE_IMAGE));
            int itemType = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_HOUSE_DEVICE_TYPE));

            HouseData data = new HouseData(_id, title, uri, itemType);

            HouseItems.add(data);

            cursor.moveToNext();
        }

        cursor.close();
    }

    private class HouseAdapter extends ArrayAdapter<HouseData> {

        public HouseAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return HouseItems.size();
        }

        public long getItemId(int position) {
            return HouseItems.get(position).get_id();
        }

        public HouseData getItem(int position) {
            return HouseItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = House.this.getLayoutInflater();
            View result = null;

            result = inflater.inflate(R.layout.house_list_row, null);

            TextView HouseItemText = (TextView) result.findViewById((R.id.houseItemText));
            HouseItemText.setText(getItem(position).getTitle());

            ImageView HouseItemImage = (ImageView) result.findViewById((R.id.houseItemImage));
            HouseItemImage.setImageDrawable(ContextCompat.getDrawable(result.getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));

            return result;
        }
    }

    public Dialog createCustomDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_house_about, null);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_one:
                intent = new Intent(House.this, LivingRoom.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_two:
                intent = new Intent(House.this, KitchenMainActivity.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_three:
                intent = new Intent(House.this, House.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_four:
                intent = new Intent(House.this, automobile.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_help:
                createCustomDialog().show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
