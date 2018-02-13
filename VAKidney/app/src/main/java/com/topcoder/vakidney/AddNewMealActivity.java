package com.topcoder.vakidney;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.ImagePicker;

import java.util.Calendar;

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

    private RelativeLayout addedImageLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_FOOD);
                startActivity(intent);
            }
        });
        SetupSeekBar();
        SetupBotomMenu();
        addedImageLayout=findViewById(R.id.addImageLayout);
        mealOrLiquidFieldErrorTv = findViewById(R.id.mealOrliquidFieldErrorTv);
        amountFieldErrorTv = findViewById(R.id.amountFieldErrorTv);
        unitSpinnerTv = findViewById(R.id.unitSpinnerErroTv);
        btnAddNewMeal = findViewById(R.id.btnAddNewMeal);
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
                    amountFieldErrorTv.setVisibility(View.GONE);
                    unitSpinnerTv.setVisibility(View.GONE);
                    if (addMealOrLiquidField.getText().toString().isEmpty()) {
                        mealOrLiquidFieldErrorTv.setVisibility(View.VISIBLE);
                    }
                    if (amountField.getText().toString().isEmpty()) {
                        amountFieldErrorTv.setVisibility(View.VISIBLE);
                    }
                    if (unitSpinner.getSelectedItemPosition() == 0) {
                        unitSpinnerTv.setVisibility(View.VISIBLE);
                    }
                } else {
                    DialogManager.showOkDialog(AddNewMealActivity.this, "New Meal Added", new DialogManager.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
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
        tvMealTime.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE));
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvMealDate.setText(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar.get(Calendar.YEAR));
            }

        };
        tvChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewMealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tvChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewMealActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvMealTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
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
        finish();
        Intent intent = new Intent(AddNewMealActivity.this, MainActivity.class);
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
     *Initialize Top Seekbar and Sets up listener
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
}
