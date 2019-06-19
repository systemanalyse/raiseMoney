package com.carolsum.jingle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

  private Unbinder unbinder;
  private List<Assignment> runningList = new ArrayList<>();
  private List<Assignment> finishedList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sponsor_list);
    unbinder = ButterKnife.bind(this);
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
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  private void initAssignment() {
    for(int i = 0; i < 1; i++) {
      Assignment assignment = new Assignment("求好心人帮拿快递！", 1, -5, 0, "发布时间：今天17:40");
      Assignment assignment1 = new Assignment("帮我评论点个赞？谢啦", 0, -10, 1, "发布时间：今天17:40");
      runningList.add(assignment);
      runningList.add(assignment1);
    }

    for(int i = 0; i < 4; i++) {
      Assignment assignment = new Assignment("求好心人帮拿快递！", 1, -34, 0, "结束时间：18/5/22 17:40");
      Assignment assignment1 = new Assignment("帮我评论点个赞？谢啦", 0, -7, 1, "结束时间：18/5/22 17:40");
      finishedList.add(assignment1);
      finishedList.add(assignment);
    }

  }
}
