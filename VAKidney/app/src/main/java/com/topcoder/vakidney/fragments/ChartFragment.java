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
import com.topcoder.vakidney.databinding.FragmentChartBinding;
import com.topcoder.vakidney.fragments.charts.BloodSugarFragment;
import com.topcoder.vakidney.fragments.charts.BodyWeightFragment;
import com.topcoder.vakidney.fragments.charts.PotassiumFragment;
import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass.
 * This fragment consists of three Fragments BloodSugarFragment, BodyWeightFragment and PotassiumFragment. All of these fragments shows respective data in chart view
 */
public class ChartFragment extends Fragment {


    private FragmentChartBinding binder;

    public ChartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false);
        final View view = binder.getRoot();

        binder.viewpager.setOffscreenPageLimit(5);
        setupViewPager(binder.viewpager);

        binder.tabs.setupWithViewPager(binder.viewpager);
        return view;
    }


    /**
     * Populate the viewpager with fragments
     *
     * @param viewPager required to setup with the fragments
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BloodSugarFragment(), "Blood Sugar");
        adapter.addFragment(new BodyWeightFragment(), "Body Weight");
        adapter.addFragment(new PotassiumFragment(), "Potassium");
        viewPager.setAdapter(adapter);
    }


}
