package com.sayadev.finalproject.livingroom;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.sayadev.finalproject.livingroom.Lamps.Lamp1;
import com.sayadev.finalproject.livingroom.Lamps.Lamp2;
import com.sayadev.finalproject.livingroom.Lamps.Lamp3;
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
        roomItems.add(new RoomData(1, "Lamp1", "@drawable/lamp", 1, null, null, null));
        roomItems.add(new RoomData(2, "Blinding", "@drawable/blind", 4, null, null, null));
        roomItems.add(new RoomData(4, "TV", "@drawable/tv", 0, null, null, null));

        roomAdapter.notifyDataSetChanged();

        livingRoomFrame = (FrameLayout) findViewById(R.id.livingRoomFrame);

        isFrameLoaded = (livingRoomFrame != null);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Object o = roomList.getItemAtPosition(position);
                RoomData itemData = (RoomData) o;
                Bundle data = new Bundle();

                Intent intent = null;

                data.putString("id", Long.toString(id));
                data.putString("itemTitle", roomItems.get(position).getTitle());
                data.putString("itemImage", roomItems.get(position).getImageUri());
                data.putString("deviceType", Integer.toString(itemData.getItemType()));

                if (!isFrameLoaded) {        // phone
                    data.putString("orientation", "port");
                    intent = new Intent(LivingRoom.this, RoomDetails.class);
                    intent.putExtras(data);
                    startActivityForResult(intent, 5);
                } else {                    // tablet
                    data.putString("orientation", "land");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    Fragment f = null;

                    if(itemData.getItemType() == RoomData.DEVICE_TV) {
                        f = new TV();
                    } else if(itemData.getItemType() == RoomData.DEVICE_LAMP1) {
                        f = new Lamp1();
                    } else if(itemData.getItemType() == RoomData.DEVICE_LAMP2) {
                        f = new Lamp2();
                    } else if(itemData.getItemType() == RoomData.DEVICE_LAMP3) {
                        f = new Lamp3();
                    } else if(itemData.getItemType() == RoomData.DEVICE_BLINDING) {
                        f = new Blinding();
                    }

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
