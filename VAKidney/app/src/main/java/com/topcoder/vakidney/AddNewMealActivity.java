package com.topcoder.vakidney;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.topcoder.vakidney.Model.DrugInteraction;
import com.topcoder.vakidney.Model.FoodRecommendation;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.Model.MealDrug;
import com.topcoder.vakidney.Util.ImagePicker;
import com.topcoder.vakidney.Util.ServiceCallUtil;
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
        MEAL_TYPE_INDEX.put(Meal.MEAL_TYPE_CUSTOM, 5);
    }

    private LinearLayout bottomMenu1, bottomMenu2, bottomMenu3, bottomMenu4, bottomMenu5;
    private AppCompatImageView backBtn;
    private Button seekBtn1, seekBtn2, seekBtn3, seekBtn4, seekBtn5;
    private int currentSeekIndex = 1;
    private TextView tvMealDate, tvMealTime;
    private TextView tvChangeDate, tvChangeTime;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private LinearLayout addImageBtn;
    private RoundedImageView addedImage;
    private AppCompatImageView btnRemoveImage;
    private Button btnAddNewMeal;
    private LinearLayout dateLayout, timeLayout;
    private RelativeLayout addedImageLayout;

    private Meal mMeal;
    private DrugInteraction mDrugInteraction;
    private List<MealDrug> mAddedMealDrugs = new ArrayList<>();

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
        btnAddNewMeal = findViewById(R.id.btnAddNewMeal);
        btnAddNewMeal.setEnabled(false);
        addImageBtn = findViewById(R.id.addPhotoBtn);
        addedImage = findViewById(R.id.addedImage);
        btnRemoveImage = findViewById(R.id.removeImageBtn);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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
                checkPermissionThenPickPhoto();
            }
        });
        tvMealDate = findViewById(R.id.tveMealDate);
        tvMealTime = findViewById(R.id.tvMealTime);
        tvChangeDate = findViewById(R.id.tvChangeDate);
        tvChangeTime = findViewById(R.id.tvChangeTime);
        myCalendar = Calendar.getInstance();
        tvMealDate.setText(myCalendar.get(Calendar.MONTH) + 1
                + "/" + myCalendar.get(Calendar.DAY_OF_MONTH)
                + "/" + myCalendar.get(Calendar.YEAR));


        String input = myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE);
        DateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("KK:mm a", Locale.US);
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
                tvMealDate.setText(myCalendar.get(Calendar.MONTH) + 1
                        + "/" + myCalendar.get(Calendar.DAY_OF_MONTH)
                        + "/" + myCalendar.get(Calendar.YEAR));

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
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);

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

        btnAddNewMeal.setOnClickListener(new View.OnClickListener() {

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
                    for (MealDrug mealDrug: mMeal.getMealDrugs()) {
                        if (mealDrug.getType() == MealDrug.TYPE_DRUG) drugs.add(mealDrug.getName());
                    }
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
                NavigateHome(false);

            }

        });

        TextView tvAddMeal = findViewById(R.id.tvAddMeal);
        TextView tvAddDrug = findViewById(R.id.tvAddDrug);
        tvAddMeal.setOnClickListener(this);
        tvAddDrug.setOnClickListener(this);

        if(getIntent().hasExtra("meal")) {
            Meal meal = (Meal) getIntent().getSerializableExtra("meal");
            initSavedMeal(meal);
        }
        else {
            mMeal = new Meal();
            mMeal.setMealId(System.currentTimeMillis());
            mMeal.setType(Meal.MEAL_TYPE_BREAKFAST);
        }
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

        tvMealDate.setText(dateStr);
        tvMealTime.setText(timeStr);

        btnAddNewMeal.setText("Save Meal");
        btnAddNewMeal.setEnabled(true);

        if (mMeal.getPhotoUrl() != null) {
            addedImageLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mMeal.getPhotoUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(addedImage);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                addedImageLayout.setVisibility(View.VISIBLE);
                String filePath = ImagePicker.getImagePathFromResult(this, resultCode, data);
                Glide.with(this)
                        .load(filePath)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(addedImage);
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
                mMeal.setType(Meal.MEAL_TYPE_BREAKFAST);
                currentSeekIndex = 1;
                seekButton();
            }
        });


        seekBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_LUNCH);
                currentSeekIndex = 2;
                seekButton();
            }
        });

        seekBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_DINNER);
                currentSeekIndex = 3;
                seekButton();
            }
        });

        seekBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_SNACK);
                currentSeekIndex = 4;
                seekButton();
            }
        });


        seekBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeal.setType(Meal.MEAL_TYPE_CUSTOM);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddMeal: {
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        AddMealDrugPopup.POPUP_MODE_MEAL,
                        AddMealDrugPopup.POPUP_ACTION_ADD,
                        null);
                popup.setListener(AddNewMealActivity.this);
                popup.showAt(view);
            }
            break;
            case R.id.tvAddDrug: {
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        AddMealDrugPopup.POPUP_MODE_DRUG,
                        AddMealDrugPopup.POPUP_ACTION_ADD,
                        null);
                popup.setListener(AddNewMealActivity.this);
                popup.showAt(view);
            }
            break;
        }
    }

    @Override
    public void onAdded(MealDrug mealDrug) {
        addMealDrug(mealDrug);
        btnAddNewMeal.setEnabled(true);
        if(mealDrug.getType() == MealDrug.TYPE_DRUG) {
            mMeal.setHasDrug(true);
        }

        mAddedMealDrugs.add(mealDrug);
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onDeleted(AddMealDrugPopup parent) {
        LinearLayout layout = findViewById(R.id.llMealDrug);
        layout.removeView(parent.getParent());
    }

    @Override
    public void onEdited(AddMealDrugPopup parent, MealDrug mealDrug) {
        View view = parent.getParent();
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvAmount = view.findViewById(R.id.tvAmount);

        tvName.setText(mealDrug.getName());
        tvAmount.setText(mealDrug.getAmount() + " " + mealDrug.getUnit());
    }

    private void addMealDrug(MealDrug mealDrug) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_add_mealdrug, null);
        view.setClickable(true);
        view.setFocusable(true);
        view.setTag(mealDrug);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDrug editMealDrug = (MealDrug) view.getTag();
                AddMealDrugPopup popup = new AddMealDrugPopup(
                        AddNewMealActivity.this,
                        editMealDrug.getType(),
                        AddMealDrugPopup.POPUP_ACTION_EDIT,
                        editMealDrug);
                popup.setListener(AddNewMealActivity.this);
                popup.showAt(view);
            }
        });

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvAmount = view.findViewById(R.id.tvAmount);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.app_standard_padding));

        LinearLayout layout = findViewById(R.id.llMealDrug);
        layout.addView(view, layoutParams);

        tvName.setText(mealDrug.getName());
        tvAmount.setText(mealDrug.getAmount() + " " + mealDrug.getUnit());
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
        if (Build.VERSION.SDK_INT >= 23){
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
            }else{
                pickPhotoProfile();
            }
        }else {
            pickPhotoProfile();
        }
    }
}
