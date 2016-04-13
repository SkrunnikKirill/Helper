package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;

/**
 * Created by User on 29.03.2016.
 */
public class Advertisement extends Fragment {
    private TextView advertisementText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.advertisement,container,false);
        advertisementText = (TextView) root.findViewById(R.id.advertised_text);
        return root;
    }
}
