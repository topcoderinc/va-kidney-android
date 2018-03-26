package com.topcoder.vakidney;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topcoder.vakidney.fragments.ChartMenuFragment;
import com.topcoder.vakidney.fragments.FoodFragment;
import com.topcoder.vakidney.fragments.GoalFragment;
import com.topcoder.vakidney.fragments.HomeFragment;
import com.topcoder.vakidney.fragments.RecommendationsFragment;
import com.topcoder.vakidney.fragments.MyProfileFragment;
import com.topcoder.vakidney.fragments.ResourcesFragment;
import com.topcoder.vakidney.fragments.WorkoutFragment;
import com.topcoder.vakidney.util.LoginManager;

/**
 * This is the main Activity which consists of various fragments inside viewpager, side menu, bottom menu etc
 */
public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
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
    private ImageView menuIcon;
    private ImageView goalIcon;

    /**
     * Bottom Menu Setup
     */
    private LinearLayout bottomMenu1, bottomMenu2, bottomMenu3, bottomMenu4, bottomMenu5;
    private LinearLayout barIndicator1, barIndicator2, barIndicator3, barIndicator4, barIndicator5;
    private AppCompatImageView barImage1, barImage2, barImage3, barImage4, barImage5;
    private TextView barTitle1, barTitle2, barTitle3, barTitle4, barTitle5;
    private TextView actionBarTitle;


    private LinearLayout menu1, menu2, menu3;
    private AppCompatImageView menuImg1, menuImg2, menuImg3;
    private TextView menuTv1, menuTv2, menuTv3;
    private LinearLayout menuBar1, menuBar2, menuBar3;
    private TextView tvSettings, tvReminders, tvLogOut;


    /**
     * Initialize Side Menu View and Sets UP Listeners
     */
    private void initSideMenu() {
        tvSettings = findViewById(R.id.tvSettings);
        tvReminders = findViewById(R.id.tvReminders);
        tvLogOut = findViewById(R.id.tvLogOut);

        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);

        menuImg1 = findViewById(R.id.menuImage1);
        menuImg2 = findViewById(R.id.menuImage2);
        menuImg3 = findViewById(R.id.menuImage3);

        menuTv1 = findViewById(R.id.menuTitle1);
        menuTv2 = findViewById(R.id.menuTitle2);
        menuTv3 = findViewById(R.id.menuTitle3);

        menuBar1 = findViewById(R.id.menuBar1);
        menuBar2 = findViewById(R.id.menuBar2);
        menuBar3 = findViewById(R.id.menuBar3);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MYPROFILE;
                loadHomeFragment();
                selectBottomBar(0);
                selectSideMenu(1);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_RESOURCES;
                loadHomeFragment();
                selectBottomBar(0);
                selectSideMenu(2);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSideMenu(3);
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.setLoggedIn(getApplicationContext(), false, null);
                NavigateToLogin();
            }
        });


    }


    /**
     * Selects Side Menu and emulate side menu behaviour with given index
     * @param index required to specify the position of menu item in side menu
     */
    private void selectSideMenu(int index) {
        drawer.closeDrawers();
        switch (index) {

            case 0:
                ImageViewCompat.setImageTintList(menuImg1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                menuTv1.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv2.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv3.setTextColor(getColor(R.color.colorLightDarkGray));

                menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;

            case 1:
                ImageViewCompat.setImageTintList(menuImg1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(menuImg2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                menuTv1.setTextColor(getColor(R.color.colorAccent));
                menuTv2.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv3.setTextColor(getColor(R.color.colorLightDarkGray));

                menuBar1.setBackgroundColor(getColor(R.color.colorAccent));
                menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 2:
                ImageViewCompat.setImageTintList(menuImg2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(menuImg1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                menuTv2.setTextColor(getColor(R.color.colorAccent));
                menuTv1.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv3.setTextColor(getColor(R.color.colorLightDarkGray));

                menuBar2.setBackgroundColor(getColor(R.color.colorAccent));
                menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 3:
                ImageViewCompat.setImageTintList(menuImg3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)));
                ImageViewCompat.setImageTintList(menuImg2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                menuTv3.setTextColor(getColor(R.color.colorAccent));
                menuTv2.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv1.setTextColor(getColor(R.color.colorLightDarkGray));

                menuBar3.setBackgroundColor(getColor(R.color.colorAccent));
                menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
            case 4:
                ImageViewCompat.setImageTintList(menuImg2, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg3, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));
                ImageViewCompat.setImageTintList(menuImg1, ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.colorLightDarkGray)));

                menuTv2.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv3.setTextColor(getColor(R.color.colorLightDarkGray));
                menuTv1.setTextColor(getColor(R.color.colorLightDarkGray));

                menuBar2.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar3.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                menuBar1.setBackgroundColor(getColor(R.color.colorLightDarkGray));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!LoginManager.isTermsAgreed(getApplicationContext())){
            NavigateToWelcomeScreen();
        }else{
            if(!LoginManager.isLoggedIn(getApplicationContext())){
                NavigateToLogin();
            }
        }

        initBottomBar();
        initSideMenu();

        mHandler = new Handler();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuIcon = findViewById(R.id.menuIcon);
        goalIcon = findViewById(R.id.goalIcon);
        actionBarTitle = findViewById(R.id.actionBarTitle);


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


        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        goalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_TAG = TAG_GOAL;
                loadHomeFragment();
                selectBottomBar(0);
            }
        });

    }


    /**
     * Initialize view and sets up listener for bottom menu
     */
    private void initBottomBar() {
        bottomMenu1 = findViewById(R.id.barLin1);
        bottomMenu2 = findViewById(R.id.barLin2);
        bottomMenu3 = findViewById(R.id.barLin3);
        bottomMenu4 = findViewById(R.id.barLin4);
        bottomMenu5 = findViewById(R.id.barLin5);

        barIndicator1 = findViewById(R.id.bar1);
        barIndicator2 = findViewById(R.id.bar2);
        barIndicator3 = findViewById(R.id.bar3);
        barIndicator4 = findViewById(R.id.bar4);
        barIndicator5 = findViewById(R.id.bar5);

        barImage1 = findViewById(R.id.barImg1);
        barImage2 = findViewById(R.id.barImg2);
        barImage3 = findViewById(R.id.barImg3);
        barImage4 = findViewById(R.id.barImg4);
        barImage5 = findViewById(R.id.barImg5);

        barTitle1 = findViewById(R.id.tvBar1);
        barTitle2 = findViewById(R.id.tvBar2);
        barTitle3 = findViewById(R.id.tvBar3);
        barTitle4 = findViewById(R.id.tvBar4);
        barTitle5 = findViewById(R.id.tvBar5);

        bottomMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(1);
            }
        });


        bottomMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(2);
            }
        });


        bottomMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(3);
            }
        });


        bottomMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(4);
            }
        });


        bottomMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBottomBar(5);
            }
        });
    }

    /**
     * Emulates the sselecting behaviour for the menus at the bottom
     * @param index required to specify the position of menu item in side menu
     */
    private void selectBottomBar(int index) {
        selectSideMenu(0);
        switch (index) {

            case 0:

                barIndicator1.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator2.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator3.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator4.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle1.setTextColor(getColor(R.color.colorAccent));
                barTitle2.setTextColor(getColor(R.color.colorAccent));
                barTitle3.setTextColor(getColor(R.color.colorAccent));
                barTitle4.setTextColor(getColor(R.color.colorAccent));
                barTitle5.setTextColor(getColor(R.color.colorAccent));

                break;

            case 1:
                CURRENT_TAG = TAG_HOME;

                barIndicator1.setBackgroundColor(getColor(R.color.colorWhite));
                barIndicator2.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator3.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator4.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle1.setTextColor(getColor(R.color.colorWhite));
                barTitle2.setTextColor(getColor(R.color.colorAccent));
                barTitle3.setTextColor(getColor(R.color.colorAccent));
                barTitle4.setTextColor(getColor(R.color.colorAccent));
                barTitle5.setTextColor(getColor(R.color.colorAccent));

                break;


            case 2:
                CURRENT_TAG = TAG_CHART;

                barIndicator2.setBackgroundColor(getColor(R.color.colorWhite));
                barIndicator1.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator3.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator4.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle2.setTextColor(getColor(R.color.colorWhite));
                barTitle1.setTextColor(getColor(R.color.colorAccent));
                barTitle3.setTextColor(getColor(R.color.colorAccent));
                barTitle4.setTextColor(getColor(R.color.colorAccent));
                barTitle5.setTextColor(getColor(R.color.colorAccent));

                break;


            case 3:
                CURRENT_TAG = TAG_MEDICATION;

                barIndicator3.setBackgroundColor(getColor(R.color.colorWhite));
                barIndicator2.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator1.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator4.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle3.setTextColor(getColor(R.color.colorWhite));
                barTitle2.setTextColor(getColor(R.color.colorAccent));
                barTitle1.setTextColor(getColor(R.color.colorAccent));
                barTitle4.setTextColor(getColor(R.color.colorAccent));
                barTitle5.setTextColor(getColor(R.color.colorAccent));

                break;


            case 4:
                CURRENT_TAG = TAG_FOOD;

                barIndicator4.setBackgroundColor(getColor(R.color.colorWhite));
                barIndicator2.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator3.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator1.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator5.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle4.setTextColor(getColor(R.color.colorWhite));
                barTitle2.setTextColor(getColor(R.color.colorAccent));
                barTitle3.setTextColor(getColor(R.color.colorAccent));
                barTitle1.setTextColor(getColor(R.color.colorAccent));
                barTitle5.setTextColor(getColor(R.color.colorAccent));

                break;


            case 5:
                CURRENT_TAG = TAG_WORKOUT;

                barIndicator5.setBackgroundColor(getColor(R.color.colorWhite));
                barIndicator2.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator3.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator4.setBackgroundColor(getColor(R.color.colorPrimary));
                barIndicator1.setBackgroundColor(getColor(R.color.colorPrimary));

                ImageViewCompat.setImageTintList(barImage5, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite)));
                ImageViewCompat.setImageTintList(barImage2, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage3, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage4, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                ImageViewCompat.setImageTintList(barImage1, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));

                barTitle5.setTextColor(getColor(R.color.colorWhite));
                barTitle2.setTextColor(getColor(R.color.colorAccent));
                barTitle3.setTextColor(getColor(R.color.colorAccent));
                barTitle4.setTextColor(getColor(R.color.colorAccent));
                barTitle1.setTextColor(getColor(R.color.colorAccent));

                break;
        }

        loadHomeFragment();
    }


    /**
     * Select the required fragement based on the current Tag and Load the required fragment
     */
    private void loadHomeFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 1 || CURRENT_TAG!=TAG_HOME) {
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
     * @return The fragment specified by the CURRENT_TAG is returned
     */
    private Fragment getHomeFragment() {
        switch (CURRENT_TAG) {

            case TAG_MYPROFILE:
                goalIcon.setVisibility(View.GONE);
                actionBarTitle.setText("MY PRofile");
                return new MyProfileFragment();

            case TAG_RESOURCES:
                goalIcon.setVisibility(View.GONE);
                actionBarTitle.setText("Resources");
                return new ResourcesFragment();

            case TAG_HOME:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Home");
                return new HomeFragment();


            case TAG_CHART:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("History & Metrics");
                return new ChartMenuFragment();


            case TAG_MEDICATION:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Recommendations");
                return new RecommendationsFragment();


            case TAG_FOOD:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Food Intake");
                return new FoodFragment();


            case TAG_WORKOUT:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Daily Workout");
                return new WorkoutFragment();


            case TAG_GOAL:
                goalIcon.setVisibility(View.GONE);
                actionBarTitle.setText("Goal");
                return new GoalFragment();


            default:
                goalIcon.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Home");
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
     *Navigates to Login Screen
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
