package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
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

public class CertificationActivity extends AppCompatActivity {

  @BindView(R.id.certification_toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.register_male_radio)
  RadioButton maleRadio;
  @BindView(R.id.register_female_radio)
  RadioButton femaleRadio;
  @BindView(R.id.student_card_image)
  ImageView studentCardImage;
  @BindView(R.id.register_username)
  EditText usernameEditText;
  @BindView(R.id.register_school)
  EditText schoolEdit;
  @BindView(R.id.register_enrollment)
  EditText enrollmentEdit;
  @BindView(R.id.register_dorm)
  EditText dormEdit;

  private Unbinder unbinder;
  private User user;
  private String userId;

  private String selectedStudentCardImagePath = "";
  private final int SELECT_STUDENT_CARD_IMAGE_CODE = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_certification);
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

    // 修改RadioButton drawableRight 的大小
    Drawable maleIcon = getDrawable(R.drawable.man);
    maleIcon.setBounds(0,0,54,54);
    maleRadio.setCompoundDrawables(null, null, maleIcon, null);

    Drawable femaleIcon = getDrawable(R.drawable.woman);
    femaleIcon.setBounds(0,0,54,54);
    femaleRadio.setCompoundDrawables(null, null, femaleIcon, null);

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

                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    usernameEditText.setText(user.getName());
                    if (user.getGender() == 1) {
                      maleRadio.setChecked(true);
                      femaleRadio.setChecked(false);
                    } else {
                      femaleRadio.setChecked(true);
                      maleRadio.setChecked(false);
                    }
                    schoolEdit.setText(user.getSchool());
                    enrollmentEdit.setText(user.getEnrollment());
                    dormEdit.setText(user.getDormitory());

                    if (user.getStudentCardURL() != null && !user.getStudentCardURL().equals("")) {
                      Glide.with(getApplicationContext()).load(HttpClient.getPictureBaseUrl + user.getStudentCardURL()).into(studentCardImage);
                    } else {
                      Glide.with(getApplicationContext()).load(R.drawable.add_student_card_image).into(studentCardImage);
                    }
                  }
                });
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


  @OnClick(R.id.student_card_image)
  public void selecStudentCardImage() {
    int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    if (checkPermission == PackageManager.PERMISSION_GRANTED){
      pickImage(SELECT_STUDENT_CARD_IMAGE_CODE);
    } else {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_STUDENT_CARD_IMAGE_CODE);
    }
  }

  private void pickImage(int requestCode) {
    SImagePicker
            .from(CertificationActivity.this)
            .rowCount(3)
            .pickMode(SImagePicker.MODE_IMAGE)
            .forResult(requestCode);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case SELECT_STUDENT_CARD_IMAGE_CODE: {
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
    if (resultCode == Activity.RESULT_OK && requestCode == SELECT_STUDENT_CARD_IMAGE_CODE) {
      final ArrayList<String> pathList =
              data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
              data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
      if (pathList.size() == 1) {
        if (requestCode == SELECT_STUDENT_CARD_IMAGE_CODE) {
          Glide.with(this).load(pathList.get(0)).into(studentCardImage);
          this.selectedStudentCardImagePath = pathList.get(0);
        }
      }
    }
  }


  @OnFocusChange(R.id.register_enrollment)
  public void setEnrollment(boolean focused) {
    if (focused) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
              CertificationActivity.this,
              new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                  enrollmentEdit.setText(Integer.toString(selectedYear));
                }
              }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

      builder.showYearOnly()
              .setYearRange(1990, 2020)
              .setActivatedYear(calendar.get(Calendar.YEAR))
              .setTitle("请选择入学年份")
              .build()
              .show();
    }
  }

  @OnClick(R.id.confirm_btn)
  public void confirmChanges() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        user.setName(usernameEditText.getText().toString());
        user.setSchool(schoolEdit.getText().toString());
        user.setEnrollment(enrollmentEdit.getText().toString());
        user.setDormitory(dormEdit.getText().toString());
        if (maleRadio.isChecked()) {
          user.setGender(1);
        } else {
          user.setGender(0);
        }


        if (!selectedStudentCardImagePath.equals("")) {
          // 上传用户头像以获取对应的uri
          String res = HttpClient.getInstance().upload(selectedStudentCardImagePath);
          if (res != null) {
            user.setStudentCardURL(res.replace("\n", ""));
          }
        }
        try {
          HttpClient.getInstance().put("/user/" + user.getUserid() + "/Privary", gson.toJson(user), new Callback() {
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
                    Toast.makeText(getApplicationContext(), "重新认证成功", Toast.LENGTH_SHORT).show();
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
