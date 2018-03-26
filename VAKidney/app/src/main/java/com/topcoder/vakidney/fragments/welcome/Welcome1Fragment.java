package com.topcoder.vakidney.fragments.welcome;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass.
 * This fragment is used in first screen of Welcome Activity. It Extends WelcomeBaseFragment for scaling
 */
public class Welcome1Fragment extends WelcomeBaseFragment {


    public Welcome1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome1, container, false);
    }

    @Override
    public void scale(float scaleX) {
        super.scale(scaleX);
    }
}
