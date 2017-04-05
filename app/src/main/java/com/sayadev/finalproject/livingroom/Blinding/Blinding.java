package com.sayadev.finalproject.livingroom.Blinding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayadev.finalproject.BaseActivity;
import com.sayadev.finalproject.R;

/**
 * Created by saleh on 3/26/2017.
 */

public class Blinding extends Fragment {
    private ImageView image;
    private TextView blindingText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle data = this.getArguments();

        View v = inflater.inflate(R.layout.activity_blinding, container, false);

        image = (ImageView) v.findViewById(R.id.blindingImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        blindingText = (TextView) v.findViewById(R.id.blindingText);

        blindingText.setText("ID: " + data.getString("id"));

        return v;
    }
}
