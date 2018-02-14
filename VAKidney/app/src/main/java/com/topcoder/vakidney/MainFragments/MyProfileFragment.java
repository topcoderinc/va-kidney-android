package com.topcoder.vakidney.MainFragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.ImagePicker;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * A simple {@link Fragment} subclass.
 * It is used to show the overall profile information for a patient using the app. All the data displayed here are fully customizeable
 */
public class MyProfileFragment extends Fragment {

    private TextView tvName, tvBirthDate, tvAge, tvHeight, tvWeight, tvDialysis, tvDiseaseCategory, tvSetupGoals, tvAvatar, tvBiometricDevice;
    private UserData currentUserData;
    private RoundedImageView profileImageView;
    private Bitmap profileBitmap = null;
    private RelativeLayout profileFieldAge, profileFieldHeight, profileFieldWeight, profileFieldDialysis, profileFieldDisease, profileFieldGoal, profileFieldAvatar, profileFieldBiometric;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;


    private int birthYear = 1960, birthMonth = 1, birthDay = 1;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        currentUserData = JsondataUtil.getUserData(getContext());
        initView(view);
        addFieldListeners();
        populateFields();
        return view;
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
                populateFields();
            }
        };
        profileFieldAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(YEAR), myCalendar.get(MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        profileFieldHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showNumberPickerDialog(getActivity(), "Please select your height (In Cm)", new DialogManager.OnNumberPicked() {
                    @Override
                    public void NumberPicked(int number) {
                        currentUserData.setHeight(number);
                        populateFields();
                    }
                }, 100, 250, currentUserData.getHeight());
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

        profileFieldAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(getActivity(), "Do you want to Setup Avatar?", "Yes", "No", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        currentUserData.setAvatar(true);
                        populateFields();
                    }
                }, new DialogManager.OnNoClicked() {
                    @Override
                    public void NoClicked() {
                        currentUserData.setAvatar(false);
                        populateFields();
                    }
                });
            }
        });



        profileFieldBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(getActivity(), "Do you want to add biometric Device?", "Yes", "No", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        currentUserData.setBiometric(true);
                        populateFields();
                    }
                }, new DialogManager.OnNoClicked() {
                    @Override
                    public void NoClicked() {
                        currentUserData.setBiometric(false);
                        populateFields();
                    }
                });
            }
        });

        profileFieldDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showFieldDialog(getActivity(), "Enter Disease Category", new DialogManager.OnOkClicked() {
                    @Override
                    public void onclicked(String fieldString) {
                        currentUserData.setDiseaseCategory(fieldString);
                        populateFields();
                    }
                }, currentUserData.getDiseaseCategory());
            }
        });
    }

    /**
     * Populates respective Fields
     */
    private void populateFields() {
        tvName.setText(currentUserData.getFullname());
        tvAge.setText(currentUserData.getAge() + " years");
        tvHeight.setText(currentUserData.getHeight() + " cm");
        tvWeight.setText(currentUserData.getWeight() + " pounds");
        tvBirthDate.setText(getFormattedDate(birthYear, birthMonth, birthDay));
        if (currentUserData.isDialysis()) {
            tvDialysis.setText("Yes");
        } else {
            tvDialysis.setText("No");
        }
        tvDiseaseCategory.setText(currentUserData.getDiseaseCategory());
        if (currentUserData.isSetupgoals()) {
            tvSetupGoals.setText("Yes");
        } else {
            tvSetupGoals.setText("No");
        }
        if (currentUserData.isAvatar()) {
            tvAvatar.setText("Yes");
        } else {
            tvAvatar.setText("No");
        }
        if (currentUserData.isBiometric()) {
            tvBiometricDevice.setText("Yes");
        } else {
            tvBiometricDevice.setText("No");
        }
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
        tvAvatar = view.findViewById(R.id.tvAvatar);
        tvBiometricDevice = view.findViewById(R.id.tvBiometricDevice);
        profileFieldAge = view.findViewById(R.id.profileFieldAge);
        profileFieldHeight = view.findViewById(R.id.profileFieldHeight);
        profileFieldWeight = view.findViewById(R.id.profileFieldWeight);
        profileFieldDialysis = view.findViewById(R.id.profileFieldDialysis);
        profileFieldDisease = view.findViewById(R.id.profileFieldDisease);
        profileFieldGoal = view.findViewById(R.id.profileFieldGoals);
        profileFieldAvatar = view.findViewById(R.id.profileFieldAvatar);
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
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private String getFormattedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, year);
        calendar.set(MONTH, month - 1);
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
                "dd MMM YYYY", Locale.ENGLISH);
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

}
