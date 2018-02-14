package com.topcoder.vakidney.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.topcoder.vakidney.AddNewMealActivity;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.R;

import java.util.ArrayList;

/**
 * Created by Abinash Neupane on 2/7/2018.
 */

/**
 * This adapter is used in FoodFragment. It is also used to populate gridview with meals from meals.json file
 */
public class FoodAdapter extends BaseAdapter {



    private ArrayList<Meal> mealArrayList;
    private Activity activity;

    public FoodAdapter(ArrayList<Meal> mealArrayList, Activity activity) {
        this.mealArrayList = mealArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mealArrayList.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return mealArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i==0){
            view=activity.getLayoutInflater().inflate(R.layout.item_grid_food_addnew, viewGroup, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    activity.startActivity(new Intent(activity, AddNewMealActivity.class));
                }
            });
        }else{
            view=activity.getLayoutInflater().inflate(R.layout.item_grid_food, viewGroup, false);
            TextView tvMealName=view.findViewById(R.id.tvMealName);
            TextView tvMealDesc=view.findViewById(R.id.tvMealDesc);

            Meal currentMeal=mealArrayList.get(i-1);
            tvMealName.setText(currentMeal.getName());
            tvMealDesc.setText(currentMeal.getDesc());
        }
        return view;
    }
}
