package com.topcoder.vakidney.Model;

/**
 * Created by abina on 2/7/2018.
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
