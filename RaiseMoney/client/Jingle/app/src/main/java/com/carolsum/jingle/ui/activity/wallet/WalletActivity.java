package com.carolsum.jingle.ui.activity.wallet;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WalletActivity extends AppCompatActivity {

  @BindView(R.id.withdraw_btn)
  LinearLayout withdrawBtn;
  @BindView(R.id.recharge_btn)
  LinearLayout rechargeBtn;

  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.wallet_toolbar)
  Toolbar toolbar;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);
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

  @OnClick(R.id.recharge_btn)
  public void gotoRecharge() {
    Intent intent = new Intent(WalletActivity.this, RechargeActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.withdraw_btn)
  public void gotoWithdraw() {
    Intent intent = new Intent(WalletActivity.this, WithdrawActivity.class);
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
