package com.topcoder.vakidney.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topcoder.vakidney.model.FoodRecommendation;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesDetailActivity;
import com.topcoder.vakidney.util.TextUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class FoodRecommendationAdapter extends Adapter implements View.OnClickListener {

    private final Context mContext;
    private final RecyclerView mRecycleView;
    private final List<FoodRecommendation> mFoodRecommendations;

    public FoodRecommendationAdapter(RecyclerView parent, List<FoodRecommendation> foodRecommendations) {
        mRecycleView = parent;
        mFoodRecommendations = foodRecommendations;
        mContext = parent.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicationresource_titledesc, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FoodRecommendation foodRecommendation = mFoodRecommendations.get(position);

        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setTag(foodRecommendation);

        viewHolder.textTitle.setText(TextUtil.capitalizeFirstLetter(foodRecommendation.getName()));
        viewHolder.textDesc.setText(foodRecommendation.getDesc());
    }

    @Override
    public int getItemCount() {
        return mFoodRecommendations.size();
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() != null && view.getTag() instanceof FoodRecommendation) {
            FoodRecommendation foodRecommendation = (FoodRecommendation) view.getTag();
            Intent intent = new Intent(mContext, ResourcesDetailActivity.class);
            intent.putExtra("title", TextUtil.capitalizeFirstLetter(foodRecommendation.getName()));
            intent.putExtra(
                    "actionbartitle",
                    foodRecommendation.getType() == FoodRecommendation.TYPE_UNSAFE ?
                            "Unsafe Food" : "Food Recommendation"
            );
            String description = "Per 100 g, contains:\n\n";

            try {
                JSONArray drugsArray = new JSONArray(foodRecommendation.getNutritionArray());
                for (int i = 0; i < drugsArray.length(); i++) {
                    description =
                            description +
                                    drugsArray
                                            .getJSONObject(i)
                                            .getString("name") +
                                    "\t\t" +
                                    drugsArray
                                            .getJSONObject(i)
                                            .getString("value") +
                                    drugsArray
                                            .getJSONObject(i)
                                            .getString("unit") +
                                    "\n";
                }
                description = description + "\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }
            intent.putExtra("desc", description);
            mContext.startActivity(intent);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;

        private ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textDesc = itemView.findViewById(R.id.desc);
        }
    }

}
