package com.topcoder.vakidney.constant;

import com.topcoder.vakidney.model.Goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by afrisalyp on 18/03/2018.
 */

public class ChartType {

    public static class ChartThreshold {
        float max;
        float min;

        ChartThreshold(float min, float max) {
            this.min = min;
            this.max = max;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }
    }

    private final static Map<Long, String> CHART_TITLE = new HashMap<>();
    private final static Map<Long, String> CHART_UNIT = new HashMap<>();
    private final static Map<Long, ChartThreshold> CHART_THRESHOLD = new HashMap<>();
    private final static Map<Integer, List<Long>> CHARTS_BY_CATEGORY = new HashMap<>();

    public final static long TYPE_E_GFR = 0x00000001;
    public final static long TYPE_PHOSPHORUS = 0x00000002;
    public final static long TYPE_POTASSIUM = 0x00000003;
    public final static long TYPE_CREATININE = 0x00000004;
    public final static long TYPE_CO2 = 0x00000005;
    public final static long TYPE_SODIUM = 0x00000006;
    public final static long TYPE_BUN = 0x00000007;
    public final static long TYPE_PTH = 0x00000008;
    public final static long TYPE_VITAMIN_D = 0x00000009;
    public final static long TYPE_IRON = 0x000000010;
    public final static long TYPE_HGBA1C = 0x000000011;
    public final static long TYPE_HGB = 0x000000012;

    private final static List<Long> STAGE_1_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_2_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_3A_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_3B_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_4_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_5_CHARTS = new ArrayList<>();
    private final static List<Long> STAGE_5D_CHARTS = new ArrayList<>();

