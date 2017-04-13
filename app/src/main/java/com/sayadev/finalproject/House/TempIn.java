package com.sayadev.finalproject.House;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;
import com.sayadev.finalproject.livingroom.TV.Channel;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class TempIn extends Fragment {
    private ImageView image;
    private TextView tempText;
    private Button addTemprature;
    private EditText tempData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_temprature_inside, container, false);

        final Bundle data = this.getArguments();

        image = (ImageView) view.findViewById(R.id.tempInImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        tempText = (TextView) view.findViewById(R.id.tempInTitle);

        tempText.setText("ID: " + data.getString("id"));

        return view;
    }
}
