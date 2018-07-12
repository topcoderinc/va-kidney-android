package com.topcoder.vakidney.constant;

import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by afrisalyp on 17/03/2018.
 */

public class GoalType {

    private final static Map<Integer, Integer> ICONS_MAP = new HashMap<>();
    private final static Map<Integer, String> ADD_TITLE_MAP = new HashMap<>();

    static {
        ICONS_MAP.put(Goal.TYPE_ACTIVITY, R.drawable.ic_running);
        ICONS_MAP.put(Goal.TYPE_PILL, R.drawable.ic_bar_medication);
        ICONS_MAP.put(Goal.TYPE_FLUID, R.drawable.ic_water_bottle);
        ICONS_MAP.put(Goal.TYPE_PIECE, R.drawable.ic_fruits_veggies);
        ICONS_MAP.put(Goal.TYPE_GENERAL, R.drawable.ic_fruits_veggies);

        ADD_TITLE_MAP.put(Goal.TYPE_ACTIVITY, "Sync Now");
        ADD_TITLE_MAP.put(Goal.TYPE_PILL, "Add Pill");
        ADD_TITLE_MAP.put(Goal.TYPE_FLUID, "Add glass");
        ADD_TITLE_MAP.put(Goal.TYPE_PIECE, "Add piece");
        ADD_TITLE_MAP.put(Goal.TYPE_GENERAL, "Add");

    }

    public static int getIcon(int type) {
        if (!ICONS_MAP.containsKey(type)) return R.drawable.ic_bar_medication;
        return ICONS_MAP.get(type);
    }

    public static String getAddTitle(int type) {
        return ADD_TITLE_MAP.get(type);
    }

}
