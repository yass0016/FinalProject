package com.sayadev.finalproject.automobile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.R;

public class fragmentLayout extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        final View v ;

        Log.i("bundle items", "" + getArguments().getString("Type"));
        switch (getArguments().getString("Type")){
            case "Temp Control":
                v = inflater.inflate(R.layout.auto_temp_frag,container,false);
                final SeekBar temp,fan;
                Button set1,set2,set3,set4,delete;
                final Switch acswitch = (Switch) v.findViewById(R.id.acswitch);

                set1 = (Button) v.findViewById(R.id.buttemp1);
                set2 = (Button) v.findViewById(R.id.buttemp2);
                set3 = (Button) v.findViewById(R.id.buttemp3);
                set4 = (Button) v.findViewById(R.id.buttemp4);
                temp = (SeekBar) v.findViewById(R.id.tempslider);
                fan = (SeekBar) v.findViewById(R.id.fanslider);
                delete = (Button) v.findViewById(R.id.tempdelete);

                temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    Toast toast = new Toast(getActivity());

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
                    Toast toast = new Toast(getActivity());
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
                        acswitch.setChecked(true);
                        temp.setProgress(20);
                        fan.setProgress(5);
                        Snackbar.make(getView(),"Preset 1 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acswitch.setChecked(true);
                        temp.setProgress(8);
                        fan.setProgress(5);
                        Snackbar.make(getView(),"Preset 2 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acswitch.setChecked(false);
                        temp.setProgress(30);
                        fan.setProgress(10);
                        Snackbar.make(getView(),"Preset 3 selected",Snackbar.LENGTH_SHORT).show();
                    }
                });

                set4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acswitch.setChecked(false);
                        temp.setProgress(15);
                        fan.setProgress(5);
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
