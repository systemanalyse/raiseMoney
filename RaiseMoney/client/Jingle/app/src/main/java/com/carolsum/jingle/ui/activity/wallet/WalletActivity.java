package com.carolsum.jingle.ui.activity.wallet;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WalletActivity extends AppCompatActivity {

  @BindView(R.id.withdraw_btn)
  MaterialButton withdrawBtn;
  @BindView(R.id.recharge_btn)
  MaterialButton rechargeBtn;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);
    unbinder = ButterKnife.bind(this);
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
}
