package com.carolsum.jingle.ui.activity;

import android.content.SharedPreferences;
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
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.adapters.NotificationAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.carolsum.jingle.net.HttpClient.gson;

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

    private String userId = "";

    private NotificationAdapter unreadAdapter;
    private NotificationAdapter readAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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

//    initNotifications();

        // 设置未读消息列表
        LinearLayoutManager unreadlayoutManager = new LinearLayoutManager(this);
        unreadRecyclerView.setLayoutManager(unreadlayoutManager);
        unreadAdapter = new NotificationAdapter(unreadList);
        unreadRecyclerView.setAdapter(unreadAdapter);

        LinearLayoutManager readlayoutManager = new LinearLayoutManager(this);
        readRecyclerView.setLayoutManager(readlayoutManager);
        readAdapter = new NotificationAdapter(readList);
        readRecyclerView.setAdapter(readAdapter);

        fetchNotifies();

    }

    private void fetchNotifies() {
        readList.clear();
        unreadList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = HttpClient.getInstance().get("/user/" + userId + "/Notice");
                    if (res != null &&  !res.equals("")) {
                        // 对返回的json串进行解析
//                        String tempRes = "[{\"noticeId\":0,\"userType\":true,\"taskType\":true,\"title\":\"this is title\",\"desc\":\"this is desc\",\"time\":\"下午 18:30\",\"status\":true},{\"noticeId\":1,\"userType\":false,\"taskType\":false,\"title\":\"this is title\",\"desc\":\"this is desc\",\"time\":\"下午 18:30\",\"status\":false}]";

                        //Json的解析类对象
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(res).getAsJsonArray();
                        ArrayList<JNotification> notifications = new ArrayList<>();
                        for (JsonElement notify : jsonArray) {
                            JNotification notification = gson.fromJson(notify, JNotification.class);
                            notifications.add(notification);
                        }

                        for (JNotification notification : notifications) {
                            if (notification.isStatus()) {
                                readList.add(notification);
                            } else {
                                unreadList.add(notification);
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                readAdapter.notifyDataSetChanged();
                                unreadAdapter.notifyDataSetChanged();
                                showList();
                            }
                        });

                    }
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

    @OnClick(R.id.mark_all_read_btn)
    public void markAll() {
        // 全部标记为已读
    }
}
