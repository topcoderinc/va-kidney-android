package com.topcoder.vakidney.Model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

public class FoodRecommendation extends SugarRecord<FoodRecommendation>
    implements Serializable{

    public static final int TYPE_UNSAFE = 0x00000001;
    public static final int TYPE_GOOD = 0x00000002;

    private String name;
    private String desc;
    private String ndbno;
    private double amount;
    private String unit;
    private int type;
    private String nutritionArray;

    public FoodRecommendation() {}

    public FoodRecommendation(
            String name,
            String desc,
            String ndbno,
            double amount,
            String unit,
            int type,
            String nutritionArray) {
        this.name = name;
        this.desc = desc;
        this.ndbno = ndbno;
        this.amount = amount;
        this.unit = unit;
        this.type = type;
        this.nutritionArray = nutritionArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNutritionArray() {
        return nutritionArray;
    }

    public void setNutritionArray(String nutritionArray) {
        this.nutritionArray = nutritionArray;
    }

    public static List<FoodRecommendation> getByName(String name) {
        return FoodRecommendation.find(FoodRecommendation.class, "name = ?", name);
    }

    public static List<FoodRecommendation> getUnsafe() {
        return FoodRecommendation.find(
                FoodRecommendation.class,
                "type = ?",
                String.valueOf(TYPE_UNSAFE));
    }
}
