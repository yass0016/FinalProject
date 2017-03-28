package com.sayadev.finalproject.automobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

import java.util.ArrayList;

public class automobile extends AppCompatActivity {
    private ListView ls;
    private Button butt;
    private EditText nameText,descText;
    private ProjectDatabaseHelper dbh;
    private SQLiteDatabase db;
    private ArrayList<String> items,desc;
    private ItemAdapter adapter;
    private Cursor cursor;
    private String[] sa;
    private ContentValues cv;
    private AlertDialog.Builder builder;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);
        ls = (ListView) findViewById(R.id.autoList);
        butt = (Button) findViewById(R.id.autoButton);
        nameText = (EditText) findViewById(R.id.nameText);
        descText = (EditText) findViewById(R.id.descText);
        dbh = new ProjectDatabaseHelper(getApplicationContext());
        db = dbh.getWritableDatabase();
        items = new ArrayList<>();
        desc = new ArrayList<>();
        adapter = new ItemAdapter(this);
        ls.setAdapter(adapter);
        sa = new String[]{dbh.COLUMN_AUTO_ID,dbh.COLUMN_AUTO_NAME,dbh.COLUMN_AUTO_DESCRIPTION};
        cv = new ContentValues();
        builder = new AlertDialog.Builder(this);
       // setList();

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","this(" + nameText.getText().toString() + ")");
                if(nameText.getText().toString().equalsIgnoreCase("")){
                    toast = Toast.makeText(automobile.this,"Name was not entered",Toast.LENGTH_LONG);
                    toast.show();
                }else{
               /* cv.put(dbh.COLUMN_AUTO_NAME,nameText.getText().toString());
                cv.put(dbh.COLUMN_AUTO_DESCRIPTION,descText.getText().toString());
                db.insert(dbh.TABLE_AUTO_ITEMS,null,cv);
                setList();*/
                items.add(nameText.getText().toString());
                desc.add(descText.getText().toString());
                adapter.notifyDataSetChanged();
                nameText.setHint("Name of Item");
                descText.setHint("Description");
                nameText.setText("");
                descText.setText("");
                }
            }
        });

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.setTitle(items.get(position));
                builder.setMessage(desc.get(position));
                builder.setPositiveButton("OK",null);
                builder.show();
            }
        });
    }

    private void setList(){
        items.clear();

        cursor = db.query(dbh.TABLE_AUTO_ITEMS,sa,null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            items.add(cursor.getString(cursor.getColumnIndex(dbh.COLUMN_AUTO_NAME)));
            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
        dbh.close();
    }

    private class ItemAdapter extends ArrayAdapter<String>{

        public ItemAdapter(Context cnt){super(cnt,0);}

        public String getItem(int pos){return items.get(pos);}

        public int getCount(){
            return items.size();
        }

        public View getView(int pos, View convertView, ViewGroup parent){
            LayoutInflater inflater = automobile.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.autoitemrow,null);
            TextView name = (TextView) result.findViewById(R.id.nameRow);
            name.setText(getItem(pos));
            return result;
        }
    }
}


