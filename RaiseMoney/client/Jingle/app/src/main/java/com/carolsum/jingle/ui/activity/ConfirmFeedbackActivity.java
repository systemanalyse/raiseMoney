package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmFeedbackActivity extends AppCompatActivity {
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.detail_toolbar)
  Toolbar toolbar;

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.hint)
  TextView hint;
  @BindView(R.id.btn)
  LinearLayout btn;
  @BindView(R.id.btn_text)
  TextView btnText;

  private Unbinder unbinder;

  Assignment assignment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_confirm_feedback);
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

    assignment = new Assignment();

    init();
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

  public void init() {
    // TODO
    String op = getIntent().getStringExtra("operation");
    if (op.equals("publishPP")) {
      title.setText("找人跑跑");
      hint.setText("发布成功");
      btnText.setText("立即查看");

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      });


    } else if (op.equals("publishDD")) {
      title.setText("找人点点");
      hint.setText("发布成功");
      btnText.setText("立即查看");

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      });


    } else if (op.equals("finishPP")) {
      title.setText("通知已取");
      hint.setText("发送成功");
      btnText.setText("返回任务详情");

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          assignment = (Assignment) getIntent().getSerializableExtra("assignment");
          Intent intent = new Intent(ConfirmFeedbackActivity.this, AssignmentDetailActivity.class);
          intent.putExtra("assignment", assignment);
          startActivity(intent);
          finish();
        }
      });

    } else if (op.equals("finishDD")) {
      title.setText("通知已完成");
      hint.setText("发送成功");
      btnText.setText("返回任务详情");

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          assignment = (Assignment) getIntent().getSerializableExtra("assignment");
          Intent intent = new Intent(ConfirmFeedbackActivity.this, AssignmentDetailActivity.class);
          intent.putExtra("assignment", assignment);
          startActivity(intent);
          finish();
        }
      });

    } else if (op.equals("confirm")) {
      title.setText("确认完成");
      hint.setText("确认成功");
      btnText.setText("返回查看");

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          assignment = (Assignment) getIntent().getSerializableExtra("assignment");
          Intent intent = new Intent(ConfirmFeedbackActivity.this, AssignmentDetailActivity.class);
          intent.putExtra("assignment", assignment);
          startActivity(intent);
          finish();
        }
      });

    }
  }
}
