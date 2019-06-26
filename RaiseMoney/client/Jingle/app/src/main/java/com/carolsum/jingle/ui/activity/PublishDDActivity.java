package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class PublishDDActivity extends AppCompatActivity {

  @BindView(R.id.dd_toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.receipt_desc)
  EditText receiptDesc;
  @BindView(R.id.image_pick)
  ImageView imagePick;
  @BindView(R.id.remove_image)
  ImageView removeImage;
  @BindView(R.id.dd_title)
  EditText ddTitle;
  @BindView(R.id.dd_ddl)
  EditText ddDdl;
  @BindView(R.id.dd_peopleNum)
  EditText ddPeopleNum;
  @BindView(R.id.dd_value)
  EditText ddValue;
  @BindView(R.id.dd_allocation)
  RadioGroup ddAllocation;
  @BindView(R.id.dd_average)
  RadioButton ddAverage;
  @BindView(R.id.dd_random)
  RadioButton ddRandom;
  @BindView(R.id.image_1)
  ImageView image1;
  @BindView(R.id.image_num)
  TextView imageNum;

  private Unbinder unbinder;

  private User user;
  private Assignment assignment;
  private String userId;
  private Calendar calendar;

  private String selectedReceiptImagePath = "";
  private final int SELECT_RECEIPT_IMAGE = 10002;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_publish_dd);
    unbinder = ButterKnife.bind(this);


    SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
      .setImageLoader(new GlideImageLoader())
      .setToolbaseColor(getColor(R.color.colorPrimary))
      .build());

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

    assignment = new Assignment();

  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }


  @Override
  protected void onStart() {
    super.onStart();
    if (!userId.equals("")) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            String res = HttpClient.getInstance().get("/user/" + userId + "/Privary");
            if (res != null && !res.equals("")) {
              User tempUser = gson.fromJson(res, User.class);
              if (tempUser != null) {
                user = tempUser;
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }).start();
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


  @OnClick(R.id.image_pick)
  public void selectReceiptImage() {
    int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    if (checkPermission == PackageManager.PERMISSION_GRANTED){
      pickImage(SELECT_RECEIPT_IMAGE);
    } else {
      ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_RECEIPT_IMAGE);
    }
  }

  private void pickImage(int requestCode) {
    SImagePicker
      .from(PublishDDActivity.this)
      .rowCount(3)
      .pickMode(SImagePicker.MODE_IMAGE)
      .forResult(requestCode);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case SELECT_RECEIPT_IMAGE: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          pickImage(requestCode);
        } else {
          Toast.makeText(this, "缺少读取外部存储权限导致不能选择图片", Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK && requestCode == SELECT_RECEIPT_IMAGE) {
      final ArrayList<String> pathList =
        data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
        data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
      if (pathList.size() == 1) {
        if (requestCode == SELECT_RECEIPT_IMAGE) {
          Glide.with(this).load(pathList.get(0)).into(image1);
          image1.setVisibility(View.VISIBLE);
          removeImage.setVisibility(View.VISIBLE);
          imageNum.setText("（1/2）");
          this.selectedReceiptImagePath = pathList.get(0);
        }
      }
    }
  }

  @OnClick(R.id.remove_image)
  public void removeImage() {
    selectedReceiptImagePath = "";
    image1.setVisibility(View.GONE);
    removeImage.setVisibility(View.GONE);
    imageNum.setText("（0/2）");
  }


  @OnFocusChange(R.id.dd_ddl)
  public void setEnrollment(boolean focused) {
    if (focused) {
      //实例化日期类
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      calendar = Calendar.getInstance();
      calendar.setTimeInMillis(System.currentTimeMillis());
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH);
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      int minute = calendar.get(Calendar.MINUTE);

      //实例化时间选择器
      //参数1：上下文对象
      //参数2：监听事件
      //参数3：初始化小时
      //参数4：初始化分钟
      //参数5：是否24小时制
      new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
          calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
          calendar.set(Calendar.MINUTE, minute);
          ddDdl.setText(dateFormat.format(calendar.getTime()));
        }
      }, hour, minute,true).show();

      //实例化日期选择器悬浮窗
      //参数1：上下文对象
      //参数2：监听事件
      //参数3：初始化年份
      //参数4：初始化月份
      //参数5：初始化日期
      new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        //实现监听方法
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
          calendar.set(Calendar.YEAR, year);
          calendar.set(Calendar.MONTH, month);
          calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
          ddDdl.setText(dateFormat.format(calendar.getTime()));
        }
      },year, month, day).show();
    }
  }

  @OnClick(R.id.dd_confirm)
  public void confirmChanges() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        assignment.setTaskType(1);
        assignment.setStatusCode(0);
        assignment.setBeginTime(String.valueOf(System.currentTimeMillis()));
        assignment.setValue(ddValue.getText().toString());
        if (ddAverage.isChecked()) {
          assignment.setAllocation(1);
        } else if (ddRandom.isChecked()) {
          assignment.setAllocation(0);
        }
        assignment.setTitle(ddTitle.getText().toString());
        assignment.setDesc(receiptDesc.getText().toString());
        assignment.setDdl(String.valueOf(calendar.getTimeInMillis()));
        assignment.setTotalNum(Integer.parseInt(ddPeopleNum.getText().toString()));

        if (!selectedReceiptImagePath.equals("")) {
          // 上传截图以获取对应的url
          String res = HttpClient.getInstance().upload(selectedReceiptImagePath);
          if (res != null) {
            user.setStudentCardURL(res.replace("\n", ""));
          }
        }
        try {
          HttpClient.getInstance().post("/task/Publish/" + user.getUserid(), gson.toJson(assignment), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String res = response.body().string();
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PublishDDActivity.this, ConfirmFeedbackActivity.class);
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

  public class GlideImageLoader implements ImageLoader {
    @Override
    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
      Glide.with(getApplicationContext()).load(uri).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher).override(width, height).dontAnimate().into(imageView);
    }

    @Override
    public void bindImage(ImageView imageView, Uri uri) {
      Glide.with(getApplicationContext()).load(uri).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher).dontAnimate().into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
      ImageView imageView = new ImageView(context);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      return imageView;
    }

    @Override
    public ImageView createFakeImageView(Context context) {
      return new ImageView(context);
    }
  }
}
