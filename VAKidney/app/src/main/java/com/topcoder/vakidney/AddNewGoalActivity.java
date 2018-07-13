package com.topcoder.vakidney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.adapter.GoalBarButtonAdapter;
import com.topcoder.vakidney.constant.DiseaseCategory;
import com.topcoder.vakidney.databinding.ActivityAddNewGoalBinding;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.DialogManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * This class is used to add a new goal
 */
public class AddNewGoalActivity extends AppCompatActivity
        implements GoalBarButtonAdapter.OnSeekBarButtonChangeListener {

    private String[] frequencyString = {"Daily", "Weekly", "Monthly"};

    private enum Activity {EDIT, DELETE, ADD, NOTHING}

    private boolean edited = false;
    private ArrayAdapter<String> unitSpinnerAdapter;

    private Goal mGoal;
    private List<Goal> mGoals;
    private UserData mUserData;
    private GoalBarButtonAdapter mAdapter;

    ActivityAddNewGoalBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_new_goal);

        mUserData = UserData.get();
        mGoals = Goal.getAllWithoutComorbidities(mUserData.getDiseaseCategory(), mUserData.isDialysis());

        //Add new goal was not implemented
        if(mGoals.size()==0) {
            Goal sGoalRun = new Goal();
            sGoalRun.setTitleStr("Workout");
            sGoalRun.setGoal(2.0);
            sGoalRun.setGoalMax(8.0);
            sGoalRun.setGoalMin(1.0);
            sGoalRun.setGoalStep(1.0);
            sGoalRun.setGoalId(1);
            sGoalRun.setUnitStr("minutes");
            sGoalRun.setColorCode("557630");
            sGoalRun.setType(Goal.TYPE_ACTIVITY);
            sGoalRun.setAction(Goal.ACTION_INTAKE);
            sGoalRun.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
            sGoalRun.setIcon(R.drawable.ic_running);
            mGoals.add(sGoalRun);
        }

        mAdapter = new GoalBarButtonAdapter(this, mGoals);
        mAdapter.setOnSeekBarButtonChangeListener(this);
        binder.seekButtonRecycler.setAdapter(mAdapter);

        binder.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binder.unitSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binder.unitSpinner.setAlpha(1.0f);
                return false;
            }
        });

        binder.frequencySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binder.frequencySpinner.setAlpha(1.0f);
                return false;
            }
        });


        if (getIntent().hasExtra("goal")) {
            Goal goalFromIntent = (Goal) getIntent().getSerializableExtra("goal");
            for (Goal goal : mGoals) {
                if (goal.getGoalId() == goalFromIntent.getGoalId()) {
                    mGoal = goal;
                }
            }
            if (mGoal != null) {
                mAdapter.setSelectedGoal(mGoal);
                int index = mGoals.indexOf(mGoal);
                if (index > 0) {
                    binder.seekButtonRecycler.scrollToPosition(index);
                }

                binder.btnAddNewGoal.setText("SAVE MY GOAL");
                binder.actionBarTitle.setText("Edit Goal");
            }
        } else {
            mGoal = mGoals.get(0);
            binder.actionBarTitle.setText("Add New Goal");
            binder.btnDeleteGoal.setVisibility(GONE);
        }

        binder.btnAddNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (binder.btnDeleteGoal.getVisibility() == GONE) {
                    if (mGoal.isHidden()) {
                        message = "A new goal has been created";
                    } else {
                        message = "Existing goal updated";
                    }
                } else {
                    message = "Your goal has been saved";
                }

                DialogManager.showOkDialog(AddNewGoalActivity.this, message, new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        mGoal.setHidden(false);
                        mGoal.save();
                        finish();
                    }
                });
            }
        });

        binder.btnDeleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(
                        AddNewGoalActivity.this,
                        "Do you want to delete this goal?",
                        "Delete",
                        "Cancel",
                        new DialogManager.OnYesClicked() {

                            @Override
                            public void YesClicked() {
                                DialogManager.showOkDialog(
                                        AddNewGoalActivity.this,
                                        "Your goal has been deleted",
                                        new DialogManager.OnYesClicked() {
                                            @Override
                                            public void YesClicked() {
                                                mGoal.setHidden(true);
                                                mGoal.setCurrentLevel(0);
                                                mGoal.save();
                                                finish();
                                            }
                                        });
                            }
                        }, null);
            }
        });
        populateSpinner();
    }


    /**
     * Populate Frequency and Unit Spinner with respective data
     */
    private void populateSpinner() {
        ArrayAdapter<String> frequencySpinnerAdapter = new ArrayAdapter<>(
                AddNewGoalActivity.this,
                android.R.layout.simple_spinner_item,
                frequencyString);
        frequencySpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        binder.frequencySpinner.setAdapter(frequencySpinnerAdapter);

        List<String> spinnerItemArray = new ArrayList<>();
        double value = mGoal.getGoalMin();
        int selectedIndex = 0;
        while (value <= mGoal.getGoalMax()) {
            if (value == mGoal.getGoal()) {
                selectedIndex = spinnerItemArray.size();
            }
            spinnerItemArray.add(value + " " + mGoal.getUnitStr());
            value += mGoal.getGoalStep();
        }

        String[] spinnerItem = new String[spinnerItemArray.size()];
        spinnerItemArray.toArray(spinnerItem);

        unitSpinnerAdapter = new ArrayAdapter<>(
                AddNewGoalActivity.this,
                android.R.layout.simple_spinner_item,
                spinnerItem
        );
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binder.unitSpinner.setAdapter(unitSpinnerAdapter);
        binder.unitSpinner.setSelection(selectedIndex);

        binder.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mGoal != null) {
                    try {
                        Number num = NumberFormat.getInstance().parse(unitSpinnerAdapter.getItem(i));
                        mGoal.setGoal(num.doubleValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onGoalSelected(Goal goal) {
        mGoal = goal;
        populateSpinner();
    }

}
