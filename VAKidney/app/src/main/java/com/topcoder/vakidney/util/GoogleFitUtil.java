package com.topcoder.vakidney.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.topcoder.vakidney.model.UserData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Created by afrisalyp on 18/03/2018.
 * This is util class to process query and insert to Google Fit.
 */

public class GoogleFitUtil {

    /**
     * Insert weight data to Google Fit
     * @param context The context it was called from
     * @param weight weight value in lb / pound
     * @return A task represent inserting process to Google Fit
     */
    public static Task insertWeight(Context context, int weight) {
        // Set a start and end time for our data, using a start time of 1 hour before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();

        // Create a data source
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_WEIGHT)
                        .setStreamName("weight")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        // Create a data set
        float kilograms = (float) weight * 0.453592f;
        DataSet dataSet = DataSet.create(dataSource);
        // For each data point, specify a start time, end time, and the data value -- in this case,
        // the number of new steps.
        DataPoint dataPoint =
                dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_WEIGHT).setFloat(kilograms);
        dataSet.add(dataPoint);

        Task response = Fitness.getHistoryClient(
                context,
                GoogleSignIn.getLastSignedInAccount(context)
        ).insertData(dataSet);
        return response;
    }

    /**
     * Insert height data to Google Fit
     * @param context The context it was called from
     * @param feet height value in feet
     * @param inch height value in inch
     * @return A task represent inserting process to Google Fit
     */
    public static Task insertHeight(Context context, int feet, int inch) {
        // Set a start and end time for our data, using a start time of 1 hour before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();

        // Create a data source
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_HEIGHT)
                        .setStreamName("height")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        // Create a data set
        float meters = (float) feet / 3.2808f;
        meters = meters + (float) inch * 0.0254f;
        DataSet dataSet = DataSet.create(dataSource);
        // For each data point, specify a start time, end time, and the data value -- in this case,
        // the number of new steps.
        DataPoint dataPoint =
                dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_HEIGHT).setFloat(meters);
        dataSet.add(dataPoint);

        Task response = Fitness.getHistoryClient(
                context,
                GoogleSignIn.getLastSignedInAccount(context)
        ).insertData(dataSet);
        return response;
    }

    public final static OnSuccessListener<DataReadResponse> sDefaultGetStepSuccessListener
            = new OnSuccessListener<DataReadResponse>() {
        @Override
        public void onSuccess(DataReadResponse dataReadResponse) {
            int steps = 0;
            List<DataPoint> dps = dataReadResponse
                    .getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                    .getDataPoints();
            for (DataPoint dp : dps) {
                Value value = dp.getValue(Field.FIELD_STEPS);
                steps = steps + value.asInt();
            }
            UserData userData = UserData.get();
            if (userData != null) {
                userData.setStepcurrent(steps);
                userData.save();
            }
        }
    };

    public final static OnSuccessListener<DataReadResponse> sDefaultGetDistanceSuccessListener
            = new OnSuccessListener<DataReadResponse>() {
        @Override
        public void onSuccess(DataReadResponse dataReadResponse) {
            float distance = 0;
            List<DataPoint> dps = dataReadResponse
                    .getDataSet(DataType.TYPE_DISTANCE_DELTA)
                    .getDataPoints();
            for (DataPoint dp : dps) {
                Value value = dp.getValue(Field.FIELD_DISTANCE);
                distance = distance + value.asFloat();
            }
            UserData userData = UserData.get();
            if (userData != null) {
                userData.setRunningcurrent(distance * 0.000621);
                userData.save();
            }
        }
    };

    /**
     * Get height data from Google Fit
     * @param context The context it was called from
     * @param successListener success event listener
     * @param failureListener error listener
     */
    public static void getHeight (
            Context context,
            OnSuccessListener<DataReadResponse> successListener,
            OnFailureListener failureListener) {

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readData(readRequest)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(LOG_TAG, "onComplete()");
                    }
                });

    }

    /**
     * Get weight data from Google Fit
     * @param context The context it was called from
     * @param successListener success event listener
     * @param failureListener error listener
     */
    public static void getWeight (
            Context context,
            OnSuccessListener<DataReadResponse> successListener,
            OnFailureListener failureListener) {

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readData(readRequest)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(LOG_TAG, "onComplete()");
                    }
                });

    }

    /**
     * Get daily activity distance data from Google Fit
     * @param context The context it was called from
     * @param successListener success event listener
     * @param failureListener error listener
     */
    public static void getDistance (
            Context context,
            OnSuccessListener<DataReadResponse> successListener,
            OnFailureListener failureListener) {

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_DISTANCE_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readData(readRequest)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(LOG_TAG, "onComplete()");
                    }
                });

    }

    /**
     * Get daily activity step data from Google Fit
     * @param context The context it was called from
     * @param successListener success event listener
     * @param failureListener error listener
     */
    public static void getStep (
            Context context,
            OnSuccessListener<DataReadResponse> successListener,
            OnFailureListener failureListener) {

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readData(readRequest)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(LOG_TAG, "onComplete()");
                    }
                });

    }

    /**
     * Insert nutrients data to Google Fit
     * @param name The name of food (Optionals)
     * @param values values map
     */
    public static void insertNutrients(String name, Map<String, Float> values) {
        DataSource nutritionSource = new DataSource.Builder()
            .setDataType(DataType.TYPE_NUTRITION)
            .setType(DataSource.TYPE_RAW)
            .build();

        DataPoint data = DataPoint.create(nutritionSource);
        data.setTimestamp(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

        if (name != null) data.getValue(Field.FIELD_FOOD_ITEM).setString(name);
        for (String key: values.keySet()) {
            data.getValue(Field.FIELD_NUTRIENTS).setKeyValue(key, values.get(key));
        }

    }


    public static FitnessOptions getFitnessOptions() {
        return FitnessOptions.builder()
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
    }

}
