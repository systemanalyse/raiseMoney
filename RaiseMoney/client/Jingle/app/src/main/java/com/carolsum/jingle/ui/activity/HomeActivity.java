package com.carolsum.jingle.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.event.LoginEvent;
import com.carolsum.jingle.event.UserEvent;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.ui.fragment.HomeFragment;
import com.carolsum.jingle.ui.fragment.PublishFragment;
import com.carolsum.jingle.ui.fragment.SpaceFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.bottom_nav_bar)
    BottomNavigationViewEx bnve;
    @BindView(R.id.home_viewPager)
    ViewPager viewPager;

    private HomeFragment homeFragment = new HomeFragment();
    private SpaceFragment spaceFragment = new SpaceFragment();
    private PublishFragment publishFragment = new PublishFragment();

    private Unbinder unbinder;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);
        initViewPager();
        initBottomNavBar();
        setupUser();
    }

    private void setupUser() {
        user = (User) getIntent().getSerializableExtra("user");
        // 通过 eventbus 向fragment 传递 user 实例
        EventBus.getDefault().post(new UserEvent(user));
    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void initViewPager() {
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
        bnve.enableItemShiftingMode(false);
        bnve.setOnNavigationItemSelectedListener(menuItem -> {
            viewPager.setCurrentItem(menuItem.getOrder());
            return true;
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        Toast.makeText(getApplicationContext(), "Welcome " + event.username + "!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
