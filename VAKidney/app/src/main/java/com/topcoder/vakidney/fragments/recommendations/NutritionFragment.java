package com.topcoder.vakidney.fragments.recommendations;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.FoodRecommendationAdapter;
import com.topcoder.vakidney.databinding.FragmentNutritionBinding;
import com.topcoder.vakidney.model.FoodRecommendation;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This fragment shows the articles related to Nutrition that can be used by the patient using the app
 */
public class NutritionFragment extends Fragment {
    FragmentNutritionBinding binder;

    public NutritionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_nutrition, container, false);
        final View view = binder.getRoot();


        binder.rvFoodSuggestion.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));
        binder.rvFoodUnsafe.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));

        List<FoodRecommendation> foodRecommendations = FoodRecommendation.getGood();
        FoodRecommendationAdapter adapter1 = new FoodRecommendationAdapter(
                binder.rvFoodUnsafe,
                foodRecommendations
        );
        binder.rvFoodSuggestion.setAdapter(adapter1);

        List<FoodRecommendation> foodUnsafeRecommendations = FoodRecommendation.getUnsafe();
        FoodRecommendationAdapter adapter2 = new FoodRecommendationAdapter(
                binder.rvFoodSuggestion,
                foodUnsafeRecommendations
        );
        binder.rvFoodUnsafe.setAdapter(adapter2);

        return view;
    }

}
