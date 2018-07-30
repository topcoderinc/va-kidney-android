package com.topcoder.vakidney.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.topcoder.vakidney.constant.Comorbidities;
import com.topcoder.vakidney.model.Goal;
import com.topcoder.vakidney.model.Meal;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.model.UserData;

/**
 * Created by Abinash Neupane on 2/9/2018.
 * This class is used for managing login and task agreement
 */

public class LoginManager {
    private static String PREF_LOGGING = "loggingPreference";
    private static String IS_LOGGED_IN = "isLoggedIn";
    private static String PREF_TERMS_AGREED = "termsAgreed";
    private static String IS_AGREED = "isAgreed";

    /**
     * Checks if an user is logged In Or Not
     *
     * @param context
     * @return a boolean value: true for logged in, false for logged out
     */
    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGGING, 0);
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    /**
     * Logs In or Log Out an user
     *
     * @param context
     * @param isLoggedIn
     */
    public static void setLoggedIn(Context context, boolean isLoggedIn, UserData userData) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGGING, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
        if (isLoggedIn) {
            initializeData(context);
            userData.logIn();
        } else {
//            Meal.deleteAll(Meal.class);
//            MealDrug.deleteAll(MealDrug.class);
            Goal.deleteAll(Goal.class);
            userData.logOut();
        }
    }

    private static void initializeData(Context context) {
//        JsondataUtil.getMeals(context);
        GoalGenerator.generateGoals();
    }

    /**
     * Checks if user has agreed the terms or not
     *
     * @param context
     * @return a boolean value: true for terms agreed, false for terms disagreed
     */
    public static boolean isTermsAgreed(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_TERMS_AGREED, 0);
        return preferences.getBoolean(IS_AGREED, false);
    }

    /**
     * Sets if the user has agreed on terms or they have disagreed the terms
     *
     * @param context
     * @param isTermsAgreed
     */
    public static void setTermsAgreed(Context context, boolean isTermsAgreed) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_TERMS_AGREED, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_AGREED, isTermsAgreed);
        editor.commit();
    }

}
