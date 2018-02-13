package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.topcoder.vakidney.Adapter.FoodAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    private GridView gridView;
    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food, container, false);
        gridView=view.findViewById(R.id.gridView);
        FoodAdapter foodAdapter=new FoodAdapter(JsondataUtil.getMeals(getActivity()), getActivity());
        gridView.setAdapter(foodAdapter);
        return view;
    }

}
