package com.carolsum.jingle.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.JNotification;
import com.carolsum.jingle.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationActivity extends AppCompatActivity {

  @BindView(R.id.mark_all_read_btn)
  TextView markAllReadBtn;
  @BindView(R.id.unread_recycler_view)
  RecyclerView unreadRecyclerView;
  @BindView(R.id.read_recycler_view)
  RecyclerView readRecyclerView;
  @BindView(R.id.divider)
  View divider;

  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.notification_toolbar)
  Toolbar toolbar;

  private Unbinder unbinder;

  // 未读列表
  private List<JNotification> unreadList = new ArrayList<>();
  // 已读列表
  private List<JNotification> readList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
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

    initNotifications();

    // 设置未读消息列表
    LinearLayoutManager unreadlayoutManager = new LinearLayoutManager(this);
    unreadRecyclerView.setLayoutManager(unreadlayoutManager);
    NotificationAdapter adapter = new NotificationAdapter(unreadList);
    unreadRecyclerView.setAdapter(adapter);

    LinearLayoutManager readlayoutManager = new LinearLayoutManager(this);
    readRecyclerView.setLayoutManager(readlayoutManager);
    NotificationAdapter adapter1 = new NotificationAdapter(readList);
    readRecyclerView.setAdapter(adapter1);

    showList();
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  private void initNotifications() {
    for (int i = 0; i < 5; i++) {
      JNotification notification = new JNotification(0, "你发起的任务已完成，点击确认", 0, "求好心人帮忙带快递～求好心人帮gadugiaugdiuga", "今天17:40", false);
      JNotification notification3 = new JNotification(2, "你的提现请求已完成，点击查看", 2, "您于2018/5/22 17:35分向XXX提...", "今天17:40", true);
      unreadList.add(notification);
      readList.add(notification3);
    }
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

  private void showList() {
    if (readList.size() <= 0 && unreadList.size() > 0) {
      readRecyclerView.setVisibility(View.GONE);
      divider.setVisibility(View.GONE);
      unreadRecyclerView.setVisibility(View.VISIBLE);
    } else if (readList.size() > 0 && unreadList.size() <= 0) {
      readRecyclerView.setVisibility(View.VISIBLE);
      divider.setVisibility(View.GONE);
      unreadRecyclerView.setVisibility(View.GONE);
    } else if (readList.size() > 0 && unreadList.size() > 0) {
      readRecyclerView.setVisibility(View.VISIBLE);
      divider.setVisibility(View.VISIBLE);
      unreadRecyclerView.setVisibility(View.VISIBLE);
    } else {
      readRecyclerView.setVisibility(View.GONE);
      unreadRecyclerView.setVisibility(View.GONE);
      divider.setVisibility(View.GONE);
    }
  }
}
