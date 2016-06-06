package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;


public class ExitFragment extends Fragment {
    SaveDataFromAccount saveDataFromAccount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.exit_fragment,container,false);
        saveDataFromAccount = new SaveDataFromAccount();
        saveDataFromAccount.setPreferences(getActivity(), "status", "0");
        getActivity().finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        return root;
    }



}
