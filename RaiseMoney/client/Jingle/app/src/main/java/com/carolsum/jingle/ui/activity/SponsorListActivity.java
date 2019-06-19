package com.carolsum.jingle.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.ui.adapters.AssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SponsorListActivity extends AppCompatActivity {

  @BindView(R.id.running_sponsor_list)
  RecyclerView runnintListView;
  @BindView(R.id.finished_sponsor_list)
  RecyclerView finishedListView;

  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.running_list_wrapper)
  LinearLayout runningListLayout;
  @BindView(R.id.divider)
  View divider;
  @BindView(R.id.finish_list_wrapper)
  LinearLayout finishListLayout;

  private Unbinder unbinder;
  private List<Assignment> runningList = new ArrayList<>();
  private List<Assignment> finishedList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sponsor_list);
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

    initAssignment();
    // 设置进行中列表
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    runnintListView.setLayoutManager(layoutManager);
    AssignmentAdapter adapter = new AssignmentAdapter(runningList);
    runnintListView.setAdapter(adapter);

    // 设置已完成列表
    LinearLayoutManager _layoutManager = new LinearLayoutManager(this);
    finishedListView.setLayoutManager(_layoutManager);
    AssignmentAdapter _adapter = new AssignmentAdapter(finishedList);
    finishedListView.setAdapter(_adapter);

    setupList();
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  private void initAssignment() {
    for(int i = 0; i < 1; i++) {
      Assignment assignment = new Assignment("求好心人帮拿快递！", 0, -5, 3, "今天17:40 发起");
      Assignment assignment1 = new Assignment("帮我评论点个赞？谢啦", 1, -10, 4, "今天17:40 发起");
      runningList.add(assignment);
      runningList.add(assignment1);
    }

    for(int i = 0; i < 4; i++) {
      Assignment assignment = new Assignment("求好心人帮拿快递！", 0, -34, 6, "今天17:40 发起");
      Assignment assignment1 = new Assignment("帮我评论点个赞？谢啦", 1, -7, 2, "今天17:40 发起");
      finishedList.add(assignment1);
      finishedList.add(assignment);
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

  private void setupList() {
    if (runningList.size() <= 0 && finishedList.size() > 0) {
      runningListLayout.setVisibility(View.GONE);
      finishListLayout.setVisibility(View.VISIBLE);
      divider.setVisibility(View.GONE);
    } else if (runningList.size() > 0 && finishedList.size() <= 0) {
      runningListLayout.setVisibility(View.VISIBLE);
      finishListLayout.setVisibility(View.GONE);
      divider.setVisibility(View.GONE);
    } else {
      runningListLayout.setVisibility(View.VISIBLE);
      finishListLayout.setVisibility(View.VISIBLE);
      divider.setVisibility(View.VISIBLE);
    }
  }
}
