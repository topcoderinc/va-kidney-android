package com.topcoder.vakidney.model;

import android.os.Bundle;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Abinash Neupane on 2/8/2018.
 * This model class is used to store data of a particular goal. This model class is used in various fragments such as GoalFragment, HOme1Fragment and WorkoutFragment
 */

public class Goal extends SugarRecord<Goal> {

    public final static int TYPE_PILL = 0x00000001;
    public final static int TYPE_FLUID = 0x00000002;
    public final static int TYPE_PIECE = 0x00000003;
    public final static int TYPE_ACTIVITY = 0x00000004;
    public final static int TYPE_GENERAL = 0x00000005;

    public final static int ACTION_REDUCE = 0x00000001;
    public final static int ACTION_INTAKE = 0x00000002;
    public final static int ACTION_ADJUST = 0x00000003;

    private int id;
    private String colorCode;
    private int title;
    private String titleStr;
    private double goal;
    private double currentLevel;
    private int unit;
    private String unitStr;
    private String nutrient;
    private String addString;
    private int icon;
    private int type;
    private int action;
    private boolean dialysisOnly;
    private int minCategory;

    public Goal() {}

    public Goal(
            String title,
            double goal,
            double currentLevel,
            String unit,
            String nutrient,
            String addString,
            int icon,
            int type,
            int action,
            boolean dialysisOnly,
            int minCategory) {
        this.titleStr = title;
        this.goal = goal;
        this.currentLevel = currentLevel;
        this.unitStr = unit;
        this.nutrient = nutrient;
        this.addString = addString;
        this.icon = icon;
        this.type = type;
        this.action = action;
        this.dialysisOnly = dialysisOnly;
        this.minCategory = minCategory;
    }

    public int getGoalId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(double currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getAddString() {
        return addString;
    }

    public void setAddString(String addString) {
        this.addString = addString;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getUnitStr() {
        return unitStr;
    }

    public void setUnitStr(String unitStr) {
        this.unitStr = unitStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDialysisOnly() {
        return dialysisOnly;
    }

    public void setDialysisOnly(boolean dialysisOnly) {
        this.dialysisOnly = dialysisOnly;
    }

    public int getMinCategory() {
        return minCategory;
    }

    public void setMinCategory(int minCategory) {
        this.minCategory = minCategory;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public Bundle getBundle(){
        Bundle bundle=new Bundle();
        bundle.putInt("title", title);
        bundle.putInt("unit", unit);
        bundle.putInt("id", id);
        bundle.putInt("icon", icon);
        bundle.putDouble("goal", goal);
        bundle.putDouble("currentLevel", currentLevel);
        bundle.putString("addString", addString);
        bundle.putString("colorCode", colorCode);
        return bundle;
    }

    public static Goal getGoalFromBundle(Bundle bundle){
        Goal goal=new Goal();
        goal.setId(bundle.getInt("id"));
        goal.setTitle(bundle.getInt("title"));
        goal.setUnit(bundle.getInt("unit"));
        goal.setIcon(bundle.getInt("icon"));
        goal.setGoal(bundle.getDouble("goal"));
        goal.setCurrentLevel(bundle.getDouble("currentLevel"));
        goal.setAddString(bundle.getString("addString"));
        goal.setColorCode(bundle.getString("colorCode"));
        return goal;
    }

    public static List<Goal> get(int diseaseCategry, boolean dialysis) {
        if (dialysis) {
            return Goal.find(Goal.class, "min_category <= ?", String.valueOf(diseaseCategry));
        }
        else {
            return Goal.find(Goal.class, "min_category <= ? and dialysis_only = ?",
                    String.valueOf(diseaseCategry),
                    "0");
        }
    }
}
