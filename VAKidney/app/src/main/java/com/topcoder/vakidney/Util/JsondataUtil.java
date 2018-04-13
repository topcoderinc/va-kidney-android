package com.topcoder.vakidney.util;

import android.content.Context;
import android.util.Log;

import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.LabData;
import com.topcoder.vakidney.model.Meal;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.model.MedicationResources;
import com.topcoder.vakidney.model.Resources;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Abinash Neupane on 1/24/2018.
 * This method is used to perform various json related tasks
 */

public class JsondataUtil {

    /**
     * Returns UserData object read from UserData.json file
     * @param context The context it was called from
     * @return The Userdata object parsed from the specified json file is returned
     */
    public static UserData getUserData(Context context){
        try {
            String jsonString = loadJSONFromAsset(context, "UserData.json");
            JSONObject jsonObject = new JSONObject(jsonString);
            UserData userData= new UserData();
            userData.setUsername(jsonObject.getString("username"));
            userData.setPassword(jsonObject.getString("password"));
            userData.setFullname(jsonObject.getString("fullname"));
            userData.setAge(jsonObject.getInt("age"));
            userData.setBirthday(new Date().getTime() - ((long) userData.getAge() * 1000 * 60 * 60 * 24 * 360));
            userData.setPoints(jsonObject.getInt("points"));
            userData.setHeight(jsonObject.getInt("height"));
            userData.setWeight(jsonObject.getInt("weight"));
            userData.setDialysis(jsonObject.getBoolean("dialysis"));
            userData.setDiseaseCategory(jsonObject.getInt("diseasecategory"));
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
     * @param context   The context it was called from
     * @param type type of unit
     * @param max minimum value for the array
     * @param min maximum value for the array
     * @return An Array of String with respective units ranging from min to max according to their type
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
            array[k]=((i+1)*3)+" "+unit;
            k++;
        }
        return array;
    }

    /**
     * Returns an array of Resources object read from Resources.json file
     * @param context  The context it was called from
     * @return An ArrayList of resources object is returned parsed from resources.json file
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
     * @param context  The context it was called from
     * @return An ArrayList of LabData is returned by parsing LabData.json file
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
     * @param context  The context it was called from
     * @param type the type of medication article
     * @return An ArrayList of MedicationResources is returned by parsing MedicationResources.json file
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
     * @param context  The context it was called from
     * @param id The id of a targeted goal
     * @return It returns the Unit of a goal with given id
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
     * @param context  The context it was called from
     * @param id The id of targeted goal
     * @return It returns the Title of goal with given id
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
     *
     * @param context  The context it was called from
     * @param id The id of a targeted goal
     * @return It returns the Color of a goal with given id
     */
    public static String getGoalColorById(Context context, int id){
        try {
            String jsonString = loadJSONFromAsset(context, "ColorCode.json");
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
     * @param context  The context it was called from
     * @param id The id of a targeted goal
     * @return It returns the Goal object with given id
     */
    public static Goal getGoalByID(Context context, int id){
        try {
            String jsonString = loadJSONFromAsset(context, "Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getInt("id")==id) {
                    Goal goal = new Goal();
                    goal.setTitle(jsonObject.getInt("title"));
                    goal.setGoal(jsonObject.getDouble("goal"));
                    goal.setCurrentLevel(jsonObject.getDouble("currentLevel"));
                    goal.setUnit(jsonObject.getInt("unit"));
                    goal.setAddString(jsonObject.getString("addString"));
                    goal.setColorCode(getGoalColorById(context, jsonObject.getInt("color")));
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
     * @param context  The context it was called from
     * @return An ArrayList of Goal is returned by parsing Goals.json file
     */
    public static ArrayList<Goal> getGoals(Context context) {
        ArrayList<Goal> goals = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Goal goal = new Goal();
                goal.setTitle(jsonObject.getInt("title"));
                goal.setGoal(jsonObject.getDouble("goal"));
                goal.setCurrentLevel(jsonObject.getDouble("currentLevel"));
                goal.setUnit(jsonObject.getInt("unit"));
                goal.setAddString(jsonObject.getString("addString"));
                goal.setColorCode(getGoalColorById(context, jsonObject.getInt("color")));
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
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return An ArrayList of ChartData is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getPotassiumDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/Potassium/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("potassiumlevel"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return An ArrayList of ChartData for Potassium is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getPotassiumDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/Potassium/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("potassiumlevel"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return An ArrayList of ChartData is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getBodyWeightDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BodyWeight/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("weight"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return An ArrayList of ChartData is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getBodyWeightDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BodyWeight/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("weight"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return An ArrayList of ChartData is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getBloodSugarDataGoal(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BloodSugar/" + year + "/Goals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("bloodsugar"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }

    /**
     * Returns Chart Data for given year which is read from Respective json file
     * @param year The year of which the data is to be returned
     * @param context  The context it was called from
     * @return  An ArrayList of ChartData is returned by parsing respective json file
     */
    public static ArrayList<ChartData> getBloodSugarDataActual(int year, Context context) {
        ArrayList<ChartData> chartDataArrayList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "Charts/BloodSugar/" + year + "/Actuals.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ChartData chartData = new ChartData(jsonObject.getString("month"), jsonObject.getDouble("bloodsugar"));
//                chartDataArrayList.add(chartData);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return chartDataArrayList;
    }


    /**
     * Returns an array of Meals Object read from meals.json  File
     * @param context  The context it was called from
     * @return  An ArrayList of Meal Object is returned by parsing Meals.json file
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
                meal.setPhotoUrl(jsonObject.getString("photoUrl"));
                meal.setDesc(jsonObject.getString("desc"));
                meal.setType(jsonObject.getString("type"));
                meal.setDate(DateUtil.fromISO8601UTC(jsonObject.getString("date")));
                meal.setMealId(System.currentTimeMillis());
                meal.save();

                JSONArray arrayMealDrugs = jsonObject.getJSONArray("mealDrugs");
                for (int j = 0; j < arrayMealDrugs.length(); j++) {
                    JSONObject objectMealDrugs = arrayMealDrugs.getJSONObject(j);
                    MealDrug mealDrug = new MealDrug();
                    mealDrug.setName(objectMealDrugs.getString("name"));
                    mealDrug.setAmount(objectMealDrugs.getDouble("amount"));
                    mealDrug.setUnit(objectMealDrugs.getString("unit"));
                    mealDrug.setType(objectMealDrugs.getString("type").equals("meal") ?
                            MealDrug.TYPE_MEAL : MealDrug.TYPE_DRUG);
                    mealDrug.setMealId(meal.getMealId());
                    if(mealDrug.getType() == MealDrug.TYPE_DRUG && !meal.isHasDrug()) {
                        meal.setHasDrug(true);
                        meal.save();
                    }
                    mealDrug.save();
                }
                meals.add(meal);
            }
        } catch (JSONException e) {
            Log.e("json Exception", e.getMessage() + "");
        }

        return meals;
    }


    /**
     * Reads string from json file
     * @param context  The context it was called from
     * @param filename The name of json file
     * @return It returns the string of a specified json file
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
