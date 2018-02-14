package com.topcoder.vakidney.Model;

import android.os.Bundle;

/**
 * Created by Abinash Neupane on 2/8/2018.
 * This model class is used to store data of a particular goal. This model class is used in various fragments such as GoalFragment, HOme1Fragment and WorkoutFragment
 */

public class Goal {

    private int id;
    private String colorCode;
    private int title;
    private double goal;
    private double currentLevel;
    private int unit;
    private String addString;
    private int icon;

    public int getId() {
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
}
