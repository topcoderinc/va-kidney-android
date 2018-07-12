package com.topcoder.vakidney.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.ViewPagerAdapter;
import com.topcoder.vakidney.databinding.FragmentBloodSugarBinding;
import com.topcoder.vakidney.databinding.FragmentMedicationBinding;
import com.topcoder.vakidney.fragments.recommendations.DrugFragment;
import com.topcoder.vakidney.fragments.recommendations.NutritionFragment;
import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass.
 * This fragment consists of a viewpager which displays two fragments related to drugs and nutrition resource articles.
 */
public class RecommendationsFragment extends Fragment {


    FragmentMedicationBinding binder;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_medication, container, false);
        final View view = binder.getRoot();

        setupViewPager(binder.viewpager);


        binder.tabs.setupWithViewPager(binder.viewpager);
        return view;
    }

    /**
     * Populates the viewpager
     *
     * @param viewPager Required to Setup with fragments
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NutritionFragment(), "Nutrition");
        adapter.addFragment(new DrugFragment(), "Drug Interactions");
        viewPager.setAdapter(adapter);
    }


}
