package com.topcoder.vakidney.Util;

import com.topcoder.vakidney.Model.Goal;
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
        sGoalRun.setGoal(6.2);
        sGoalRun.setUnitStr("miles");
        sGoalRun.setColorCode("557630");
        sGoalRun.setType(Goal.TYPE_ACTIVITY);
        sGoalRun.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalFluidIntake.setTitleStr("Fluid Intake");
        sGoalFluidIntake.setGoal(8);
        sGoalFluidIntake.setUnitStr("glasses");
        sGoalFluidIntake.setColorCode("557630");
        sGoalFluidIntake.setType(Goal.TYPE_FLUID);
        sGoalFluidIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalProteinIntake.setTitleStr("Protein Intake");
        sGoalProteinIntake.setGoal(3);
        sGoalProteinIntake.setUnitStr("pills");
        sGoalProteinIntake.setColorCode("557630");
        sGoalProteinIntake.setType(Goal.TYPE_PILL);
        sGoalProteinIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalPotasiumIntake.setTitleStr("Potasium Intake");
        sGoalPotasiumIntake.setGoal(3);
        sGoalPotasiumIntake.setUnitStr("pills");
        sGoalPotasiumIntake.setColorCode("557630");
        sGoalPotasiumIntake.setType(Goal.TYPE_PILL);
        sGoalPotasiumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalSodiumIntake.setTitleStr("Sodium Intake");
        sGoalSodiumIntake.setGoal(3);
        sGoalSodiumIntake.setUnitStr("pills");
        sGoalSodiumIntake.setColorCode("557630");
        sGoalSodiumIntake.setType(Goal.TYPE_PILL);
        sGoalSodiumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalPhosporusIntake.setTitleStr("Phosporus Intake");
        sGoalPhosporusIntake.setGoal(3);
        sGoalPhosporusIntake.setUnitStr("pills");
        sGoalPhosporusIntake.setColorCode("557630");
        sGoalPhosporusIntake.setType(Goal.TYPE_PILL);
        sGoalPhosporusIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalAlcoholIntake.setTitleStr("Alcohol Intake");
        sGoalAlcoholIntake.setGoal(3);
        sGoalAlcoholIntake.setUnitStr("glasses");
        sGoalAlcoholIntake.setColorCode("557630");
        sGoalAlcoholIntake.setType(Goal.TYPE_FLUID);
        sGoalAlcoholIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalMeatIntake.setTitleStr("Meat Intake");
        sGoalMeatIntake.setGoal(3);
        sGoalMeatIntake.setUnitStr("pieces");
        sGoalMeatIntake.setColorCode("557630");
        sGoalMeatIntake.setType(Goal.TYPE_PIECE);
        sGoalMeatIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalFruitsIntake.setTitleStr("Fruits/Veggies");
        sGoalFruitsIntake.setGoal(3);
        sGoalFruitsIntake.setUnitStr("bowls");
        sGoalFruitsIntake.setColorCode("557630");
        sGoalFruitsIntake.setType(Goal.TYPE_GENERAL);
        sGoalFruitsIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_1);

        sGoalVitaminB1dIntake.setTitleStr("Vitamin B1 Intake");
        sGoalVitaminB1dIntake.setGoal(3);
        sGoalVitaminB1dIntake.setUnitStr("pills");
        sGoalVitaminB1dIntake.setColorCode("557630");
        sGoalVitaminB1dIntake.setType(Goal.TYPE_PILL);
        sGoalVitaminB1dIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_3A);

        sGoalVitaminCIntake.setTitleStr("Vitamin C Intake");
        sGoalVitaminCIntake.setGoal(3);
        sGoalVitaminCIntake.setUnitStr("pills");
        sGoalVitaminCIntake.setColorCode("557630");
        sGoalVitaminCIntake.setType(Goal.TYPE_PILL);
        sGoalVitaminCIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_3A);

        sGoalCalciumIntake.setTitleStr("Calcium Intake");
        sGoalCalciumIntake.setGoal(3);
        sGoalCalciumIntake.setUnitStr("pills");
        sGoalCalciumIntake.setColorCode("557630");
        sGoalCalciumIntake.setType(Goal.TYPE_PILL);
        sGoalCalciumIntake.setMinCategory(DiseaseCategory.CATEGORY_STAGE_5);
        sGoalCalciumIntake.setDialysisOnly(true);

    }

    public static void generateGoals() {
        sGoalRun.save();
        sGoalFluidIntake.save();
        sGoalProteinIntake.save();
        sGoalPotasiumIntake.save();
        sGoalSodiumIntake.save();
        sGoalPhosporusIntake.save();
        sGoalAlcoholIntake.save();
        sGoalMeatIntake.save();
        sGoalFruitsIntake.save();
        sGoalVitaminB1dIntake.save();
        sGoalVitaminCIntake.save();
        sGoalCalciumIntake.save();
    }
}
