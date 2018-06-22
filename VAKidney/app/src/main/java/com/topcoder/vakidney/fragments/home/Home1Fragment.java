package com.topcoder.vakidney.fragments.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.ChartActivity;
import com.topcoder.vakidney.ComorbiditiesActivity;
import com.topcoder.vakidney.databinding.FragmentHome1Binding;
import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This fragemnt is first of three fragment used inside HomeFragment. It shows the data of current goals that are saved by the users
 */
public class Home1Fragment extends Fragment {


    private int NoOfGoalsDisplayed;
    private UserData currentUser;
    private List<Goal> goalArrayList;
    private FragmentHome1Binding binder;

    public Home1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_home1, container, false);
        final View view = binder.getRoot();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        PopulateFields();
    }

    /**
     * Populate the fields with Goal and User Data
     */
    private void PopulateFields() {
        currentUser = UserData.get();
        NoOfGoalsDisplayed=0;
        NoOfGoalsDisplayed+=getComorbiditiesGoals();
        goalArrayList = Goal.getWithoutComorbidities(currentUser.getDiseaseCategory(), currentUser.isDialysis());
        binder.tvWelcomeName.setText(getString(R.string.welcome_string) + currentUser.getFullname().split(" ")[0] + ",");
        binder.btnPoints.setText(currentUser.getPoints() + " Points");
        fillComorbiditiesGoals();
        fillRemainingGoals();
    }

    private int getComorbiditiesGoals() {
        int total=0;
        if(currentUser.isComorbiditiesCongestiveheartfailure())
            total++;
        if(currentUser.isComorbiditiesDiabetesmellitus())
            total++;
        if(currentUser.isComorbiditiesHypertension())
            total++;
        return total;
    }

    private void fillRemainingGoals() {

        //Add Goal from remaining user goals.
        int mGoalIndex = 0;
        if (NoOfGoalsDisplayed < 1) {
            final Goal goal1 = goalArrayList.size() > mGoalIndex ? goalArrayList.get(mGoalIndex) : null;
            if (goal1 != null) {
                binder.arcLayout1.setVisibility(View.VISIBLE);
                binder.arcProgress1.setBottomText(goal1.getTitleStr());
                binder.tvUnit1.setText(goal1.getUnitStr());
                if ((goal1.getCurrentLevel() == Math.floor(goal1.getCurrentLevel())) && !Double.isInfinite(goal1.getCurrentLevel())) {
                    binder.tvStat1.setText((int) goal1.getCurrentLevel() + "/" + (int) goal1.getGoal());
                } else {
                    binder.tvStat1.setText(goal1.getCurrentLevel() + "/" + goal1.getGoal());
                }
                binder.arcProgress1.setProgress((int) goal1.getCurrentLevel());
                binder.arcProgress1.setMax((int) goal1.getGoalMax());
                binder.arcProgress1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ChartActivity.class);
                        intent.putExtra("chartType", goal1.getGoalId());
                        getContext().startActivity(intent);
                    }
                });
                binder.arcProgress1.setFinishedStrokeColor(Color.parseColor("#" + goal1.getColorCode()));
                binder.arcProgress1.setIcon(goal1.getIcon());
                binder.arcProgress1.setIconColor(Color.WHITE);
                binder.arcProgress1.setUnfinishedStrokeColor(Color.WHITE);
                NoOfGoalsDisplayed++;
                mGoalIndex++;
            } else
                binder.arcLayout1.setVisibility(View.INVISIBLE);
        }

        if (NoOfGoalsDisplayed < 2) {
            final Goal goal2 = goalArrayList.size() > mGoalIndex ? goalArrayList.get(mGoalIndex) : null;
            if (goal2 != null) {
                binder.arcLayout2.setVisibility(View.VISIBLE);
                binder.arcProgress2.setBottomText(goal2.getTitleStr());
                binder.tvUnit2.setText(goal2.getUnitStr());
                if ((goal2.getCurrentLevel() == Math.floor(goal2.getCurrentLevel())) && !Double.isInfinite(goal2.getCurrentLevel())) {
                    binder.tvStat2.setText((int) goal2.getCurrentLevel() + "/" + (int) goal2.getGoal());
                } else {
                    binder.tvStat2.setText(goal2.getCurrentLevel() + "/" + goal2.getGoal());
                }
                binder.arcProgress2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ChartActivity.class);
                        intent.putExtra("chartType", goal2.getGoalId());
                        getContext().startActivity(intent);
                    }
                });
                binder.arcProgress2.setProgress((int) goal2.getCurrentLevel());
                binder.arcProgress2.setMax((int) goal2.getGoalMax());
                binder.arcProgress2.setFinishedStrokeColor(Color.parseColor("#" + goal2.getColorCode()));
                binder.arcProgress2.setIcon(goal2.getIcon());
                binder.arcProgress2.setIconColor(Color.WHITE);
                binder.arcProgress2.setUnfinishedStrokeColor(Color.WHITE);
                NoOfGoalsDisplayed++;
                mGoalIndex++;
            } else
                binder.arcLayout2.setVisibility(View.INVISIBLE);
        }


        if (NoOfGoalsDisplayed < 3) {
            final Goal goal3 = goalArrayList.size() > mGoalIndex ? goalArrayList.get(mGoalIndex) : null;
            if (goal3 != null) {
                binder.arcLayout3.setVisibility(View.VISIBLE);
                binder.arcProgress3.setBottomText(goal3.getTitleStr());
                binder.tvUnit3.setText(goal3.getUnitStr());
                if ((goal3.getCurrentLevel() == Math.floor(goal3.getCurrentLevel())) && !Double.isInfinite(goal3.getCurrentLevel())) {
                    binder.tvStat3.setText((int) goal3.getCurrentLevel() + "/" + (int) goal3.getGoal());
                } else {
                    binder.tvStat3.setText(goal3.getCurrentLevel() + "/" + goal3.getGoal());
                }
                binder.arcProgress3.setProgress((int) goal3.getCurrentLevel());
                binder.arcProgress3.setMax((int) goal3.getGoalMax());
                binder.arcProgress3.setFinishedStrokeColor(Color.parseColor("#" + goal3.getColorCode()));
                binder.arcProgress3.setIcon(goal3.getIcon());
                binder.arcProgress3.setIconColor(Color.WHITE);
                binder.arcProgress3.setUnfinishedStrokeColor(Color.WHITE);
                binder.arcProgress3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ChartActivity.class);
                        intent.putExtra("chartType", goal3.getGoalId());
                        getContext().startActivity(intent);
                    }
                });
                NoOfGoalsDisplayed++;
                mGoalIndex++;
            } else
                binder.arcLayout3.setVisibility(View.INVISIBLE);
        }
        if (NoOfGoalsDisplayed < 4) {
            final Goal goal4 = goalArrayList.size() > mGoalIndex ? goalArrayList.get(mGoalIndex) : null;
            if (goal4 != null) {
                binder.arcLayout4.setVisibility(View.VISIBLE);
                binder.arcProgress4.setProgress((int) goal4.getCurrentLevel());
                binder.arcProgress4.setMax((int) goal4.getGoalMax());
                binder.arcProgress4.setBottomText(goal4.getTitleStr());

                binder.arcProgress4.setFinishedStrokeColor(Color.parseColor("#" + goal4.getColorCode()));
                binder.arcProgress4.setIcon(goal4.getIcon());
                binder.arcProgress4.setIconColor(Color.WHITE);
                binder.arcProgress4.setUnfinishedStrokeColor(Color.WHITE);
                if ((goal4.getCurrentLevel() == Math.floor(goal4.getCurrentLevel())) && !Double.isInfinite(goal4.getCurrentLevel())) {
                    binder.tvStat4.setText((int) goal4.getCurrentLevel() + "/" + (int) goal4.getGoal());
                } else {
                    binder.tvStat4.setText(goal4.getCurrentLevel() + "/" + goal4.getGoal());
                }
                binder.tvUnit4.setText(goal4.getUnitStr());
                binder.arcProgress4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ChartActivity.class);
                        intent.putExtra("chartType", goal4.getGoalId());
                        getContext().startActivity(intent);
                    }
                });
                NoOfGoalsDisplayed++;
                mGoalIndex++;
            } else
                binder.arcLayout4.setVisibility(View.INVISIBLE);
        }
    }
    private void fillComorbiditiesGoals() {
        final Goal goal1;
        if (currentUser.isComorbiditiesHypertension()) {
            binder.tvUnit1.setText("");
            binder.tvStat1.setText("");
            goal1 = Goal.getByTitleStr("Blood Pressure").get(0);
            binder.arcProgress1.setBottomText(goal1.getTitleStr());
            binder.arcProgress1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ComorbiditiesActivity.getIntent(getContext(),  goal1.getGoalId()));

                }
            });
            List<ChartData> data = ChartData.getChartData(goal1.getGoalId());
            binder.arcProgress1.setProgress(0);
            binder.arcProgress1.setMax(0);
            if (data != null) {//gets today value
                if (data.size() > 0 && DateUtils.isToday(data.get(0).getDate())) {
                    binder.tvUnit1.setText(goal1.getUnitStr());
                    binder.tvStat1.setText(data.get(0).getValue() + "");
                    binder.arcProgress1.setProgress(100);
                    binder.arcProgress1.setMax(100);
                }
            }
            binder.arcProgress1.setFinishedStrokeColor(Color.parseColor("#" + goal1.getColorCode()));
            binder.arcProgress1.setIcon(goal1.getIcon());
            binder.arcProgress1.setIconColor(Color.WHITE);
            binder.arcProgress1.setUnfinishedStrokeColor(Color.WHITE);
            binder.arcLayout1.setVisibility(View.VISIBLE);
        }
        final Goal goal3;
        if (currentUser.isComorbiditiesCongestiveheartfailure()) {
            goal3 = Goal.getByTitleStr("Body Weight").get(0);
            binder.arcLayout3.setVisibility(View.VISIBLE);
            binder.arcProgress3.setBottomText(goal3.getTitleStr());
            binder.tvUnit3.setText("");
            binder.tvStat3.setText("");
            binder.arcProgress3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ComorbiditiesActivity.getIntent(getContext(),  goal3.getGoalId()));
                }
            });
            binder.arcProgress3.setProgress(0);
            binder.arcProgress3.setMax(0);
            List<ChartData> data = ChartData.getChartData(goal3.getGoalId());
            if (data != null) { //gets today value
                if (data.size() > 0 && DateUtils.isToday(data.get(0).getDate())) {
                    binder.tvUnit3.setText(goal3.getUnitStr());
                    binder.tvStat3.setText(data.get(0).getValue() + "");
                    binder.arcProgress3.setProgress(100);
                    binder.arcProgress3.setMax(100);
                }
            } else {
                binder.tvUnit3.setText(goal3.getUnitStr());
                binder.tvStat3.setText(currentUser.getWeight() + "");
                binder.arcProgress3.setProgress(100);
                binder.arcProgress3.setMax(100);
            }
            binder.arcProgress3.setFinishedStrokeColor(Color.parseColor("#" + goal3.getColorCode()));
            binder.arcProgress3.setIcon(goal3.getIcon());
            binder.arcProgress3.setIconColor(Color.WHITE);
            binder.arcProgress3.setUnfinishedStrokeColor(Color.WHITE);
        }
        final Goal goal2;
        if (currentUser.isComorbiditiesDiabetesmellitus()) {
            binder.tvUnit2.setText("");
            binder.tvStat2.setText("");
            goal2 = Goal.getByTitleStr("Blood Glucose").get(0);
            binder.arcProgress2.setBottomText(goal2.getTitleStr());
            binder.arcProgress2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ComorbiditiesActivity.getIntent(getContext(),  goal2.getGoalId()));
                }
            });
            List<ChartData> data = ChartData.getChartData(goal2.getGoalId());
            binder.arcProgress2.setProgress(0);
            binder.arcProgress2.setMax(0);
            if (data != null) {//gets today value
                if (data.size() > 0 && DateUtils.isToday(data.get(0).getDate())) {
                    binder.tvUnit2.setText(goal2.getUnitStr());
                    binder.tvStat2.setText(data.get(0).getValue() + "");
                    binder.arcProgress2.setProgress(100);
                    binder.arcProgress2.setMax(100);
                }
            }
            binder.arcLayout2.setVisibility(View.VISIBLE);
            binder.arcProgress2.setFinishedStrokeColor(Color.parseColor("#" + goal2.getColorCode()));
            binder.arcProgress2.setIcon(goal2.getIcon());
            binder.arcProgress2.setIconColor(Color.WHITE);
            binder.arcProgress2.setUnfinishedStrokeColor(Color.WHITE);
        }
    }


}
