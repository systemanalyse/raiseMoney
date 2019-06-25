package com.carolsum.jingle.ui.activity;

import android.content.SharedPreferences;
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
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.adapters.AssignmentAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.carolsum.jingle.net.HttpClient.gson;

public class AcceptListActivity extends AppCompatActivity {

  @BindView(R.id.running_accept_list)
  RecyclerView runnintListView;
  @BindView(R.id.finished_accept_list)
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

  private AssignmentAdapter adapter;
  private AssignmentAdapter _adapter;

  private String userId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_accept_list);

    unbinder = ButterKnife.bind(this);

    SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
    userId = sharedPreferences.getString("userid","");

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

    // 设置进行中列表
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    runnintListView.setLayoutManager(layoutManager);
    adapter = new AssignmentAdapter(runningList);
    runnintListView.setAdapter(adapter);

    // 设置已完成列表
    LinearLayoutManager _layoutManager = new LinearLayoutManager(this);
    finishedListView.setLayoutManager(_layoutManager);
    _adapter = new AssignmentAdapter(finishedList);
    finishedListView.setAdapter(_adapter);
  }


  @Override
  protected void onStart() {
    super.onStart();
    fetchAcceptList();
  }

  private void fetchAcceptList() {
    if (userId == null || userId.equals("")) return;
    runningList.clear();
    finishedList.clear();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String res2 = HttpClient.getInstance().get("/task/" + "Received/" + userId);
          if (res2 != null &&  !res2.equals("")) {
            // 对返回的json串进行解析
//                        String tempRes = "[{\"origin\":1,\"userid\":12,\"taskid\":1,\"taskStatus\":1,\"taskType\":1,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":10,\"acceptor\":[],\"finishor\":[]},{\"origin\":1,\"userid\":12,\"taskid\":2,\"taskStatus\":1,\"taskType\":0,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":1,\"acceptor\":[],\"finishor\":[]}]";

            //Json的解析类对象
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res2).getAsJsonArray();
            ArrayList<Assignment> assignments = new ArrayList<>();
            for (JsonElement assignment : jsonArray) {
              Assignment item = gson.fromJson(assignment, Assignment.class);
              assignments.add(item);
            }

            for (Assignment assignment : assignments) {
              // 如果这个接受的任务正在进行中
              if (assignment.getTaskStatus() == 1) {
                runningList.add(assignment);
              } else {
                finishedList.add(assignment);
              }
            }
          }

          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              adapter.notifyDataSetChanged();
              _adapter.notifyDataSetChanged();
              setupList();
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
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
