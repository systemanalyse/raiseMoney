package com.carolsum.jingle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
  private Unbinder unbinder;

  @BindView(R.id.tabs)
  TabLayout tabLayout;
  @BindView(R.id.fragment_home_viewpager)
  ViewPager viewPager;

  private ArrayList<Fragment> fragments = new ArrayList();
  private ArrayList<String> titles = new ArrayList<>();

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
    for (int i = 0; i < tabLayout.getTabCount(); i++) {
      TabLayout.Tab tab = tabLayout.getTabAt(i);
      if (tab != null) {
        tab.setCustomView(getTabView(i));
      }
    }

    tabLayout.getTabAt(0).select();
    viewPager.setCurrentItem(1);
    viewPager.setCurrentItem(0);
  }

  @Override
  protected void initEvent () {
    tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        String type = tab.getText().toString();
        EventBus.getDefault().post(new LoginEvent(type));

        View view = tab.getCustomView();
        if (null != view && view instanceof TextView) {
          ((TextView) view).setTextSize(22);
          ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.home_tab_selected));
        }

      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null != view && view instanceof TextView) {
          ((TextView) view).setTextSize(18);
          ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.home_tab_unselected));
        }

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  public void onDestroyView () {
    unbinder.unbind();
    super.onDestroyView();
  }

  /**
   * 自定义Tab的View
   */
  private View getTabView(int currentPosition) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tab, null);
    TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
    textView.setText(titles.get(currentPosition));
    return view;
  }

}
