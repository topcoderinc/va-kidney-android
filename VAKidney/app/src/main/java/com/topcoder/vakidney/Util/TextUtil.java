package com.topcoder.vakidney.util;

/**
 * Created by afrisalyp on 25/03/2018.
 */

public class TextUtil {

    public static String capitalizeFirstLetter(String string) {
        String cap = string.substring(0, 1).toUpperCase() + string.substring(1);
        return cap;
    }

}
