package com.sayadev.finalproject.livingroom;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.Blinding.Blinding;
import com.sayadev.finalproject.livingroom.Lamps.Lamps;
import com.sayadev.finalproject.livingroom.TV.TV;

import java.util.ArrayList;

public class LivingRoom extends BaseActivity {

    private ListView roomList;
    private ArrayList<RoomData> roomItems;
    private RoomAdapter roomAdapter;

    private SQLiteDatabase db;

    private boolean isFrameLoaded;
    private FrameLayout livingRoomFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_living_room);

        getSupportActionBar().setTitle("Living Room");

        roomItems = new ArrayList<>();

        roomList = (ListView) findViewById(R.id.roomItems);

        db = getDbHelper().getWritableDatabase();

        roomAdapter = new RoomAdapter(this);

        roomList.setAdapter(roomAdapter);

        roomItems.add(new RoomData(0, "TV", "@drawable/tv", 0, null, null, null));
        roomItems.add(new RoomData(1, "Lamps", "@drawable/lamp", 1, null, null, null));
        roomItems.add(new RoomData(2, "Blinding", "@drawable/blind", 4, null, null, null));
        roomItems.add(new RoomData(4, "TV", "@drawable/tv", 0, null, null, null));

        roomAdapter.notifyDataSetChanged();

        livingRoomFrame = (FrameLayout) findViewById(R.id.livingRoomFrame);

        isFrameLoaded = (livingRoomFrame != null);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Object o = roomList.getItemAtPosition(position);
                RoomData str = (RoomData) o;
                Bundle data = new Bundle();

                Intent intent = null;

                data.putString("id", Long.toString(id));
                data.putString("itemTitle", roomItems.get(position).getTitle());
                data.putString("itemImage", roomItems.get(position).getImageUri());

                if (!isFrameLoaded) {        // phone
                    if (str.getItemType() == RoomData.DEVICE_TV) {
                        data.putString("deviceType", Integer.toString(RoomData.DEVICE_TV));

                        data.putString("orientation", "port");
                        intent = new Intent(LivingRoom.this, TV.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    } else if (str.getItemType() == RoomData.DEVICE_LAMP1) {
                        data.putString("deviceType", Integer.toString(RoomData.DEVICE_LAMP1));

                        data.putString("orientation", "port");
                        intent = new Intent(LivingRoom.this, Lamps.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    } else if (str.getItemType() == RoomData.DEVICE_LAMP2) {
                        data.putString("deviceType", Integer.toString(RoomData.DEVICE_LAMP2));

                        data.putString("orientation", "port");
                        intent = new Intent(LivingRoom.this, Lamps.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    } else if (str.getItemType() == RoomData.DEVICE_LAMP3) {
                        data.putString("deviceType", Integer.toString(RoomData.DEVICE_LAMP3));

                        data.putString("orientation", "port");
                        intent = new Intent(LivingRoom.this, Lamps.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);

                    } else if (str.getItemType() == RoomData.DEVICE_BLINDING) {
                        data.putString("deviceType", Integer.toString(RoomData.DEVICE_BLINDING));

                        data.putString("orientation", "port");
                        intent = new Intent(LivingRoom.this, Blinding.class);
                        intent.putExtras(data);
                        startActivityForResult(intent, 5);
                    }

                } else {                    // tablet
                    data.putString("deviceType", Integer.toString(str.getItemType()));

                    data.putString("orientation", "land");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    RoomFragment f = new RoomFragment();
                    f.setArguments(data);

                    ft.replace(R.id.livingRoomFrame, f);
                    ft.commit();
                }

            }
        });

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
}
