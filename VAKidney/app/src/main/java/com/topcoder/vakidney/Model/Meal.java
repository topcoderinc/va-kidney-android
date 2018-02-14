package com.topcoder.vakidney.Model;

import android.os.Bundle;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store data for different meals created by the user.
 */

public class Meal {

    private String name;
    private String desc;

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

    public Bundle getBundle(){
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        bundle.putString("desc", desc);
        return bundle;
    }

    public static Meal getMealFromBundle(Bundle bundle){
        Meal meal=new Meal();
        meal.setName(bundle.getString("name"));
        meal.setDesc(bundle.getString("desc"));
        return meal;
    }
}
