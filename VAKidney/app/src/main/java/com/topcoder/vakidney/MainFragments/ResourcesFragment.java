package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.Adapter.ViewPagerAdapter;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.ResourcesFragments.ResourceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment {



    private TabLayout tabLayout;
    private ViewPager viewPager;
    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_resources, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    /**
     * Populates the ViewPager with respective fields
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ResourceFragment(), "Articles");
        adapter.addFragment(new ResourceFragment(), "Tutorials");
        adapter.addFragment(new ResourceFragment(), "Education");
        viewPager.setAdapter(adapter);
    }


}
