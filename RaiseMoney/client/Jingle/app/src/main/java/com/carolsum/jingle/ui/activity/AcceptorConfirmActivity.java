package com.carolsum.jingle.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AcceptorConfirmActivity extends AppCompatActivity {
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.detail_toolbar)
  Toolbar toolbar;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_accpter_confirm);
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
}
