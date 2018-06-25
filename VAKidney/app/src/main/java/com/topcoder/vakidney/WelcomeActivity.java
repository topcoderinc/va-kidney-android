package com.topcoder.vakidney;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.topcoder.vakidney.adapter.ViewPagerAdapter;
import com.topcoder.vakidney.databinding.ActivityWelcomeBinding;
import com.topcoder.vakidney.fragments.welcome.Welcome1Fragment;
import com.topcoder.vakidney.fragments.welcome.Welcome2Fragment;
import com.topcoder.vakidney.fragments.welcome.Welcome3Fragment;
import com.topcoder.vakidney.fragments.welcome.WelcomeBaseFragment;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.LoginManager;

/**
 * This is the welcome screen, where user are directed when they use the app for the first time. It contains terms and agreement which should be agreed by the user in order to use the app.
 */
public class WelcomeActivity extends AppCompatActivity {


    private float RATIO_SCALE = 0.2f;

    ActivityWelcomeBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        binder.btnNextSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.mViewPager.setCurrentItem(binder.mViewPager.getCurrentItem() + 1);
            }
        });
        setIndicator(1);
        binder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.welcomeScreenBeginning.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binder.welcomeScreenBeginning.setVisibility(View.GONE);
                                binder.WelcomeSliderScreen.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });
        binder.btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.setTermsAgreed(getApplicationContext(), true);
                NavigateToHome();
            }
        });
        binder.btnDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showYesNoDialog(WelcomeActivity.this, "Please agree to the terms and condition. Click YES to exit or click NO to continue using the app", "YES", "NO", new DialogManager.OnYesClicked() {
                    @Override
                    public void YesClicked() {
                        finish();
                    }
                }, null);
            }
        });
        binder.mViewPager.setClipToPadding(false);
        binder.mViewPager.setPageMargin(24);
        binder.mViewPager.setPadding(80, 60, 80, 30);
        binder.mViewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter campaignPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        campaignPagerAdapter.addFragment(new Welcome1Fragment(), "");
        campaignPagerAdapter.addFragment(new Welcome2Fragment(), "");
        campaignPagerAdapter.addFragment(new Welcome3Fragment(), "");
        binder.mViewPager.setAdapter(campaignPagerAdapter);
        binder.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                WelcomeBaseFragment sampleFragment = (WelcomeBaseFragment) ((ViewPagerAdapter) binder.mViewPager.getAdapter()).getItem(position);
                float scale = 1 - (positionOffset * RATIO_SCALE);
                sampleFragment.scale(scale);
                if (position + 1 < binder.mViewPager.getAdapter().getCount()) {
                    sampleFragment = (WelcomeBaseFragment) ((ViewPagerAdapter) binder.mViewPager.getAdapter()).getItem(position + 1);
                    scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                    sampleFragment.scale(scale);
                }
                setIndicator(position + 1);
                if (position + 1 == 3) {
                    binder.btnNextSlider.setVisibility(View.GONE);
                    binder.agreeDisagreeLayout.setVisibility(View.VISIBLE);
                } else {
                    binder.btnNextSlider.setVisibility(View.VISIBLE);
                    binder.agreeDisagreeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    WelcomeBaseFragment fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) binder.mViewPager.getAdapter()).getItem(binder.mViewPager.getCurrentItem());
                    fragment.scale(1);
                    if (binder.mViewPager.getCurrentItem() > 0) {
                        fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) binder.mViewPager.getAdapter()).getItem(binder.mViewPager.getCurrentItem() - 1);
                        fragment.scale(1 - RATIO_SCALE);
                    }

                    if (binder.mViewPager.getCurrentItem() + 1 < binder.mViewPager.getAdapter().getCount()) {
                        fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) binder.mViewPager.getAdapter()).getItem(binder.mViewPager.getCurrentItem() + 1);
                        fragment.scale(1 - RATIO_SCALE);
                    }
                }
            }
        });
    }

    /**
     * Emulates the behaviour of sliding dot indicator when viewpager is slided
     *
     * @param index required to specify the position of menu item in side menu
     */
    private void setIndicator(int index) {
        switch (index) {
            case 1:
                binder.indicator1.setBackgroundResource(R.drawable.welcome_indicator_selected);
                binder.indicator2.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                binder.indicator3.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                break;

            case 2:
                binder.indicator1.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                binder.indicator2.setBackgroundResource(R.drawable.welcome_indicator_selected);
                binder.indicator3.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                break;


            case 3:
                binder.indicator1.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                binder.indicator2.setBackgroundResource(R.drawable.welcome_indicator_unselected);
                binder.indicator3.setBackgroundResource(R.drawable.welcome_indicator_selected);
                break;

        }
    }


    /**
     *
     */
    private void NavigateToHome() {
        Intent intent;
        if (LoginManager.isLoggedIn(getApplicationContext())) {
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
        } else {
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
