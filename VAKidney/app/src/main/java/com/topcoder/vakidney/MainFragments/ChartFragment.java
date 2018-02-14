package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.Adapter.ViewPagerAdapter;
import com.topcoder.vakidney.MainFragments.ChartFragments.BloodSugarFragment;
import com.topcoder.vakidney.MainFragments.ChartFragments.BodyWeightFragment;
import com.topcoder.vakidney.MainFragments.ChartFragments.PotassiumFragment;
import com.topcoder.vakidney.R;

/**
 * A simple {@link Fragment} subclass.
 * This fragment consists of three Fragments BloodSugarFragment, BodyWeightFragment and PotassiumFragment. All of these fragments shows respective data in chart view
 */
public class ChartFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    public ChartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chart, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    /**
     * Populate the viewpager with fragments
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
