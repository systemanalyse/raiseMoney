package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.carolsum.jingle.MainActivity;
import com.carolsum.jingle.R;
import com.carolsum.jingle.ui.adapters.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

  @BindView(R.id.intro_indicator_image)
  ImageView indicatorImage;
  @BindView(R.id.view_pager)
  ViewPager viewPager;
  private List<View> viewList = new ArrayList<>();

  private int currentPage = 0;
  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide);
    unbinder = ButterKnife.bind(this);
    initViews();
  }

  private void initViews() {
    viewList.add(View.inflate(getApplicationContext(), R.layout.first_intro_frag, null));
    viewList.add(View.inflate(getApplicationContext(), R.layout.second_intro_frag, null));
    viewList.add(View.inflate(getApplicationContext(), R.layout.third_intro_frag, null));
    viewPager.setAdapter(new GuidePageAdapter(viewList));
    viewPager.addOnPageChangeListener(this);
  }

  @OnClick(R.id.intro_indicator_image)
  public void next() {
    if (currentPage == viewList.size() - 1) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
      finish();
    } else {
      viewPager.setCurrentItem(currentPage + 1);
    }
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  @Override
  public void onPageScrolled(int i, float v, int i1) {

  }

  @Override
  public void onPageSelected(int i) {
    currentPage = i;
  }

  @Override
  public void onPageScrollStateChanged(int i) {

  }
}
