package com.topcoder.vakidney.fragments;


import java.util.List;
import java.util.Locale;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.databinding.FragmentWorkoutBinding;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.GoogleFitUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * It shows the current workout data of a current user
 */
public class WorkoutFragment extends Fragment implements View.OnClickListener {


    private static final int REQUEST_SYNC_DATA_OAUTH_REQUEST_CODE = 0x1001;
    private static final int REQUEST_SYNC_STEPS_OAUTH_REQUEST_CODE = 0x1002;
    private static final int REQUEST_SYNC_DISTANCE_OAUTH_REQUEST_CODE = 0x1003;

    private UserData currentUserData;
    private FragmentWorkoutBinding binder;
    private final static String TAG = "WorkoutFragment";

    public WorkoutFragment() {
        currentUserData = UserData.get();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false);
        final View view = binder.getRoot();

        binder.tvSyncData.setOnClickListener(this);
        binder.tvSyncDistance.setOnClickListener(this);
        binder.tvSyncStep.setOnClickListener(this);

        if (getActivity() != null) {
            Typeface boldTypeface = ResourcesCompat.getFont(getActivity(), R.font.nexa_bold);
            binder.tvSyncData.setTypeface(boldTypeface);
            binder.tvSyncDistance.setTypeface(boldTypeface);
            binder.tvSyncStep.setTypeface(boldTypeface);
            binder.tvManageDevice.setTypeface(boldTypeface);
            binder.tvDataSync.setTypeface(boldTypeface);
            binder.arcBottomText1.setTypeface(boldTypeface);
            binder.arcBottomText.setTypeface(boldTypeface);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    public void initGoogleData(int requestCode) {
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getContext()), GoogleFitUtil.getFitnessOptions())) {
            GoogleSignIn.requestPermissions(
                    this,
                    requestCode,
                    GoogleSignIn.getLastSignedInAccount(getContext()),
                    GoogleFitUtil.getFitnessOptions());
        } else {
            syncFitData(requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
            case REQUEST_SYNC_DATA_OAUTH_REQUEST_CODE:
            case REQUEST_SYNC_DISTANCE_OAUTH_REQUEST_CODE:
            case REQUEST_SYNC_STEPS_OAUTH_REQUEST_CODE:
                syncFitData(requestCode);
                break;
            }
        }
    }

    private void syncFitData(int requestCode) {
        Context context = getContext();
        switch (requestCode) {
        case REQUEST_SYNC_DATA_OAUTH_REQUEST_CODE:
            syncDistance(context);
            syncSteps(context);
            break;
        case REQUEST_SYNC_DISTANCE_OAUTH_REQUEST_CODE:
            syncDistance(context);
            break;
        case REQUEST_SYNC_STEPS_OAUTH_REQUEST_CODE:
            syncSteps(context);
            break;
        }
    }

    void syncSteps(Context context) {
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
               .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
               .addOnSuccessListener(
                       new OnSuccessListener<DataSet>() {
                           @Override
                           public void onSuccess(DataSet dataSet) {
                               int total = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                               currentUserData.setStepcurrent(total);
                               populateData();
                               currentUserData.save();
                               Log.i(TAG, "Total steps: " + total);
                           }
                       })
               .addOnFailureListener(
                       new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.w(TAG, "There was a problem getting the step count.", e);
                           }
                       });
    }

    void syncDistance(Context context) {
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
               .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
               .addOnSuccessListener(
                       new OnSuccessListener<DataSet>() {
                           @Override
                           public void onSuccess(DataSet dataSet) {
                               float total = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                               currentUserData.setRunningcurrent(total * 0.000621);
                               populateData();
                               currentUserData.save();
                               Log.i(TAG, "Total distance: " + total);
                           }
                       })
               .addOnFailureListener(
                       new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.w(TAG, "There was a problem getting the step count.", e);
                           }
                       });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.tvSyncData:
            initGoogleData(REQUEST_SYNC_DATA_OAUTH_REQUEST_CODE);
            break;
        case R.id.tvSyncDistance:
            initGoogleData(REQUEST_SYNC_DISTANCE_OAUTH_REQUEST_CODE);
            break;
        case R.id.tvSyncStep:
            initGoogleData(REQUEST_SYNC_STEPS_OAUTH_REQUEST_CODE);
            break;
        }
    }


    /**
     * Populates the fields with respective data
     */
    private void populateData() {
        String distanceStr = String.format(
                Locale.US,
                "%.02f",
                currentUserData.getRunningcurrent());
        String distanceGoalStr = String.format(
                Locale.US,
                "%.02f",
                currentUserData.getRunninggoal());
        binder.tvRunning.setText(
                distanceStr +
                "/" +
                distanceGoalStr);
        binder.tvSteps.setText(currentUserData.getStepcurrent() + "/" + currentUserData.getStepgoal());
        binder.arcRunning.setMax((int) currentUserData.getRunninggoal());
        binder.arcRunning.setProgress((float) currentUserData.getRunningcurrent());
        binder.arcSteps.setMax(currentUserData.getStepgoal());
        binder.arcSteps.setProgress(currentUserData.getStepcurrent());
    }


    public static void printData(DataReadResponse dataReadResult) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(
                    TAG, "Number of returned buckets of DataSets is: " + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    dumpDataSet(dataSet);
                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet);
            }
        }
        // [END parse_read_data_result]
    }

    // [START parse_dataset]
    private static void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            for (Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
            }
        }
    }
}
