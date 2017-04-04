package com.sayadev.finalproject.automobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.sayadev.finalproject.R;

public class FragPhone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_empty_frame);
        fragmentLayout frag = new fragmentLayout();
        Bundle bun = getIntent().getExtras();
        frag.setArguments(bun);
        getFragmentManager().beginTransaction().add(R.id.fraghold,frag).commit();

        /*switch (bun.getString("Option")){
            case "Temperature":
                Log.i("fragment","temp");
                SeekBar temp,fan;
                Button set1,set2,set3;
                final Switch acswitch = (Switch) frag.getView().findViewById(R.id.acswitch);

                set1 = (Button) frag.getView().findViewById(R.id.buttemp1);
                set2 = (Button) frag.getView().findViewById(R.id.buttemp2);
                set3 = (Button) frag.getView().findViewById(R.id.buttemp3);
                temp = (SeekBar) frag.getView().findViewById(R.id.tempslider);
                fan = (SeekBar) frag.getView().findViewById(R.id.fanslider);


                temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(acswitch.isActivated()){
                            Toast toast = Toast.makeText(FragPhone.this,"-" + progress + "C",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                break;
        }*/
    }
}
