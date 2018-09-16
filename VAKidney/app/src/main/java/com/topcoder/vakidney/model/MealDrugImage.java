package com.topcoder.vakidney.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class MealDrugImage extends SugarRecord<MealDrugImage> implements Serializable {

    long mealId;
    String url;

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
