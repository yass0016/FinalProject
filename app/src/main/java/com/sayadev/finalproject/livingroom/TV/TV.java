package com.sayadev.finalproject.livingroom.TV;

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

import com.sayadev.finalproject.R;

/**
 * Created by saleh on 3/26/2017.
 */

public class TV extends Fragment {

    private ImageView image;
    private TextView tvText;
    private Button addChannel;
    private EditText channelData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle data = this.getArguments();

        View v = inflater.inflate(R.layout.activity_tv, container, false);

        image = (ImageView) v.findViewById(R.id.tvImage);

        image.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), getResources().getIdentifier(data.getString("itemImage"), null, getActivity().getPackageName())));

        tvText = (TextView) v.findViewById(R.id.tvText);
        tvText.setText("TV ID: " + data.getString("id") + " No Channel is Playing ");

        channelData = (EditText) v.findViewById(R.id.channelTitle);
        addChannel = (Button) v.findViewById(R.id.addChannel);

        addChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Channel channel = new Channel();

                if(channelData.getText().length() != 0) {
                    channel.setChannel_name(channelData.getText().toString());
                    tvText.setText("TV ID: " + data.getString("id") + " Playing " + channel.getChannel_name());
                }
            }
        });

        return v;
    }
}
