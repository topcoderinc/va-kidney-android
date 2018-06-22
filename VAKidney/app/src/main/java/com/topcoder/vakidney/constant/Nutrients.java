package com.topcoder.vakidney.constant;

import com.google.android.gms.fitness.data.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by afrisalyp on 13/04/2018.
 * This class contains Nutrient mapping for Google Fit Field
 */

public class Nutrients {

    private static Map<String, String> GOOGLE_FIT_NUTRIENT_KEY = new HashMap<>();

    static {
        GOOGLE_FIT_NUTRIENT_KEY.put("calcium", Field.NUTRIENT_CALCIUM);
        GOOGLE_FIT_NUTRIENT_KEY.put("vitamin c", Field.NUTRIENT_VITAMIN_C);
        GOOGLE_FIT_NUTRIENT_KEY.put("protein", Field.NUTRIENT_PROTEIN);
        GOOGLE_FIT_NUTRIENT_KEY.put("sodium", Field.NUTRIENT_SODIUM);
        GOOGLE_FIT_NUTRIENT_KEY.put("potassium", Field.NUTRIENT_POTASSIUM);
    }

    /**
     * Find Google Fit Nutrient Field key based on nutrient string.
     *
     * @param nutrient nutrient string.
     * @return String Google Fit Nutrient Field key.
     */
    public static String searchGoogleFitNutrientField(String nutrient) {
        for (String key : GOOGLE_FIT_NUTRIENT_KEY.keySet()) {
            if (nutrient.toLowerCase().contains(key)) {
                return GOOGLE_FIT_NUTRIENT_KEY.get(key);
            }
        }
        return null;
    }

    /**
     * Convert nutrient weight to grams
     *
     * @param unit  unit of the nutrients. (g, mg or pills).
     * @param value value of nutrient
     * @return float grams of nutrient.
     */
    public static float calculateNutrientGrams(String unit, float value) {
        if (unit.compareToIgnoreCase("g") == 0) {
            return value;
        } else if (unit.compareToIgnoreCase("mg") == 0) {
            return value / 1000;
        } else if (unit.compareToIgnoreCase("pills") == 0) {
            return value * 0.5f;
        }
        return value;
    }

}
