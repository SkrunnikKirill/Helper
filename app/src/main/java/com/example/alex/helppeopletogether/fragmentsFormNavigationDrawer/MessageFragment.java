package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;

/**
 * Created by Alex on 04.04.2016.
 */
public class MessageFragment extends Fragment {
    private TextView messageText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.message_fragment,container,false);
        messageText = (TextView) root.findViewById(R.id.exit_fragment_text);
        return root;
    }
}
