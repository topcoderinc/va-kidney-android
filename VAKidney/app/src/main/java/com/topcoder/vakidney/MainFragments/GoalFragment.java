package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.GoalAdapter;
import com.topcoder.vakidney.Model.Goal;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * The goal fragment is used to show the goals that are added by the patient using the app.
 */
public class GoalFragment extends Fragment {


    private GridView gridView;
    ArrayList<Goal> goalArrayList;
    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_goal, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initAndPopulate(getView());
    }

    private void initAndPopulate(View view) {
        gridView=view.findViewById(R.id.gridView);
        goalArrayList= JsondataUtil.getGoals(getActivity());
        if(getActivity().getIntent().hasExtra("addgoal")){
            Bundle bundle=getActivity().getIntent().getBundleExtra("goal");
            Goal goal=Goal.getGoalFromBundle(bundle);
            goalArrayList.add(goal);
        }

        if(getActivity().getIntent().hasExtra("deletegoal")){
            Bundle bundle=getActivity().getIntent().getBundleExtra("goal");
            Goal goal=Goal.getGoalFromBundle(bundle);
            for(Goal goal1:goalArrayList) {
                if(goal1.getId()==goal.getId()){
                    goalArrayList.remove(goal1);
                    break;
                }
            }
        }

        if(getActivity().getIntent().hasExtra("editgoal")){
            Bundle bundle=getActivity().getIntent().getBundleExtra("goal");
            Goal goal=Goal.getGoalFromBundle(bundle);
            for(Goal goal1:goalArrayList) {
                if(goal1.getId()==goal.getId()){
                    goalArrayList.remove(goal1);
                    break;
                }
            }
            goalArrayList.add(goal);

        }


        GoalAdapter goalAda=new GoalAdapter(goalArrayList, getActivity());
        gridView.setAdapter(goalAda);
    }

}
