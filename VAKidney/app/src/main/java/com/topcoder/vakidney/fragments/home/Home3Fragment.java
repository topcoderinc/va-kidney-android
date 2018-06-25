package com.topcoder.vakidney.fragments.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.adapter.LabDataAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.databinding.FragmentHome3Binding;
import com.topcoder.vakidney.util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 * This fragemnt is first of three fragment used inside HomeFragment. It shows the labdata of the patient using this app
 */
public class Home3Fragment extends Fragment {

    FragmentHome3Binding binder;

    public Home3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_home3, container, false);
        final View view = binder.getRoot();
        LabDataAdapter goal = new LabDataAdapter(JsondataUtil.getLabData(getActivity()), getActivity());
        binder.gridView.setAdapter(goal);
        return view;
    }

}
