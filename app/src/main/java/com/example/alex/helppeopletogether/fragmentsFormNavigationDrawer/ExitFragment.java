package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.alex.helppeopletogether.SupportClasses.ConstantPreferences;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.registration.Login;


public class ExitFragment extends Fragment implements ConstantPreferences {
    Preferences preferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(getActivity());

        preferences.remove(PREFERENCES_ID);
        preferences.remove(PREFERENCES_FOTO);
        preferences.remove(PREFERENCES_NAME);
        preferences.remove(PREFERENCES_PASSWORD);
        preferences.remove(PREFERENCES_LOGIN);
        preferences.remove(PREFERENCES_ID);

        Intent intent = new Intent(getActivity(), Login.class);
        getActivity().startActivity(intent);

    }


    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.exit_fragment,container,false);
//
//        return root;
//    }


}
