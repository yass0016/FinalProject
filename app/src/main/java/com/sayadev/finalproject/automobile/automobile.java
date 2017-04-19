package com.sayadev.finalproject.automobile;

import android.app.Dialog;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

import java.util.ArrayList;

public class automobile extends AppCompatActivity {
    private Button butt;
    private EditText nameText,descText;
    private ProjectDatabaseHelper dbh;
    private SQLiteDatabase db;
    private ArrayList<String> items,desc,type,typedummy,itemdummy,descdummy;
    private ArrayList<Integer> idal,iddummy;
    private ItemAdapter adapter;
    private String[] sa;
    private ContentValues cv;
    private AlertDialog.Builder builder;
    private Toast toast;
    private Getdb asyncdb;
    private Spinner comboBox;
    private ProgressBar pb;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);
        FloatingActionButton addbutt = (FloatingActionButton) findViewById(R.id.floatadd);


        ListView ls = (ListView) findViewById(R.id.autoList);

        count = 0;
        dbh = new ProjectDatabaseHelper(this);
        db = dbh.getWritableDatabase();
        items = new ArrayList<>();
        type = new ArrayList<>();
        idal = new ArrayList<>();
        iddummy = new ArrayList<>();
        typedummy = new ArrayList<>();
        itemdummy = new ArrayList<>();
        descdummy = new ArrayList<>();
       /* items.add(getString(R.string.temp));
        items.add(getString(R.string.radio));
        items.add(getString(R.string.cb));
        items.add(getString(R.string.gps));
        items.add(getString(R.string.chair));*/
        desc = new ArrayList<>();
       /* desc.add(getString(R.string.tempdesc));
        desc.add(getString(R.string.radiodesc));
        desc.add(getString(R.string.cbdesc));
        desc.add(getString(R.string.gpsdesc));
        desc.add(getString(R.string.chairdesc));*/
        adapter = new ItemAdapter(this);
        ls.setAdapter(adapter);
        sa = new String[]{dbh.COLUMN_AUTO_ID,dbh.COLUMN_AUTO_NAME,dbh.COLUMN_AUTO_DESCRIPTION,dbh.COLUMN_AUTO_TYPE};
        cv = new ContentValues();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("firstTime",true)){
            addData();
            prefs.edit().putBoolean("firstTime", false).apply();
        }
        builder = new AlertDialog.Builder(this);
        asyncdb = new Getdb();
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setMax(1000);
        setList();
        pb.setVisibility(View.INVISIBLE);

        /*butt.setOnClickListener(new View.OnClickListener() {
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
        });*/

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setTitle(items.get(position));
                builder.setMessage("Type: " + type.get(position) + "\nDescription: " + desc.get(position));
                builder.setPositiveButton("Options", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* bundle.clear();
                        bundle.putString("Option",items.get(position));*/
                        Intent i = new Intent(automobile.this,FragPhone.class);
                        i.putExtra("position",position);
                        i.putExtra("id",idal.get(position));
                        i.putExtra("Type",type.get(position));
                        i.putExtra("Name",items.get(position));
                        i.putExtra("Desc",desc.get(position));
                        startActivityForResult(i,1);
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
            }
        });

        addbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(automobile.this);
                dialog.setContentView(R.layout.auto_add_dialog);
                dialog.setTitle("Add Item");

                ArrayAdapter<CharSequence> adapterspin = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.spinopt, R.layout.support_simple_spinner_dropdown_item);
                adapterspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                comboBox = (Spinner) dialog.findViewById(R.id.spinner);
                comboBox.setAdapter(adapterspin);
                nameText = (EditText) dialog.findViewById(R.id.nameText);
                descText = (EditText) dialog.findViewById(R.id.descText);
                butt = (Button) dialog.findViewById(R.id.autoButton);

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
                            cv.put(dbh.COLUMN_AUTO_TYPE,comboBox.getSelectedItem().toString());
                            db.insert(dbh.TABLE_AUTO_ITEMS,null,cv);
                            idal.add(db.query(dbh.TABLE_AUTO_ITEMS,sa,dbh.COLUMN_AUTO_NAME+"= '"+nameText.getText().toString()+ "'",null,null,null,null).getColumnIndex(dbh.COLUMN_AUTO_ID));
                            items.add(nameText.getText().toString());
                            desc.add(descText.getText().toString());
                            type.add(comboBox.getSelectedItem().toString());
                            adapter.notifyDataSetChanged();
                            nameText.setHint("Name of Item");
                            descText.setHint("Description");
                            nameText.setText("");
                            descText.setText("");
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == 1) && (resultCode == 4)){
            Log.i("ID for delete",Integer.toString(data.getExtras().getInt("id")));
            Log.i("Database","Got here");
            Log.i("position",Integer.toString(data.getExtras().getInt("position")));
            db.execSQL("delete from " + dbh.TABLE_AUTO_ITEMS + " where " + dbh.COLUMN_AUTO_ID  + " = " + data.getExtras().getInt("id"));
            items.remove(data.getExtras().getInt("position"));
            desc.remove(data.getExtras().getInt("position"));
            type.remove(data.getExtras().getInt("position"));
            idal.remove(data.getExtras().getInt("position"));
            adapter.notifyDataSetChanged();
        }

    }

    private void setList(){
        items.clear();
        Cursor cursor = db.query(dbh.TABLE_AUTO_ITEMS, sa, null, null, null, null, null, null);
        cursor.moveToFirst();
       while(!cursor.isAfterLast()){
            count ++;
            cursor.moveToNext();
        }

        cursor.moveToFirst();
        asyncdb.execute(cursor);
        items = itemdummy;
        desc = descdummy;
        type = typedummy;
        idal = iddummy;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
        dbh.close();
    }

    private class ItemAdapter extends ArrayAdapter<String>{

        ItemAdapter(Context cnt){super(cnt,0);}

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
                iddummy.add(params[0].getInt(params[0].getColumnIndex(dbh.COLUMN_AUTO_ID)));
                itemdummy.add(params[0].getString(params[0].getColumnIndex(dbh.COLUMN_AUTO_NAME)));
                descdummy.add(params[0].getString(params[0].getColumnIndex(dbh.COLUMN_AUTO_DESCRIPTION)));
                typedummy.add(params[0].getString(params[0].getColumnIndex(dbh.COLUMN_AUTO_TYPE)));
                params[0].moveToNext();
                publishProgress(1000/count);

            }
            publishProgress(1000);
            return itemdummy;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == 1000) {
                pb.setProgress(values[0]);
            }else{
                pb.setProgress(pb.getProgress()+values[0]);
            }
            Log.i("progress",Integer.toString(values[0]));
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
        }
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.auto_toolbar,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        Log.i("Toolbar","Option selected");
        builder.setTitle("Automobile");
        builder.setMessage("Author:Sheldon McGrath\nVersion:1.0\n\nSelect an option from the menu to get more information about it. \nContinuing from there will give you controls for the option. \nMore options can be added with the button at the bottom, right of the screen");
        builder.setPositiveButton("OK",null);
        builder.show();
        return true;
    }

    private void addData(){
        cv.put(dbh.COLUMN_AUTO_NAME, getString(R.string.temp));
        cv.put(dbh.COLUMN_AUTO_DESCRIPTION, getString(R.string.tempdesc));
        cv.put(dbh.COLUMN_AUTO_TYPE, "Temp Control");
        db.insert(dbh.TABLE_AUTO_ITEMS, null, cv);

        cv.clear();
        cv.put(dbh.COLUMN_AUTO_NAME, getString(R.string.radio));
        cv.put(dbh.COLUMN_AUTO_DESCRIPTION, getString(R.string.radiodesc));
        cv.put(dbh.COLUMN_AUTO_TYPE, "Radio");
        db.insert(dbh.TABLE_AUTO_ITEMS, null, cv);

        cv.clear();
        cv.put(dbh.COLUMN_AUTO_NAME, getString(R.string.cb));
        cv.put(dbh.COLUMN_AUTO_DESCRIPTION, getString(R.string.cbdesc));
        cv.put(dbh.COLUMN_AUTO_TYPE, "CB Radio");
        db.insert(dbh.TABLE_AUTO_ITEMS, null, cv);

        cv.clear();
        cv.put(dbh.COLUMN_AUTO_NAME, getString(R.string.gps));
        cv.put(dbh.COLUMN_AUTO_DESCRIPTION, getString(R.string.gpsdesc));
        cv.put(dbh.COLUMN_AUTO_TYPE, "GPS");
        db.insert(dbh.TABLE_AUTO_ITEMS, null, cv);

        cv.clear();
        cv.put(dbh.COLUMN_AUTO_NAME, getString(R.string.chair));
        cv.put(dbh.COLUMN_AUTO_DESCRIPTION, getString(R.string.chairdesc));
        cv.put(dbh.COLUMN_AUTO_TYPE, "Seat");
        db.insert(dbh.TABLE_AUTO_ITEMS, null, cv);

        cv.clear();
        cv.put(dbh.COLUMN_TEMP_TEMP, 20);
        cv.put(dbh.COLUMN_TEMP_FAN,5);
        cv.put(dbh.COLUMN_TEMP_AC,1);
        db.insert(dbh.TABLE_AUTO_TEMP,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_TEMP_TEMP, 8);
        cv.put(dbh.COLUMN_TEMP_FAN,5);
        cv.put(dbh.COLUMN_TEMP_AC,1);
        db.insert(dbh.TABLE_AUTO_TEMP,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_TEMP_TEMP, 30);
        cv.put(dbh.COLUMN_TEMP_FAN,10);
        cv.put(dbh.COLUMN_TEMP_AC,0);
        db.insert(dbh.TABLE_AUTO_TEMP,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_TEMP_TEMP, 15);
        cv.put(dbh.COLUMN_TEMP_FAN,5);
        cv.put(dbh.COLUMN_TEMP_AC,0);
        db.insert(dbh.TABLE_AUTO_TEMP,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_RADIO_VOLUME,0);
        cv.put(dbh.COLUMN_RADIO_CHANNEL,0);
        db.insert(dbh.TABLE_AUTO_RADIO,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_RADIO_VOLUME,25);
        cv.put(dbh.COLUMN_RADIO_CHANNEL,25);
        db.insert(dbh.TABLE_AUTO_RADIO,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_RADIO_VOLUME,80);
        cv.put(dbh.COLUMN_RADIO_CHANNEL,50);
        db.insert(dbh.TABLE_AUTO_RADIO,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_RADIO_VOLUME,80);
        cv.put(dbh.COLUMN_RADIO_CHANNEL,100);
        db.insert(dbh.TABLE_AUTO_RADIO,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_CB_VOLUME,80);
        cv.put(dbh.COLUMN_CB_CHANNEL,16);
        cv.put(dbh.COLUMN_CB_GAIN,5);
        db.insert(dbh.TABLE_AUTO_CB,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_CB_VOLUME,80);
        cv.put(dbh.COLUMN_CB_CHANNEL,9);
        cv.put(dbh.COLUMN_CB_GAIN,10);
        db.insert(dbh.TABLE_AUTO_CB,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_CB_VOLUME,80);
        cv.put(dbh.COLUMN_CB_CHANNEL,19);
        cv.put(dbh.COLUMN_CB_GAIN,0);
        db.insert(dbh.TABLE_AUTO_CB,null,cv);

        cv.clear();
        cv.put(dbh.COLUMN_CB_VOLUME,0);
        cv.put(dbh.COLUMN_CB_CHANNEL,1);
        cv.put(dbh.COLUMN_CB_GAIN,0);
        db.insert(dbh.TABLE_AUTO_CB,null,cv);
    }

}


