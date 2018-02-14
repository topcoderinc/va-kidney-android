package com.topcoder.vakidney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.Model.Goal;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.text.NumberFormat;
import java.text.ParseException;

import static android.view.View.GONE;

/**
 * This class is used to add a new goal
 */
public class AddNewGoalActivity extends AppCompatActivity {

    private LinearLayout bottomMenu1, bottomMenu2, bottomMenu3, bottomMenu4, bottomMenu5;
    private AppCompatImageView backBtn;
    private Button seekBtn1, seekBtn2, seekBtn3, seekBtn4;
    private int currentSeekIndex = 1;
    private String[] frequencyString = {"Daily", "Weekly",  "Monthly"};
    private AppCompatSpinner unitSpinner, frequencySpinner;
    private Goal currentGoal;

    private Button btnDeleteGoal, btnAddGoal;
    private TextView actionBarTitle;

    private enum Activity {EDIT, DELETE, ADD, NOTHING}

    private boolean edited = false;
    private ArrayAdapter<String> unitSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_goal);
        backBtn = findViewById(R.id.backBtn);
        actionBarTitle = findViewById(R.id.actionBarTitle);
        btnDeleteGoal = findViewById(R.id.btnDeleteGoal);
        btnAddGoal = findViewById(R.id.btnAddNewGoal);
        unitSpinner = findViewById(R.id.unitSpinner);
        frequencySpinner = findViewById(R.id.frequencySpinner);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edited) {
                    NavigateHome(Activity.EDIT);
                } else {
                    NavigateHome(Activity.NOTHING);
                }
            }
        });

        unitSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                unitSpinner.setAlpha(1.0f);
                return false;
            }
        });


        frequencySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                frequencySpinner.setAlpha(1.0f);
                return false;
            }
        });
        SetupSeekBar();
        SetupBotomMenu();
        if (getIntent().hasExtra("id")) {
            currentGoal = JsondataUtil.getGoalByID(getApplicationContext(), getIntent().getIntExtra("id", 1));
            currentSeekIndex = currentGoal.getTitle();
            btnAddGoal.setText("SAVE MY GOAL");
            seekButton();
            actionBarTitle.setText("Edit Goal");
        } else {
            unitSpinner.setAlpha(0.0f);
            frequencySpinner.setAlpha(0.0f);
            actionBarTitle.setText("Add NEw Goal");
            btnDeleteGoal.setVisibility(GONE);
        }
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (btnDeleteGoal.getVisibility() == GONE) {
                    message = "A New Goal Has been created";
                } else {
                    message = "Your goal has been saved";
                }

                DialogManager.showOkDialog(AddNewGoalActivity.this, message, new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {

                        if (btnDeleteGoal.getVisibility() == GONE) {
                            NavigateHome(Activity.ADD);
                        } else {
                            NavigateHome(Activity.EDIT);
                        }

                    }
                });
            }
        });
        btnDeleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(AddNewGoalActivity.this, "Do you want to delete this goal?", "Delete", "Cancel", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {

                        DialogManager.showOkDialog(AddNewGoalActivity.this, "Your goal has been deleted", new DialogManager.OnYesClicked() {
                            @Override
                            public void YesClicked() {
                                NavigateHome(Activity.DELETE);
                            }
                        });
                    }
                }, null);
            }
        });
        PopulateSpinner();
    }

    @Override
    public void onBackPressed() {

        if (edited) {
            NavigateHome(Activity.EDIT);
        } else {
            NavigateHome(Activity.NOTHING);
        }
    }

    /**
     * Navigate to Home Screen
     */
    private void NavigateHome(Activity activity) {
        finish();
        Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
        if(activity==Activity.NOTHING) {
            intent.putExtra("tag", MainActivity.TAG_GOAL);
        }
        if(activity==Activity.ADD){
            intent.putExtra("tag", MainActivity.TAG_GOAL);
            intent.putExtra("addgoal", true);
            intent.putExtra("goal", getCurrentGoal().getBundle());
        }
        if(activity==Activity.DELETE){
            intent.putExtra("tag", MainActivity.TAG_GOAL);
            intent.putExtra("deletegoal", true);
            intent.putExtra("goal", getCurrentGoal().getBundle());
        }
        if(activity==Activity.EDIT){
            intent.putExtra("tag", MainActivity.TAG_GOAL);
            intent.putExtra("editgoal", true);
            intent.putExtra("goal", getCurrentGoal().getBundle());
        }
        startActivity(intent);
    }

    /**
     * Populate Frequency and Unit Spinner with respective data
     */
    private void PopulateSpinner() {
        ArrayAdapter<String> frequencySpinnerAdapter = new ArrayAdapter<>(AddNewGoalActivity.this, android.R.layout.simple_spinner_item, frequencyString);
        frequencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencySpinnerAdapter);
        unitSpinnerAdapter = new ArrayAdapter<>(AddNewGoalActivity.this, android.R.layout.simple_spinner_item, JsondataUtil.getUnitsArray(getApplicationContext(), currentSeekIndex, 5, 0));
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitSpinnerAdapter);


        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentGoal!=null){
                    try {
                        Number num = NumberFormat.getInstance().parse(unitSpinnerAdapter.getItem(i));
                        currentGoal.setGoal(num.doubleValue());
                        currentGoal.setCurrentLevel(num.doubleValue()/2);
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

    /**
     * Initialize view and sets up listener for bottom menu
     */
    private void SetupBotomMenu() {
        bottomMenu1 = findViewById(R.id.barLin1);
        bottomMenu2 = findViewById(R.id.barLin2);
        bottomMenu3 = findViewById(R.id.barLin3);
        bottomMenu4 = findViewById(R.id.barLin4);
        bottomMenu5 = findViewById(R.id.barLin5);


        bottomMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_HOME);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_CHART);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_MEDICATION);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_FOOD);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewGoalActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_WORKOUT);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Initialize Top Seekbar and Sets up listener
     */
    private void SetupSeekBar() {
        seekBtn1 = findViewById(R.id.seekBtn1);
        seekBtn2 = findViewById(R.id.seekBtn2);
        seekBtn3 = findViewById(R.id.seekBtn3);
        seekBtn4 = findViewById(R.id.seekBtn4);

        seekBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSeekIndex = 1;
                seekButton();
            }
        });


        seekBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSeekIndex = 2;
                seekButton();
            }
        });

        seekBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSeekIndex = 3;
                seekButton();
            }
        });


        seekBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSeekIndex = 4;
                seekButton();
            }
        });

    }

    /**
     * Method to emulate seek button transition
     */
    private void seekButton() {
        unitSpinner.setAlpha(0.0f);
        frequencySpinner.setAlpha(0.0f);
        if(currentGoal!=null){
            currentGoal.setTitle(currentSeekIndex);
            currentGoal.setUnit(currentSeekIndex);
            currentGoal.setColorCode(JsondataUtil.getGoalColorById(getApplicationContext(), currentSeekIndex));
            switch (currentSeekIndex) {
                case 1:
                    currentGoal.setIcon(R.drawable.ic_running);
                    break;
                case 2:
                    currentGoal.setIcon(R.drawable.ic_water_bottle);
                    break;
                case 3:
                    currentGoal.setIcon(R.drawable.ic_fruits_veggies);
                    break;
                case 4:
                    currentGoal.setIcon(R.drawable.ic_weight_loss);
                    break;
                default:
                    currentGoal.setIcon(R.drawable.ic_running);
                    break;
            }
            try {
                Number num = NumberFormat.getInstance().parse(unitSpinnerAdapter.getItem(unitSpinner.getSelectedItemPosition()));
                currentGoal.setGoal(num.doubleValue());
                currentGoal.setCurrentLevel(num.doubleValue()/2);
            } catch (Exception e){
                Log.e("Exception", e.getMessage()+"");
            }
        }
        switch (currentSeekIndex) {
            case 1:
                seekBtn1.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn1.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));

                break;

            case 2:
                seekBtn2.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn2.setTextColor(getColor(R.color.colorWhite));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


            case 3:

                seekBtn3.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn3.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


            case 4:
                seekBtn4.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn4.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


        }
        PopulateSpinner();
    }

    private Goal getCurrentGoal() {
        if (currentGoal != null) {
            return currentGoal;
        } else {
            currentGoal = JsondataUtil.getGoalByID(getApplicationContext(), 1);
            currentGoal.setTitle(currentSeekIndex);
            currentGoal.setUnit(currentSeekIndex);
            currentGoal.setColorCode(JsondataUtil.getGoalColorById(getApplicationContext(), currentSeekIndex));
            switch (currentSeekIndex) {
                case 1:
                    currentGoal.setIcon(R.drawable.ic_running);
                    break;
                case 2:
                    currentGoal.setIcon(R.drawable.ic_water_bottle);
                    break;
                case 3:
                    currentGoal.setIcon(R.drawable.ic_fruits_veggies);
                    break;
                case 4:
                    currentGoal.setIcon(R.drawable.ic_weight_loss);
                    break;
                default:
                    currentGoal.setIcon(R.drawable.ic_running);
                    break;
            }
            try {
                Number num = NumberFormat.getInstance().parse(unitSpinnerAdapter.getItem(unitSpinner.getSelectedItemPosition()));
                currentGoal.setGoal(num.doubleValue());
                currentGoal.setCurrentLevel(num.doubleValue()/2);
            } catch (Exception e){
                Log.e("Exception", e.getMessage()+"");
            }
            return currentGoal;

        }
    }
}
