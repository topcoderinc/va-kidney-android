package com.topcoder.vakidney.fragments.recommendations;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.MedicationAdapter;
import com.topcoder.vakidney.databinding.FragmentDrugBinding;
import com.topcoder.vakidney.model.DrugInteraction;
import com.topcoder.vakidney.R;

import java.util.List;

import static com.orm.SugarRecord.find;

/**
 * A simple {@link Fragment} subclass.
 * This fragment shows the articles related to Drugs that can be used by the patient using the app
 */
public class DrugFragment extends Fragment {

    private FragmentDrugBinding binder;

    public DrugFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_drug, container, false);
        final View view = binder.getRoot();
        List<DrugInteraction> drugInteractionList = DrugInteraction.find(DrugInteraction.class, "");

        binder.rvMedications.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));
        binder.rvDrugInteraction.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));


        MedicationAdapter adapter1 = new MedicationAdapter(
                binder.rvDrugInteraction, drugInteractionList,
                getActivity(),
                MedicationAdapter.DrugInteractionWarning
        );
        binder.rvMedications.setAdapter(adapter1);


        MedicationAdapter adapter2 = new MedicationAdapter(
                binder.rvMedications,
                drugInteractionList,
                getActivity(),
                MedicationAdapter.Medication
        );
        binder.rvDrugInteraction.setAdapter(adapter2);

        return view;
    }


}
