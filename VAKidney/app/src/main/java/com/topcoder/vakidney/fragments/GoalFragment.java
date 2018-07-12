package com.topcoder.vakidney.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.GoalAdapter;
import com.topcoder.vakidney.databinding.FragmentGoalBinding;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * The goal fragment is used to show the goals that are added by the patient using the app.
 */
public class GoalFragment extends Fragment {


    private UserData mUserData;

    private List<Goal> goalArrayList;
    private FragmentGoalBinding binder;

    public GoalFragment() {
        // Required empty public constructor
        mUserData = UserData.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_goal, container, false);
        final View view = binder.getRoot();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populate();
    }

    private void populate() {
        goalArrayList = Goal.getWithoutComorbidities(mUserData.getDiseaseCategory(), mUserData.isDialysis());
        GoalAdapter goalAdapter = new GoalAdapter(goalArrayList, getActivity());
        binder.gridView.setAdapter(goalAdapter);
    }


}
