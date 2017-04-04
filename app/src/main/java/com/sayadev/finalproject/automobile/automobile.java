package com.sayadev.finalproject.automobile;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Spinner;
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
    private ArrayList<String> items,desc,itemdummy,descdummy;
    private ItemAdapter adapter;
    private Cursor cursor;
    private String[] sa;
    private ContentValues cv;
    private AlertDialog.Builder builder;
    private Toast toast;
    private Getdb asyncdb;
    private Spinner comboBox;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);
        comboBox = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterspin = ArrayAdapter.createFromResource(this,
                R.array.spinopt, R.layout.support_simple_spinner_dropdown_item);
        adapterspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboBox.setAdapter(adapterspin);
        ls = (ListView) findViewById(R.id.autoList);
        butt = (Button) findViewById(R.id.autoButton);
        nameText = (EditText) findViewById(R.id.nameText);
        descText = (EditText) findViewById(R.id.descText);
        bundle = new Bundle();
        dbh = new ProjectDatabaseHelper(this);
        db = dbh.getWritableDatabase();
        items = new ArrayList<>();
        itemdummy = new ArrayList<>();
        descdummy = new ArrayList<>();
        items.add(getString(R.string.temp));
        items.add(getString(R.string.radio));
        items.add(getString(R.string.cb));
        items.add(getString(R.string.gps));
        items.add(getString(R.string.chair));
        desc = new ArrayList<>();
        desc.add(getString(R.string.tempdesc));
        desc.add(getString(R.string.radiodesc));
        desc.add(getString(R.string.cbdesc));
        desc.add(getString(R.string.gpsdesc));
        desc.add(getString(R.string.chairdesc));
        adapter = new ItemAdapter(this);
        ls.setAdapter(adapter);
        sa = new String[]{dbh.COLUMN_AUTO_ID,dbh.COLUMN_AUTO_NAME,dbh.COLUMN_AUTO_DESCRIPTION};
        cv = new ContentValues();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("firstTime",true)){
            cv.put(dbh.COLUMN_AUTO_NAME,getString(R.string.temp));
            cv.put(dbh.COLUMN_AUTO_DESCRIPTION,getString(R.string.tempdesc));
            cv.put(dbh.COLUMN_AUTO_TYPE,"Temp Control");
            db.insert(dbh.TABLE_AUTO_ITEMS,null,cv);

            prefs.edit().putBoolean("firstTime", false).commit();
        }
        builder = new AlertDialog.Builder(this);
        asyncdb = new Getdb();
        setList();

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","this(" + nameText.getText().toString() + ")");
                if(nameText.getText().toString().equalsIgnoreCase("")){
                    toast = Toast.makeText(automobile.this,"Name was not entered",Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    cv.clear();
                cv.put(dbh.COLUMN_AUTO_NAME,nameText.getText().toString());
                cv.put(dbh.COLUMN_AUTO_DESCRIPTION,descText.getText().toString());
                db.insert(dbh.TABLE_AUTO_ITEMS,null,cv);
                items.add(nameText.getText().toString());
                desc.add(descText.getText().toString());
                adapter.notifyDataSetChanged();
                nameText.setHint("Name of Item");
                descText.setHint("Description");
                nameText.setText("");
                descText.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setTitle(items.get(position));
                builder.setMessage(desc.get(position));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bundle.clear();
                        bundle.putString("Option",items.get(position));
                        Intent i = new Intent(automobile.this,FragPhone.class);
                        startActivity(i,bundle);
                    }
                });
                builder.show();
            }
        });
    }

    private void setList(){
        items.clear();
        cursor = db.query(dbh.TABLE_AUTO_ITEMS,sa,null,null,null,null,null,null);
        cursor.moveToFirst();
        asyncdb.execute(cursor);

       /*while(!cursor.isAfterLast()){
            items.add(cursor.getString(cursor.getColumnIndex(dbh.COLUMN_AUTO_NAME)));
            cursor.moveToNext();
        }*/
        items = itemdummy;
        desc = descdummy;
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

    private class Getdb extends AsyncTask<Cursor,Integer, ArrayList>{

        @Override
        protected ArrayList doInBackground(Cursor... params) {
            while(!params[0].isAfterLast()){
                itemdummy.add(params[0].getString(params[0].getColumnIndex(dbh.COLUMN_AUTO_NAME)));
                descdummy.add(params[0].getString(params[0].getColumnIndex(dbh.COLUMN_AUTO_DESCRIPTION)));
                params[0].moveToNext();
            }
            return itemdummy;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
        }
    }
}


