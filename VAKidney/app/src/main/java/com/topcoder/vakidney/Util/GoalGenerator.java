package com.topcoder.vakidney.util;

import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.constant.DiseaseCategory;


/**
 * Created by afrisalyp on 17/03/2018.
 */

public class GoalGenerator {

    private final static Goal sGoalRun = new Goal();
    private final static Goal sGoalFluidIntake = new Goal();
    private final static Goal sGoalProteinIntake = new Goal();
    private final static Goal sGoalPotasiumIntake = new Goal();
    private final static Goal sGoalSodiumIntake = new Goal();
    private final static Goal sGoalPhosporusIntake = new Goal();
    private final static Goal sGoalAlcoholIntake = new Goal();
    private final static Goal sGoalMeatIntake = new Goal();
    private final static Goal sGoalFruitsIntake = new Goal();
    private final static Goal sGoalVitaminB1dIntake = new Goal();
    private final static Goal sGoalVitaminCIntake = new Goal();
    private final static Goal sGoalCalciumIntake = new Goal();

    static {
        sGoalRun.setTitleStr("Run");
        sGoalRun.setGoal(2.0);
        sGoalRun.setGoalMax(8.0);
        sGoalRun.setGoalMin(1.0);
        sGoalRun.setGoalStep(1.0);
        sGoalRun.setUnitStr("miles");
        sGoalRun.setColorCode("557630");
        sGoalRun.setType(Goal.TYPE_ACTIVITY);
        sGoalRun.setAction(Goal.ACTION_INTAKE);
        sGoalRun.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalFruitsIntake.setTitleStr("Fruits/Veggies");
        sGoalFruitsIntake.setGoal(3);
        sGoalFruitsIntake.setGoalMax(10);
        sGoalFruitsIntake.setGoalMin(1);
        sGoalFruitsIntake.setGoalStep(1.0);
        sGoalFruitsIntake.setUnitStr("bowls");
        sGoalFruitsIntake.setColorCode("557630");
        sGoalFruitsIntake.setType(Goal.TYPE_GENERAL);
        sGoalFruitsIntake.setAction(Goal.ACTION_INTAKE);
        sGoalFruitsIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalVitaminB1dIntake.setTitleStr("Vitamin B1 Intake");
        sGoalVitaminB1dIntake.setGoal(3);
        sGoalVitaminB1dIntake.setGoalMax(10);
        sGoalVitaminB1dIntake.setGoalMin(1);
        sGoalVitaminB1dIntake.setGoalStep(1.0);
        sGoalVitaminB1dIntake.setUnitStr("pills");
        sGoalVitaminB1dIntake.setColorCode("557630");
        sGoalVitaminB1dIntake.setType(Goal.TYPE_PILL);
        sGoalVitaminB1dIntake.setAction(Goal.ACTION_INTAKE);
        sGoalVitaminB1dIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_3A);

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


        sGoalFluidIntake.setTitleStr("Fluid Intake");
        sGoalFluidIntake.setGoal(8);
        sGoalFluidIntake.setGoalMax(12);
        sGoalFluidIntake.setGoalMin(4);
        sGoalFluidIntake.setGoalStep(1.0);
        sGoalFluidIntake.setUnitStr("glasses");
        sGoalFluidIntake.setColorCode("ff7f32");
        sGoalFluidIntake.setType(Goal.TYPE_FLUID);
        sGoalFluidIntake.setAction(Goal.ACTION_ADJUST);
        sGoalFluidIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

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

        sGoalMeatIntake.setTitleStr("Meat Intake");
        sGoalMeatIntake.setGoal(1);
        sGoalMeatIntake.setGoalMax(5);
        sGoalMeatIntake.setGoalMin(1);
        sGoalMeatIntake.setGoalStep(1);
        sGoalMeatIntake.setUnitStr("pieces");
        sGoalMeatIntake.setColorCode("ff7f32");
        sGoalMeatIntake.setType(Goal.TYPE_PIECE);
        sGoalMeatIntake.setAction(Goal.ACTION_ADJUST);
        sGoalMeatIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

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


        sGoalAlcoholIntake.setTitleStr("Alcohol Intake");
        sGoalAlcoholIntake.setGoal(0);
        sGoalAlcoholIntake.setGoalMax(3);
        sGoalAlcoholIntake.setGoalMin(0);
        sGoalAlcoholIntake.setGoalStep(1);
        sGoalAlcoholIntake.setUnitStr("glasses");
        sGoalAlcoholIntake.setColorCode("cc0000");
        sGoalAlcoholIntake.setNutrient("Alcohol");
        sGoalAlcoholIntake.setType(Goal.TYPE_FLUID);
        sGoalAlcoholIntake.setAction(Goal.ACTION_REDUCE);
        sGoalAlcoholIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

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

    }

    public static void generateGoals() {
        sGoalRun.save();
        sGoalFruitsIntake.save();
        sGoalVitaminB1dIntake.save();
        sGoalVitaminCIntake.save();

        sGoalFluidIntake.save();
        sGoalProteinIntake.save();
        sGoalMeatIntake.save();
        sGoalCalciumIntake.save();

        sGoalAlcoholIntake.save();
        sGoalSodiumIntake.save();
        sGoalPotasiumIntake.save();
        sGoalPhosporusIntake.save();
    }
}
