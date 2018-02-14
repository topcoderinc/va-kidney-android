package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.CustomView.ArcProgress;
import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 * It shows the current workout data of a current user
 */
public class WorkoutFragment extends Fragment {

    private TextView tvRunning, tvSteps, tvJumping, tvSwimming;
    private ArcProgress arcRunning, arcSteps, arcJumping, arcSwimming;
    private UserData currentUserData;


    public WorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_workout, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        currentUserData = JsondataUtil.getUserData(getContext());
        initView(getView());
        populateData();
    }

    /**
     * Populates the fields with respective data
     */
    private void populateData() {
        tvRunning.setText(currentUserData.getRunningcurrent()+"/"+currentUserData.getRunninggoal());
        tvSteps.setText(currentUserData.getStepcurrent()+"/"+currentUserData.getStepgoal());
        tvJumping.setText(currentUserData.getJumpcurrent()+"/"+currentUserData.getJumpgoal());
        tvSwimming.setText(currentUserData.getSwimmingcurrent()+"/"+currentUserData.getSwimminggoal());
        arcRunning.setMax((int)currentUserData.getRunninggoal());
        arcRunning.setProgress((int)currentUserData.getRunningcurrent());
        arcSteps.setMax(currentUserData.getStepgoal());
        arcSteps.setProgress(currentUserData.getStepcurrent());
        arcJumping.setMax(currentUserData.getJumpgoal());
        arcJumping.setProgress(currentUserData.getJumpcurrent());
        arcSwimming.setMax(currentUserData.getSwimminggoal());
        arcSwimming.setProgress(currentUserData.getSwimmingcurrent());
    }

    /**
     * Initializes the view
     * @param view This View is required to find all views in this fragment/Activity
     */
    private void initView(View view) {
        tvRunning=view.findViewById(R.id.tvRunning);
        tvSteps=view.findViewById(R.id.tvSteps);
        tvJumping=view.findViewById(R.id.tvJumping);
        tvSwimming=view.findViewById(R.id.tvSwimming);
        arcRunning=view.findViewById(R.id.arcRunning);
        arcSteps=view.findViewById(R.id.arcSteps);
        arcJumping=view.findViewById(R.id.arcJumping);
        arcSwimming=view.findViewById(R.id.arcSwimming);
    }

}
