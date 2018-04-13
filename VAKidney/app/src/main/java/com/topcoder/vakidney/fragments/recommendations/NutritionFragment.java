package com.topcoder.vakidney.fragments.recommendations;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.FoodRecommendationAdapter;
import com.topcoder.vakidney.model.FoodRecommendation;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This fragment shows the articles related to Nutrition that can be used by the patient using the app
 */
public class NutritionFragment extends Fragment {

    public NutritionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        RecyclerView recyclerViewSuggestion = view.findViewById(R.id.rv_food_suggestion);
        RecyclerView recyclerViewUnsafe = view.findViewById(R.id.rv_food_unsafe);

        recyclerViewSuggestion.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));
        recyclerViewUnsafe.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));

        List<FoodRecommendation> foodRecommendations = FoodRecommendation.getGood();
        FoodRecommendationAdapter adapter1 = new FoodRecommendationAdapter(
                recyclerViewUnsafe,
                foodRecommendations
        );
        recyclerViewSuggestion.setAdapter(adapter1);

        List<FoodRecommendation> foodUnsafeRecommendations = FoodRecommendation.getUnsafe();
        FoodRecommendationAdapter adapter2 = new FoodRecommendationAdapter(
                recyclerViewUnsafe,
                foodUnsafeRecommendations
        );
        recyclerViewUnsafe.setAdapter(adapter2);

        return view;
    }

}
