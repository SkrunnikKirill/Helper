package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alex.helppeopletogether.R;


public class NoLikeData extends Fragment {
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_no_like_data, container, false);
        image = (ImageView) root.findViewById(R.id.fragment_no_like_data_image);
        image.setImageResource(R.drawable.noannouncements);
        return root;
    }

}
