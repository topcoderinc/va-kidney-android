package com.topcoder.vakidney.constant;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.model.Goal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by afrisalyp on 17/03/2018.
 */

public class GoalFrequency {

    public final static String[] LABELS = new String[]{
            "Daily",
            "Weekly",
            "Monthly",
    };

    public final static int getFrequencyId(String label) {
        for (int i=0; LABELS.length>i; i++) {
            if (LABELS[i].equals(label)) {
                return i;
            }
        }
        return 0;
    }

    public final static String getFrequencyString(int id) {
        if (id >= 0 && id < LABELS.length) {
            return LABELS[id];
        }
        return "";
    }


}
