package com.topcoder.vakidney;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.makeramen.roundedimageview.RoundedImageView;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.ImagePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used to add a new meal
 */
public class AddNewMealActivity extends AppCompatActivity {


    private LinearLayout bottomMenu1, bottomMenu2, bottomMenu3, bottomMenu4, bottomMenu5;
    private AppCompatImageView backBtn;
    private Button seekBtn1, seekBtn2, seekBtn3, seekBtn4, seekBtn5;
    private int currentSeekIndex = 1;
    private TextView tvMealDate, tvMealTime;
    private TextView tvChangeDate, tvChangeTime;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private EditText addMealOrLiquidField, amountField;
    private Spinner unitSpinner;
    private LinearLayout addImageBtn;
    private RoundedImageView addedImage;
    private AppCompatImageView btnRemoveImage;
    private String[] unitSpinerItems = {"Select", "Item 1", "Item 2", "Item 3"};
    private Button btnAddNewMeal;
    private TextView mealOrLiquidFieldErrorTv, amountFieldErrorTv, unitSpinnerTv;
    private LinearLayout dateLayout, timeLayout;
    private RelativeLayout addedImageLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateHome(false);
            }
        });
        SetupSeekBar();
        SetupBotomMenu();
        addedImageLayout = findViewById(R.id.addImageLayout);
        dateLayout = findViewById(R.id.dateLayout);
        timeLayout = findViewById(R.id.timeLayout);
        mealOrLiquidFieldErrorTv = findViewById(R.id.mealOrliquidFieldErrorTv);
        amountFieldErrorTv = findViewById(R.id.amountFieldErrorTv);
        unitSpinnerTv = findViewById(R.id.unitSpinnerErroTv);
        btnAddNewMeal = findViewById(R.id.btnAddNewMeal);
        btnAddNewMeal.setEnabled(false);
        addMealOrLiquidField = findViewById(R.id.mealOrliquidField);
        amountField = findViewById(R.id.amountField);
        unitSpinner = findViewById(R.id.unitSpinner);
        addImageBtn = findViewById(R.id.addPhotoBtn);
        addedImage = findViewById(R.id.addedImage);
        btnRemoveImage = findViewById(R.id.removeImageBtn);
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(AddNewMealActivity.this, android.R.layout.simple_spinner_item, unitSpinerItems);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(gameKindArray);
        unitSpinner.setSelection(0);
        btnAddNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMealOrLiquidField.getText().toString().isEmpty() || amountField.getText().toString().isEmpty() || unitSpinner.getSelectedItemPosition() == 0) {
                    mealOrLiquidFieldErrorTv.setVisibility(View.GONE);
                    mealOrLiquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    amountFieldErrorTv.setVisibility(View.GONE);
                    amountFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    unitSpinnerTv.setVisibility(View.GONE);
                    unitSpinnerTv.setBackgroundResource(R.drawable.bg_round_white);
                    if (addMealOrLiquidField.getText().toString().isEmpty()) {
                        mealOrLiquidFieldErrorTv.setVisibility(View.VISIBLE);
                        mealOrLiquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (amountField.getText().toString().isEmpty()) {
                        amountFieldErrorTv.setVisibility(View.VISIBLE);
                        amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (unitSpinner.getSelectedItemPosition() == 0) {
                        unitSpinnerTv.setVisibility(View.VISIBLE);
                        unitSpinner.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                } else {
                    DialogManager.showOkDialog(AddNewMealActivity.this, "New Meal Added", new DialogManager.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            NavigateHome(true);
                        }
                    });
                }
            }
        });
        btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addedImageLayout.setVisibility(View.INVISIBLE);
                addedImage.setImageBitmap(null);
            }
        });
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(AddNewMealActivity.this);
                startActivityForResult(chooseImageIntent, 1);
            }
        });
        tvMealDate = findViewById(R.id.tveMealDate);
        tvMealTime = findViewById(R.id.tvMealTime);
        tvChangeDate = findViewById(R.id.tvChangeDate);
        tvChangeTime = findViewById(R.id.tvChangeTime);
        myCalendar = Calendar.getInstance();
        tvMealDate.setText(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar.get(Calendar.YEAR));

        String input = myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE);
        DateFormat inputFormat = new SimpleDateFormat("HH:mm");
        DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
        try {
            tvMealTime.setText(outputFormat.format(inputFormat.parse(input)));
        } catch (ParseException e) {
            tvMealTime.setText(input);
        }
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvMealDate.setText(myCalendar.get(Calendar.MONTH) + "/" + myCalendar.get(Calendar.DAY_OF_MONTH) + 1 + "/" + myCalendar.get(Calendar.YEAR));
            }

        };
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewMealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewMealActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        String input = selectedHour + ":" + selectedMinute;
                        DateFormat inputFormat = new SimpleDateFormat("HH:mm");
                        DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
                        try {
                            tvMealTime.setText(outputFormat.format(inputFormat.parse(input)));
                        } catch (ParseException e) {
                            tvMealTime.setText(input);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        EnableDisableAddMealButton();

    }

    private void EnableDisableAddMealButton() {
        addMealOrLiquidField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!amountField.getText().toString().isEmpty()) {
                        if (unitSpinner.getSelectedItemPosition() != 0) {
                            btnAddNewMeal.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!addMealOrLiquidField.getText().toString().isEmpty()) {
                        if (unitSpinner.getSelectedItemPosition() != 0) {
                            btnAddNewMeal.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (!addMealOrLiquidField.getText().toString().isEmpty()) {
                        if (!amountField.getText().toString().isEmpty()) {
                            btnAddNewMeal.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                addedImage.setImageBitmap(bitmap);
                addedImageLayout.setVisibility(View.VISIBLE);
                // TODO use bitmap
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        NavigateHome(false);

    }

    private void NavigateHome(boolean save) {
        finish();
        Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
        if (save) {
            intent.putExtra("addmeal", true);
            intent.putExtra("meal", getCurrentMeal().getBundle());
        }
        intent.putExtra("tag", MainActivity.TAG_FOOD);
        startActivity(intent);
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
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_HOME);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_CHART);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_MEDICATION);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_FOOD);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
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
        seekBtn5 = findViewById(R.id.seekBtn5);

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


        seekBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSeekIndex = 5;
                seekButton();
            }
        });
    }

    /**
     * Method to emulate seek button transition
     */
    private void seekButton() {
        switch (currentSeekIndex) {
            case 1:
                seekBtn1.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn1.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));

                break;

            case 2:
                seekBtn2.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn2.setTextColor(getColor(R.color.colorWhite));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


            case 3:

                seekBtn3.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn3.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


            case 4:
                seekBtn4.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn4.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;

            case 5:
                seekBtn5.setBackgroundResource(R.drawable.bg_seekbar_selected);
                seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));

                seekBtn5.setTextColor(getColor(R.color.colorWhite));
                seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


        }
    }

    private Meal getCurrentMeal() {
        Meal meal = new Meal();
        switch (currentSeekIndex) {
            case 1:
                meal.setName("Breakfast");
                break;
            case 2:
                meal.setName("Lunch");
                break;
            case 3:
                meal.setName("Dinner");
                break;
            case 4:
                meal.setName("Snack");
                break;
            case 5:
                meal.setName("Casual");
                break;
        }
        meal.setDesc("Salad, pasta, pudding, grape");
        return meal;
    }
}
