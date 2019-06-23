package com.carolsum.jingle.ui.activity.wallet;

import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RechargeActivity extends AppCompatActivity {

  @BindView(R.id.recharge_50_btn)
  LinearLayout recharge50Btn;
  @BindView(R.id.recharge_100_btn)
  LinearLayout recharge100Btn;
  @BindView(R.id.recharge_500_btn)
  LinearLayout recharge500Btn;
  @BindView(R.id.recharge_1000_btn)
  LinearLayout recharge1000Btn;

  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.recharge_toolbar)
  Toolbar toolbar;

  @BindView(R.id.recharge_info_btn)
  MaterialButton rechargeInfo;
  @BindView(R.id.confirm_recharge_btn)
  MaterialButton confirmBtn;

  // text
  @BindView(R.id.jin50)
  TextView jin50;
  @BindView(R.id.jin100)
  TextView jin100;
  @BindView(R.id.jin500)
  TextView jin500;
  @BindView(R.id.jin1000)
  TextView jin1000;
  @BindView(R.id.yuan50)
  TextView yuan50;
  @BindView(R.id.yuan100)
  TextView yuan100;
  @BindView(R.id.yuan500)
  TextView yuan500;
  @BindView(R.id.yuan1000)
  TextView yuan1000;

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

  private void resetStyle() {
    recharge50Btn.setBackgroundResource(R.drawable.recharge_cardview_normal);
    recharge100Btn.setBackgroundResource(R.drawable.recharge_cardview_normal);
    recharge500Btn.setBackgroundResource(R.drawable.recharge_cardview_normal);
    recharge1000Btn.setBackgroundResource(R.drawable.recharge_cardview_normal);

    jin50.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    jin100.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    jin500.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    jin1000.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));

    yuan50.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    yuan100.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    yuan500.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));
    yuan1000.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.recharge_cardview_text_normal));

  }

  @OnClick(R.id.recharge_50_btn)
  public void recharge50() {
    resetStyle();
    recharge50Btn.setBackgroundResource(R.drawable.cardview_edge);
    jin50.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    yuan50.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

    rechargeInfo.setText("共支付 5 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_100_btn)
  public void recharge100() {
    resetStyle();
    recharge100Btn.setBackgroundResource(R.drawable.cardview_edge);
    jin100.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    yuan100.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    rechargeInfo.setText("共支付 10 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_500_btn)
  public void recharge500() {
    resetStyle();
    recharge500Btn.setBackgroundResource(R.drawable.cardview_edge);
    jin500.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    yuan500.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    rechargeInfo.setText("共支付 50 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.recharge_1000_btn)
  public void recharge1000() {
    resetStyle();
    recharge1000Btn.setBackgroundResource(R.drawable.cardview_edge);
    jin1000.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    yuan1000.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    rechargeInfo.setText("共支付 100 元");
    rechargeInfo.setVisibility(View.VISIBLE);
  }
}
