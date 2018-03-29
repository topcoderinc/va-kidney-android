package com.topcoder.vakidney.model;

/**
 * Created by Abinash Neupane on 2/8/2018.
 * Labdata class is used to store the data of different lab attributes such as Blood Sugar, Body Weight etc
 */

public class LabData {

    private String name;
    private String currentValue;
    private String unit;
    private int standing;
    private int trend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public int getTrend() {
        return trend;
    }

    public void setTrend(int trend) {
        this.trend = trend;
    }
}
