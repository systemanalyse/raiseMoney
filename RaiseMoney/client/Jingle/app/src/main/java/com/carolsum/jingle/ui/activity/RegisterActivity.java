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
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.MainActivity;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.shuhart.stepview.StepView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.test_btn)
    MaterialButton test;
    @BindView(R.id.step_image)
    ImageView stepImage;
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
    @BindView(R.id.register_enrollment)
    EditText enrollmentInput;
    @BindView(R.id.register_radio_group)
    RadioGroup genderRadioGroup;
    @BindView(R.id.register_username)
    EditText registerNameInput;
    @BindView(R.id.register_school)
    EditText registerSchoolInput;
    @BindView(R.id.register_dorm)
    EditText registerDormInput;

    // step3 表单元素
    @BindView(R.id.profile_username)
    TextView profileUsername;
    @BindView(R.id.profile_gender)
    ImageView profileGender;
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
    @BindView(R.id.profile_image_template)
    ImageView profileImageTemplate;
    @BindView(R.id.profile_image_wrapper)
    RelativeLayout profileImageWrapper;
    @BindView(R.id.jump_register_btn)
    MaterialButton jumpRegisterBtn;

    private int state = 0;
    private int totalState = 3;
    private Unbinder unbinder;
    private String selectedStudentCardImagePath = "";
    private String selectedProfileImagePath = "";
    private String registerGender = "";

    private static final String emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
    private static final int SELECT_IMAGE_REQUEST_CODE = 1001;
    private static final int REQUEST_PERMISSION_CODE = 1002;
    private static final int SELECT_PROFILE_IMAGE_CODE = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
          .setImageLoader(new GlideImageLoader())
          .setToolbaseColor(getColor(R.color.colorPrimary))
          .build());
    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.test_btn)
    public void click() {
      if(!validate()) return;
      if (this.state != this.totalState - 1) {
        changeEditForm(this.state + 1);
      } else {
        registerUser();
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

    @OnClick(R.id.profile_image_wrapper)
    public void selectProfileImage() {
      int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
      if (checkPermission == PackageManager.PERMISSION_GRANTED){
        pickImage(SELECT_PROFILE_IMAGE_CODE);
      } else {
        ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_PROFILE_IMAGE_CODE);
      }
    }

    @OnFocusChange(R.id.register_enrollment)
    public void setEnrollment(boolean focused) {
        if (focused) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
            RegisterActivity.this,
            new MonthPickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(int selectedMonth, int selectedYear) {
                enrollmentInput.setText(Integer.toString(selectedYear));
              }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

          builder
            .showYearOnly()
            .setYearRange(1990, 2020)
            .setActivatedYear(calendar.get(Calendar.YEAR))
            .setTitle("请选择入学年份")
            .build()
            .show();
        }
    }

    @OnClick({R.id.register_male_radio, R.id.register_female_radio})
    public void onRadioButtonClicked(RadioButton radioButton) {
      boolean checked = radioButton.isChecked();
      if (checked) {
        Toast.makeText(getApplicationContext(), radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
        this.registerGender = radioButton.getText().toString();
      }
    }

    private void registerUser() {
        // 发起网络请求，并根据返回码判断是否创建成功
        // 成功的话跳转到注册完成界面

        final JSONObject object = new JSONObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    object.put("email", emailInput.getText().toString());
                    object.put("password", passwordInput.getText().toString());

                    object.put("name", registerNameInput.getText().toString());
                    object.put("gender", registerGender.equals("男生") ? 1 : 0);
                    object.put("school", registerSchoolInput.getText().toString());
                    object.put("enrollment", Integer.parseInt(enrollmentInput.getText().toString()));
                    object.put("dormitory", registerDormInput.getText().toString());

                    // 如果点击的是以后再说，那么此时应该不需要下面字段
                    object.put("signature", signatureText.getText().toString());
                    object.put("phone", phoneInput.getText().toString());
                    object.put("wechat", wechatInput.getText().toString());
                    object.put("qq", qqInput.getText().toString());

                    if (!selectedStudentCardImagePath.equals("")) {
                        // 上传用户头像以获取对应的uri
                        String res = HttpClient.getInstance().upload(selectedStudentCardImagePath);
                        if (res != null) {
                            object.put("studentCardURL", res.replaceAll("\n", ""));
                        }
                    }

                    if (!selectedProfileImagePath.equals("")) {
                        // 上传用户头像以获取对应的uri
                        String res = HttpClient.getInstance().upload(selectedProfileImagePath);
                        if (res != null) {
                            object.put("avatarURL", res.replaceAll("\n", ""));
                        }
                    }
                    postRegisterInfo(object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void postRegisterInfo(String json) {
        try {
            HttpClient.getInstance().post("/regist", json, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    try {
                        User user = gson.fromJson(res, User.class);
                        // 将 userid 存到 sharePreferences 中
                        SharedPreferences.Editor editor = getSharedPreferences("share", MODE_PRIVATE).edit();
                        editor.putString("userid", Integer.toString(user.getUserid()));
                        editor.commit();
                        loginRes(res, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loginRes(String res, User user) {
        // 切回主线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    // 将 user 传递给 RegisterSuccessActivity
                    Toast.makeText(RegisterActivity.this, "完成注册", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            }
        });
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
          titleText.setText("校园认证");
          stepDesc.setText("为确保你和其他同学的安全只有通过校园认证才能正常使用和查看这款App");
          stepImage.setImageResource(R.drawable.step_2);

          // 修改RadioButton drawableRight 的大小
          RadioButton maleButton = findViewById(R.id.register_male_radio);
          Drawable maleIcon = getDrawable(R.drawable.man);
          maleIcon.setBounds(0,0,54,54);
          maleButton.setCompoundDrawables(null, null, maleIcon, null);

          RadioButton femaleButton = findViewById(R.id.register_female_radio);
          Drawable femaleIcon = getDrawable(R.drawable.woman);
          femaleIcon.setBounds(0,0,54,54);
          femaleButton.setCompoundDrawables(null, null, femaleIcon, null);

          step1Layout.setVisibility(View.VISIBLE);
          break;
        case 2:
          this.state = 2;
          resetVisibility();
          titleText.setText("填写名片");
          stepDesc.setText("让其他同学更了解你。你也可以先跳过此部分，可在“我的-设置”中继续完善更改。");
          stepImage.setImageResource(R.drawable.step_3);
          if (this.registerGender.equals("男生")) {
            profileGender.setImageResource(R.drawable.man);
          } else {
            profileGender.setImageResource(R.drawable.woman);
          }
          profileUsername.setText(registerNameInput.getText().toString());
          jumpRegisterBtn.setVisibility(View.VISIBLE);
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
        Snackbar.make(test, "邮箱格式错误或密码不匹配", Snackbar.LENGTH_SHORT).show();
        return  false;
    }

    private boolean validateStep2() {
        if (registerNameInput.getText().toString().equals("")) {
          Snackbar.make(test, "请输入学生姓名", Snackbar.LENGTH_SHORT).show();
          return  false;
        }
        if (registerSchoolInput.getText().toString().equals("")) {
            Snackbar.make(test, "请输入学校名称", Snackbar.LENGTH_SHORT).show();
            return  false;
        }
        if (enrollmentInput.getText().toString().equals("")) {
            Snackbar.make(test, "请选择入学年份", Snackbar.LENGTH_SHORT).show();
            return  false;
        }
        if (registerDormInput.getText().toString().equals("")) {
          Snackbar.make(test, "请输入宿舍地址", Snackbar.LENGTH_SHORT).show();
          return  false;
        }
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
          profileImageTemplate.setVisibility(View.GONE);
          profileImage.setVisibility(View.VISIBLE);
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
