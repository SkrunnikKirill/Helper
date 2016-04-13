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
 * Created by Alex on 04.04.2016.
 */
public class FriendFragment extends Fragment {
    private TextView fiendText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friend_fragment,container,false);
        fiendText = (TextView) root.findViewById(R.id.friend_fragment_text);
        return root;
    }
}
