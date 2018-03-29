package com.topcoder.vakidney.fragments.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.adapter.LabDataAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 * This fragemnt is first of three fragment used inside HomeFragment. It shows the labdata of the patient using this app
 */
public class Home3Fragment extends Fragment {



    private GridView gridView;
    public Home3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home3, container, false);
        gridView=view.findViewById(R.id.gridView);
        LabDataAdapter goal=new LabDataAdapter(JsondataUtil.getLabData(getActivity()), getActivity());
        gridView.setAdapter(goal);
        return view;
    }

}
