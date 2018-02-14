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
 * This fragment shows the articles related to Drugs that can be used by the patient using the app
 */
public class DrugFragment extends Fragment {

    private ListView drugsListView;
    public DrugFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_drug, container, false);
        drugsListView =view.findViewById(R.id.drugsListView);
        MedicationAdapter medicationAdapter=new MedicationAdapter(JsondataUtil.getMedicationResources(getActivity(), 2), getActivity());
        drugsListView.setAdapter(medicationAdapter);
        return view;
    }


}
