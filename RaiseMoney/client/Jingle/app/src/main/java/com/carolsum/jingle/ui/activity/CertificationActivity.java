package com.carolsum.jingle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carolsum.jingle.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CertificationActivity extends AppCompatActivity {

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_certification);
    unbinder = ButterKnife.bind(this);
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }
}
