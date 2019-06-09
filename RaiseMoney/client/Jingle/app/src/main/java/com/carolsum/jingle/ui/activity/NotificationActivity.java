package com.carolsum.jingle.ui.activity;

import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
  MaterialButton markAllReadBtn;
  @BindView(R.id.unread_recycler_view)
  RecyclerView unreadRecyclerView;

  private Unbinder unbinder;

  private List<JNotification> unreadList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    unbinder = ButterKnife.bind(this);
    initNotifications();

    // 设置未读消息列表
    LinearLayoutManager unreadlayoutManager = new LinearLayoutManager(this);
    unreadRecyclerView.setLayoutManager(unreadlayoutManager);
    NotificationAdapter adapter = new NotificationAdapter(unreadList);
    unreadRecyclerView.setAdapter(adapter);
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  private void initNotifications() {
    for (int i = 0; i < 5; i++) {
      JNotification notification = new JNotification(0, "你发起的任务已完成，点击确认", 0, "求好心人帮忙带快递～求好心人帮gadugiaugdiuga", "今天17:40", false);
      JNotification notification2 = new JNotification(1, "你接受的任务已被确认完成，点击查看", 1, "帮我评论点个赞？谢啦", "今天12:10", true);
      unreadList.add(notification);
      unreadList.add(notification2);
    }

  }
}
