package com.topcoder.vakidney.MainFragments.HomeFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.topcoder.vakidney.CustomView.ArcProgress;
import com.topcoder.vakidney.Model.Goal;
import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home1Fragment extends Fragment {

    private ArcProgress arcProgress1, arcProgress2,arcProgress3,arcProgress4;
    private TextView tvStat1, tvStat2,tvStat3,tvStat4;
    private TextView tvUnit1, tvUnit2,tvUnit3,tvUnit4;

    private TextView tvWelcomeName;
    private Button btnPoints;

    private UserData currentUser;
    private ArrayList<Goal> goalArrayList;

    public Home1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home1, container, false);
        initView(view);
        PopulateFields();
        return view;
    }


    /**
     * Populate the fields with Goal and User Data
     */
    private void PopulateFields() {
        currentUser= JsondataUtil.getUserData(getActivity());
        goalArrayList=JsondataUtil.getGoals(getActivity());
        tvWelcomeName.setText("Welcome "+currentUser.getFullname().split(" ")[0]);
        btnPoints.setText(currentUser.getPoints()+" Points");
        Goal goal1=goalArrayList.get(0);
        arcProgress1.setProgress((int)goal1.getCurrentLevel());
        arcProgress1.setMax((int)goal1.getGoal());
        arcProgress1.setBottomText(JsondataUtil.getGoalTitleById(getActivity(), goal1.getTitle()));
        arcProgress1.setFinishedStrokeColor(Color.parseColor("#"+goal1.getColorCode()));
        arcProgress1.setIcon(goal1.getIcon());
        arcProgress1.setIconColor(Color.WHITE);
        arcProgress1.setUnfinishedStrokeColor(Color.WHITE);
        tvStat1.setText((int)goal1.getCurrentLevel()+"/"+(int)goal1.getGoal());
        tvUnit1.setText(JsondataUtil.getGoalUnitById(getActivity(), goal1.getUnit()));
        Goal goal2=goalArrayList.get(1);
        arcProgress2.setProgress((int)goal2.getCurrentLevel());
        arcProgress2.setMax((int)goal2.getGoal());
        arcProgress2.setBottomText(JsondataUtil.getGoalTitleById(getActivity(), goal2.getTitle()));
        arcProgress2.setFinishedStrokeColor(Color.parseColor("#"+goal2.getColorCode()));
        arcProgress2.setIcon(goal2.getIcon());
        arcProgress2.setIconColor(Color.WHITE);
        arcProgress2.setUnfinishedStrokeColor(Color.WHITE);
        tvStat2.setText((int)goal2.getCurrentLevel()+"/"+(int)goal2.getGoal());
        tvUnit2.setText(JsondataUtil.getGoalUnitById(getActivity(), goal2.getUnit()));
        Goal goal3=goalArrayList.get(2);
        arcProgress3.setProgress((int)goal3.getCurrentLevel());
        arcProgress3.setMax((int)goal3.getGoal());
        arcProgress3.setBottomText(JsondataUtil.getGoalTitleById(getActivity(), goal3.getTitle()));
        arcProgress3.setFinishedStrokeColor(Color.parseColor("#"+goal3.getColorCode()));
        arcProgress3.setIcon(goal3.getIcon());
        arcProgress3.setIconColor(Color.WHITE);
        arcProgress3.setUnfinishedStrokeColor(Color.WHITE);
        tvStat3.setText((int)goal3.getCurrentLevel()+"/"+(int)goal3.getGoal());
        tvUnit3.setText(JsondataUtil.getGoalUnitById(getActivity(), goal3.getUnit()));
        Goal goal4=goalArrayList.get(3);
        arcProgress4.setProgress((int)goal4.getCurrentLevel());
        arcProgress4.setMax((int)goal4.getGoal());
        arcProgress4.setBottomText(JsondataUtil.getGoalTitleById(getActivity(), goal4.getTitle()));
        arcProgress4.setFinishedStrokeColor(Color.parseColor("#"+goal4.getColorCode()));
        arcProgress4.setIcon(goal4.getIcon());
        arcProgress4.setIconColor(Color.WHITE);
        arcProgress4.setUnfinishedStrokeColor(Color.WHITE);
        tvStat4.setText((int)goal4.getCurrentLevel()+"/"+(int)goal4.getGoal());
        tvUnit4.setText(JsondataUtil.getGoalUnitById(getActivity(), goal4.getUnit()));
    }

    /**
     * Initializes The View
     * @param view
     */
    private void initView(View view) {
        arcProgress1=view.findViewById(R.id.arcProgress1);
        arcProgress2=view.findViewById(R.id.arcProgress2);
        arcProgress3=view.findViewById(R.id.arcProgress3);
        arcProgress4=view.findViewById(R.id.arcProgress4);
        tvStat1=view.findViewById(R.id.tvStat1);
        tvStat2=view.findViewById(R.id.tvStat2);
        tvStat3=view.findViewById(R.id.tvStat3);
        tvStat4=view.findViewById(R.id.tvStat4);
        tvUnit1=view.findViewById(R.id.tvUnit1);
        tvUnit2=view.findViewById(R.id.tvUnit2);
        tvUnit3=view.findViewById(R.id.tvUnit3);
        tvUnit4=view.findViewById(R.id.tvUnit4);
        tvWelcomeName=view.findViewById(R.id.tvWelcomeName);
        btnPoints=view.findViewById(R.id.btnPoints);
    }

}
