package com.topcoder.vakidney.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store data for different meals created by the user.
 */

public class Meal extends SugarRecord<Meal> implements Serializable {

    public final static String MEAL_TYPE_BREAKFAST = "breakfast";
    public final static String MEAL_TYPE_LUNCH = "lunch";
    public final static String MEAL_TYPE_DINNER = "dinner";
    public final static String MEAL_TYPE_SNACK = "snack";
    public final static String MEAL_TYPE_CUSTOM = "custom";

    long mealId;
    String name;
    String photoUrl;
    String desc;
    Date date;
    String type;
    boolean hasDrug;

    public Meal() {
    }

    public Meal(
            long mealId,
            String name,
            String photoUrl,
            String desc,
            Date date,
            String type,
            boolean hasDrug){
        this.mealId = mealId;
        this.name = name;
        this.photoUrl = photoUrl;
        this.desc = desc;
        this.date = date;
        this.type = type;
        this.hasDrug = hasDrug;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List <MealDrug> getMealDrugs() {
        return MealDrug.find(MealDrug.class, "meal_id = ?", String.valueOf(this.getMealId()));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isHasDrug() {
        return hasDrug;
    }

    public void setHasDrug(boolean hasDrug) {
        this.hasDrug = hasDrug;
    }

    public static List<Meal> getAllWithDrug() {
        return Meal.find(Meal.class, "has_drug = ?", "true");
    }

    //    public Bundle getBundle(){
//        Bundle bundle=new Bundle();
//        bundle.putString("name", name);
//        bundle.putString("desc", desc);
//        bundle.putSerializable("mealDrugs", mealDrugs);
//        bundle.putString("type", type);
//        return bundle;
//    }
//
//    public static Meal getMealFromBundle(Bundle bundle){
//        Meal meal = new Meal();
//        meal.setName(bundle.getString("name"));
//        meal.setDesc(bundle.getString("desc"));
//        meal.setMealDrugs((ArrayList<MealDrug>) bundle.getSerializable("mealDrugs"));
//        meal.setType(bundle.getString("type"));
//        return meal;
//    }
}
