package com.topcoder.vakidney.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.adapter.ViewPagerAdapter;
import com.topcoder.vakidney.databinding.FragmentResourcesBinding;
import com.topcoder.vakidney.fragments.resources.DoMoreFragment;
import com.topcoder.vakidney.fragments.resources.LearnMoreFragment;
import com.topcoder.vakidney.fragments.resources.ReadMoreFragment;

/**
 * A simple {@link Fragment} subclass.
 * It Displays different articles suitable for the patient using this app
 */
public class ResourcesFragment extends Fragment {


    FragmentResourcesBinding binder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_resources, container, false);
        final View view = binder.getRoot();

        binder.viewpager.setOffscreenPageLimit(5);
        setupViewPager(binder.viewpager);

        binder.tabs.setupWithViewPager(binder.viewpager);
        return view;
    }

    /**
     * Populates the ViewPager with respective fields
     *
     * @param viewPager Required to setup with fragments
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ReadMoreFragment(), "Read More");
        adapter.addFragment(new DoMoreFragment(), "Do More");
        adapter.addFragment(new LearnMoreFragment(), "Learn More");
        viewPager.setAdapter(adapter);
    }


}
