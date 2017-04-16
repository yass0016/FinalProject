package com.sayadev.finalproject.automobile;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.Model.ProjectDatabaseHelper;
import com.sayadev.finalproject.R;

import java.util.ArrayList;
import java.util.Random;

import static com.sayadev.finalproject.R.drawable.up;

public class fragmentLayout extends Fragment {
    private Toast toast;
    private ProjectDatabaseHelper dbh;
    private SQLiteDatabase db;
    private String[] sa;
    private ContentValues cv;
    private ArrayList<Integer> tempar,fanar,acar,radvolume,radchannel,cbvolume,cbchannel,cbgain;
    private Cursor cursor;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        toast = new Toast(getActivity());
        View v = null ;
        Button delete;


        tempar = new ArrayList();
        fanar = new ArrayList();
        acar = new ArrayList();
        cv = new ContentValues();

        Log.i("bundle items", "" + getArguments().getString("Type"));
        switch (getArguments().getString("Type")){
            case "Temp Control":
                v = inflater.inflate(R.layout.auto_temp_frag,container,false);
                dbh = new ProjectDatabaseHelper(v.getContext());
                db = dbh.getWritableDatabase();

                /*sa = new String[]{dbh.COLUMN_TEMP_ID,dbh.COLUMN_TEMP_TEMP,dbh.COLUMN_TEMP_FAN,dbh.COLUMN_TEMP_AC};
                cursor = db.query(dbh.TABLE_AUTO_TEMP,sa,null,null,null,null,null,null);
                cursor.moveToFirst();*/
                final SeekBar temp,fan;
                Button set1,set2,set3,set4,save1,save2,save3,save4;

                final Switch acswitch = (Switch) v.findViewById(R.id.acswitch);
                delete = (Button) v.findViewById(R.id.tempdelete);
                set1 = (Button) v.findViewById(R.id.buttemp1);
                set2 = (Button) v.findViewById(R.id.buttemp2);
                set3 = (Button) v.findViewById(R.id.buttemp3);
                set4 = (Button) v.findViewById(R.id.buttemp4);
                save1 = (Button) v.findViewById(R.id.tempsavebutt1);
                save2 = (Button) v.findViewById(R.id.tempsavebutt2);
                save3 = (Button) v.findViewById(R.id.tempsavebutt3);
                save4 = (Button) v.findViewById(R.id.tempsavebutt4);
                temp = (SeekBar) v.findViewById(R.id.tempslider);
                fan = (SeekBar) v.findViewById(R.id.fanslider);

               /* while(!cursor.isAfterLast()){
                    tempar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_TEMP)));
                    fanar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_FAN)));
                    acar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_AC)));
                    cursor.moveToNext();
                }*/
               readDB("temp");


                temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        Log.i("Slider",Integer.toString(progress));
                        if(acswitch.isChecked()){
                            toast = Toast.makeText(getActivity(),"-" + progress + "C",Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            toast = Toast.makeText(getActivity(),progress + "C",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) {}@Override public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                fan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Speed:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) {}@Override public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                set1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(acar.get(0) == 0){
                            acswitch.setChecked(false);
                        }else{
                            acswitch.setChecked(true);
                        }
                        Log.i("tempDB",""+String.valueOf(tempar.get(0)));
                        temp.setProgress(tempar.get(0));
                        fan.setProgress(fanar.get(0));
                       /* acswitch.setChecked(true);
                        temp.setProgress(20);
                        fan.setProgress(5);*/
                        Snackbar.make(getView(),"Preset 1 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(acar.get(1) == 0){
                            acswitch.setChecked(false);
                        }else{
                            acswitch.setChecked(true);
                        }

                        temp.setProgress(tempar.get(1));
                        fan.setProgress(fanar.get(1));
                        /*acswitch.setChecked(true);
                        temp.setProgress(8);
                        fan.setProgress(5);*/
                        Snackbar.make(getView(),"Preset 2 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(acar.get(2) == 0){
                            acswitch.setChecked(false);
                        }else{
                            acswitch.setChecked(true);
                        }

                        temp.setProgress(tempar.get(2));
                        fan.setProgress(fanar.get(2));
                        /* acswitch.setChecked(false);
                        temp.setProgress(30);
                        fan.setProgress(10);*/
                        Snackbar.make(getView(),"Preset 3 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(acar.get(3) == 0){
                            acswitch.setChecked(false);
                        }else{
                            acswitch.setChecked(true);
                        }

                        temp.setProgress(tempar.get(3));
                        fan.setProgress(fanar.get(3));
                       /* acswitch.setChecked(false);
                        temp.setProgress(15);
                        fan.setProgress(5);*/
                        Snackbar.make(getView(),"Preset 4 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                save1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_TEMP_TEMP, temp.getProgress());
                        cv.put(dbh.COLUMN_TEMP_FAN,fan.getProgress());
                        if(acswitch.isChecked()){
                            cv.put(dbh.COLUMN_TEMP_AC,1);
                        }else{
                            cv.put(dbh.COLUMN_TEMP_AC,0);
                        }
                        db.update(dbh.TABLE_AUTO_TEMP,cv,dbh.COLUMN_TEMP_ID + " = " + 1,null);
                        readDB("temp");
                    }
                });