    static {
        CHART_TITLE.put(TYPE_E_GFR, "eGFR");
        CHART_TITLE.put(TYPE_PHOSPHORUS, "Phosphorus");
        CHART_TITLE.put(TYPE_POTASSIUM, "Potassium");
        CHART_TITLE.put(TYPE_CREATININE, "Creatinine");
        CHART_TITLE.put(TYPE_CO2, "Co2 (Bicarb)");
        CHART_TITLE.put(TYPE_SODIUM, "Sodium");
        CHART_TITLE.put(TYPE_BUN, "BUN");
        CHART_TITLE.put(TYPE_PTH, "PTH");
        CHART_TITLE.put(TYPE_VITAMIN_D, "Vitamin D");
        CHART_TITLE.put(TYPE_IRON, "Iron Profile");
        CHART_TITLE.put(TYPE_HGBA1C, "HgbA1C");
        CHART_TITLE.put(TYPE_HGB, "Hgb or Hct");

        CHART_THRESHOLD.put(TYPE_E_GFR, new ChartThreshold(90, 120));
        CHART_THRESHOLD.put(TYPE_PHOSPHORUS, new ChartThreshold(2.5f, 4.5f));
        CHART_THRESHOLD.put(TYPE_POTASSIUM, new ChartThreshold(3.5f, 5));
        CHART_THRESHOLD.put(TYPE_CREATININE, new ChartThreshold(0.6f, 1.2f));
        CHART_THRESHOLD.put(TYPE_CO2, new ChartThreshold(23, 29));
        CHART_THRESHOLD.put(TYPE_SODIUM, new ChartThreshold(135, 145));
        CHART_THRESHOLD.put(TYPE_BUN, new ChartThreshold(5, 20));
        CHART_THRESHOLD.put(TYPE_PTH, new ChartThreshold(10, 65));
        CHART_THRESHOLD.put(TYPE_VITAMIN_D, new ChartThreshold(20, 50));
        CHART_THRESHOLD.put(TYPE_IRON, new ChartThreshold(60, 170));
        CHART_THRESHOLD.put(TYPE_HGBA1C, new ChartThreshold(4, 6.4f));
        CHART_THRESHOLD.put(TYPE_HGB, new ChartThreshold(12, 17.5f));

        CHART_UNIT.put(TYPE_E_GFR, "mL/min/1.73 m2");
        CHART_UNIT.put(TYPE_PHOSPHORUS, "mg/dL");
        CHART_UNIT.put(TYPE_POTASSIUM, "mEq/L");
        CHART_UNIT.put(TYPE_CREATININE, "mg/dL");
        CHART_UNIT.put(TYPE_CO2, "mEq/L");
        CHART_UNIT.put(TYPE_SODIUM, "mEq/L");
        CHART_UNIT.put(TYPE_BUN, "mg/dL");
        CHART_UNIT.put(TYPE_PTH, "pg/mL");
        CHART_UNIT.put(TYPE_VITAMIN_D, "ng/mL");
        CHART_UNIT.put(TYPE_IRON, "mcg/dL");
        CHART_UNIT.put(TYPE_HGBA1C, "%");
        CHART_UNIT.put(TYPE_HGB, "grams/dL");

        STAGE_1_CHARTS.add(TYPE_E_GFR);
        STAGE_1_CHARTS.add(TYPE_CREATININE);
        STAGE_1_CHARTS.add(TYPE_BUN);

        STAGE_2_CHARTS.add(TYPE_E_GFR);
        STAGE_2_CHARTS.add(TYPE_CREATININE);
        STAGE_2_CHARTS.add(TYPE_BUN);

        STAGE_3A_CHARTS.add(TYPE_E_GFR);
        STAGE_3A_CHARTS.add(TYPE_POTASSIUM);
        STAGE_3A_CHARTS.add(TYPE_CREATININE);
        STAGE_3A_CHARTS.add(TYPE_CO2);
        STAGE_3A_CHARTS.add(TYPE_BUN);
        STAGE_3A_CHARTS.add(TYPE_VITAMIN_D);

        STAGE_3B_CHARTS.add(TYPE_E_GFR);
        STAGE_3B_CHARTS.add(TYPE_PHOSPHORUS);
        STAGE_3B_CHARTS.add(TYPE_POTASSIUM);
        STAGE_3B_CHARTS.add(TYPE_CREATININE);
        STAGE_3B_CHARTS.add(TYPE_CO2);
        STAGE_3B_CHARTS.add(TYPE_BUN);
        STAGE_3B_CHARTS.add(TYPE_VITAMIN_D);
        STAGE_3B_CHARTS.add(TYPE_PTH);
        STAGE_3B_CHARTS.add(TYPE_IRON);
        STAGE_3B_CHARTS.add(TYPE_HGB);

        STAGE_4_CHARTS.add(TYPE_E_GFR);
        STAGE_4_CHARTS.add(TYPE_PHOSPHORUS);
        STAGE_4_CHARTS.add(TYPE_POTASSIUM);
        STAGE_4_CHARTS.add(TYPE_SODIUM);
        STAGE_4_CHARTS.add(TYPE_CREATININE);
        STAGE_4_CHARTS.add(TYPE_CO2);
        STAGE_4_CHARTS.add(TYPE_BUN);
        STAGE_4_CHARTS.add(TYPE_VITAMIN_D);
        STAGE_4_CHARTS.add(TYPE_PTH);
        STAGE_4_CHARTS.add(TYPE_IRON);
        STAGE_4_CHARTS.add(TYPE_HGB);

        STAGE_5_CHARTS.add(TYPE_E_GFR);
        STAGE_5_CHARTS.add(TYPE_PHOSPHORUS);
        STAGE_5_CHARTS.add(TYPE_POTASSIUM);
        STAGE_5_CHARTS.add(TYPE_SODIUM);
        STAGE_5_CHARTS.add(TYPE_CREATININE);
        STAGE_5_CHARTS.add(TYPE_CO2);
        STAGE_5_CHARTS.add(TYPE_BUN);
        STAGE_5_CHARTS.add(TYPE_VITAMIN_D);
        STAGE_5_CHARTS.add(TYPE_PTH);
        STAGE_5_CHARTS.add(TYPE_IRON);
        STAGE_5_CHARTS.add(TYPE_HGB);

        STAGE_5D_CHARTS.add(TYPE_PHOSPHORUS);
        STAGE_5D_CHARTS.add(TYPE_POTASSIUM);
        STAGE_5D_CHARTS.add(TYPE_SODIUM);
        STAGE_5D_CHARTS.add(TYPE_CO2);
        STAGE_5D_CHARTS.add(TYPE_BUN);
        STAGE_5D_CHARTS.add(TYPE_VITAMIN_D);
        STAGE_5D_CHARTS.add(TYPE_PTH);
        STAGE_5D_CHARTS.add(TYPE_IRON);
        STAGE_5D_CHARTS.add(TYPE_HGB);

        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_1, STAGE_1_CHARTS);
        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_2, STAGE_2_CHARTS);
        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_3A, STAGE_3A_CHARTS);
        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_3B, STAGE_3B_CHARTS);
        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_4, STAGE_4_CHARTS);
        CHARTS_BY_CATEGORY.put(DiseaseCategory.CATEGORY_STAGE_5, STAGE_5_CHARTS);
    }

    public static List<Long> getChartsByCategory(int category, boolean dialysis) {
        if (dialysis) return STAGE_5D_CHARTS;
        else return CHARTS_BY_CATEGORY.get(category);
    }

    public static String getChartLabel(long chartType) {
        if (!CHART_TITLE.containsKey(chartType)) {
            List<Goal> goals = Goal.find(Goal.class, "goal_id = ?", String.valueOf(chartType));
            if (goals.size() > 0) {
                return goals.get(0).getTitleStr();
            }
            return null;
        }
        return CHART_TITLE.get(chartType);
    }

    public static String getChartUnit(long chartType) {
        if (!CHART_UNIT.containsKey(chartType)) {
            List<Goal> goals = Goal.find(Goal.class, "goal_id = ?", String.valueOf(chartType));
            if (goals.size() > 0) {
                return goals.get(0).getUnitStr();
            }
            return null;
        }
        return CHART_UNIT.get(chartType);
    }

    public static ChartThreshold getChartThreshold(long chartType) {
        if (!CHART_THRESHOLD.containsKey(chartType)) {
            List<Goal> goals = Goal.find(Goal.class, "goal_id = ?", String.valueOf(chartType));
            if (goals.size() > 0) {
                if (goals.get(0).getAction() == Goal.ACTION_REDUCE) {
                    return new ChartThreshold((float) goals.get(0).getGoalMin(), (float) goals.get(0).getGoal());
                }
                else {
                    return new ChartThreshold((float) goals.get(0).getGoal(), (float) goals.get(0).getGoalMax());
                }
            }
            return null;
        }
        return CHART_THRESHOLD.get(chartType);
    }
}
