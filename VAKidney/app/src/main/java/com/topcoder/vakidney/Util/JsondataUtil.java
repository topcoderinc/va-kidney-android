package com.topcoder.vakidney.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;


import com.topcoder.vakidney.Model.ChartData;
import com.topcoder.vakidney.Model.Goal;
import com.topcoder.vakidney.Model.LabData;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.Model.MedicationResources;
import com.topcoder.vakidney.Model.Resources;
import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by abina on 1/24/2018.
 */

public class JsondataUtil {

    /**
     * Returns UserData object read from UserData.json file
     * @param context
     * @return
     */
    public static UserData getUserData(Context context){
        try {
            String jsonString = loadJSONFromAsset(context, "UserData.json");
            JSONObject jsonObject=new JSONObject(jsonString);
            UserData userData=new UserData();
            userData.setUsername(jsonObject.getString("username"));
            userData.setPassword(jsonObject.getString("password"));
            userData.setFullname(jsonObject.getString("fullname"));
            userData.setAge(jsonObject.getInt("age"));
            userData.setPoints(jsonObject.getInt("points"));
            userData.setHeight(jsonObject.getInt("height"));
            userData.setWeight(jsonObject.getInt("weight"));
            userData.setDialysis(jsonObject.getBoolean("dialysis"));
            userData.setDiseaseCategory(jsonObject.getString("diseasecategory"));
            userData.setSetupgoals(jsonObject.getBoolean("setupgoals"));
            userData.setAvatar(jsonObject.getBoolean("avatar"));
            userData.setBiometric(jsonObject.getBoolean("biometric"));
            userData.setRunningcurrent(jsonObject.getDouble("runningcurrent"));
            userData.setRunninggoal(jsonObject.getDouble("runninggoal"));
            userData.setStepcurrent(jsonObject.getInt("stepcurrent"));
            userData.setStepgoal(jsonObject.getInt("stepsgoal"));
            userData.setJumpcurrent(jsonObject.getInt("jumpcurrent"));
            userData.setJumpgoal(jsonObject.getInt("jumpgoal"));
            userData.setSwimmingcurrent(jsonObject.getInt("swimmingcurrent"));
            userData.setSwimminggoal(jsonObject.getInt("swimminggoal"));
            return userData;

        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
            return null;
        }
    }

    /**
     * Returns an array of units from minimum to maximum index
     * @param context
     * @param type
     * @param max
     * @param min
     * @return
     */
    public static String[] getUnitsArray(Context context, int type, int max, int min){
        String unit="";
        try {
            String jsonString = loadJSONFromAsset(context, "Units.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getInt("type")==type){
                    unit=jsonObject.getString("name");
                    break;
                }
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }
        String[] array=new String[max-min];
        int k=0;
        for(int i=min; i<max; i++){
            array[k]=(i+1)+" "+unit;
            k++;
        }
        return array;
    }

    /**
     * Returns an array of Resources object read from Resources.json file
     * @param context
     * @return
     */
    public static ArrayList<Resources> getResources(Context context) {
        ArrayList<Resources> resourcesArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Resources.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Resources resources=new Resources();
                resources.setTitle(jsonObject.getString("title"));
                resources.setDesc(jsonObject.getString("desc"));
                resourcesArrayList.add(resources);

            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return resourcesArrayList;
    }


