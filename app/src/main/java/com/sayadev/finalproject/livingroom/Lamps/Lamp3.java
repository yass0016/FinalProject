package com.sayadev.finalproject.livingroom.Lamps;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sayadev.finalproject.R;

public class Lamp3 extends Fragment {
    private ImageView image;
    private TextView lampText;
    private Button lightStatus;
    private boolean status = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle data = this.getArguments();

        View v = inflater.inflate(R.layout.activity_lamp3, container, false);

        image = (ImageView) v.findViewById(R.id.lampsImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        lampText = (TextView) v.findViewById(R.id.lampsText);
        lampText.setText("ID: " + data.getString("id") + " Lamp OFF ");

        lightStatus = (Button) v.findViewById(R.id.switchLamp);

        lightStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!status) {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light ON", Toast.LENGTH_SHORT).show();
                    lampText.setText("ID: " + data.getString("id") + " Light ON");
                    lightStatus.setText("SWITCH LAMP OFF");
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You turned the light OFF", Toast.LENGTH_SHORT).show();
                    lampText.setText("ID: " + data.getString("id") + " Light OFF");
                    lightStatus.setText("SWITCH LAMP ON");
                }
                status = !status;
            }
        });

        return v;
    }
}
