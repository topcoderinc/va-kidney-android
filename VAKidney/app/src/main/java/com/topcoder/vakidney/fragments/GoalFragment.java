package com.topcoder.vakidney.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.adapter.GoalAdapter;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * The goal fragment is used to show the goals that are added by the patient using the app.
 */
public class GoalFragment extends Fragment {


    private GridView gridView;
    private UserData mUserData;

    private List<Goal> goalArrayList;
    public GoalFragment() {
        // Required empty public constructor
        mUserData = UserData.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initAndPopulate(getView());
    }

    private void initAndPopulate(View view) {
        gridView = view.findViewById(R.id.gridView);
        goalArrayList = Goal.get(mUserData.getDiseaseCategory(), mUserData.isDialysis());

        GoalAdapter goalAda = new GoalAdapter(goalArrayList, getActivity());
        gridView.setAdapter(goalAda);
    }

}
