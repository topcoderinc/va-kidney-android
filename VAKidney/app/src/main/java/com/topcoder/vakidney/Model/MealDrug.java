package com.topcoder.vakidney.Model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by afrisalyp on 14/03/2018.
 */

public class MealDrug extends SugarRecord<MealDrug> implements Serializable {

    public final static int TYPE_MEAL = 0x00000001;
    public final static int TYPE_DRUG = 0x00000002;

    long mealId;
    String name;
    String unit;
    double amount;
    int type = TYPE_MEAL;

    public MealDrug() {}

    public MealDrug(long mealId, String name, String unit, double amount, int type) {
        this.mealId = mealId;
        this.name = name;
        this.unit = unit;
        this.amount = amount;
        this.type = type;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
