package com.carolsum.jingle.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.carolsum.jingle.R;
import com.carolsum.jingle.ui.fragment.HomeFragment;
import com.carolsum.jingle.ui.fragment.PublishFragment;
import com.carolsum.jingle.ui.fragment.SpaceFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private BottomNavigationViewEx bnve;
    private ViewPager viewPager;

    private HomeFragment homeFragment = new HomeFragment();
    private SpaceFragment spaceFragment = new SpaceFragment();
    private PublishFragment publishFragment = new PublishFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViewPager();
        initBottomNavBar();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.home_viewPager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return homeFragment;
                    case 1:
                        return publishFragment;
                    case 2:
                        return spaceFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    private void initBottomNavBar() {
        bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_bar);
        bnve.enableItemShiftingMode(false);

        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                viewPager.setCurrentItem(menuItem.getOrder());
                return true;
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bnve.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
