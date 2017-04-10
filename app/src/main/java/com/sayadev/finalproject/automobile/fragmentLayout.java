package com.sayadev.finalproject.automobile;

import android.app.Fragment;
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
    Toast toast;
    private ProjectDatabaseHelper dbh;
    private SQLiteDatabase db;
    private String[] sa;
    private ArrayList<Integer> tempar,fanar,acar;
    private Cursor cursor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        toast = new Toast(getActivity());
        View v = null ;
        Button delete;


        tempar = new ArrayList();
        fanar = new ArrayList();
        acar = new ArrayList();


        Log.i("bundle items", "" + getArguments().getString("Type"));
        switch (getArguments().getString("Type")){
            case "Temp Control":
                v = inflater.inflate(R.layout.auto_temp_frag,container,false);
                dbh = new ProjectDatabaseHelper(v.getContext());
                db = dbh.getWritableDatabase();
                sa = new String[]{dbh.COLUMN_TEMP_ID,dbh.COLUMN_TEMP_TEMP,dbh.COLUMN_TEMP_FAN,dbh.COLUMN_TEMP_AC};
                cursor = db.query(dbh.TABLE_AUTO_TEMP,sa,null,null,null,null,null,null);
                cursor.moveToFirst();
                final SeekBar temp,fan;
                Button set1,set2,set3,set4;

                final Switch acswitch = (Switch) v.findViewById(R.id.acswitch);
                delete = (Button) v.findViewById(R.id.tempdelete);
                set1 = (Button) v.findViewById(R.id.buttemp1);
                set2 = (Button) v.findViewById(R.id.buttemp2);
                set3 = (Button) v.findViewById(R.id.buttemp3);
                set4 = (Button) v.findViewById(R.id.buttemp4);
                temp = (SeekBar) v.findViewById(R.id.tempslider);
                fan = (SeekBar) v.findViewById(R.id.fanslider);

                while(!cursor.isAfterLast()){
                    tempar.add(cursor.getColumnIndex(dbh.COLUMN_TEMP_TEMP));
                    fanar.add(cursor.getColumnIndex(dbh.COLUMN_TEMP_FAN));
                    acar.add(cursor.getColumnIndex(dbh.COLUMN_TEMP_AC));
                    cursor.moveToNext();
                }


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

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FragPhone) getActivity()).delete();
                    }
                });
                break;

            case "Radio":
                    v = inflater.inflate(R.layout.auto_radio,container,false);
                    Button rad1,rad2,rad3,rad4;
                    final SeekBar radvolseek,radchanseek;

                    delete = (Button) v.findViewById(R.id.raddelete);
                    rad1 = (Button) v.findViewById(R.id.radset1);
                    rad2 = (Button) v.findViewById(R.id.radset2);
                    rad3 = (Button) v.findViewById(R.id.radset3);
                    rad4 = (Button) v.findViewById(R.id.radset4);
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
                        radvolseek.setProgress(0);
                        radchanseek.setProgress(0);
                    }
                });
                rad2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(25);
                        radvolseek.setProgress(25);
                    }
                });
                rad3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(80);
                        radchanseek.setProgress(50);
                    }
                });
                rad4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radvolseek.setProgress(80);
                        radchanseek.setProgress(100);
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
                Button cb1,cb2,cb3,cb4;
                final SeekBar cbvoseek,cbchanseek,cbgainseek;

                cbvoseek =(SeekBar) v.findViewById(R.id.cbvolsb);
                cbchanseek =(SeekBar) v.findViewById(R.id.cbchansb);
                cbgainseek =(SeekBar) v.findViewById(R.id.cbgainsb);
                cb1 =(Button) v.findViewById(R.id.cbset1);
                cb2 = (Button) v.findViewById(R.id.cbset2);
                cb3 = (Button)v.findViewById(R.id.cbset3);
                cb4 = (Button) v.findViewById(R.id.cbset4);
                delete = (Button) v.findViewById(R.id.cbdelete);

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
                        cbvoseek.setProgress(80);
                        cbchanseek.setProgress(16);
                        cbgainseek.setProgress(5);
                    }
                });
                cb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(80);
                        cbchanseek.setProgress(9);
                        cbgainseek.setProgress(10);
                    }
                });
                cb3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(80);
                        cbchanseek.setProgress(19);
                        cbgainseek.setProgress(0);
                    }
                });
                cb4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbvoseek.setProgress(0);
                        cbchanseek.setProgress(1);
                        cbgainseek.setProgress(0);
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
}
