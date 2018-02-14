package com.topcoder.vakidney.MainFragments.MedicationFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.topcoder.vakidney.Adapter.MedicationAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.Util.JsondataUtil;

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
        View view=inflater.inflate(R.layout.fragment_nutrition, container, false);
        nutritionListView=view.findViewById(R.id.nutritionListView);
        MedicationAdapter medicationAdapter=new MedicationAdapter(JsondataUtil.getMedicationResources(getActivity(), 1), getActivity());
        nutritionListView.setAdapter(medicationAdapter);
        return view;
    }




}
