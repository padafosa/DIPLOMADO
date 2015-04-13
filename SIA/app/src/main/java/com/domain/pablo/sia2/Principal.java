package com.domain.pablo.sia2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Pablo on 4/1/2015.
 */
public class Principal extends Fragment {

    public Principal(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_principal, container, false);
        return rootView;
    }
}
