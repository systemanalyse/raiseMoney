package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    unbinder = ButterKnife.bind(this);
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
}
