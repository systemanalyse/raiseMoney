package com.carolsum.jingle.ui.activity.wallet;

import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RechargeActivity extends AppCompatActivity {

  @BindView(R.id.recharge_50_btn)
  MaterialCardView recharge50Btn;
  @BindView(R.id.recharge_100_btn)
  MaterialCardView recharge100Btn;
  @BindView(R.id.recharge_500_btn)
  MaterialCardView recharge500Btn;
  @BindView(R.id.recharge_1000_btn)
  MaterialCardView recharge1000Btn;

  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.recharge_toolbar)
  Toolbar toolbar;

  @BindView(R.id.recharge_info_btn)
  MaterialButton rechargeInfo;
  @BindView(R.id.confirm_recharge_btn)
  MaterialButton confirmBtn;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recharge);
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


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.recharge_50_btn)
  public void recharge50() {
    rechargeInfo.setText("共支付 5 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_100_btn)
  public void recharge100() {
    rechargeInfo.setText("共支付 10 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_500_btn)
  public void recharge500() {
    rechargeInfo.setText("共支付 50 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_1000_btn)
  public void recharge1000() {
    rechargeInfo.setText("共支付 100 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }
}
