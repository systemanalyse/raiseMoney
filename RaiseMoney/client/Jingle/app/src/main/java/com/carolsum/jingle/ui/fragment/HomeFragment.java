package com.carolsum.jingle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

  private View fragmentView;
  @BindView(R.id.tabs)
  TabLayout tabLayout;
  @BindView(R.id.fragment_home_viewpager)
  ViewPager viewPager;

  private ArrayList<Fragment> fragments = new ArrayList();
  private ArrayList<String> titles = new ArrayList<>();

  private Unbinder unbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PPListFragment ppListFragment = new PPListFragment();
    DDListFragment ddListFragment = new DDListFragment();
    fragments.add(ppListFragment);
    fragments.add(ddListFragment);

    titles.add("跑跑");
    titles.add("点点");
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    initViewPager();
  }

  @Override
  protected void initData() {
  }

  @Override
  protected View initView() {
    fragmentView =  View.inflate(getActivity(), R.layout.fragment_home, null);
    unbinder = ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  private void initViewPager() {
    viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
      @Override
      public Fragment getItem(int i) {
        return fragments.get(i);
      }

      @Override
      public int getCount() {
        return fragments.size();
      }

      @Override
      public CharSequence getPageTitle(int i) {
        return titles.get(i);
      }
    });

    tabLayout.setupWithViewPager(viewPager);
  }

  @Override
  protected void initEvent() {
    tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        String type = tab.getText().toString();
        EventBus.getDefault().post(new LoginEvent(type));
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  public void onDestroyView() {
    unbinder.unbind();
    super.onDestroyView();
  }
}
