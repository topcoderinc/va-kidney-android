package com.topcoder.vakidney;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.topcoder.vakidney.databinding.ActivityAddNewMealBinding;
import com.topcoder.vakidney.databinding.ItemAddMealdrugBinding;
import com.topcoder.vakidney.model.DrugInteraction;
import com.topcoder.vakidney.model.Meal;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.util.ImagePicker;
import com.topcoder.vakidney.util.ServiceCallUtil;
import com.topcoder.vakidney.popup.AddMealDrugPopup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class is used to add a new meal
 */
public class AddNewMealActivity extends AppCompatActivity implements
        View.OnClickListener,
        AddMealDrugPopup.AddMealDrugPopupListener {

    private final static int REQUEST_CODE_PICK_IMAGE = 1;

    private final static Map<String, Integer> MEAL_TYPE_INDEX = new HashMap<>();

    static {
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_BREAKFAST, 1);
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_LUNCH, 2);
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_DINNER, 3);
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_SNACK, 4);
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_CASUAL, 5);
    }


    private int currentSeekIndex = 1;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private Meal mMeal;
    private DrugInteraction mDrugInteraction;
    private List<MealDrug> mAddedMealDrugs = new ArrayList<>();
    public static Activity activity;
    ActivityAddNewMealBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_new_meal);
        activity = this;

        binder.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateHome(false);
            }
        });
        SetupSeekBar();

        binder.btnAddNewMeal.setEnabled(false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        binder.removeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.addImageLayout.setVisibility(View.INVISIBLE);
                binder.addedImage.setImageBitmap(null);
                if (mMeal != null && mMeal.getPhotoUrl() != null) {
                    mMeal.setPhotoUrl(null);
                }
            }
        });
        binder.addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionThenPickPhoto();
            }
        });

        myCalendar = Calendar.getInstance();
        binder.tveMealDate.setText(myCalendar.get(Calendar.MONTH) + 1
                + "/" + myCalendar.get(Calendar.DAY_OF_MONTH)
                + "/" + myCalendar.get(Calendar.YEAR));


        String input = myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE);
        DateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("KK:mm a", Locale.US);
        try {
            binder.tvMealTime.setText(outputFormat.format(inputFormat.parse(input)));
        } catch (ParseException e) {
            binder.tvMealTime.setText(input);
        }
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                binder.tveMealDate.setText(myCalendar.get(Calendar.MONTH) + 1
                        + "/" + myCalendar.get(Calendar.DAY_OF_MONTH)
                        + "/" + myCalendar.get(Calendar.YEAR));

            }

        };
        binder.dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatePickerDialog datePickerDialog=new DatePickerDialog(AddNewMealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
               datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
               datePickerDialog.show();
            }
        });

        binder.timeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewMealActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);

                        String input = selectedHour + ":" + selectedMinute;
                        DateFormat inputFormat = new SimpleDateFormat("HH:mm");
                        DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
                        try {
                            binder.tvMealTime.setText(outputFormat.format(inputFormat.parse(input)));
                        } catch (ParseException e) {
                            binder.tvMealTime.setText(input);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.updateTime(myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE));
                mTimePicker.show();
            }
        });

        binder.btnAddNewMeal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = mMeal.getType().substring(0, 1).toUpperCase()
                        + mMeal.getType().substring(1);
                mMeal.setName(name);
                mMeal.setDate(myCalendar.getTime());
                for (MealDrug mealDrug : mAddedMealDrugs) {
                    mealDrug.setMealId(mMeal.getMealId());
                    mealDrug.save();
                }
                mMeal.save();

                for (MealDrug mealDrug : mAddedMealDrugs) {
                    if (mealDrug.getType() == MealDrug.TYPE_MEAL) {
                        ServiceCallUtil.searchFoodRecommendation(
                                AddNewMealActivity.this,
                                mealDrug.getName());
                    }
                }

                if (mMeal.isHasDrug()) {
                    List<String> drugs = new ArrayList<>();
                    for (MealDrug mealDrug : mMeal.getMealDrugs()) {
                        if (mealDrug.getType() == MealDrug.TYPE_DRUG) drugs.add(mealDrug.getName());
                    }
                    if (drugs.size() > 0) {
                        String[] drugsStr = new String[drugs.size()];
                        drugs.toArray(drugsStr);
                        if (DrugInteraction.findByDrugs(drugsStr) == null) {

                            mDrugInteraction = new DrugInteraction();
                            mDrugInteraction.setDrugs(drugsStr);

                            ServiceCallUtil.searchDrugInteraction(
                                    AddNewMealActivity.this,
                                    mDrugInteraction);

                        }
                    }
                }
                NavigateHome(false);

            }

        });


        binder.tvAddMeal.setOnClickListener(this);
        binder.tvAddDrug.setOnClickListener(this);

        if (getIntent().hasExtra("meal")) {
            Meal meal = (Meal) getIntent().getSerializableExtra("meal");
            initSavedMeal(meal);
            binder.actionBarTitle.setText("Edit Meal");
        } else {
            mMeal = new Meal();
            mMeal.setMealId(System.currentTimeMillis());
            mMeal.setType(Meal.MEAL_TYPE_BREAKFAST);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.nexa_bold);
        binder.actionBarTitle.setTypeface(typeface);
    }

    private void initSavedMeal(Meal meal) {
        mMeal = Meal.find(Meal.class, "meal_id = ?", String.valueOf(meal.getMealId())).get(0);

        currentSeekIndex = MEAL_TYPE_INDEX.get(meal.getType());
        seekButton();

        for (int i = 0; i < mMeal.getMealDrugs().size(); i++) {
            addMealDrug(mMeal.getMealDrugs().get(i));
        }

        myCalendar.setTime(meal.getDate());

        String dateStr = new SimpleDateFormat("M/d/yyyy", Locale.US).format(meal.getDate());
        String timeStr = new SimpleDateFormat("KK:mm a", Locale.US).format(meal.getDate());

        binder.tveMealDate.setText(dateStr);
        binder.tvMealTime.setText(timeStr);

        binder.btnAddNewMeal.setText("Save Meal");
        binder.btnAddNewMeal.setEnabled(true);

        if (mMeal.getPhotoUrl() != null) {
            binder.addImageLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mMeal.getPhotoUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binder.addedImage);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                binder.addImageLayout.setVisibility(View.VISIBLE);
                String filePath = ImagePicker.getImagePathFromResult(this, resultCode, data);
                Glide.with(this)
                        .load(filePath)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binder.addedImage);
                mMeal.setPhotoUrl(filePath);
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
            intent.putExtra("meal", getCurrentMeal());
        }
        intent.putExtra("tag", MainActivity.TAG_FOOD);
        startActivity(intent);
    }

    /**
     * Initialize Top Seekbar and Sets up listener
     */
    private void SetupSeekBar() {

        binder.seekBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_BREAKFAST);
                currentSeekIndex = 1;
                seekButton();
            }
        });


        binder.seekBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_LUNCH);
                currentSeekIndex = 2;
                seekButton();
            }
        });

        binder.seekBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_DINNER);
                currentSeekIndex = 3;
                seekButton();
            }
        });

        binder.seekBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_SNACK);
                currentSeekIndex = 4;
                seekButton();
            }
        });


        binder.seekBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_CASUAL);
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
                binder.seekBtn1.setBackgroundResource(R.drawable.bg_seekbar_selected);
                binder.seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                binder.seekBtn1.setTextColor(getColor(R.color.colorWhite));
                binder.seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));

                break;

            case 2:
                binder.seekBtn2.setBackgroundResource(R.drawable.bg_seekbar_selected);
                binder.seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                binder.seekBtn2.setTextColor(getColor(R.color.colorWhite));
                binder.seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;

            case 3:

                binder.seekBtn3.setBackgroundResource(R.drawable.bg_seekbar_selected);
                binder.seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                binder.seekBtn3.setTextColor(getColor(R.color.colorWhite));
                binder.seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;


            case 4:
                binder.seekBtn4.setBackgroundResource(R.drawable.bg_seekbar_selected);
                binder.seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn5.setBackgroundColor(getColor(android.R.color.transparent));

                binder.seekBtn4.setTextColor(getColor(R.color.colorWhite));
                binder.seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn5.setTextColor(getColor(R.color.colorLightDarkGray));
                break;

            case 5:
                binder.seekBtn5.setBackgroundResource(R.drawable.bg_seekbar_selected);
                binder.seekBtn2.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn3.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn4.setBackgroundColor(getColor(android.R.color.transparent));
                binder.seekBtn1.setBackgroundColor(getColor(android.R.color.transparent));

                binder.seekBtn5.setTextColor(getColor(R.color.colorWhite));
                binder.seekBtn2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn3.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn4.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.seekBtn1.setTextColor(getColor(R.color.colorLightDarkGray));
                break;

        }
    }

    private Meal getCurrentMeal() {
        Meal meal = new Meal();
        switch (currentSeekIndex) {
            case 1:
                meal.setName(Meal.MEAL_TYPE_BREAKFAST);
                break;
            case 2:
                meal.setName(Meal.MEAL_TYPE_LUNCH);
                break;
            case 3:
                meal.setName(Meal.MEAL_TYPE_DINNER);
                break;
            case 4:
                meal.setName(Meal.MEAL_TYPE_SNACK);
                break;
            case 5:
                meal.setName(Meal.MEAL_TYPE_CASUAL);
                break;
        }
        meal.setDesc("Salad, pasta, pudding, grape");
        return meal;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddMeal: {
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        AddMealDrugPopup.POPUP_MODE_MEAL,
                        AddMealDrugPopup.POPUP_ACTION_ADD,
                        null, null);
                popup.setListener(AddNewMealActivity.this);
                popup.show();
                Window window = popup.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            break;
            case R.id.tvAddDrug: {
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        AddMealDrugPopup.POPUP_MODE_DRUG,
                        AddMealDrugPopup.POPUP_ACTION_ADD,
                        null, null);
                popup.setListener(AddNewMealActivity.this);
                popup.show();
                Window window = popup.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            break;
        }
    }

    @Override
    public void onAdded(MealDrug mealDrug) {
        addMealDrug(mealDrug);
        binder.btnAddNewMeal.setEnabled(true);
        if (mealDrug.getType() == MealDrug.TYPE_DRUG) {
            mMeal.setHasDrug(true);
        }

        mAddedMealDrugs.add(mealDrug);
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onDeleted(View parent, MealDrug deleted) {
        binder.llMealDrug.removeView(parent);
        if (mAddedMealDrugs != null && mAddedMealDrugs.contains(deleted)) {
            mAddedMealDrugs.remove(deleted);
        }

    }

    @Override
    public void onEdited(View parent, MealDrug mealDrug) {

        TextView tvName = parent.findViewById(R.id.tvName);
        TextView tvAmount = parent.findViewById(R.id.tvAmount);

        tvName.setText(mealDrug.getName());
        tvAmount.setText(mealDrug.getAmount() + " " + mealDrug.getUnit());
    }

    private void addMealDrug(MealDrug mealDrug) {
        final ItemAddMealdrugBinding itemAddMealdrugBinding=ItemAddMealdrugBinding.bind(
                LayoutInflater.from(this).inflate(R.layout.item_add_mealdrug, null));
        itemAddMealdrugBinding.getRoot().setClickable(true);
        itemAddMealdrugBinding.getRoot().setFocusable(true);
        itemAddMealdrugBinding.getRoot().setTag(mealDrug);
        itemAddMealdrugBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDrug editMealDrug = (MealDrug) view.getTag();
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        editMealDrug.getType(),
                        AddMealDrugPopup.POPUP_ACTION_EDIT,
                        editMealDrug, view);
                popup.setListener(AddNewMealActivity.this);
                popup.show();
                Window window = popup.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        });


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.app_standard_padding));

        binder.llMealDrug.addView(itemAddMealdrugBinding.getRoot(), layoutParams);

        itemAddMealdrugBinding.tvName.setText(mealDrug.getName());
        itemAddMealdrugBinding.tvAmount.setText(mealDrug.getAmount() + " " + mealDrug.getUnit());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoProfile();
                } else {
                    Toast.makeText(this,
                            "UserPermission is not set",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void pickPhotoProfile() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(AddNewMealActivity.this);
        startActivityForResult(chooseImageIntent, REQUEST_CODE_PICK_IMAGE);
    }

    private void checkPermissionThenPickPhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PICK_IMAGE);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                pickPhotoProfile();
            }
        } else {
            pickPhotoProfile();
        }
    }
}
