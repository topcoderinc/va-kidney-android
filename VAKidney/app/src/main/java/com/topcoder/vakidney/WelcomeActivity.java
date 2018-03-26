package com.topcoder.vakidney;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.topcoder.vakidney.adapter.ViewPagerAdapter;
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

    private RelativeLayout welcomeScreenBeginning;
    private RelativeLayout welccomeSliderScreen;
    private Button btnNext;
    private LinearLayout btnNextSlider;
    private float RATIO_SCALE=0.2f;
    private LinearLayout indicator1, indicator2, indicator3;
    private ViewPager mViewpager;
    private LinearLayout agreeDisagreeLayout;
    private Button btnAgree, btnDisagree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeScreenBeginning=findViewById(R.id.welcomeScreenBeginning);
        welccomeSliderScreen=findViewById(R.id.WelcomeSliderScreen);
        btnNext=findViewById(R.id.btnNext);
        btnNextSlider=findViewById(R.id.btnNextSlider);
        btnNextSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewpager.setCurrentItem(mViewpager.getCurrentItem()+1);
            }
        });
        indicator1=findViewById(R.id.indicator1);
        indicator2=findViewById(R.id.indicator2);
        indicator3=findViewById(R.id.indicator3);
        mViewpager=findViewById(R.id.mViewPager);
        agreeDisagreeLayout=findViewById(R.id.agreeDisagreeLayout);
        btnAgree=findViewById(R.id.btnAgree);
        btnDisagree=findViewById(R.id.btnDisagree);
        setIndicator(1);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeScreenBeginning.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                welcomeScreenBeginning.setVisibility(View.GONE);
                                welccomeSliderScreen.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.setTermsAgreed(getApplicationContext(), true);
                NavigateToHome();
            }
        });
        btnDisagree.setOnClickListener(new View.OnClickListener() {
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
        mViewpager.setClipToPadding(false);
        mViewpager.setPageMargin(24);
        mViewpager.setPadding(80, 60, 80, 30);
        mViewpager.setOffscreenPageLimit(3);
        ViewPagerAdapter campaignPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        campaignPagerAdapter.addFragment(new Welcome1Fragment(), "");
        campaignPagerAdapter.addFragment(new Welcome2Fragment(), "");
        campaignPagerAdapter.addFragment(new Welcome3Fragment(), "");
        mViewpager.setAdapter(campaignPagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                WelcomeBaseFragment sampleFragment = (WelcomeBaseFragment) ((ViewPagerAdapter) mViewpager.getAdapter()).getItem(position);
                float scale = 1 - (positionOffset * RATIO_SCALE);
                sampleFragment.scale(scale);
                if (position + 1 < mViewpager.getAdapter().getCount()) {
                    sampleFragment = (WelcomeBaseFragment) ((ViewPagerAdapter) mViewpager.getAdapter()).getItem(position + 1);
                    scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                    sampleFragment.scale(scale);
                }
                setIndicator(position+1);
                if(position+1==3){
                    btnNextSlider.setVisibility(View.GONE);
                    agreeDisagreeLayout.setVisibility(View.VISIBLE);
                }else{
                    btnNextSlider.setVisibility(View.VISIBLE);
                    agreeDisagreeLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    WelcomeBaseFragment fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) mViewpager.getAdapter()).getItem(mViewpager.getCurrentItem());
                    fragment.scale(1);
                    if (mViewpager.getCurrentItem() > 0) {
                        fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) mViewpager.getAdapter()).getItem(mViewpager.getCurrentItem() - 1);
                        fragment.scale(1 - RATIO_SCALE);
                    }

                    if (mViewpager.getCurrentItem() + 1 < mViewpager.getAdapter().getCount()) {
                        fragment = (WelcomeBaseFragment) ((ViewPagerAdapter) mViewpager.getAdapter()).getItem(mViewpager.getCurrentItem() + 1);
                        fragment.scale(1 - RATIO_SCALE);
                    }
                }
            }
        });
    }

    /**
     * Emulates the behaviour of sliding dot indicator when viewpager is slided
     * @param index required to specify the position of menu item in side menu
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


    /**
     *
     */
    private void NavigateToHome() {
        Intent intent;
        if(LoginManager.isLoggedIn(getApplicationContext())) {
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
        }else{
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
