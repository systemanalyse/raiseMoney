package com.carolsum.jingle.ui.activity.wallet;

import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RechargeActivity extends AppCompatActivity {

  @BindView(R.id.recharge_50_btn)
  MaterialCardView recharge50Btn;
  @BindView(R.id.recharge_100_btn)
  MaterialCardView recharge100Btn;
  @BindView(R.id.recharge_200_btn)
  MaterialCardView recharge200Btn;
  @BindView(R.id.recharge_500_btn)
  MaterialCardView recharge500Btn;
  @BindView(R.id.recharge_1000_btn)
  MaterialCardView recharge1000Btn;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recharge);
    unbinder = ButterKnife.bind(this);
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }
}
