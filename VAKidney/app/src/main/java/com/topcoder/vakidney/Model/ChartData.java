package com.topcoder.vakidney.Model;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store chart data that can be used in three CHart Fragments
 */

public class ChartData {
    private String month;

    private double value;

    public ChartData(String month, double sugarLevel) {
        this.month = month;
        this.value = sugarLevel;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }



    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
