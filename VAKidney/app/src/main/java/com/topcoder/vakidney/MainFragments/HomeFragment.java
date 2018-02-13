package com.topcoder.vakidney.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.topcoder.vakidney.Adapter.ViewPagerAdapter;
import com.topcoder.vakidney.Fragments.Welcome1Fragment;
import com.topcoder.vakidney.Fragments.Welcome2Fragment;
import com.topcoder.vakidney.Fragments.Welcome3Fragment;
import com.topcoder.vakidney.MainFragments.HomeFragments.Home1Fragment;
import com.topcoder.vakidney.MainFragments.HomeFragments.Home2Fragment;
import com.topcoder.vakidney.MainFragments.HomeFragments.Home3Fragment;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.WelcomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private LinearLayout indicator1, indicator2, indicator3;

    private ViewPager mViewpager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        PopulateView();


        return view;
    }

    /**
     * Populates the Respective Fields
     */
    private void PopulateView() {
        ViewPagerAdapter homeFragmentPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        homeFragmentPagerAdapter.addFragment(new Home1Fragment(), "");
        homeFragmentPagerAdapter.addFragment(new Home2Fragment(), "");
        homeFragmentPagerAdapter.addFragment(new Home3Fragment(), "");
        mViewpager.setAdapter(homeFragmentPagerAdapter);
        mViewpager.setOffscreenPageLimit(5);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setIndicator(position+1);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Initializes the view
     * @param view
     */
    private void initView(View view) {
        indicator1=view.findViewById(R.id.indicator1);
        indicator2=view.findViewById(R.id.indicator2);
        indicator3=view.findViewById(R.id.indicator3);
        mViewpager=view.findViewById(R.id.mViewPager);
        indicator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewpager.setCurrentItem(0);
                setIndicator(1);
            }
        });
        indicator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewpager.setCurrentItem(1);
                setIndicator(2);
            }
        });
        indicator3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewpager.setCurrentItem(2);
                setIndicator(3);
            }
        });
    }

    /**
     * Changes the position of mainindex and otherindex with
     * @param index
     */
    private void setIndicator(int index){
        switch (index){
            case 1:
                indicator1.setBackgroundResource(R.drawable.welcome_indicator_selected);
                indicator2.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                indicator3.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                break;

            case 2:
                indicator1.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                indicator2.setBackgroundResource(R.drawable.welcome_indicator_selected);
                indicator3.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                break;


            case 3:
                indicator1.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                indicator2.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                indicator3.setBackgroundResource(R.drawable.welcome_indicator_selected);
                break;

        }
    }


}
