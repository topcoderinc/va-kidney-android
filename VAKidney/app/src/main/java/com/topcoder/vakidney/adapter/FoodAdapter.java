package com.topcoder.vakidney.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.topcoder.vakidney.AddNewMealActivity;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.model.Meal;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.model.MealDrugImage;

import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This adapter is used in FoodFragment. It is also used to populate gridview with meals from meals.json file
 */
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meal> mealArrayList;
    private Activity activity;

    public FoodAdapter(List<Meal> mealArrayList, Activity activity) {
        this.mealArrayList = mealArrayList;
        this.activity = activity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new FoodAdapter.ViewHolder1(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_grid_food_addnew, parent, false));

            case 2:
                return new FoodAdapter.ViewHolder2(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_grid_food, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1: {
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                Typeface typeface = ResourcesCompat.getFont(activity, R.font.nexa_bold);
                viewHolder1.tvAddMeal.setTypeface(typeface);

                viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                        activity.startActivity(new Intent(activity, AddNewMealActivity.class));
                    }
                });
            }
            break;
            case 2: {
                final ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                Meal currentMeal = mealArrayList.get(position - 1);

                View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            Meal meal = mealArrayList.get(viewHolder2.getAdapterPosition() - 1);
                            activity.finish();
                            Intent intent = new Intent(activity, AddNewMealActivity.class);
                            intent.putExtra("meal", meal);
                            activity.startActivity(intent);
                        }
                        return true;
                    }
                };

                viewHolder2.itemView.setOnTouchListener(onTouchListener);
                viewHolder2.mealDrugImageRecycler.setOnTouchListener(onTouchListener);

                viewHolder2.tvMealName.setText(currentMeal.getName());

                List<MealDrugImage> mealDrugImages = currentMeal.getMealDrugImages(4);
                if (mealDrugImages.size() > 1) {
                    if(mealDrugImages.size() == 2) {
                        mealDrugImages.add(new MealDrugImage());
                    }
                    viewHolder2.mealDrugImageRecycler.setVisibility(View.VISIBLE);
                    viewHolder2.imageView.setVisibility(View.GONE);
                    viewHolder2.mealDrugImageRecycler.setLayoutManager(new GridLayoutManager(activity, 2));
                    viewHolder2.mealDrugImageRecycler.setAdapter(new MealDrugImageAdapter(mealDrugImages));
                } else {
                    viewHolder2.mealDrugImageRecycler.setVisibility(View.GONE);
                    viewHolder2.imageView.setVisibility(View.VISIBLE);
                    if (mealDrugImages.size() > 0) {
                        Glide.with(activity)
                                .load(mealDrugImages.get(0).getUrl())
                                .into(viewHolder2.imageView);
                    }
                }

                String desc = "";
                for (int j = 0; j < currentMeal.getMealDrugs().size(); j++) {
                    MealDrug mealDrug = currentMeal.getMealDrugs().get(j);
                    desc = desc + mealDrug.getName();
                    if (j < currentMeal.getMealDrugs().size() - 1) desc = desc + ", ";
                    else desc = desc + ".";
                }
                viewHolder2.tvMealDesc.setText(desc);
            }
            break;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        return 2;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView tvMealName;
        TextView tvMealDesc;
        ImageView imageView;
        RecyclerView mealDrugImageRecycler;

        public ViewHolder2(View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvMealDesc = itemView.findViewById(R.id.tvMealDesc);
            imageView = itemView.findViewById(R.id.image);
            mealDrugImageRecycler = itemView.findViewById(R.id.mealDrugImageRecycler);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView tvAddMeal;

        public ViewHolder1(View itemView) {
            super(itemView);
            tvAddMeal = itemView.findViewById(R.id.tvAddMeal);

        }
    }
}
