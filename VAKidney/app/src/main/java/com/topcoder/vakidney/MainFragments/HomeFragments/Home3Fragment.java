package com.topcoder.vakidney.MainFragments.HomeFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.GoalAdapter;
import com.topcoder.vakidney.Adapter.LabDataAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
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
