package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.carolsum.jingle.MainActivity;
import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsActivity extends AppCompatActivity {

  @BindView(R.id.campus_certification_setting)
  MaterialCardView certificationView;
  @BindView(R.id.profile_setting)
  MaterialCardView profileView;
  @BindView(R.id.logout_btn)
  MaterialButton logout_btn;
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.settings_toolbar)
  Toolbar toolbar;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
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
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  @OnClick(R.id.campus_certification_setting)
  public void gotoCertificationSetting() {
    Intent intent = new Intent(this, CertificationActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.profile_setting)
  public void gotoProfileSetting() {
    Intent intent = new Intent(this, ProfileSettingActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.logout_btn)
  public void logout() {
    // 登出
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
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
