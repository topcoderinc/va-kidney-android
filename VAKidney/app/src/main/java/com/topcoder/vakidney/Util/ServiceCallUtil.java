package com.topcoder.vakidney.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.topcoder.vakidney.BuildConfig;
import com.topcoder.vakidney.model.DrugInteraction;
import com.topcoder.vakidney.model.FoodRecommendation;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.api.FDAServiceAPI;
import com.topcoder.vakidney.api.FDARestClient;
import com.topcoder.vakidney.api.NDBRestClient;
import com.topcoder.vakidney.api.NDBServiceAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by afrisalyp on 17/03/2018.
 */

public class ServiceCallUtil {

    private final static int REQUEST_LIMIT = 10;

    public static void searchFoodRecommendation(
            final Context context,
            final String name) {
        if (FoodRecommendation.getByName(name.toLowerCase()).size() > 0) return;

        final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class);
        Call<String> result = ndbServiceAPI.searchFood(BuildConfig.NDB_API_KEY, name);
        Log.d("TOPCODER", "call searchFoodRecommendation ");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                JSONObject jsonObject;
                try {
                    Log.d("TOPCODER", "response.body() " + response.body());
                    jsonObject = new JSONObject(response.body());
                    JSONArray items = jsonObject.getJSONObject("list").getJSONArray("item");
                    if (items.length() > 0) {
                        final String ndbno = items.getJSONObject(0).getString("ndbno");
                        Call<String> result2 = ndbServiceAPI.searchFoodNutrition(BuildConfig.NDB_API_KEY, ndbno);
                        result2.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                                try {
                                    JSONArray nutrients = new JSONObject(response.body())
                                            .getJSONObject("report")
                                            .getJSONObject("food")
                                            .getJSONArray("nutrients");

                                    UserData userData = UserData.get();
                                    if (userData == null) return;
                                    List<Goal> goals = Goal.get(
                                            userData.getDiseaseCategory(),
                                            userData.isDialysis());

//                                    String description = "";
                                    for (int i = 0; i < nutrients.length(); i++) {
                                        String nutrientName = nutrients.getJSONObject(i)
                                                .getString("name");
                                        Log.d("TOPCODER", "resp " + nutrientName);

                                        for (int j = 0; j < goals.size(); j++) {
                                            if (goals.get(j).getAction() == Goal.ACTION_REDUCE) {
                                                if (nutrientName
                                                        .toLowerCase()
                                                        .contains(goals
                                                                .get(j)
                                                                .getNutrient()
                                                                .toLowerCase())) {

                                                    String nutrientId = nutrients.getJSONObject(i)
                                                            .getString("nutrient_id");

                                                    searchHighNutrientFood(
                                                            context,
                                                            nutrientId,
                                                            name,
                                                            nutrientName);

//                                                    description = description
//                                                            + "Contains "
//                                                            + goals.get(j).getNutrient()
//                                                            + "\n";
                                                    goals.remove(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }

//                                    if (description.length() > 0) {
//                                        new FoodRecommendation(
//                                                name.toLowerCase(),
//                                                description,
//                                                ndbno,
//                                                0,
//                                                null,
//                                                FoodRecommendation.TYPE_UNSAFE,
//                                                nutrients.toString()
//                                        ).save();

                                        Toast.makeText(
                                                context.getApplicationContext(),
                                                "New Food Recommendation Found",
                                                Toast.LENGTH_LONG).show();
//                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }

    private static void searchHighNutrientFood(
            final Context context,
            final String nutrientId,
            final String foodName,
            final String nutrientName) {

        final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class);
        Call<String> result = ndbServiceAPI.searchNutritionFoods(
                BuildConfig.NDB_API_KEY,
                nutrientId,
                String.valueOf(REQUEST_LIMIT),
                String.valueOf(0));
        Log.d("TOPCODER", "call searchHighNutrientFood ");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                JSONObject jsonObject;
                try {
                    Log.d("TOPCODER", "response.body() " + response.body());
                    jsonObject = new JSONObject(response.body()).getJSONObject("report");
                    int total = jsonObject.getInt("total");
                    JSONArray items = jsonObject.getJSONArray("foods");
                    if (items.length() > 0) {
                        String description = "";
                        for (int i = 0; i < items.length(); i++) {
                            String foodName = items.getJSONObject(i).getString("name");
                            description = description + "- " + foodName + ".\n";
                        }
                        new FoodRecommendation(
                                "Reduce " + foodName,
                                description,
                                "",
                                0,
                                null,
                                FoodRecommendation.TYPE_UNSAFE,
                                nutrientName
                        ).save();
                    }
                    searchLowNutrientFood(
                            context,
                            nutrientId,
                            foodName,
                            nutrientName,
                            total - REQUEST_LIMIT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }

    private static void searchLowNutrientFood(
            final Context context,
            final String nutrientId,
            final String foodName,
            final String nutrientName,
            final int offset) {

        final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class);
        Call<String> result = ndbServiceAPI.searchNutritionFoods(
                BuildConfig.NDB_API_KEY,
                nutrientId,
                String.valueOf(REQUEST_LIMIT),
                String.valueOf(offset));
        Log.d("TOPCODER", "call searchLowNutrientFood ");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                JSONObject jsonObject;
                try {
                    Log.d("TOPCODER", "response.body() " + response.body());
                    jsonObject = new JSONObject(response.body()).getJSONObject("report");
                    JSONArray items = jsonObject.getJSONArray("foods");
                    if (items.length() > 0) {
                        String description = "";
                        for (int i = 0; i < items.length(); i++) {
                            String foodName = items.getJSONObject(i).getString("name");
                            description = description + "- " + foodName + ".\n";
                        }
                        new FoodRecommendation(
                                "Reduce " + foodName,
                                description,
                                "",
                                0,
                                null,
                                FoodRecommendation.TYPE_GOOD,
                                nutrientName
                        ).save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }

    public static void searchDrugInteraction(
            final Context context,
            final DrugInteraction drugInteraction) {

        FDAServiceAPI fdaServiceAPI = FDARestClient.getService(FDAServiceAPI.class);
        Call<String> result = fdaServiceAPI.searchDrugInteractions(
                "patient.drug.medicinalproduct:\""
                        + drugInteraction.getQuery()
                        + "\"");

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (!response.isSuccessful() || response.body() == null) return;

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() > 0) {
                        JSONObject firstResult = results.getJSONObject(0);
                        String reportId = firstResult.getString("safetyreportid");
                        String reportDate = firstResult.getString("receivedate");
                        JSONArray drugs = firstResult.getJSONObject("patient").getJSONArray("drug");
                        JSONArray reactions = firstResult.getJSONObject("patient").getJSONArray("reaction");
                        drugInteraction.setDrugsArray(drugs.toString());
                        drugInteraction.setReportId(reportId);
                        drugInteraction.setReactionsArray(reactions.toString());
                        drugInteraction.setDate(
                                new SimpleDateFormat("yyyyMMdd", Locale.US)
                                        .parse(reportDate));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                drugInteraction.save();
                Toast.makeText(
                        context.getApplicationContext(),
                        "New Drug Interaction Found",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("TOPCODER", "resp e " + t.getMessage());
            }
        });

    }

}
