package com.carolsum.jingle.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.Receipt;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class AssignmentDetailActivity  extends AppCompatActivity {
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.detail_toolbar)
  Toolbar toolbar;

  @BindView(R.id.tv_page_title)
  TextView tvPageTitle;
  @BindView(R.id.tv_assignment_title)
  TextView tvAssignmentTitle;
  @BindView(R.id.tv_assignment_publishTime)
  TextView tvAssignmentPublishTime;
  @BindView(R.id.iv_assignment_status)
  ImageView ivAssignmentStatus;

  // 任务信息
  @BindView(R.id.ll_detail_pp_contain1)
  LinearLayout llDetailPpContain1;
  @BindView(R.id.ll_detail_pp_contain2)
  LinearLayout llDetailPpContain2;
  @BindView(R.id.ll_detail_dd_contain1)
  LinearLayout llDetailDdContain1;
  @BindView(R.id.ll_detail_dd_contain2)
  LinearLayout llDetailDdContain2;
  @BindView(R.id.tv_detail_dd_startPosition)
  TextView tvStartPostion;
  @BindView(R.id.tv_detail_dd_endPosition)
  TextView tvEndPostion;
  @BindView(R.id.tv_detail_pp_time)
  TextView tvDetailPpTime;
  @BindView(R.id.tv_detail_pp_weight)
  TextView tvDetailPpWeight;
  @BindView(R.id.mpb_detail_dd_progress)
  MagicProgressBar mpbDetailDdProgress;
  @BindView(R.id.tv_detail_dd_progress)
  TextView tvDetailDdProgress;
  @BindView(R.id.tv_detail_dd_date)
  TextView tvDetailDdDate;
  @BindView(R.id.tv_detail_dd_ddl)
  TextView tvDetailDdDdl;
  @BindView(R.id.tv_detail_dd_value_type)
  TextView tvDetailDdValueType;
  @BindView(R.id.tv_detail_dd_value)
  TextView tvDetailDdValue;

  // 发起人/接收人信息
  @BindView(R.id.ll_detail_certification)
  LinearLayout llDetailCertification;
  @BindView(R.id.ll_publisher_private)
  LinearLayout llPublisherPrivate;
  @BindView(R.id.space_user_avatar)
  CircleImageView userAvatar;
  @BindView(R.id.space_username)
  TextView username;
  @BindView(R.id.space_gender)
  ImageView genderImage;
  @BindView(R.id.space_address)
  TextView address;
  @BindView(R.id.space_accept_number)
  TextView acceptNum;
  @BindView(R.id.space_publish_number)
  TextView publishNum;

  // 提示信息
  @BindView(R.id.ll_status_wait_for_order)
  LinearLayout llStatusWaitForOrder;
  @BindView(R.id.ll_status_accepted)
  LinearLayout llStatusAccepted;
  @BindView(R.id.ll_status_unconfirm)
  LinearLayout llStatusUnconfirm;
  @BindView(R.id.ll_status_finish)
  LinearLayout llStatusFinish;
  @BindView(R.id.ll_status_not_on_time)
  LinearLayout llStatusNotOnTime;
  @BindView(R.id.ll_status_overdue)
  LinearLayout llStatusOverdue;
  @BindView(R.id.tv_status_accepted_time_to_go)
  TextView tvStatusAcceptedTimeToGo;
  @BindView(R.id.tv_status_unconfirm_title)
  TextView tvStatusUnconfirmTitle;
  @BindView(R.id.tv_status_unconfirm_description)
  TextView getTvStatusUnconfirmDescription;
  @BindView(R.id.iv_status_unconfirm_image1)
  ImageView ivImage1;
  @BindView(R.id.tv_status_finish_hint)
    TextView tvStatusFinishHint;
  @BindView(R.id.iv_status_finish_logo)
  ImageView tvStatusFinishLogo;
  @BindView(R.id.tv_overdue_title)
  TextView tvOverdueTitle;
  @BindView(R.id.ll_status_overdue_hint)
  LinearLayout llStatusOverdueHint;
  @BindView(R.id.tv_status_overdue_text)
    TextView tvStatusOverdueText;
  @BindView(R.id.tv_not_on_time_title)
  TextView tvNotOnTimeTitle;
  @BindView(R.id.ll_status_not_on_time_hint)
  LinearLayout llStatusNotOnTimeHint;
  @BindView(R.id.tv_status_not_on_time_text)
  TextView tvStatusNotOnTimeText;



  Assignment assignment;

  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_assignment_detail);
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

    init();
  }

  private void init() {
    assignment = (Assignment) getIntent().getSerializableExtra("assignment");

    // assignment title
    tvAssignmentTitle.setText(assignment.getTitle());

    // assignment publish time
    tvAssignmentPublishTime.setText(assignment.getBeginTime());



    // 任务详情部分
    if (assignment != null) {
      if (assignment.getTaskType() == 0) {
        // page title
        tvPageTitle.setText("跑跑详情");

        // 3 column
        llDetailPpContain1.setVisibility(View.VISIBLE);
        llDetailPpContain2.setVisibility(View.VISIBLE);
        llDetailDdContain1.setVisibility(View.GONE);
        llDetailDdContain2.setVisibility(View.GONE);

        //
        tvStartPostion.setText(assignment.getStartPosition());
        tvEndPostion.setText(assignment.getEndPosition());
        tvDetailPpTime.setText(assignment.getDdl());
        // TODO: Weight


      } else {
        // page title
        tvPageTitle.setText("点点详情");

        // 3 column
        llDetailPpContain1.setVisibility(View.GONE);
        llDetailPpContain2.setVisibility(View.GONE);
        llDetailDdContain1.setVisibility(View.VISIBLE);
        llDetailDdContain2.setVisibility(View.VISIBLE);

        //
        mpbDetailDdProgress.setSmoothPercent((float) assignment.getFinishNum() / assignment.getTotalNum());
        tvDetailDdProgress.setText(Integer.toString(assignment.getFinishNum()) + "/" + Integer.toString(assignment.getTotalNum()));
        tvDetailDdDdl.setText(assignment.getDdl());
        // TODO: 赏金分配方式
        tvDetailDdValue.setText(assignment.getValue() + " JIN币");
      }



      // 发起者/接收者信息部分
      // 作为发布方
      if (assignment.getOrigin() == 1) {
        // 待接单或已超期，无接受方
        if (assignment.getStatusCode() == 0 || assignment.getStatusCode() == 4) {

          llDetailCertification.setVisibility(View.GONE);

        } else {
          fetchUserInfo(assignment.getPublishorInfo().getUserid());
        }

      // 作为接受方
      } else if (assignment.getOrigin() == 2) {

        fetchUserInfo(assignment.getPublishorInfo().getUserid());

      // 接受方待接单
      } else {
        llDetailCertification.setVisibility(View.VISIBLE);
        llPublisherPrivate.setVisibility(View.GONE);

        fetchUserInfo(assignment.getPublishorInfo().getUserid());
      }



      // assignment status
      switch (assignment.getStatusCode()) {
        case 0:
          ivAssignmentStatus.setImageResource(R.drawable.state_wait_for_order);
          // 作为发布方
          if (assignment.getOrigin() == 1) {
            llStatusWaitForOrder.setVisibility(View.VISIBLE);
          }

          break;
        case 1:
          ivAssignmentStatus.setImageResource(R.drawable.state_running);
          llStatusAccepted.setVisibility(View.VISIBLE);
          tvStatusAcceptedTimeToGo.setText(assignment.getDdl());

          break;
        case 2:
          ivAssignmentStatus.setImageResource(R.drawable.state_unconfirm);
          llStatusUnconfirm.setVisibility(View.VISIBLE);
          // 作为接单方
          if (assignment.getOrigin() == 2) {
            tvStatusUnconfirmTitle.setText("已通知对方，等待确认…");
              // 调用API，获取回执
              SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
              String userId = sharedPreferences.getString("userid","");
              fetchReceipt(assignment.getTaskid(), Integer.parseInt(userId));
          } else {
            // 跑跑
            if (assignment.getTaskType() == 0) {
              tvStatusUnconfirmTitle.setText("对方已帮你取到，这是TA的任务回执");
              fetchReceipt(assignment.getTaskid(), assignment.getPublishorInfo().getUserid());
            }
          }

          break;
        case 3:
          ivAssignmentStatus.setImageResource(R.drawable.state_finish);
          llStatusFinish.setVisibility(View.VISIBLE);
          // 作为发起方
          if (assignment.getOrigin() == 1) {
            tvStatusFinishHint.setText("Jingle~你已确认订单，任务完成！");
            tvStatusFinishLogo.setImageResource(R.drawable.pp_acceptor_finish);
          }

          break;
        case 4:
          ivAssignmentStatus.setImageResource(R.drawable.state_overdue);
          llStatusOverdue.setVisibility(View.VISIBLE);
          // 作为发起方
          if (assignment.getOrigin() == 1) {
            // 跑跑
            if (assignment.getTaskType() == 0) {
              tvStatusOverdueText.setText("支付的" + assignment.getValue() + "JIN币已退回你的钱包");
            } else {
              tvStatusOverdueText.setText("剩余的" + assignment.getValue() + "JIN币已退回你的钱包");
            }
          } else {
            llStatusOverdueHint.setVisibility(View.GONE);
            tvOverdueTitle.setText("很遗憾，你未能按时完成任务");
          }

          break;
        case 5:
//          ivAssignmentStatus.setImageResource(R.drawable.state_not_on_time);
//          llStatusNotOnTime.setVisibility(View.VISIBLE);
//          // 作为发起方
//          if (assignment.getOrigin() == 1) {
//            // 跑跑
//            if (assignment.getTaskType() == 0) {
//              tvStatusNotOnTimeText.setText("支付的" + assignment.getValue() + "JIN币已退回你的钱包");
//            } else {
//              tvStatusNotOnTimeText.setText("剩余的" + assignment.getValue() + "JIN币已退回你的钱包");
//            }
//          } else {
//            llStatusNotOnTimeHint.setVisibility(View.GONE);
//            tvNotOnTimeTitle.setText("很遗憾，你未能按时完成任务");
//          }

          break;
      }
    }
  }

  private void fetchUserInfo(int userid) {
    new Thread(new Runnable() {
      @Override
      public void run() {
          try {
            String res = HttpClient.getInstance().get("/user/" + userid + "/Public");

            if (res.startsWith("{")) {
              User user = gson.fromJson(res, User.class);
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  setUserInfo(user);
                }
              });
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (IllegalStateException e) {
            e.printStackTrace();
          }
        }

    }).start();
  }

  private void setUserInfo(User currentUser) {
    if (currentUser != null &&  currentUser.avatarURL != null && !currentUser.avatarURL.equals("")) {
      // 加载用户头像
      Glide.with(this).load(HttpClient.getPictureBaseUrl + currentUser.avatarURL).into(userAvatar);
    } else {
      // 加载默认头像
      Glide.with(this).load(R.drawable.default_avatar).into(userAvatar);
    }
    username.setText(currentUser.getName());
    address.setText(currentUser.getDormitory());
    if (currentUser.getGender() == 1) {
      genderImage.setImageResource(R.drawable.man);
    } else {
      genderImage.setImageResource(R.drawable.woman);
    }
    acceptNum.setText(Integer.toString(currentUser.getAcceptNum()));
    publishNum.setText(Integer.toString(currentUser.getPublishNum()));
  }

  private void fetchReceipt(int taskid, int userid) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String res = HttpClient.getInstance().get("/task/Feedback/" + taskid + "/" + userid);

          if (res.startsWith("{")) {
            Receipt receipt = gson.fromJson(res, Receipt.class);
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                getTvStatusUnconfirmDescription.setText(receipt.getDesc());
                Glide.with(getApplicationContext()).load(HttpClient.getPictureBaseUrl + receipt.getImgURL()).into(ivImage1);
                // TODO: 或有两张返回图片
              }
            });
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (IllegalStateException e) {
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
}
