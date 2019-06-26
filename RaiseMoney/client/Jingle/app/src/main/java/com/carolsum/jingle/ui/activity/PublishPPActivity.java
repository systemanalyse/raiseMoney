package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
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

public class PublishPPActivity extends AppCompatActivity {

  @BindView(R.id.pp_toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.pp_screenshot)
  ImageView ppScreenshot;
  @BindView(R.id.pp_title)
  EditText ppTitle;
  @BindView(R.id.pp_position)
  EditText ppPosition;
  @BindView(R.id.pp_ddl)
  EditText ppDdl;
  @BindView(R.id.pp_value)
  EditText ppValue;

  private Unbinder unbinder;
  private User user;
  private Assignment assignment;
  private String userId;
  private Calendar calendar;

  private String selectedScreenshotImagePath = "";
  private final int SELECT_PP_SCREENSHOT = 10001;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_publish_pp);
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


  @OnClick(R.id.pp_screenshot)
  public void selectScreenshotImage() {
    int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    if (checkPermission == PackageManager.PERMISSION_GRANTED){
      pickImage(SELECT_PP_SCREENSHOT);
    } else {
      ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_PP_SCREENSHOT);
    }
  }

  private void pickImage(int requestCode) {
    SImagePicker
      .from(PublishPPActivity.this)
      .rowCount(3)
      .pickMode(SImagePicker.MODE_IMAGE)
      .forResult(requestCode);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case SELECT_PP_SCREENSHOT: {
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
    if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PP_SCREENSHOT) {
      final ArrayList<String> pathList =
        data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
        data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
      if (pathList.size() == 1) {
        if (requestCode == SELECT_PP_SCREENSHOT) {
          Glide.with(this).load(pathList.get(0)).into(ppScreenshot);
          this.selectedScreenshotImagePath = pathList.get(0);
        }
      }
    }
  }


  @OnFocusChange(R.id.pp_ddl)
  public void setEnrollment(boolean focused) {
    if (focused) {
      calendar = Calendar.getInstance();
      calendar.setTimeInMillis(System.currentTimeMillis());
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      int minute = calendar.get(Calendar.MINUTE);
      new TimePickerDialog(PublishPPActivity.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
          calendar.setTimeInMillis(System.currentTimeMillis());
          calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
          calendar.set(Calendar.MINUTE, minute);
          ppDdl.setText(hourOfDay + ":" + minute);
        }
      }, hour, minute, true).show();
    }
  }

  @OnClick(R.id.pp_confirm)
  public void confirmChanges() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        assignment.setTaskType(0);
        assignment.setStatusCode(0);
        assignment.setBeginTime(String.valueOf(System.currentTimeMillis()));
        assignment.setValue(ppValue.getText().toString());
        assignment.setTitle(ppTitle.getText().toString());
        assignment.setStartPosition(user.getDormitory());
        assignment.setEndPosition(ppPosition.getText().toString());
        assignment.setDdl(String.valueOf(calendar.getTimeInMillis()));
        assignment.setTotalNum(1);

        if (!selectedScreenshotImagePath.equals("")) {
          // 上传快递截图以获取对应的url
          String res = HttpClient.getInstance().upload(selectedScreenshotImagePath);
          if (res != null) {
            user.setStudentCardURL(res.replace("\n", ""));
          }
        }
        try {
          HttpClient.getInstance().post("/task/Publish/" + userId, gson.toJson(assignment), new Callback() {
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
                    Intent intent = new Intent(PublishPPActivity.this, ConfirmFeedbackActivity.class);
                    intent.putExtra("operation", "publishPP");
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
