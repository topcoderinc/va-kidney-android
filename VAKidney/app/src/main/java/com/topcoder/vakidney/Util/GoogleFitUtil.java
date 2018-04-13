package com.topcoder.vakidney.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Created by afrisalyp on 18/03/2018.
 */

public class GoogleFitUtil {

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

    public static void insertNutrients() {
        DataSource nutritionSource = new DataSource.Builder()
        .build();

        DataPoint banana = DataPoint.create(nutritionSource);
        banana.setTimestamp(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        banana.getValue(Field.FIELD_FOOD_ITEM).setString("banana");
        banana.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_SNACK);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_FAT, 0.4f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SODIUM, 1f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SATURATED_FAT, 0.1f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_PROTEIN, 1.3f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_CARBS, 27.0f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CHOLESTEROL, 0.0f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CALORIES, 105.0f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SUGAR, 14.0f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_DIETARY_FIBER, 3.1f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_POTASSIUM, 422f);

    }

}
