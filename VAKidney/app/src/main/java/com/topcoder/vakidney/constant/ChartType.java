package com.topcoder.vakidney.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by afrisalyp on 18/03/2018.
 */

public class ChartType {

    private final static Map<Integer, String> CHART_TITLE = new HashMap<>();
    private final static Map<Integer, List<Integer>> CHARTS_BY_CATEGORY = new HashMap<>();

    public final static int TYPE_E_GFR = 0x00000001;
    public final static int TYPE_PHOSPHORUS = 0x00000002;
    public final static int TYPE_POTASSIUM = 0x00000003;
    public final static int TYPE_CREATININE = 0x00000004;
    public final static int TYPE_CO2 = 0x00000005;
    public final static int TYPE_SODIUM = 0x00000006;
    public final static int TYPE_BUN = 0x00000007;
    public final static int TYPE_PTH = 0x00000008;
    public final static int TYPE_VITAMIN_D = 0x00000009;
    public final static int TYPE_IRON = 0x000000010;
    public final static int TYPE_HGBA1C = 0x000000011;
    public final static int TYPE_HGB = 0x000000012;

    private final static List<Integer> STAGE_1_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_2_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_3A_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_3B_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_4_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_5_CHARTS = new ArrayList<>();
    private final static List<Integer> STAGE_5D_CHARTS = new ArrayList<>();

    static {
        CHART_TITLE.put(TYPE_E_GFR, "eGFR");
        CHART_TITLE.put(TYPE_PHOSPHORUS, "Phosphorus");
        CHART_TITLE.put(TYPE_POTASSIUM, "Potassium");
        CHART_TITLE.put(TYPE_CREATININE, "creatinine");
        CHART_TITLE.put(TYPE_CO2, "Co2 (bicarb)");
        CHART_TITLE.put(TYPE_SODIUM, "sodium");
        CHART_TITLE.put(TYPE_BUN, "bun");
        CHART_TITLE.put(TYPE_PTH, "PTH");
        CHART_TITLE.put(TYPE_VITAMIN_D, "vitamin D");
        CHART_TITLE.put(TYPE_IRON, "iron profile");
        CHART_TITLE.put(TYPE_HGBA1C, "HgbA1C");
        CHART_TITLE.put(TYPE_HGB, "Hgb or Hct");

        STAGE_1_CHARTS.add(TYPE_E_GFR);
        STAGE_1_CHARTS.add(TYPE_CREATININE);
        STAGE_1_CHARTS.add(TYPE_BUN);

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

    public static List<Integer> getChartsByCategory(int category) {
        return CHARTS_BY_CATEGORY.get(category);
    }

}
