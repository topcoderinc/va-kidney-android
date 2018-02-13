package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.FoodAdapter;
import com.topcoder.vakidney.Adapter.GoalAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment {


    private GridView gridView;
    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_goal, container, false);
        gridView=view.findViewById(R.id.gridView);
        GoalAdapter goal=new GoalAdapter(JsondataUtil.getGoals(getActivity()), getActivity());
        gridView.setAdapter(goal);
        return view;
    }

}
