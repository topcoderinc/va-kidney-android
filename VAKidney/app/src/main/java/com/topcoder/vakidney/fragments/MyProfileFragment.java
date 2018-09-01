package com.topcoder.vakidney.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;

import com.topcoder.vakidney.constant.Comorbidities;
import com.topcoder.vakidney.databinding.DialogComorbidities1Binding;
import com.topcoder.vakidney.databinding.FragmentMyProfileBinding;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.GoalGenerator;
import com.topcoder.vakidney.util.GoogleFitUtil;
import com.topcoder.vakidney.util.ImagePicker;
import com.topcoder.vakidney.constant.DiseaseCategory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final String TAG = "MyProfileFragment";
    private static final String PROFILE_IMAGE_PATH = "/profile_image.png";

    private UserData currentUserData;
    private Bitmap profileBitmap = null;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    boolean[] checkedItems;

    private int birthYear = 1960, birthMonth = 1, birthDay = 1;
    private FragmentMyProfileBinding binder;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false);
        final View view = binder.getRoot();


        currentUserData = UserData.get();
        checkedItems = new boolean[]{currentUserData.isComorbiditiesHypertension(),
                currentUserData.isComorbiditiesDiabetesmellitus(),
                currentUserData.isComorbiditiesCongestiveheartfailure()};

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
        binder.profileImageView.setOnClickListener(new View.OnClickListener() {
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
                birthMonth = monthOfYear + 1;
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
        binder.profileFieldAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), date, birthYear, birthMonth,
                        birthDay);
                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                pickerDialog.show();
            }
        });

        binder.profileFieldHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout LL = new LinearLayout(MyProfileFragment.this.getActivity());
                LL.setOrientation(LinearLayout.HORIZONTAL);

                final NumberPicker aNumberPickerFeet = new NumberPicker(MyProfileFragment.this.getActivity());
                aNumberPickerFeet.setMaxValue(8);
                aNumberPickerFeet.setMinValue(1);
                if (currentUserData.getHeightFeet() > 0) {
                    aNumberPickerFeet.setValue(currentUserData.getHeightFeet());
                } else {
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

        binder.profileFieldName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                edittext.setText(currentUserData.getFullname());
                alert.setTitle("Enter Your Full Name");

                alert.setView(edittext);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.setNegativeButton("Cancel", null);

                final AlertDialog dialog = alert.create();
                dialog.show();

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fullname = edittext.getText().toString();
                        currentUserData.setFullname(fullname);
                        populateFields();
                        dialog.dismiss();
                    }
                });
            }
        });


        binder.profileFieldWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                edittext.setKeyListener(DigitsKeyListener.getInstance(true, false));
                edittext.setText(currentUserData.getWeight() + "");
                alert.setTitle("Enter Your Weight");

                alert.setView(edittext);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.setNegativeButton("Cancel", null);

                final AlertDialog dialog = alert.create();
                dialog.show();

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String weight = edittext.getText().toString();
                        if(weight.length()>3) {
                            Drawable errorImage=getResources().getDrawable(R.drawable.bg_login_field_error);
                            errorImage.setTint(Color.RED);
                            edittext.setError("Enter Weight between 0-999",errorImage);
                            return;
                        }
                        currentUserData.setWeight(Integer.parseInt(weight));
                        populateFields();

                        if (currentUserData.isBiometric()) {
                            GoogleFitUtil.insertWeight(getActivity(), Integer.parseInt(weight));
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        binder.profileFieldDialysis.setOnClickListener(new View.OnClickListener() {
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


        binder.profileFieldBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBiometricPermissionDialog();
            }
        });

        binder.profileFieldDisease.setOnClickListener(new View.OnClickListener() {
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
        binder.profileFieldComorbidities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayComorbiditiesDialog();
            }
        });

        binder.btnResetGoals
                .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (currentUserData.getDiseaseCategory() < 0) {
                                Toast.makeText(getActivity(), "No goals to reset", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            final List<Goal> goals = Goal.getAllWithoutComorbidities(currentUserData.getDiseaseCategory(),
                                    currentUserData.isDialysis());
                            if (goals.size() > 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Are you sure to reset all goals?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                GoalGenerator.removeGoals();
                                                Toast.makeText(getActivity(), "Goals have been reset",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("No", null);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                Toast.makeText(getActivity(), "No goals to reset", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                );
        binder.btnGenerateGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUserData.getDiseaseCategory() < 0) {
                    Toast.makeText(getActivity(), "Select disease category to generate goals.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final List<Goal> goals = Goal.getAllWithoutComorbidities(currentUserData.getDiseaseCategory(), currentUserData.isDialysis());
                if (goals.size() > 0) {
                    Toast.makeText(getActivity(), "Goals have been already generated", Toast.LENGTH_SHORT).show();
                } else {
                    GoalGenerator.generateGoals();
                    Toast.makeText(getActivity(), "Goals have been generated", Toast.LENGTH_SHORT).show();
                }
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
        profileBitmap = getProfileImage();
        if (profileBitmap != null) {
            Glide.with(getContext()).load(profileBitmap).apply(RequestOptions.circleCropTransform()).into(binder.profileImageView);
        } else {
            Glide.with(getContext()).load(R.drawable.profile_bg).apply(RequestOptions.circleCropTransform()).into(binder.profileImageView);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentUserData.getBirthday());
        birthYear = calendar.get(Calendar.YEAR);
        birthMonth = calendar.get(Calendar.MONTH);
        birthDay = calendar.get(Calendar.DAY_OF_MONTH);

        binder.tvName.setText(currentUserData.getFullname());
        binder.tvNameEdit.setText(currentUserData.getFullname());

        String ageStr = (currentUserData.getAge() > 0 ? currentUserData.getAge() : "-")
                + " years";
        binder.tvAge.setText(ageStr);

        String heightFeetStr = currentUserData.getHeightFeet() > 0 ?
                String.valueOf(currentUserData.getHeightFeet()) : "-";

        String heightInchStr = currentUserData.getHeightInch() > 0 ?
                String.valueOf(currentUserData.getHeightInch()) : "-";

        if (currentUserData.getHeightFeet() > 0 && currentUserData.getHeightInch() == 0) {
            heightInchStr = "0";
        }

        binder.tvHeight.setText(
                heightFeetStr + " feet " +
                heightInchStr + " inch(es)");

        String weightStr =
                (currentUserData.getWeight() > 0 ? currentUserData.getWeight() : "-")
                        + " pounds";

        binder.tvWeight.setText(weightStr);
        binder.tvBirthDate.setText(getFormattedDate(birthYear, birthMonth, birthDay));
        if (currentUserData.isDialysis()) {
            binder.tvDialysis.setText("Yes");
        } else {
            binder.tvDialysis.setText("No");
        }
        if (currentUserData.getDiseaseCategory() >=0 ) {
            binder.tvDiseaseCategory.setText(
                    DiseaseCategory.LABELS[currentUserData.getDiseaseCategory()]
            );
        }
        else {
            binder.tvDiseaseCategory.setText("-");
        }
        if (currentUserData.isBiometric()) {
            binder.tvBiometricDevice.setText("Yes");
        } else {
            binder.tvBiometricDevice.setText("No");
        }
        populateComorbiditesFields();
        currentUserData.save();
    }

    private void populateComorbiditesFields() {
        StringBuilder comorbities = new StringBuilder();
        if (currentUserData.isComorbiditiesHypertension()) {
            comorbities.append(Comorbidities.ComorbiditiesLabels[Comorbidities.Comorbidities_Hypertension]);
        }
        if (currentUserData.isComorbiditiesDiabetesmellitus()) {
            if (!comorbities.toString().isEmpty())
                comorbities.append(",\n");
            comorbities.append(Comorbidities.ComorbiditiesLabels[Comorbidities.Comorbidities_Diabetesmellitus]);
        }
        if (currentUserData.isComorbiditiesCongestiveheartfailure()) {
            if (!comorbities.toString().isEmpty())
                comorbities.append(",\n");
            comorbities.append(Comorbidities.ComorbiditiesLabels[Comorbidities.Comorbidities_Congestiveheartfailure]);
        }
        binder.tvComorbidities.setText(comorbities.toString());
        if (comorbities.toString().isEmpty())
            binder.tvComorbidities.setText("No");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                profileBitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                if (profileBitmap != null) {
                    storeImage(profileBitmap);
                    Glide.with(getContext()).load(profileBitmap).apply(RequestOptions.circleCropTransform()).into(binder.profileImageView);
                }
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
        FitnessOptions fitnessOptions = GoogleFitUtil.getFitnessOptions();
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

    private void displayComorbiditiesDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.dialog_comorbidities1, null);
        final DialogComorbidities1Binding binding=DialogComorbidities1Binding.bind(sheetView);
        dialog.setContentView(sheetView);
        //Check the fields
        binding.checkboxHypertension.setChecked(currentUserData.isComorbiditiesHypertension());
        binding.checkboxDiabetesMellitus.setChecked(currentUserData.isComorbiditiesDiabetesmellitus());
        binding.checkboxCongestiveheartfailure.setChecked(currentUserData.isComorbiditiesCongestiveheartfailure());
        //Create On click listeners
        binding.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItems[0]=binding.checkboxHypertension.isChecked();
                        checkedItems[1]=binding.checkboxDiabetesMellitus.isChecked();
                                checkedItems[2]=binding.checkboxCongestiveheartfailure.isChecked();
                currentUserData.setComorbiditiesHypertension(checkedItems[0]);
                currentUserData.setComorbiditiesDiabetesmellitus(checkedItems[1]);
                currentUserData.setComorbiditiesCongestiveheartfailure(checkedItems[2]);
                GoalGenerator.addOrRemoveComorbidities(checkedItems);
                populateFields();
                dialog.dismiss();
            }
        });
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
   }

    private void storeImage(Bitmap image) {
        File pictureFile = new File(getActivity().getFilesDir() + PROFILE_IMAGE_PATH);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private Bitmap getProfileImage() {
        Bitmap bitmap = null;
        File f = new File(getActivity().getFilesDir() + PROFILE_IMAGE_PATH);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
