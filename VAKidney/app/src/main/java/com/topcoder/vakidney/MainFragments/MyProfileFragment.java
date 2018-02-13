package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    private TextView tvName, tvBirthDate, tvAge, tvHeight, tvWeight, tvDialysis, tvDiseaseCategory, tvSetupGoals, tvAvatar, tvBiometricDevice;
    private UserData currentUserData;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_profile, container, false);
        currentUserData= JsondataUtil.getUserData(getContext());
        initView(view);
        populateFields();
        return view;
    }

    /**
     * Populates respective Fields
     */
    private void populateFields() {
        tvName.setText(currentUserData.getFullname());
        tvAge.setText(currentUserData.getAge()+" Years");
        tvHeight.setText(currentUserData.getHeight()+" cm");
        tvWeight.setText(currentUserData.getWeight()+" Pounds");
        if(currentUserData.isDialysis()){
            tvDialysis.setText("Yes");
        }else{
            tvDialysis.setText("No");
        }
        tvDiseaseCategory.setText(currentUserData.getDiseaseCategory());
        if(currentUserData.isSetupgoals()) {
            tvSetupGoals.setText("Yes");
        }else{
            tvSetupGoals.setText("No");
        }
        if(currentUserData.isAvatar()){
            tvAvatar.setText("Yes");
        }else{
            tvAvatar.setText("No");
        }
        if(currentUserData.isBiometric()){
            tvBiometricDevice.setText("Yes");
        }else{
            tvBiometricDevice.setText("No");
        }
    }

    /**
     * Initializes The View
     * @param view
     */
    private void initView(View view) {
        tvName=view.findViewById(R.id.tvName);
        tvBirthDate=view.findViewById(R.id.tvBirthDate);
        tvAge=view.findViewById(R.id.tvAge);
        tvHeight=view.findViewById(R.id.tvHeight);
        tvWeight=view.findViewById(R.id.tvWeight);
        tvDialysis=view.findViewById(R.id.tvDialysis);
        tvDiseaseCategory=view.findViewById(R.id.tvDiseaseCategory);
        tvSetupGoals=view.findViewById(R.id.tvSetupGoals);
        tvAvatar=view.findViewById(R.id.tvAvatar);
        tvBiometricDevice=view.findViewById(R.id.tvBiometricDevice);
    }

}
