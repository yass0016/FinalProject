package com.sayadev.finalproject.House;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

import java.util.ArrayList;

public class House extends BaseActivity {

    private ListView HouseList;
    private ArrayList<HouseData> HouseItems;
    private HouseAdapter HouseAdapter;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_house);

        getSupportActionBar().setTitle("House Items");

        HouseItems = new ArrayList<>();

        HouseList = (ListView) findViewById(R.id.houseItems);

        db = getDbHelper().getWritableDatabase();

        HouseAdapter = new HouseAdapter(this);

        HouseList.setAdapter(HouseAdapter);

        HouseItems.add(new HouseData(0, "TV", "@drawable/tv"));
        HouseItems.add(new HouseData(1, "Lamps", "@drawable/lamp"));
        HouseItems.add(new HouseData(2, "Blinding", "@drawable/blind"));

        HouseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Object o = HouseList.getItemAtPosition(position);
                HouseData str = (HouseData) o;
                Bundle data = new Bundle();

            }
        });

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

            result = inflater.inflate(R.layout.house_data, null);

            TextView HouseItemText = (TextView) result.findViewById((R.id.houseItemText));
            HouseItemText.setText(getItem(position).getTitle());

            ImageView HouseItemImage = (ImageView) result.findViewById((R.id.houseItemImage));
            HouseItemImage.setImageDrawable(ContextCompat.getDrawable(result.getContext(), getResources().getIdentifier(getItem(position).getImageUri(), null, getPackageName())));

            return result;
        }
    }
}
