package com.topcoder.vakidney;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.topcoder.vakidney.databinding.ActivityMainBinding;
import com.topcoder.vakidney.fragments.ChartMenuFragment;
import com.topcoder.vakidney.fragments.FoodFragment;
import com.topcoder.vakidney.fragments.GoalFragment;
import com.topcoder.vakidney.fragments.HomeFragment;
import com.topcoder.vakidney.fragments.RecommendationsFragment;
import com.topcoder.vakidney.fragments.MyProfileFragment;
import com.topcoder.vakidney.fragments.ResourcesFragment;
import com.topcoder.vakidney.fragments.WorkoutFragment;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.LoginManager;

/**
 * This is the main Activity which consists of various fragments inside viewpager, side menu, bottom menu etc
 */
public class MainActivity extends AppCompatActivity {

    public static int navItemIndex = 0;

    /**
     * Tags
     */
    public static final String TAG_HOME = "Home";
    public static final String TAG_GOAL = "Goal";
    public static final String TAG_RESOURCES = "Resources";
    public static final String TAG_MYPROFILE = "MyProfile";
    public static final String TAG_CHART = "Chart";
    public static final String TAG_MEDICATION = "Medication";
    public static final String TAG_FOOD = "Food";
    public static final String TAG_WORKOUT = "Workout";
    public static String CURRENT_TAG = TAG_HOME;


    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    ActivityMainBinding binder;