    /**
     * Returns an array of Labdata object read from Labdata.json file
     * @param context
     * @return
     */
    public static ArrayList<LabData> getLabData(Context context) {
        ArrayList<LabData> labDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "LabData.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LabData labData=new LabData();
                labData.setName(jsonObject.getString("name"));
                labData.setCurrentValue(jsonObject.getString("currentValue"));
                labData.setUnit(jsonObject.getString("unit"));
                labData.setStanding(jsonObject.getInt("standing"));
                labData.setTrend(jsonObject.getInt("trend"));
                labDataArrayList.add(labData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return labDataArrayList;
    }

    /**
     * Returns an array of MedicationResources object read from MedicationResources.json file
     * @param context
     * @param type
     * @return
     */
    public static ArrayList<MedicationResources> getMedicationResources(Context context, int type) {
        ArrayList<MedicationResources> medicationResourcesArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "MedicationResources.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(type - 1);
            JSONArray contentJsonArray = jsonObject.getJSONArray("content");
            for (int k = 0; k < contentJsonArray.length(); k++) {
                JSONObject medicationJsonObject = contentJsonArray.getJSONObject(k);
                MedicationResources medicationResources = new MedicationResources();
                medicationResources.setMainTitle(medicationJsonObject.getString("maintitle"));

                JSONArray jsonTitleDescArray = medicationJsonObject.getJSONArray("titledesc");
                for (int m = 0; m < jsonTitleDescArray.length(); m++) {
                    JSONObject jsonTitleDescObject = jsonTitleDescArray.getJSONObject(m);
                    medicationResources.addMedicationTitleDesc(jsonTitleDescObject.getString("title"), jsonTitleDescObject.getString("desc"));
                }
                medicationResourcesArrayList.add(medicationResources);
            }


        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return medicationResourcesArrayList;
    }

    /**
     * Returns Title of Unit of a goal with given id
     * @param context
     * @param id
     * @return
     */
    public static String getGoalUnitById(Context context, int id){
        try {
            String jsonString = loadJSONFromAsset(context, "Units.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getInt("type")==id) {
                    return jsonObject.getString("name");
                }
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }
        return "";
    }

    /**
     * Returns Title of a goal with given id
     * @param context
     * @param id
     * @return
     */
    public static String getGoalTitleById(Context context, int id){
        try {
            String jsonString = loadJSONFromAsset(context, "Task.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getInt("type")==id) {
                    return jsonObject.getString("name");
                }
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }
        return "";
    }


    /**
     * Returns Goal Object with given goal id
     * @param context
     * @param id
     * @return
     */
    public static Goal getGoalByID(Context context, int id){
        try {
            String jsonString = loadJSONFromAsset(context, "Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getInt("id")==id) {
                    Goal goal = new Goal();
                    goal.setId(jsonObject.getInt("id"));
                    goal.setTitle(jsonObject.getInt("title"));
                    goal.setGoal(jsonObject.getDouble("goal"));
                    goal.setCurrentLevel(jsonObject.getDouble("currentLevel"));
                    goal.setUnit(jsonObject.getInt("unit"));
                    goal.setAddString(jsonObject.getString("addString"));
                    goal.setColorCode(jsonObject.getString("color"));
                    int icon = jsonObject.getInt("icon");
                    switch (icon) {
                        case 1:
                            goal.setIcon(R.drawable.ic_running);
                            break;
                        case 2:
                            goal.setIcon(R.drawable.ic_water_bottle);
                            break;
                        case 3:
                            goal.setIcon(R.drawable.ic_fruits_veggies);
                            break;
                        case 4:
                            goal.setIcon(R.drawable.ic_weight_loss);
                            break;
                        default:
                            goal.setIcon(R.drawable.ic_running);
                            break;
                    }
                    return goal;
                }
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }
        return null;
    }


    /**
     * Returns an array of goal objects read from Goals.json file
     * @param context
     * @return
     */
    public static ArrayList<Goal> getGoals(Context context) {
        ArrayList<Goal> goals = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Goal goal = new Goal();
                goal.setId(jsonObject.getInt("id"));
                goal.setTitle(jsonObject.getInt("title"));
                goal.setGoal(jsonObject.getDouble("goal"));
                goal.setCurrentLevel(jsonObject.getDouble("currentLevel"));
                goal.setUnit(jsonObject.getInt("unit"));
                goal.setAddString(jsonObject.getString("addString"));
                goal.setColorCode(jsonObject.getString("color"));
                int icon=jsonObject.getInt("icon");
                switch (icon){
                    case 1:
                        goal.setIcon(R.drawable.ic_running);
                        break;
                    case 2:
                        goal.setIcon(R.drawable.ic_water_bottle);
                        break;
                    case 3:
                        goal.setIcon(R.drawable.ic_fruits_veggies);
                        break;
                    case 4:
                        goal.setIcon(R.drawable.ic_weight_loss);
                        break;
                    default:
                        goal.setIcon(R.drawable.ic_running);
                        break;
                }
                goals.add(goal);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return goals;
    }


    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getPotassiumDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/Potassium/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("potassiumlevel"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getPotassiumDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/Potassium/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("potassiumlevel"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getBodyWeightDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BodyWeight/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("weight"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getBodyWeightDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BodyWeight/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("weight"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getBloodSugarDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BloodSugar/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("bloodsugar"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year
     * @param context
     * @return
     */
    public static ArrayList<ChartData> getBloodSugarDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BloodSugar/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("bloodsugar"));
                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }


    /**
     * Returns an array of Meals Object read from meals.json  File
     * @param context
     * @return
     */
    public static ArrayList<Meal> getMeals(Context context) {
        ArrayList<Meal> meals = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Meals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Meal meal = new Meal();
                meal.setName(jsonObject.getString("name"));
                meal.setDesc(jsonObject.getString("desc"));
                meals.add(meal);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return meals;
    }


    /**
     * Reads string from json file
     * @param context
     * @param filename
     * @return
     */
    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
