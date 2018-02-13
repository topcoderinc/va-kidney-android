package com.topcoder.vakidney.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome2Fragment extends WelcomeBaseFragment {


    public Welcome2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome2, container, false);
    }

    @Override
    public void scale(float scaleX) {
        super.scale(scaleX);
    }

}
