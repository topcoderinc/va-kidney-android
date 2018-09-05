package com.topcoder.vakidney.model;

import java.io.Serializable;
import java.util.List;

import com.orm.SugarRecord;
import com.topcoder.vakidney.constant.Comorbidities;

/**
 * Created by Abinash Neupane on 2/8/2018.
 * This model class is used to store data of a particular goal. This model class is used in various fragments such as GoalFragment, HOme1Fragment and WorkoutFragment
 */

public class Goal extends SugarRecord<Goal> implements Serializable {

    public final static int TYPE_PILL = 0x00000001;
    public final static int TYPE_FLUID = 0x00000002;
    public final static int TYPE_PIECE = 0x00000003;
    public final static int TYPE_ACTIVITY = 0x00000004;
    public final static int TYPE_GENERAL = 0x00000005;

    public final static int ACTION_REDUCE = 0x00000001;
    public final static int ACTION_INTAKE = 0x00000002;
    public final static int ACTION_ADJUST = 0x00000003;

    private long goalId;
    private String colorCode;
    private int title;
    private String titleStr;
    private double goal;
    private double goalMax;
    private double goalMin;
    private double goalStep;
    private double currentLevel;
    private int unit;
    private String unitStr;
    private String nutrient;
    private String addString;
    private int frequency;
    private int icon;
    private int type;
    private int action;
    private boolean dialysisOnly;
    private boolean hidden;
    private boolean reminder;
    private int minCategory;

    public Goal() {
    }

    public Goal(
            long goalId,
            String title,
            double goal,
            double goalMax,
            double goalMin,
            double goalStep,
            double currentLevel,
            String unit,
            String nutrient,
            String addString,
            int frequency,
            int icon,
            int type,
            int action,
            boolean dialysisOnly,
            boolean hidden,
            int minCategory,
            boolean reminder) {
        this.goalId = goalId;
        this.titleStr = title;
        this.goal = goal;
        this.goalMax = goalMax;
        this.goalMin = goalMin;
        this.goalStep = goalStep;
        this.currentLevel = currentLevel;
        this.unitStr = unit;
        this.nutrient = nutrient;
        this.addString = addString;
        this.frequency = frequency;
        this.icon = icon;
        this.type = type;
        this.action = action;
        this.dialysisOnly = dialysisOnly;
        this.hidden = hidden;
        this.reminder = reminder;
        this.minCategory = minCategory;
        this.reminder = reminder;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long id) {
        this.goalId = id;
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

    public double getGoalMax() {
        return goalMax;
    }

    public void setGoalMax(double goalMax) {
        this.goalMax = goalMax;
    }

    public double getGoalMin() {
        return goalMin;
    }

    public void setGoalMin(double goalMin) {
        this.goalMin = goalMin;
    }

    public double getGoalStep() {
        return goalStep;
    }

    public void setGoalStep(double goalStep) {
        this.goalStep = goalStep;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public void save() {
        if (this.goalId == 0) {
            this.goalId = System.currentTimeMillis();
        }
        super.save();
    }

    public static List<Goal> get(int diseaseCategry, boolean dialysis) {

        if (dialysis) {
            return Goal.find(Goal.class, "min_category <= ? and hidden = 0 ", String.valueOf(diseaseCategry));
        } else {
            return Goal.find(Goal.class, "min_category <= ? and dialysis_only = ? and hidden = 0",
                    String.valueOf(diseaseCategry),
                    "0");
        }
    }

    public static List<Goal> getWithoutComorbidities(int diseaseCategry, boolean dialysis) {

        if (dialysis) {
            return Goal.find(Goal.class, "min_category <= ? and hidden = 0 and title_str NOT IN (?,?,?)" +
                            " ORDER BY title_str DESC",
                    String.valueOf(diseaseCategry),
                    Comorbidities.ComorbiditiesGoals[0], Comorbidities.ComorbiditiesGoals[1], Comorbidities.ComorbiditiesGoals[2]
            );
        } else {
            return Goal.find(Goal.class, "min_category <= ? and dialysis_only = ? and hidden = 0  and title_str NOT IN (?,?,?)"
                            + " order by title_str DESC",
                    String.valueOf(diseaseCategry),
                    "0",
                    Comorbidities.ComorbiditiesGoals[0], Comorbidities.ComorbiditiesGoals[1], Comorbidities.ComorbiditiesGoals[2]
            );
        }
    }

    public static List<Goal> getAllWithoutComorbidities(int diseaseCategry, boolean dialysis) {

        if (dialysis) {
            return Goal.find(Goal.class, "min_category <= ?  and title_str NOT IN (?,?,?)",
                    String.valueOf(diseaseCategry),
                    Comorbidities.ComorbiditiesGoals[0], Comorbidities.ComorbiditiesGoals[1], Comorbidities.ComorbiditiesGoals[2]
            );
        } else {
            return Goal.find(Goal.class, "min_category <= ? and dialysis_only = ?  and title_str NOT IN (?,?,?)",
                    String.valueOf(diseaseCategry),
                    "0",
                    Comorbidities.ComorbiditiesGoals[0], Comorbidities.ComorbiditiesGoals[1], Comorbidities.ComorbiditiesGoals[2]
            );
        }
    }

    public static List<Goal> getAll(int diseaseCategry, boolean dialysis) {
        if (dialysis) {
            return Goal.find(Goal.class, "min_category <= ?", String.valueOf(diseaseCategry));
        } else {
            return Goal.find(Goal.class, "min_category <= ? and dialysis_only = ?",
                    String.valueOf(diseaseCategry),
                    "0");
        }
    }

    public static List<Goal> getByTitleStr(String goalTitleStr) {
        return Goal.find(Goal.class, "title_str = ?",
                goalTitleStr);
    }


}
