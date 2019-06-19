package com.carolsum.jingle.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CertificationActivity extends AppCompatActivity {

  @BindView(R.id.certification_toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.register_male_radio)
  RadioButton maleRadio;
  @BindView(R.id.register_female_radio)
  RadioButton femaleRadio;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_certification);
    unbinder = ButterKnife.bind(this);
    // 获取状态栏高度 更新toolbar的marginTop
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) appBarLayout.getLayoutParams();
      lp.setMargins(0, getResources().getDimensionPixelSize(resourceId), 0, 0);
      appBarLayout.setLayoutParams(lp);
    }

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }

    // 修改RadioButton drawableRight 的大小
    Drawable maleIcon = getDrawable(R.drawable.man);
    maleIcon.setBounds(0,0,54,54);
    maleRadio.setCompoundDrawables(null, null, maleIcon, null);

    Drawable femaleIcon = getDrawable(R.drawable.woman);
    femaleIcon.setBounds(0,0,54,54);
    femaleRadio.setCompoundDrawables(null, null, femaleIcon, null);

  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
