package com.sayadev.finalproject.livingroom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.Blinding.Blinding;
import com.sayadev.finalproject.livingroom.Lamps.Lamp1;
import com.sayadev.finalproject.livingroom.Lamps.Lamp2;
import com.sayadev.finalproject.livingroom.Lamps.Lamp3;
import com.sayadev.finalproject.livingroom.TV.TV;

import java.util.ArrayList;
import java.util.Date;

public class LivingRoom extends AppCompatActivity {

    private ListView roomList;
    private ArrayList<RoomData> roomItems;
    private RoomAdapter roomAdapter;
    private ProjectDatabaseHelper dbHelper;
    private boolean isFrameLoaded;
    private FrameLayout livingRoomFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_living_room);

        getSupportActionBar().setTitle(R.string.living_room);

        dbHelper = new ProjectDatabaseHelper(this);

        roomItems = new ArrayList<>();

        roomList = (ListView) findViewById(R.id.roomItems);

        roomAdapter = new RoomAdapter(this);

        roomList.setAdapter(roomAdapter);

        roomAdapter.notifyDataSetChanged();

        livingRoomFrame = (FrameLayout) findViewById(R.id.livingRoomFrame);

        isFrameLoaded = (livingRoomFrame != null);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Object o = roomList.getItemAtPosition(position);
                RoomData itemData = (RoomData) o;
                Bundle data = new Bundle();

                Intent intent = null;

                data.putString("id", Long.toString(position));
                data.putString("dbId", Long.toString(itemData.get_id()));
                data.putString("itemTitle", roomItems.get(position).getTitle());
                data.putString("itemImage", roomItems.get(position).getImageUri());
                data.putString("deviceType", Integer.toString(itemData.getItemType()));

                dbHelper.addDeviceCount(itemData.get_id());

                if (!isFrameLoaded) {        // phone
                    data.putString("orientation", "port");
                    intent = new Intent(LivingRoom.this, RoomDetails.class);
                    intent.putExtras(data);
                    startActivityForResult(intent, 5);
                } else {                    // tablet
                    data.putString("orientation", "land");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    Fragment f = null;

                    if (itemData.getItemType() == RoomData.DEVICE_TV) {
                        f = new TV();
                    } else if (itemData.getItemType() == RoomData.DEVICE_LAMP1) {
                        f = new Lamp1();
                    } else if (itemData.getItemType() == RoomData.DEVICE_LAMP2) {
                        f = new Lamp2();
                    } else if (itemData.getItemType() == RoomData.DEVICE_LAMP3) {
                        f = new Lamp3();
                    } else if (itemData.getItemType() == RoomData.DEVICE_BLINDING) {
                        f = new Blinding();
                    }

                    f.setArguments(data);

                    ft.replace(R.id.livingRoomFrame, f);
                    ft.commit();
                }
            }
        });

    }

    public void listItems() {
        roomItems = new ArrayList<>();
        Cursor cursor = dbHelper.getRoomItems();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_ID));
            String title = cursor.getString(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_DEVICE_TITLE));
            String imageUri = cursor.getString(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_DEVICE_IMAGE));
            int itemType = cursor.getInt(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_DEVICE_TYPE));
            long visitCount = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_VISIT_COUNT));
            long createdDate = cursor.getLong(cursor.getColumnIndex(ProjectDatabaseHelper.COLUMN_ROOM_CREATED));

            RoomData data = new RoomData(_id, title, imageUri, itemType, "", visitCount, createdDate);

            roomItems.add(data);

            cursor.moveToNext();
        }

        cursor.close();
    }

    public Dialog createCustomDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.main_dialog, null);

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

    public Dialog createDeviceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_add_room_device, null);

        final TextView deviceDesc = (TextView) v.findViewById(R.id.dialog_room_device_desc);

        final Spinner spinner = (Spinner) v.findViewById(R.id.dialog_room_device_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rooms_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        deviceDesc.setText(getResources().getString(R.string.dialog_room_device_tv_desc));
                        break;
                    case 1:
                        deviceDesc.setText(getResources().getString(R.string.dialog_room_device_lamp1_desc));
                        break;
                    case 2:
                        deviceDesc.setText(getResources().getString(R.string.dialog_room_device_lamp2_desc));
                        break;
                    case 3:
                        deviceDesc.setText(getResources().getString(R.string.dialog_room_device_lamp3_desc));
                        break;
                    case 4:
                        deviceDesc.setText(getResources().getString(R.string.dialog_room_device_blinding_desc));
                        break;
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String title = "";
                        String imageUri = "";
                        int itemType = 0;
                        long visitCount = 0;
                        long createdDate = 0;
                        Date currentDate;

                        switch (spinner.getSelectedItemPosition()) {
                            case 0:
                                title = getResources().getText(R.string.dialog_room_device_tv).toString();
                                itemType = RoomData.DEVICE_TV;
                                imageUri = "@drawable/tv";
                                currentDate = new Date();
                                createdDate = currentDate.getTime();
                                visitCount = 0;
                                break;
                            case 1:
                                title = getResources().getText(R.string.dialog_room_device_lamp1).toString();
                                itemType = RoomData.DEVICE_LAMP1;
                                imageUri = "@drawable/lamp";
                                currentDate = new Date();
                                createdDate = currentDate.getTime();
                                visitCount = 0;
                                break;
                            case 2:
                                title = getResources().getText(R.string.dialog_room_device_lamp2).toString();
                                itemType = RoomData.DEVICE_LAMP2;
                                imageUri = "@drawable/lamp";
                                currentDate = new Date();
                                createdDate = currentDate.getTime();
                                visitCount = 0;
                                break;
                            case 3:
                                title = getResources().getText(R.string.dialog_room_device_lamp3).toString();
                                itemType = RoomData.DEVICE_LAMP3;
                                imageUri = "@drawable/lamp";
                                currentDate = new Date();
                                createdDate = currentDate.getTime();
                                visitCount = 0;
                                break;
                            case 4:
                                title = getResources().getText(R.string.dialog_room_device_blinding).toString();
                                itemType = RoomData.DEVICE_BLINDING;
                                imageUri = "@drawable/blind";
                                currentDate = new Date();
                                createdDate = currentDate.getTime();
                                visitCount = 0;
                                break;
                        }

                        long _id = dbHelper.insertRoomItem(title, imageUri, itemType, createdDate);
                        RoomData data = new RoomData(_id, title, imageUri, itemType, "", visitCount, createdDate);
                        roomItems.add(data);
                        roomAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.add(R.string.room_add_device).setOnMenuItemClickListener(this.AddDevice)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    // Capture menu item click
    MenuItem.OnMenuItemClickListener AddDevice = new MenuItem.OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem item) {
            createDeviceDialog().show();
            return false;
        }
    };

    public void deleteItem(int id) {
        dbHelper.deleteRoomItem(roomItems.get(id).get_id());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (roomItems.get(id).getItemType() == RoomData.DEVICE_TV) {
            TV tv = (TV) getSupportFragmentManager().findFragmentById(R.id.livingRoomFrame);
            ft.remove(tv);
            ft.commit();
        } else if (roomItems.get(id).getItemType() == RoomData.DEVICE_LAMP1) {
            Lamp1 lamp1 = (Lamp1) getSupportFragmentManager().findFragmentById(R.id.livingRoomFrame);
            ft.remove(lamp1);
            ft.commit();
        } else if (roomItems.get(id).getItemType() == RoomData.DEVICE_LAMP2) {
            Lamp2 lamp2 = (Lamp2) getSupportFragmentManager().findFragmentById(R.id.livingRoomFrame);
            ft.remove(lamp2);
            ft.commit();
        } else if (roomItems.get(id).getItemType() == RoomData.DEVICE_LAMP3) {
            Lamp3 lamp3 = (Lamp3) getSupportFragmentManager().findFragmentById(R.id.livingRoomFrame);
            ft.remove(lamp3);
            ft.commit();
        } else if (roomItems.get(id).getItemType() == RoomData.DEVICE_BLINDING) {
            Blinding blind = (Blinding) getSupportFragmentManager().findFragmentById(R.id.livingRoomFrame);
            ft.remove(blind);
            ft.commit();
        }

        roomItems.remove(id);
        roomAdapter.notifyDataSetChanged();

        Snackbar.make(this.findViewById(android.R.id.content), R.string.room_item_deleted, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_one:
                intent = new Intent(LivingRoom.this, LivingRoom.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_two:
                intent = new Intent(LivingRoom.this, LivingRoom.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_three:
                intent = new Intent(LivingRoom.this, LivingRoom.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.action_four:
                intent = new Intent(LivingRoom.this, LivingRoom.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 5) {
            Bundle extras = data.getExtras();
            int id = Integer.parseInt(extras.getString("id"));

            dbHelper.deleteRoomItem(roomItems.get(id).get_id());

            roomItems.remove(id);
            roomAdapter.notifyDataSetChanged();
            Snackbar.make(this.findViewById(android.R.id.content), R.string.room_item_deleted, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private class RoomAdapter extends ArrayAdapter<RoomData> {

        public RoomAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return roomItems.size();
        }

        public long getItemId(int position) {
            return roomItems.get(position).get_id();
        }

        public RoomData getItem(int position) {
            return roomItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LivingRoom.this.getLayoutInflater();
            View result = null;

            result = inflater.inflate(R.layout.room_items, null);

            TextView roomItemText = (TextView) result.findViewById((R.id.roomItemText));
            roomItemText.setText(getItem(position).getTitle());

            ImageView roomItemImage = (ImageView) result.findViewById((R.id.roomItemImage));
            roomItemImage.setImageDrawable(ContextCompat.getDrawable(result.getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));

            return result;
        }
    }

    private class GetRoomItems extends AsyncTask<Object, Object, Object> {

        int count = 0;
        private ProgressDialog proDialog;

        @Override
        protected void onProgressUpdate(Object... params) {
            super.onProgressUpdate(params);
        }

        @Override
        protected Object doInBackground(Object... params) {

            listItems();
            for(int i = 0; i < 30000000; i++) {
            }
            publishProgress(count);
            return null;
        }

        @Override
        protected void onPreExecute() {
            roomList.setVisibility(View.INVISIBLE);

            proDialog = new ProgressDialog(LivingRoom.this);
            proDialog.setTitle("Room List");
            proDialog.setMessage("Loding...");
            proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            proDialog.setCancelable(true);
            proDialog.show();
        }

        @Override
        protected void onPostExecute(Object result) {

            proDialog.dismiss();
            roomList.setVisibility(View.VISIBLE);
            roomAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetRoomItems().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
