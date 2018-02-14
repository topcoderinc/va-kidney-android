package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.FoodAdapter;
import com.topcoder.vakidney.Model.Meal;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * This class is used to show all the meals that are added by the users in gridView
 */
public class FoodFragment extends Fragment {


    private GridView gridView;
    private ArrayList<Meal> mealArrayList;
    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food, container, false);
        gridView=view.findViewById(R.id.gridView);
        mealArrayList=JsondataUtil.getMeals(getActivity());
        if(getActivity().getIntent().hasExtra("addmeal")){
            Bundle bundle=getActivity().getIntent().getBundleExtra("meal");
            Meal meal=Meal.getMealFromBundle(bundle);
            mealArrayList.add(meal);
        }
        FoodAdapter foodAdapter=new FoodAdapter(mealArrayList, getActivity());
        gridView.setAdapter(foodAdapter);
        return view;
    }

}
