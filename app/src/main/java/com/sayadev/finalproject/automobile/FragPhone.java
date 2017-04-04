package com.sayadev.finalproject.automobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sayadev.finalproject.R;

public class FragPhone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_frame);
        fragmentLayout frag = new fragmentLayout();
        Bundle bun = getIntent().getExtras();
        getFragmentManager().beginTransaction().add(R.id.fraghold,frag).commit();

        switch (savedInstanceState.getString("Option")){
            case "Temperature":
                


                break;
        }
    }
}
