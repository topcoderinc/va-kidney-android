package com.topcoder.vakidney.MainFragments.recommendations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.topcoder.vakidney.Adapter.FoodRecommendationAdapter;
import com.topcoder.vakidney.Model.FoodRecommendation;
import com.topcoder.vakidney.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This fragment shows the articles related to Nutrition that can be used by the patient using the app
 */
public class NutritionFragment extends Fragment {

    private ListView nutritionListView;

    public NutritionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        List<FoodRecommendation> foodUnsafeRecommendations = FoodRecommendation.getUnsafe();
        FoodRecommendationAdapter adapter = new FoodRecommendationAdapter(
                recyclerViewUnsafe,
                foodUnsafeRecommendations
        );
        recyclerViewUnsafe.setAdapter(adapter);

//        nutritionListView = view.findViewById(R.id.nutritionListView);
//        MedicationAdapter medicationAdapter=new MedicationAdapter(JsondataUtil.getMedicationResources(getActivity(), 1), getActivity());
//        nutritionListView.setAdapter(medicationAdapter);
        return view;
    }

}
