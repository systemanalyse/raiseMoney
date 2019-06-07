package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.test_btn)
    MaterialButton test;

    @BindView(R.id.step_desc)
    TextView stepDesc;
    @BindView(R.id.register_title_text)
    TextView titleText;
    @BindView(R.id.step1_layout)
    LinearLayout step0Layout;
    @BindView(R.id.step2_layout)
    LinearLayout step1Layout;
    @BindView(R.id.step3_layout)
    LinearLayout step2Layout;

    // step1 表单元素
    @BindView(R.id.register_email_input)
    TextInputEditText emailInput;
    @BindView(R.id.register_password_input)
    TextInputEditText passwordInput;
    @BindView(R.id.register_confirm_password_input)
    TextInputEditText confirmInput;

    // step2 表单元素
    @BindView(R.id.student_card_image)
    ImageView studentCardImage;

    // step3 表单元素
    @BindView(R.id.register_signature)
    EditText signatureText;
    @BindView(R.id.register_phone)
    EditText phoneInput;
    @BindView(R.id.register_wechat)
    EditText wechatInput;
    @BindView(R.id.register_qq)
    EditText qqInput;
    @BindView(R.id.register_feedback)
    TextView registerFeedback;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    private int state = 0;
    private Unbinder unbinder;
    private String selectedStudentCardImagePath = "";
    private String selectedProfileImagePath = "";

    private static final String emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
    private static final int SELECT_IMAGE_REQUEST_CODE = 1001;
    private static final int REQUEST_PERMISSION_CODE = 1002;
    private static final int SELECT_PROFILE_IMAGE_CODE = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        setupStepView();
        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
          .setImageLoader(new GlideImageLoader())
          .setToolbaseColor(getColor(R.color.colorPrimary))
          .build());
    }

    private void setupStepView() {
        stepView.setStepsNumber(3);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.test_btn)
    public void click() {
      if(!validate()) return;
      if (stepView.getCurrentStep() != stepView.getStepCount() - 1) {
        changeEditForm(stepView.getCurrentStep() + 1);
        stepView.go(stepView.getCurrentStep() + 1, false);
      } else {
        // 发起网络请求，并根据返回码判断是否创建成功
        // 成功的话跳转到注册完成界面
        Toast.makeText(this, "完成注册", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
        startActivity(intent);
      }
    }

    @OnClick(R.id.student_card_image)
    public void selectStudentCardImage() {
      int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
      if (checkPermission == PackageManager.PERMISSION_GRANTED){
        pickImage(SELECT_IMAGE_REQUEST_CODE);
      } else {
        ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_IMAGE_REQUEST_CODE);
      }
    }

    @OnClick(R.id.profile_image)
    public void selectProfileImage() {
      int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
      if (checkPermission == PackageManager.PERMISSION_GRANTED){
        pickImage(SELECT_PROFILE_IMAGE_CODE);
      } else {
        ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_PROFILE_IMAGE_CODE);
      }
    }

    private void pickImage(int requestCode) {
      SImagePicker
        .from(RegisterActivity.this)
        .rowCount(3)
        .pickMode(SImagePicker.MODE_IMAGE)
        .forResult(requestCode);
    }

    private boolean validate() {
      switch (this.state) {
        case 0:
          return validateStep1();
        case 1:
          return validateStep2();
        case 2:
          return validateStep3();
      }
      return true;
    }

    private void changeEditForm(int idx) {
      switch (idx){
        case 1:
          this.state = 1;
          resetVisibility();
          step1Layout.setVisibility(View.VISIBLE);
          break;
        case 2:
          this.state = 2;
          resetVisibility();
          step2Layout.setVisibility(View.VISIBLE);
      }
    }

    private void resetVisibility() {
      step0Layout.setVisibility(View.GONE);
      step1Layout.setVisibility(View.GONE);
      step2Layout.setVisibility(View.GONE);
    }

    private boolean validateStep1() {
        if (emailInput.getText().toString().matches(emailReg) && !passwordInput.getText().toString().equals("") && passwordInput.getText().toString().equals(confirmInput.getText().toString())) {
          return true;
        }
        Snackbar.make(stepView, "邮箱格式错误或密码不匹配", Snackbar.LENGTH_SHORT).show();
        return  false;
    }

    private boolean validateStep2() {
        return true;
    }

    private boolean validateStep3() {
        registerFeedback.setVisibility(View.GONE);
        String[] array = new String[]{
            phoneInput.getText().toString(),
            wechatInput.getText().toString(),
            qqInput.getText().toString()
        };
        if (!array[0].equals("") && validateArrayNum(array) >= 2) {
          return true;
        }
//        Snackbar.make(stepView, "邮箱格式错误或密码不匹配", Snackbar.LENGTH_SHORT).show();
        registerFeedback.setText("请至少输入两种联系方式");
        registerFeedback.setVisibility(View.VISIBLE);
        return false;
    }

    private int validateArrayNum(String[] array) {
        int res = 0;
        for (int i = 0; i < array.length; i++){
          if(!array[i].equals("")) {
              res += 1;
          }
        }
        return res;
    }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK && (requestCode == SELECT_IMAGE_REQUEST_CODE || requestCode == SELECT_PROFILE_IMAGE_CODE)) {
      final ArrayList<String> pathList =
        data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
        data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
      if (pathList.size() == 1) {
        if (requestCode == SELECT_IMAGE_REQUEST_CODE) {
          Glide.with(this).load(pathList.get(0)).into(studentCardImage);
          this.selectedStudentCardImagePath = pathList.get(0);
          Toast.makeText(this, pathList.get(0), Toast.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_PROFILE_IMAGE_CODE) {
          Glide.with(this).load(pathList.get(0)).into(profileImage);
          this.selectedProfileImagePath = pathList.get(0);
          Toast.makeText(this, pathList.get(0), Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case SELECT_PROFILE_IMAGE_CODE:
      case SELECT_IMAGE_REQUEST_CODE: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          pickImage(requestCode);
        } else {
          Toast.makeText(this, "缺少读取外部存储权限导致不能选择图片", Toast.LENGTH_SHORT).show();
        }
      }
    }
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
