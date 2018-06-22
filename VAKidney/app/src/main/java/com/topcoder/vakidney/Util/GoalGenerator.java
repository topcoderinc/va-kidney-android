package com.topcoder.vakidney.util;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.Comorbidities;
import com.topcoder.vakidney.fragments.MyProfileFragment;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.constant.DiseaseCategory;

import java.util.List;


/**
 * Created by afrisalyp on 17/03/2018.
 */

public class GoalGenerator {

    private static Goal sGoalRun;
    private static Goal sGoalFluidIntake;
    private static Goal sGoalProteinIntake;
    private static Goal sGoalPotasiumIntake;
    private static Goal sGoalSodiumIntake;
    private static Goal sGoalPhosporusIntake;
    private static Goal sGoalMeatIntake;
    private static Goal sGoalFruitsIntake;
    private static Goal sGoalVitaminB1dIntake;
    private static Goal sGoalVitaminCIntake;
    private static Goal sGoalCalciumIntake;
    private static Goal goalBloodPressure, goalBloodGlucose, goalBodyWeight;


    public static void generateGoals() {
        sGoalCalciumIntake = new Goal();
        sGoalVitaminCIntake = new Goal();
        sGoalVitaminB1dIntake = new Goal();
        sGoalFruitsIntake = new Goal();
        sGoalMeatIntake = new Goal();
        sGoalPhosporusIntake = new Goal();
        sGoalSodiumIntake = new Goal();
        sGoalPotasiumIntake = new Goal();
        sGoalProteinIntake = new Goal();
        sGoalFluidIntake = new Goal();

        sGoalRun = new Goal();
        sGoalRun.setTitleStr("Workout");
        sGoalRun.setGoal(2.0);
        sGoalRun.setGoalMax(8.0);
        sGoalRun.setGoalMin(1.0);
        sGoalRun.setGoalStep(1.0);
        sGoalRun.setGoalId(1);
        sGoalRun.setUnitStr("minutes");
        sGoalRun.setColorCode("557630");
        sGoalRun.setType(Goal.TYPE_ACTIVITY);
        sGoalRun.setAction(Goal.ACTION_INTAKE);
        sGoalRun.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalRun.setIcon(R.drawable.ic_running);

        sGoalFruitsIntake.setTitleStr("Fruits/Veggies");
        sGoalFruitsIntake.setGoal(3);
        sGoalFruitsIntake.setGoalId(2);
        sGoalFruitsIntake.setGoalMax(10);
        sGoalFruitsIntake.setGoalMin(1);
        sGoalFruitsIntake.setGoalStep(1.0);
        sGoalFruitsIntake.setUnitStr("Servings");
        sGoalFruitsIntake.setColorCode("557630");
        sGoalFruitsIntake.setType(Goal.TYPE_GENERAL);
        sGoalFruitsIntake.setAction(Goal.ACTION_INTAKE);
        sGoalFruitsIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalFruitsIntake.setIcon(R.drawable.ic_fruits_veggies);

        sGoalVitaminB1dIntake.setTitleStr("Vitamin B1 Intake");
        sGoalVitaminB1dIntake.setGoal(3);
        sGoalVitaminB1dIntake.setGoalMax(10);
        sGoalVitaminB1dIntake.setGoalMin(1);
        sGoalVitaminB1dIntake.setGoalId(3);
        sGoalVitaminB1dIntake.setGoalStep(1.0);
        sGoalVitaminB1dIntake.setUnitStr("pills");
        sGoalVitaminB1dIntake.setColorCode("557630");
        sGoalVitaminB1dIntake.setType(Goal.TYPE_PILL);
        sGoalVitaminB1dIntake.setAction(Goal.ACTION_INTAKE);
        sGoalVitaminB1dIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_3A);
        sGoalVitaminB1dIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalVitaminCIntake.setTitleStr("Vitamin C Intake");
        sGoalVitaminCIntake.setGoal(3);
        sGoalVitaminCIntake.setGoalMax(10);
        sGoalVitaminCIntake.setGoalMin(1);
        sGoalVitaminCIntake.setGoalStep(1.0);
        sGoalVitaminCIntake.setUnitStr("pills");
        sGoalVitaminCIntake.setNutrient("Vitamin C");
        sGoalVitaminCIntake.setColorCode("557630");
        sGoalVitaminCIntake.setType(Goal.TYPE_PILL);
        sGoalVitaminCIntake.setAction(Goal.ACTION_INTAKE);
        sGoalVitaminCIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_3A);
        sGoalVitaminCIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalVitaminCIntake.setGoalId(4);


        sGoalFluidIntake.setTitleStr("Fluid Intake");
        sGoalFluidIntake.setGoal(8);
        sGoalFluidIntake.setGoalMax(12);
        sGoalFluidIntake.setGoalMin(4);
        sGoalFluidIntake.setGoalStep(1.0);
        sGoalFluidIntake.setUnitStr("Oz");
        sGoalFluidIntake.setColorCode("ff7f32");
        sGoalFluidIntake.setType(Goal.TYPE_FLUID);
        sGoalFluidIntake.setAction(Goal.ACTION_ADJUST);
        sGoalFluidIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalFluidIntake.setIcon(R.drawable.ic_water_bottle);
        sGoalFluidIntake.setGoalId(5);

        sGoalProteinIntake.setTitleStr("Protein Intake");
        sGoalProteinIntake.setGoal(50);
        sGoalProteinIntake.setGoalMax(100);
        sGoalProteinIntake.setGoalMin(10);
        sGoalProteinIntake.setGoalStep(10);
        sGoalProteinIntake.setUnitStr("g");
        sGoalProteinIntake.setColorCode("ff7f32");
        sGoalProteinIntake.setNutrient("Protein");
        sGoalProteinIntake.setType(Goal.TYPE_GENERAL);
        sGoalProteinIntake.setAction(Goal.ACTION_ADJUST);
        sGoalProteinIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalProteinIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalProteinIntake.setGoalId(6);

        sGoalMeatIntake.setTitleStr("Meat Intake");
        sGoalMeatIntake.setGoal(1);
        sGoalMeatIntake.setGoalMax(5);
        sGoalMeatIntake.setGoalMin(1);
        sGoalMeatIntake.setGoalStep(1);
        sGoalMeatIntake.setUnitStr("Oz");
        sGoalMeatIntake.setColorCode("ff7f32");
        sGoalMeatIntake.setType(Goal.TYPE_PIECE);
        sGoalMeatIntake.setAction(Goal.ACTION_ADJUST);
        sGoalMeatIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalMeatIntake.setIcon(R.drawable.ic_addmeal);
        sGoalMeatIntake.setGoalId(7);

        sGoalCalciumIntake.setTitleStr("Calcium Intake");
        sGoalCalciumIntake.setGoal(1000);
        sGoalCalciumIntake.setGoalMax(5000);
        sGoalCalciumIntake.setGoalMin(500);
        sGoalCalciumIntake.setGoalStep(500);
        sGoalCalciumIntake.setUnitStr("mg");
        sGoalCalciumIntake.setColorCode("ff7f32");
        sGoalCalciumIntake.setNutrient("Calcium");
        sGoalCalciumIntake.setType(Goal.TYPE_GENERAL);
        sGoalCalciumIntake.setAction(Goal.ACTION_ADJUST);
        sGoalCalciumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_5);
        sGoalCalciumIntake.setDialysisOnly(true);
        sGoalCalciumIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalCalciumIntake.setGoalId(8);


        sGoalSodiumIntake.setTitleStr("Sodium Intake");
        sGoalSodiumIntake.setGoal(1500);
        sGoalSodiumIntake.setGoalMax(2000);
        sGoalSodiumIntake.setGoalMin(100);
        sGoalSodiumIntake.setGoalStep(100);
        sGoalSodiumIntake.setUnitStr("mg");
        sGoalSodiumIntake.setColorCode("cc0000");
        sGoalSodiumIntake.setNutrient("Sodium");
        sGoalSodiumIntake.setType(Goal.TYPE_GENERAL);
        sGoalSodiumIntake.setAction(Goal.ACTION_REDUCE);
        sGoalSodiumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalSodiumIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalSodiumIntake.setGoalId(10);
        sGoalPotasiumIntake.setTitleStr("Potassium Intake");
        sGoalPotasiumIntake.setGoal(2000);
        sGoalPotasiumIntake.setGoalMax(4000);
        sGoalPotasiumIntake.setGoalMin(1000);
        sGoalPotasiumIntake.setGoalStep(100);
        sGoalPotasiumIntake.setUnitStr("mg");
        sGoalPotasiumIntake.setColorCode("cc0000");
        sGoalPotasiumIntake.setNutrient("Potassium");
        sGoalPotasiumIntake.setType(Goal.TYPE_GENERAL);
        sGoalPotasiumIntake.setAction(Goal.ACTION_REDUCE);
        sGoalPotasiumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalPotasiumIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalPotasiumIntake.setGoalId(11);
        sGoalPhosporusIntake.setTitleStr("Phosphorus Intake");
        sGoalPhosporusIntake.setGoal(500);
        sGoalPhosporusIntake.setGoalMax(1000);
        sGoalPhosporusIntake.setGoalMin(100);
        sGoalPhosporusIntake.setGoalStep(100);
        sGoalPhosporusIntake.setUnitStr("mg");
        sGoalPhosporusIntake.setColorCode("cc0000");
        sGoalPhosporusIntake.setNutrient("Phosphorus");
        sGoalPhosporusIntake.setType(Goal.TYPE_GENERAL);
        sGoalPhosporusIntake.setAction(Goal.ACTION_REDUCE);
        sGoalPhosporusIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);
        sGoalPhosporusIntake.setIcon(R.drawable.ic_bar_medication);
        sGoalPhosporusIntake.setGoalId(12);

        sGoalRun.save();
        sGoalFruitsIntake.save();
        sGoalVitaminB1dIntake.save();
        sGoalVitaminCIntake.save();

        sGoalFluidIntake.save();
        sGoalProteinIntake.save();
        sGoalMeatIntake.save();
        sGoalCalciumIntake.save();

        sGoalSodiumIntake.save();
        sGoalPotasiumIntake.save();
        sGoalPhosporusIntake.save();
    }

    public static void addComorbiditiesGoals(String comorbidity) {
        switch (comorbidity) {
            case "Blood Pressure": {
                goalBloodPressure = new Goal();
                goalBloodPressure.setGoalId(104);
                goalBloodPressure.setTitleStr("Blood Pressure");
                goalBloodPressure.setIcon(R.drawable.ic_pressure);
                goalBloodPressure.setUnitStr("mmHg");
                goalBloodPressure.setColorCode("800080");
                goalBloodPressure.setGoalMin(10);
                goalBloodPressure.setGoalMax(10);
                goalBloodPressure.setGoal(10);
                goalBloodPressure.save();
            }
            break;
            case "Blood Glucose": {
                goalBloodGlucose = new Goal();
                goalBloodGlucose.setGoalId(105);
                goalBloodGlucose.setTitleStr("Blood Glucose");
                goalBloodGlucose.setIcon(R.drawable.ic_blood);
                goalBloodGlucose.setUnitStr("mg/dL");
                goalBloodGlucose.setColorCode("ff7f32");
                goalBloodGlucose.setGoalMin(30);
                goalBloodGlucose.setGoal(300);
                goalBloodGlucose.setGoalMax(300);
                goalBloodGlucose.save();
            }
            break;
            case "Body Weight": {
                goalBodyWeight = new Goal();
                goalBodyWeight.setGoalId(103);
                goalBodyWeight.setTitleStr("Body Weight");
                goalBodyWeight.setIcon(R.drawable.ic_weight_loss);
                goalBodyWeight.setUnitStr("pounds");
                goalBodyWeight.setColorCode("f3cf45");
                goalBodyWeight.setGoalMin(50);
                goalBodyWeight.setGoal(300);
                goalBodyWeight.setGoalMax(300);
                goalBodyWeight.save();
            }
            break;
            default:
                break;
        }

    }

    public static void removeComorbiditiesGoals(String comorbidity) {
        switch (comorbidity) {
            case "Blood Pressure":
            case "Blood Glucose":
            case "Body Weight":
                List<Goal> goal=Goal.getByTitleStr(comorbidity);
                if (goal.size()==1) {
                    goal.get(0).delete();
                }
                break;
            default:
                break;
        }

    }

    public static void removeGoals() {
        Goal.executeQuery("Delete from Goal where title_str NOT IN (?,?,?)", Comorbidities.ComorbiditiesGoals);
    }

    public static void addOrRemoveComorbidities(boolean[] checkedItems) {
        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                if (Goal.getByTitleStr(Comorbidities.ComorbiditiesGoals[i]).size() == 0) {
                    addComorbiditiesGoals(Comorbidities.ComorbiditiesGoals[i]);
                }
            } else {
                if (Goal.getByTitleStr(Comorbidities.ComorbiditiesGoals[i]).size() == 1) {
                    removeComorbiditiesGoals(Comorbidities.ComorbiditiesGoals[i]);
                }
            }
        }


    }
}
