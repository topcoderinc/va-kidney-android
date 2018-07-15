package com.topcoder.vakidney.fragments;


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
import android.widget.TextView;

import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.topcoder.vakidney.databinding.FragmentWorkoutBinding;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.GoogleFitUtil;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * It shows the current workout data of a current user
 */
public class WorkoutFragment extends Fragment implements View.OnClickListener {


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSyncData: {
                if (getView() != null) {
                    getView().findViewById(R.id.tvSyncDistance).performClick();
                    getView().findViewById(R.id.tvSyncStep).performClick();
                }
            }
            break;
            case R.id.tvSyncDistance: {
                GoogleFitUtil.getDistance(
                        getContext(),
                        new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponse) {
                                try {
                                    float distance = 0;
                                    List<DataPoint> dps = dataReadResponse
                                            .getDataSet(DataType.TYPE_DISTANCE_DELTA)
                                            .getDataPoints();
                                    for (DataPoint dp : dps) {
                                        Value value = dp.getValue(Field.FIELD_DISTANCE);
                                        distance = distance + value.asFloat();
                                    }
                                    currentUserData.setRunningcurrent(distance * 0.000621);
                                    populateData();
                                    currentUserData.save();
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }

                            }
                        },
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        });
            }
            break;
            case R.id.tvSyncStep: {
                GoogleFitUtil.getStep(
                        getContext(),
                        new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponse) {
                                printData(dataReadResponse);
                                try {
                                    int steps = 0;
                                    List<DataPoint> dps = dataReadResponse
                                            .getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                                            .getDataPoints();
                                    for (DataPoint dp : dps) {
                                        Value value = dp.getValue(Field.FIELD_STEPS);
                                        steps = steps + value.asInt();
                                    }
                                    currentUserData.setStepcurrent(steps);
                                    populateData();
                                    currentUserData.save();
                                } catch (Exception e) {
                                }
                            }
                        },
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        });
            }
            break;
        }
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
