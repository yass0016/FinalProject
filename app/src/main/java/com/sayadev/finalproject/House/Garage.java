package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.R;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class Garage extends Fragment {
    private ImageView image;
    private TextView tempText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_garage, container, false);

        final Bundle data = this.getArguments();

        image = (ImageView) view.findViewById(R.id.garageImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        tempText = (TextView) view.findViewById(R.id.garageText);

        tempText.setText("ID: " + data.getString("id"));

        return view;
    }
}
