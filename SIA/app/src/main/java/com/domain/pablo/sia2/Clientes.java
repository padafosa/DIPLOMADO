package com.domain.pablo.sia2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pablo on 3/27/2015.
 */
public class Clientes extends Fragment {

    public Clientes(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_clientes, container, false);
        return rootView;
    }

}