                save2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_TEMP_TEMP, temp.getProgress());
                        cv.put(dbh.COLUMN_TEMP_FAN,fan.getProgress());
                        if(acswitch.isChecked()){
                            cv.put(dbh.COLUMN_TEMP_AC,1);
                        }else{
                            cv.put(dbh.COLUMN_TEMP_AC,0);
                        }
                        db.update(dbh.TABLE_AUTO_TEMP,cv,dbh.COLUMN_TEMP_ID + " = " + 2,null);
                        readDB("temp");
                    }
                });

                save3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_TEMP_TEMP, temp.getProgress());
                        cv.put(dbh.COLUMN_TEMP_FAN,fan.getProgress());
                        if(acswitch.isChecked()){
                            cv.put(dbh.COLUMN_TEMP_AC,1);
                        }else{
                            cv.put(dbh.COLUMN_TEMP_AC,0);
                        }
                        db.update(dbh.TABLE_AUTO_TEMP,cv,dbh.COLUMN_TEMP_ID + " = " + 3,null);
                        readDB("temp");
                    }
                });

                save4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_TEMP_TEMP, temp.getProgress());
                        cv.put(dbh.COLUMN_TEMP_FAN,fan.getProgress());
                        if(acswitch.isChecked()){
                            cv.put(dbh.COLUMN_TEMP_AC,1);
                        }else{
                            cv.put(dbh.COLUMN_TEMP_AC,0);
                        }
                        db.update(dbh.TABLE_AUTO_TEMP,cv,dbh.COLUMN_TEMP_ID + " = " + 4,null);
                        readDB("temp");
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;

            case "Radio":
                    v = inflater.inflate(R.layout.auto_radio,container,false);
                    dbh = new ProjectDatabaseHelper(v.getContext());
                    db = dbh.getWritableDatabase();
                    Button rad1,rad2,rad3,rad4,radsave1,radsave2,radsave3,radsave4;
                    final SeekBar radvolseek,radchanseek;



                radvolume = new ArrayList<>();
                radchannel = new ArrayList<>();
                readDB("radio");
                    delete = (Button) v.findViewById(R.id.raddelete);
                    rad1 = (Button) v.findViewById(R.id.radset1);
                    rad2 = (Button) v.findViewById(R.id.radset2);
                    rad3 = (Button) v.findViewById(R.id.radset3);
                    rad4 = (Button) v.findViewById(R.id.radset4);
                save1 = (Button) v.findViewById(R.id.radsav1);
                save2 = (Button) v.findViewById(R.id.radsav2);
                save3 = (Button) v.findViewById(R.id.radsav3);
                save4 = (Button) v.findViewById(R.id.radsav4);
                    radvolseek = (SeekBar) v.findViewById(R.id.radvolsb);
                    radchanseek = (SeekBar) v.findViewById(R.id.radcahnsb);

                radvolseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Volume:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                radchanseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Channel:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                rad1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(radvolume.get(0));
                        radchanseek.setProgress(radchannel.get(0));
                    }
                });
                rad2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(radvolume.get(1));
                        radchanseek.setProgress(radchannel.get(1));
                    }
                });
                rad3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(radvolume.get(2));
                        radchanseek.setProgress(radchannel.get(2));
                    }
                });
                rad4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(radvolume.get(3));
                        radchanseek.setProgress(radchannel.get(3));
                    }
                });
                save1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_RADIO_VOLUME, radvolseek.getProgress());
                        cv.put(dbh.COLUMN_RADIO_CHANNEL,radchanseek.getProgress());
                        db.update(dbh.TABLE_AUTO_RADIO,cv,dbh.COLUMN_RADIO_ID + " = " + 1,null);
                        readDB("radio");
                    }
                });
                save2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_RADIO_VOLUME, radvolseek.getProgress());
                        cv.put(dbh.COLUMN_RADIO_CHANNEL,radchanseek.getProgress());
                        db.update(dbh.TABLE_AUTO_RADIO,cv,dbh.COLUMN_RADIO_ID + " = " + 2,null);
                        readDB("radio");
                    }
                });
                save3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_RADIO_VOLUME, radvolseek.getProgress());
                        cv.put(dbh.COLUMN_RADIO_CHANNEL,radchanseek.getProgress());
                        db.update(dbh.TABLE_AUTO_RADIO,cv,dbh.COLUMN_RADIO_ID + " = " + 3,null);
                        readDB("radio");
                    }
                });
                save4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_RADIO_VOLUME, radvolseek.getProgress());
                        cv.put(dbh.COLUMN_RADIO_CHANNEL,radchanseek.getProgress());
                        db.update(dbh.TABLE_AUTO_RADIO,cv,dbh.COLUMN_RADIO_ID + " = " + 4,null);
                        readDB("radio");
                    }
                });


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            ((FragPhone) getActivity()).delete();
                        }
                    });
                break;
            case "CB Radio":
                v = inflater.inflate(R.layout.auto_cb,container,false);
                dbh = new ProjectDatabaseHelper(v.getContext());
                db = dbh.getWritableDatabase();
                Button cb1,cb2,cb3,cb4,cbset1,cbset2,cbset3,cbset4;
                final SeekBar cbvoseek,cbchanseek,cbgainseek;

                cbvolume = new ArrayList<>();
                cbchannel = new ArrayList<>();
                cbgain = new ArrayList<>();
                readDB("cb");
                cbvoseek =(SeekBar) v.findViewById(R.id.cbvolsb);
                cbchanseek =(SeekBar) v.findViewById(R.id.cbchansb);
                cbgainseek =(SeekBar) v.findViewById(R.id.cbgainsb);
                cb1 =(Button) v.findViewById(R.id.cbset1);
                cb2 = (Button) v.findViewById(R.id.cbset2);
                cb3 = (Button)v.findViewById(R.id.cbset3);
                cb4 = (Button) v.findViewById(R.id.cbset4);
                cbset1 = (Button) v.findViewById(R.id.cbsave1);
                cbset2 = (Button) v.findViewById(R.id.cbsave2);
                cbset3 = (Button) v.findViewById(R.id.cbsave3);
                cbset4 = (Button) v.findViewById(R.id.cbsave4);
                delete = (Button) v.findViewById(R.id.cbdelete);
                cbchanseek.setMax(32);

                cbvoseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Volume:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                cbchanseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Channel:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                cbgainseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Gain:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                cb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(cbvolume.get(0));
                        cbchanseek.setProgress(cbchannel.get(0));
                        cbgainseek.setProgress(cbgain.get(0));
                    }
                });
                cb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(cbvolume.get(1));
                        cbchanseek.setProgress(cbchannel.get(1));
                        cbgainseek.setProgress(cbgain.get(1));
                    }
                });
                cb3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(cbvolume.get(2));
                        cbchanseek.setProgress(cbchannel.get(2));
                        cbgainseek.setProgress(cbgain.get(2));
                    }
                });
                cb4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(cbvolume.get(3));
                        cbchanseek.setProgress(cbchannel.get(3));
                        cbgainseek.setProgress(cbgain.get(3));
                    }
                });

                cbset1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_CB_VOLUME,cbvoseek.getProgress());
                        cv.put(dbh.COLUMN_CB_CHANNEL,cbchanseek.getProgress());
                        cv.put(dbh.COLUMN_CB_GAIN,cbgainseek.getProgress());
                        db.update(dbh.TABLE_AUTO_CB,cv,dbh.COLUMN_CB_ID + " = " + 1,null);
                        readDB("cb");
                    }
                });
                cbset2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_CB_VOLUME,cbvoseek.getProgress());
                        cv.put(dbh.COLUMN_CB_CHANNEL,cbchanseek.getProgress());
                        cv.put(dbh.COLUMN_CB_GAIN,cbgainseek.getProgress());
                        db.update(dbh.TABLE_AUTO_CB,cv,dbh.COLUMN_CB_ID + " = " + 2,null);
                        readDB("cb");
                    }
                });
                cbset3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_CB_VOLUME,cbvoseek.getProgress());
                        cv.put(dbh.COLUMN_CB_CHANNEL,cbchanseek.getProgress());
                        cv.put(dbh.COLUMN_CB_GAIN,cbgainseek.getProgress());
                        db.update(dbh.TABLE_AUTO_CB,cv,dbh.COLUMN_CB_ID + " = " + 3,null);
                        readDB("cb");
                    }
                });
                cbset4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cv.clear();
                        cv.put(dbh.COLUMN_CB_VOLUME,cbvoseek.getProgress());
                        cv.put(dbh.COLUMN_CB_CHANNEL,cbchanseek.getProgress());
                        cv.put(dbh.COLUMN_CB_GAIN,cbgainseek.getProgress());
                        db.update(dbh.TABLE_AUTO_CB,cv,dbh.COLUMN_CB_ID + " = " + 4,null);
                        readDB("cb");
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;

            case "GPS":
                v = inflater.inflate(R.layout.auto_gps,container,false);
                dbh = new ProjectDatabaseHelper(v.getContext());
                db = dbh.getWritableDatabase();
                final TextView pos,dest;
                Button go = (Button) v.findViewById(R.id.gpsgo);
                final ImageView direc =(ImageView) v.findViewById(R.id.turniv);
                final TextView next =(TextView) v.findViewById(R.id.streetnametv);
                delete =(Button) v.findViewById(R.id.gpsdelete);
                pos = (TextView) v.findViewById(R.id.curlocet);
                dest =(TextView) v.findViewById(R.id.destet);

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((pos.getText().toString().equalsIgnoreCase("")) || (dest.getText().toString().equalsIgnoreCase(""))) {
                            toast = Toast.makeText(getActivity(),R.string.desterror,Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            next.setText("Street Rd.");
                            switch (new Random().nextInt(4) + 1) {
                                case 1:
                                    direc.setImageResource(R.drawable.up);
                                    break;
                                case 2:
                                    direc.setImageResource(R.drawable.down);
                                    break;
                                case 3:
                                    direc.setImageResource(R.drawable.right);
                                    break;
                                case 4:
                                    direc.setImageResource(R.drawable.left);
                                    break;
                            }
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;

            case "Seat":
                v = inflater.inflate(R.layout.auto_seat,container,false);
                dbh = new ProjectDatabaseHelper(v.getContext());
                db = dbh.getWritableDatabase();
                final SeekBar dist,height,angle;
                Button seat1,seat2,seat3,seat4;

                dist =(SeekBar) v.findViewById(R.id.seatdistsb);
                height = (SeekBar) v.findViewById(R.id.seathisb);
                angle = (SeekBar) v.findViewById(R.id.seatangsb);
                seat1 =(Button) v.findViewById(R.id.seatset1);
                seat2 =(Button) v.findViewById(R.id.seatset2);
                seat3 =(Button) v.findViewById(R.id.seatset3);
                seat4 =(Button) v.findViewById(R.id.seatset4);
                delete =(Button) v.findViewById(R.id.seatdelete);

                dist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Distance:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Height:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                angle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        toast.cancel();
                        toast = Toast.makeText(getActivity(),"Angle:" + progress,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                seat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dist.setProgress(15);
                        height.setProgress(50);
                        angle.setProgress(90);
                    }
                });

                seat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dist.setProgress(40);
                        height.setProgress(45);
                        angle.setProgress(75);
                    }
                });

                seat3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dist.setProgress(100);
                        height.setProgress(5);
                        angle.setProgress(22);
                    }
                });

                seat4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dist.setProgress(0);
                        height.setProgress(0);
                        angle.setProgress(0);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;
            default:
                v = inflater.inflate(R.layout.auto_trunk_frag,container,false);
                TextView trunktv = (TextView) v.findViewById(R.id.trunktext);
                trunktv.setText(getArguments().getString("Name")+"\n"+getArguments().getString("Desc"));

                Button deleteButt =(Button) v.findViewById(R.id.deletetrunk);
                deleteButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;
        }



        return v;
    }

    private void readDB(String type){
        switch(type){
            case "temp":
                tempar.clear();
                fanar.clear();
                acar.clear();
                sa = new String[]{dbh.COLUMN_TEMP_ID,dbh.COLUMN_TEMP_TEMP,dbh.COLUMN_TEMP_FAN,dbh.COLUMN_TEMP_AC};
                cursor = db.query(dbh.TABLE_AUTO_TEMP,sa,null,null,null,null,null,null);
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    tempar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_TEMP)));
                    fanar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_FAN)));
                    acar.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_TEMP_AC)));
                    cursor.moveToNext();
                }
                break;
            case "radio":
                radchannel.clear();
                radvolume.clear();
                sa = new String[]{dbh.COLUMN_RADIO_ID,dbh.COLUMN_RADIO_VOLUME,dbh.COLUMN_RADIO_CHANNEL};
                cursor = db.query(dbh.TABLE_AUTO_RADIO,sa,null,null,null,null,null,null);
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    radvolume.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_RADIO_VOLUME)));
                    radchannel.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_RADIO_CHANNEL)));
                    cursor.moveToNext();
                }
                break;
            case "cb":
                cbvolume.clear();
                cbchannel.clear();
                cbgain.clear();
                sa = new String[]{dbh.COLUMN_CB_ID,dbh.COLUMN_CB_VOLUME,dbh.COLUMN_CB_CHANNEL,dbh.COLUMN_CB_GAIN};
                cursor = db.query(dbh.TABLE_AUTO_CB,sa,null,null,null,null,null,null);
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    cbvolume.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_CB_VOLUME)));
                    cbchannel.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_CB_CHANNEL)));
                    cbgain.add(cursor.getInt(cursor.getColumnIndex(dbh.COLUMN_CB_GAIN)));
                    cursor.moveToNext();
                }
                break;
            case "gps":

                break;
            case "seat":

                break;
        }
    }
}
