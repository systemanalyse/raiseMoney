package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.adapters.HomeAssignmentAdapter;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class PublisherConfirmActivity extends AppCompatActivity {
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.detail_toolbar)
  Toolbar toolbar;
  @BindView(R.id.rv_publisher_confirm_acceptor_list)
  RecyclerView rvAccpetorList;

  @BindView(R.id.cancel)
  LinearLayout cancel;
  @BindView(R.id.confirm)
  LinearLayout confirm;

  private Unbinder unbinder;

  private List<User> accpter = new ArrayList<>();

  private HomeAssignmentAdapter ddAssignmentAdapter;

  String userId;
  Assignment assignment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_publisher_confirm);
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

    assignment = (Assignment) getIntent().getSerializableExtra("assignment");

    SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
    userId = sharedPreferences.getString("userid","");

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


  @OnClick(R.id.cancel)
  public void cancel() {
    finish();
  }

  @OnClick(R.id.confirm)
  public void confirm() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          JsonObject jsonObject  = new JsonObject();
          HttpClient.getInstance().put("/task/Confirm/" + assignment.getTaskid() + "/" + userId + "/" + assignment.getAcceptor().get(0).getUserid(), gson.toJson(jsonObject), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String res = response.body().string();
              Log.i("data", "/task/Confirm/" + assignment.getTaskid() + "/" + userId + "/" + assignment.getAcceptor().get(0).getUserid());
              Log.i("data", res);
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  if (response.isSuccessful()) {
                    Log.i("data", "Confirm: " + res);

                    assignment.setStatusCode(3);
                    /**
                     * api 返回没有 origin 字段
                     */
                    assignment.setOrigin(2);
                    Intent intent = new Intent(PublisherConfirmActivity.this, ConfirmFeedbackActivity.class);
                    intent.putExtra("operation", "confirm");
                    intent.putExtra("assignment", assignment);
                    startActivity(intent);
                    finish();
                  } else {
                    Toast.makeText(getApplicationContext(), "发生错误", Toast.LENGTH_SHORT).show();
                  }
                }
              });
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