    /**
     * Initialize Side Menu View and Sets UP Listeners
     */
    private void initSideMenu() {

        binder.menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MYPROFILE;
                loadHomeFragment();
                selectBottomBar(0);
                selectSideMenu(1);
            }
        });

        binder.menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_RESOURCES;
                loadHomeFragment();
                selectBottomBar(0);
                selectSideMenu(2);
            }
        });

        binder.menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSideMenu(3);
            }
        });

        binder.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.setLoggedIn(getApplicationContext(), false, UserData.get());
                NavigateToLogin();
            }
        });

    }


    /**
     * Selects Side Menu and emulate side menu behaviour with given index
     *
     * @param index required to specify the position of menu item in side menu
     */
    private void selectSideMenu(int index) {
        binder.drawerLayout.closeDrawers();
        switch (index) {

            case 0:
                ImageViewCompat.setImageTintList(binder.menuImage1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                binder.menuTitle1.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle3.setTextColor(getColor(R.color.colorLightDarkGray));

                binder.menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;

            case 1:
                ImageViewCompat.setImageTintList(binder.menuImage1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(binder.menuImage2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                binder.menuTitle1.setTextColor(getColor(R.color.colorAccent));
                binder.menuTitle2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle3.setTextColor(getColor(R.color.colorLightDarkGray));

                binder.menuBar1.setBackgroundColor(getColor(R.color.colorAccent));
                binder.menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 2:
                ImageViewCompat.setImageTintList(binder.menuImage2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(binder.menuImage1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                binder.menuTitle2.setTextColor(getColor(R.color.colorAccent));
                binder.menuTitle1.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle3.setTextColor(getColor(R.color.colorLightDarkGray));

                binder.menuBar2.setBackgroundColor(getColor(R.color.colorAccent));
                binder.menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 3:
                ImageViewCompat.setImageTintList(binder.menuImage3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(binder.menuImage2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                binder.menuTitle3.setTextColor(getColor(R.color.colorAccent));
                binder.menuTitle2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle1.setTextColor(getColor(R.color.colorLightDarkGray));

                binder.menuBar3.setBackgroundColor(getColor(R.color.colorAccent));
                binder.menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 4:
                ImageViewCompat.setImageTintList(binder.menuImage2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(binder.menuImage1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                binder.menuTitle2.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle3.setTextColor(getColor(R.color.colorLightDarkGray));
                binder.menuTitle1.setTextColor(getColor(R.color.colorLightDarkGray));

                binder.menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                binder.menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (!LoginManager.isTermsAgreed(getApplicationContext())) {
            NavigateToWelcomeScreen();
        } else {
            if (!LoginManager.isLoggedIn(getApplicationContext())) {
                NavigateToLogin();
            }
        }

        initBottomBar();
        initSideMenu();

        mHandler = new Handler();

        selectBottomBar(1);

        if (getIntent().hasExtra("tag")) {
            CURRENT_TAG = getIntent().getStringExtra("tag");
            loadHomeFragment();

            switch (CURRENT_TAG) {
                case TAG_HOME:
                    selectBottomBar(1);
                    break;
                case TAG_CHART:
                    selectBottomBar(2);
                    break;
                case TAG_MEDICATION:
                    selectBottomBar(3);
                    break;
                case TAG_FOOD:
                    selectBottomBar(4);
                    break;
                case TAG_WORKOUT:
                    selectBottomBar(5);
                    break;

                case TAG_GOAL:
                    selectBottomBar(0);

                case TAG_RESOURCES:
                    selectBottomBar(0);

                case TAG_MYPROFILE:
                    selectBottomBar(0);

                    break;

            }
        } else {
            if (savedInstanceState == null) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
            }
        }


        binder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        binder.goalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_TAG = TAG_GOAL;
                loadHomeFragment();
                selectBottomBar(0);
            }
        });

        Typeface typeface = ResourcesCompat.getFont(this, R.font.nexa_bold);
        binder.actionBarTitle.setTypeface(typeface);

    }


    /**
     * Initialize view and sets up listener for bottom menu
     */
    private void initBottomBar() {

        binder.barLin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(1);
            }
        });


        binder.barLin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(2);
            }
        });


        binder.barLin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(3);
            }
        });


        binder.barLin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(4);
            }
        });


        binder.barLin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(5);
            }
        });
    }

    /**
     * Emulates the sselecting behaviour for the menus at the bottom
     *
     * @param index required to specify the position of menu item in side menu
     */
    private void selectBottomBar(int index) {
        selectSideMenu(0);
        switch (index) {

            case 0:

                binder.bar1.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar2.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar3.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar4.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar1.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar2.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar3.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar4.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar5.setTextColor(getColor(R.color.brandBlueLight));

                break;

            case 1:
                CURRENT_TAG = TAG_HOME;

                binder.bar1.setBackgroundResource(R.drawable.bg_brand_line);
                binder.bar2.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar3.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar4.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar1.setTextColor(getColor(R.color.colorWhite));
                binder.tvBar2.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar3.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar4.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar5.setTextColor(getColor(R.color.brandBlueLight));

                break;


            case 2:
                CURRENT_TAG = TAG_CHART;

                binder.bar2.setBackgroundResource(R.drawable.bg_brand_line);
                binder.bar1.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar3.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar4.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar2.setTextColor(getColor(R.color.colorWhite));
                binder.tvBar1.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar3.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar4.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar5.setTextColor(getColor(R.color.brandBlueLight));

                break;


            case 3:
                CURRENT_TAG = TAG_MEDICATION;

                binder.bar3.setBackgroundResource(R.drawable.bg_brand_line);
                binder.bar2.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar1.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar4.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar3.setTextColor(getColor(R.color.colorWhite));
                binder.tvBar2.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar1.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar4.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar5.setTextColor(getColor(R.color.brandBlueLight));

                break;


            case 4:
                CURRENT_TAG = TAG_FOOD;

                binder.bar4.setBackgroundResource(R.drawable.bg_brand_line);
                binder.bar2.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar3.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar1.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar4.setTextColor(getColor(R.color.colorWhite));
                binder.tvBar2.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar3.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar1.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar5.setTextColor(getColor(R.color.brandBlueLight));

                break;


            case 5:
                CURRENT_TAG = TAG_WORKOUT;

                binder.bar5.setBackgroundResource(R.drawable.bg_brand_line);
                binder.bar2.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar3.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar4.setBackgroundColor(getColor(R.color.colorPrimary));
                binder.bar1.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(binder.barImg5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(binder.barImg2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));
                ImageViewCompat.setImageTintList(binder.barImg1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.brandBlueLight)));

                binder.tvBar5.setTextColor(getColor(R.color.colorWhite));
                binder.tvBar2.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar3.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar4.setTextColor(getColor(R.color.brandBlueLight));
                binder.tvBar1.setTextColor(getColor(R.color.brandBlueLight));

                break;
        }

        loadHomeFragment();
    }


    /**
     * Select the required fragement based on the current Tag and Load the required fragment
     */
    private void loadHomeFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            binder.drawerLayout.closeDrawers();

            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                if (UserData.get() != null && !UserData.get().isSetupgoals()) {
                    binder.goalIcon.setVisibility(View.GONE);
                }
            }
        };
        mHandler.post(mPendingRunnable);
        binder.drawerLayout.closeDrawers();
        invalidateOptionsMenu();

    }


    @Override
    public void onBackPressed() {


        if (binder.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binder.drawerLayout.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 1 || CURRENT_TAG != TAG_HOME) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                selectBottomBar(navItemIndex);
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * Returns require fragment for current TAG
     *
     * @return The fragment specified by the CURRENT_TAG is returned
     */
    private Fragment getHomeFragment() {
        switch (CURRENT_TAG) {

            case TAG_MYPROFILE:
                binder.goalIcon.setVisibility(View.GONE);
                binder.actionBarTitle.setText("MY PRofile");
                return new MyProfileFragment();

            case TAG_RESOURCES:
                binder.goalIcon.setVisibility(View.GONE);
                binder.actionBarTitle.setText("Resources");
                return new ResourcesFragment();

            case TAG_HOME:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("Home");
                return new HomeFragment();


            case TAG_CHART:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("History & Metrics");
                return new ChartMenuFragment();


            case TAG_MEDICATION:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("Recommendations");
                return new RecommendationsFragment();


            case TAG_FOOD:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("Food/Drug Intake");
                return new FoodFragment();


            case TAG_WORKOUT:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("Daily Workout");
                return new WorkoutFragment();


            case TAG_GOAL:
                binder.goalIcon.setVisibility(View.GONE);
                binder.actionBarTitle.setText("Goal");
                return new GoalFragment();


            default:
                binder.goalIcon.setVisibility(View.VISIBLE);
                binder.actionBarTitle.setText("Home");
                return new HomeFragment();

        }
    }


    /**
     * Navigates to Welcome Screen
     */
    private void NavigateToWelcomeScreen() {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /**
     * Navigates to Login Screen
     */
    private void NavigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


}
