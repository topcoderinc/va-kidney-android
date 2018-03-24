package com.topcoder.vakidney.Util;

import android.util.Log;

import com.topcoder.vakidney.Model.DrugInteraction;
import com.topcoder.vakidney.api.FDAServiceAPI;
import com.topcoder.vakidney.api.FDARestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by afrisalyp on 17/03/2018.
 */

public class ServiceCallUtil {

    public static void searchDrugInteraction(final DrugInteraction drugInteraction) {

        FDAServiceAPI fdaServiceAPI = FDARestClient.getService(FDAServiceAPI.class);
        Call<String> result = fdaServiceAPI.searchDrugInteractions(
                "patient.drug.medicinalproduct:\""
                        + drugInteraction.getQuery()
                        + "\"");

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TOPCODER", "call " + call.request().url().toString());
                Log.d("TOPCODER", "resp " + response.body());
                Log.d("TOPCODER", "resp code " + response.code());

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() > 0) {
                        JSONObject firstResult = results.getJSONObject(0);
                        String reportId = firstResult.getString("safetyreportid");
                        String reportDate = firstResult.getString("receivedate");
                        JSONArray drugs = firstResult.getJSONObject("patient").getJSONArray("drug");
                        JSONArray reactions = firstResult.getJSONObject("patient").getJSONArray("reaction");
                        Log.d("TOPCODER", "reportId " + reportId);
                        Log.d("TOPCODER", "reportDate " + reportDate);
                        Log.d("TOPCODER", "drugs " + drugs.toString());
                        Log.d("TOPCODER", "reactions " + reactions.toString());
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
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TOPCODER", "resp e " + t.getMessage());

            }
        });

    }

}
