package com.sayadev.finalproject.kitchen;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sayadev.finalproject.R;

public class RefrigeratorConfigFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final View view = inflater.inflate(R.layout.fragment_kitchen_refrigerator_config, container, false);

        return view;
    }
}
