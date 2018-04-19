package com.topcoder.vakidney.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.GoogleFitUtil;
import com.topcoder.vakidney.util.ImagePicker;
import com.topcoder.vakidney.constant.DiseaseCategory;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * A simple {@link Fragment} subclass.
 * It is used to show the overall profile information for a patient using the app. All the data displayed here are fully customizeable
 */
public class MyProfileFragment extends Fragment {

    public final static int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 0x00000002;

    private TextView tvName, tvBirthDate, tvAge, tvHeight, tvWeight, tvDialysis, tvDiseaseCategory, tvSetupGoals, tvBiometricDevice;
    private UserData currentUserData;
    private RoundedImageView profileImageView;
    private Bitmap profileBitmap = null;
    private RelativeLayout profileFieldAge, profileFieldHeight, profileFieldWeight, profileFieldDialysis, profileFieldDisease, profileFieldGoal, profileFieldBiometric;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    private int birthYear = 1960, birthMonth = 1, birthDay = 1;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        currentUserData = UserData.get();
        initView(view);
        addFieldListeners();
        populateFields();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (currentUserData != null && currentUserData.isBiometric()) {
            getWeigthFromGoogleFit();
            getHeightFromGoogleFit();
        }
    }

    private void addFieldListeners() {
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
                startActivityForResult(chooseImageIntent, 1);
            }
        });
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthYear = year;
                birthMonth = monthOfYear+1;
                birthDay = dayOfMonth;
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(YEAR, year);
                newCalendar.set(MONTH, monthOfYear);
                newCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                currentUserData.setAge(Math.abs(getDiffYears(myCalendar.getTime(), newCalendar.getTime())));
                currentUserData.setBirthday(newCalendar.getTimeInMillis());
                populateFields();
            }
        };
        profileFieldAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), date, birthYear, birthMonth,
                        birthDay);
                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                pickerDialog.show();
            }
        });

        profileFieldHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout LL = new LinearLayout(MyProfileFragment.this.getActivity());
                LL.setOrientation(LinearLayout.HORIZONTAL);

                final NumberPicker aNumberPickerFeet = new NumberPicker(MyProfileFragment.this.getActivity());
                aNumberPickerFeet.setMaxValue(8);
                aNumberPickerFeet.setMinValue(1);
                if (currentUserData.getHeightFeet() > 0) {
                    aNumberPickerFeet.setValue(currentUserData.getHeightFeet());
                }
                else {
                    aNumberPickerFeet.setValue(5);
                }

                final NumberPicker aNumberPickerInch = new NumberPicker(MyProfileFragment.this.getActivity());
                aNumberPickerInch.setMaxValue(11);
                aNumberPickerInch.setMinValue(0);
                if (currentUserData.getHeightInch() > 0) {
                    aNumberPickerInch.setValue(currentUserData.getHeightInch());
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                params.gravity = Gravity.CENTER;
                params.setMargins(
                        0,
                        (int) getActivity().getResources().getDimension(R.dimen.app_standard_padding),
                        0,
                        0);

                LinearLayout.LayoutParams feetParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                feetParams.weight = 1;
                feetParams.gravity = Gravity.CENTER;

                LinearLayout.LayoutParams inchParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                inchParams.weight = 1;
                inchParams.gravity = Gravity.CENTER;

                LinearLayout llFeet = new LinearLayout(MyProfileFragment.this.getActivity());
                llFeet.setOrientation(LinearLayout.VERTICAL);
                llFeet.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView tvFeet = new TextView(MyProfileFragment.this.getActivity());
                tvFeet.setText("Feet");
                tvFeet.setGravity(Gravity.CENTER);
                tvFeet.setLayoutParams(feetParams);

                llFeet.addView(tvFeet);
                llFeet.addView(aNumberPickerFeet);

                LinearLayout llInch = new LinearLayout(MyProfileFragment.this.getActivity());
                llInch.setOrientation(LinearLayout.VERTICAL);

                TextView tvInch = new TextView(MyProfileFragment.this.getActivity());
                tvInch.setLayoutParams(inchParams);
                tvInch.setGravity(Gravity.CENTER);
                tvInch.setText("Inch(es)");

                llInch.addView(tvInch);
                llInch.addView(aNumberPickerInch);

                LL.setLayoutParams(params);
                LL.addView(llFeet, feetParams);
                LL.addView(llInch, inchParams);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyProfileFragment.this.getActivity());
                alertDialogBuilder.setTitle("Please select your height");
                alertDialogBuilder.setView(LL);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        currentUserData.setHeightFeet(aNumberPickerFeet.getValue());
                                        currentUserData.setHeightInch(aNumberPickerInch.getValue());
                                        populateFields();
                                        if (currentUserData.isBiometric()) {
                                            GoogleFitUtil.insertHeight(
                                                    getActivity(),
                                                    aNumberPickerFeet.getValue(),
                                                    aNumberPickerInch.getValue());
                                        }
                                    }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        profileFieldWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showNumberPickerDialog(getActivity(), "Please select your current weight (Pounds)", new DialogManager.OnNumberPicked() {
                    @Override
                    public void NumberPicked(int number) {
                        currentUserData.setWeight(number);
                        populateFields();
                        if (currentUserData.isBiometric()) {
                            GoogleFitUtil.insertWeight(getActivity(), number);
                        }
                    }
                }, 100, 400, currentUserData.getWeight());
            }
        });

        profileFieldDialysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(getActivity(), "Are You In Dialysis?", "Yes", "No", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        currentUserData.setDialysis(true);
                        populateFields();
                    }
                }, new DialogManager.OnNoClicked() {
                    @Override
                    public void NoClicked() {
                        currentUserData.setDialysis(false);
                        populateFields();
                    }
                });
            }
        });


        profileFieldGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(getActivity(), "Do you want to setup Goals?", "Yes", "No", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        currentUserData.setSetupgoals(true);
                        populateFields();
                    }
                }, new DialogManager.OnNoClicked() {
                    @Override
                    public void NoClicked() {
                        currentUserData.setSetupgoals(false);
                        populateFields();
                    }
                });
            }
        });

        profileFieldBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBiometricPermissionDialog();
            }
        });

        profileFieldDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder b = new AlertDialog.Builder(MyProfileFragment.this.getActivity());
                b.setTitle("Select Category");
                b.setItems(DiseaseCategory.LABELS, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentUserData.setDiseaseCategory(which);
                        populateFields();
                        dialog.dismiss();
                    }

                });

                b.show();
            }
        });
    }

    /**
     * Ask user to connect the app to Google Fit
     */
    private void showBiometricPermissionDialog() {
        DialogManager.showYesNoDialog(
                getActivity(),
                "Do you want to add biometric Device?",
                "Yes",
                "No",
                new DialogManager.OnYesClicked() {
            @Override
            public void YesClicked() {
                initGoogleFit();
            }
        }, new DialogManager.OnNoClicked() {
            @Override
            public void NoClicked() {
                currentUserData.setBiometric(false);
                populateFields();
            }
        });
    }

    /**
     * Populates respective Fields
     */
    private void populateFields() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentUserData.getBirthday());
        birthYear = calendar.get(Calendar.YEAR);
        birthMonth = calendar.get(Calendar.MONTH);
        birthDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvName.setText(currentUserData.getFullname());
        tvAge.setText(currentUserData.getAge() + " years");
        tvHeight.setText(
                currentUserData.getHeightFeet() + " feet " +
                currentUserData.getHeightInch() + " inch(es)");
        tvWeight.setText(currentUserData.getWeight() + " pounds");
        tvBirthDate.setText(getFormattedDate(birthYear, birthMonth, birthDay));
        if (currentUserData.isDialysis()) {
            tvDialysis.setText("Yes");
        } else {
            tvDialysis.setText("No");
        }
        tvDiseaseCategory.setText(
                DiseaseCategory.LABELS[currentUserData.getDiseaseCategory()]
        );
        if (currentUserData.isSetupgoals()) {
            tvSetupGoals.setText("Yes");
        } else {
            tvSetupGoals.setText("No");
        }
        if (currentUserData.isBiometric()) {
            tvBiometricDevice.setText("Yes");
        } else {
            tvBiometricDevice.setText("No");
        }
        currentUserData.save();
    }

    /**
     * Initializes The View
     *
     * @param view This View is required to find all views in this fragment/Activity
     */
    private void initView(View view) {
        profileImageView = view.findViewById(R.id.profileImageView);
        tvName = view.findViewById(R.id.tvName);
        tvBirthDate = view.findViewById(R.id.tvBirthDate);
        tvAge = view.findViewById(R.id.tvAge);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvDialysis = view.findViewById(R.id.tvDialysis);
        tvDiseaseCategory = view.findViewById(R.id.tvDiseaseCategory);
        tvSetupGoals = view.findViewById(R.id.tvSetupGoals);
        tvBiometricDevice = view.findViewById(R.id.tvBiometricDevice);
        profileFieldAge = view.findViewById(R.id.profileFieldAge);
        profileFieldHeight = view.findViewById(R.id.profileFieldHeight);
        profileFieldWeight = view.findViewById(R.id.profileFieldWeight);
        profileFieldDialysis = view.findViewById(R.id.profileFieldDialysis);
        profileFieldDisease = view.findViewById(R.id.profileFieldDisease);
        profileFieldGoal = view.findViewById(R.id.profileFieldGoals);
        profileFieldBiometric = view.findViewById(R.id.profileFieldBiometric);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                profileBitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                profileImageView.setImageBitmap(profileBitmap);
                // TODO use bitmap
                break;
            case GOOGLE_FIT_PERMISSIONS_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    currentUserData.setBiometric(true);
                    getWeigthFromGoogleFit();
                    getHeightFromGoogleFit();
                    GoogleFitUtil.getDistance(
                            getActivity(),
                            GoogleFitUtil.sDefaultGetDistanceSuccessListener,
                            null
                    );
                    GoogleFitUtil.getStep(
                            getActivity(),
                            GoogleFitUtil.sDefaultGetStepSuccessListener,
                            null
                    );
                    populateFields();
                }
            }
            break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private String getFormattedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, year);
        calendar.set(MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        DateFormatSymbols dfsFr = new DateFormatSymbols(Locale.FRENCH);
        String[] oldMonths = dfsFr.getShortMonths();
        String[] newMonths = new String[oldMonths.length];
        for (int i = 0, len = oldMonths.length; i < len; ++i) {
            String oldMonth = oldMonths[i];

            if (oldMonth.endsWith(".")) {
                newMonths[i] = oldMonth.substring(0, oldMonths[i].length() - 1);
            } else {
                newMonths[i] = oldMonth;
            }
        }
        DateFormat dfEn = new SimpleDateFormat(
                "dd MMM yyyy", Locale.ENGLISH);
        return dfEn.format(calendar.getTime());
    }

    private int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    private void initGoogleFit() {
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_NUTRITION, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_NUTRITION, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_READ)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this.getActivity()), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this.getActivity()),
                    fitnessOptions);
        } else {
            currentUserData.setBiometric(true);
            getWeigthFromGoogleFit();
            getHeightFromGoogleFit();
            GoogleFitUtil.getDistance(
                    getActivity(),
                    GoogleFitUtil.sDefaultGetDistanceSuccessListener,
                    null
            );
            GoogleFitUtil.getStep(
                    getActivity(),
                    GoogleFitUtil.sDefaultGetStepSuccessListener,
                    null
            );
            populateFields();
        }
    }

    private void getWeigthFromGoogleFit() {
        GoogleFitUtil.getWeight(
                getActivity(),
                new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        float weight = 0;
                        List<DataPoint> dps = dataReadResponse
                                .getDataSet(DataType.TYPE_WEIGHT)
                                .getDataPoints();
                        for (DataPoint dp : dps) {
                            Value value = dp.getValue(Field.FIELD_WEIGHT);
                            weight = value.asFloat() / 0.453592f;
                        }
                        if (weight != 0) {
                            currentUserData.setWeight((int) weight);
                            currentUserData.save();
                            populateFields();
                        }
                    }
                },
                null
        );
    }

    private void getHeightFromGoogleFit() {
        GoogleFitUtil.getHeight(
                getActivity(),
                new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        float height = 0;
                        List<DataPoint> dps = dataReadResponse
                                .getDataSet(DataType.TYPE_HEIGHT)
                                .getDataPoints();
                        for (DataPoint dp : dps) {
                            Value value = dp.getValue(Field.FIELD_HEIGHT);
                            height = value.asFloat() * 3.28084f;
                        }
                        if (height != 0) {
                            int feet = (int) height;
                            int inches = (int) ((height - (float) feet) * 12);
                            currentUserData.setHeightFeet(feet);
                            currentUserData.setHeightInch(inches);
                            currentUserData.save();
                            populateFields();
                        }
                    }
                },
                null
        );
    }

}
