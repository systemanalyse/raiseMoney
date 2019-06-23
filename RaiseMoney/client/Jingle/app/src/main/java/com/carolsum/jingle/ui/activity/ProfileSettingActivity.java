package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class ProfileSettingActivity extends AppCompatActivity {

    @BindView(R.id.profile_toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.register_signature)
    EditText signatureEditText;
    @BindView(R.id.register_phone)
    EditText phoneEditText;
    @BindView(R.id.register_wechat)
    EditText wechatEditText;
    @BindView(R.id.register_qq)
    EditText qqEditText;
    @BindView(R.id.profile_image)
    CircleImageView avatarImage;
    @BindView(R.id.profile_username)
    TextView userNameText;
    @BindView(R.id.profile_gender)
    ImageView genderImage;
    @BindView(R.id.modify_profile_btn)
    MaterialButton modifyBtn;


    private Unbinder unbinder;
    private String userId;
    private User user;

    private String selectedProfileImagePath = "";

    private final int SELECT_PROFILE_IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
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
                                        userNameText.setText(user.getName());
                                        if (user.getGender() == 1) {
                                            genderImage.setImageResource(R.drawable.man);
                                        } else {
                                            genderImage.setImageResource(R.drawable.woman);
                                        }
                                        signatureEditText.setText(user.getSignature());
                                        phoneEditText.setText(user.getPhone());
                                        wechatEditText.setText(user.getWechat());
                                        qqEditText.setText(user.getQq());
                                        if (user.getAvatarURL() != null && !user.getAvatarURL().equals("")) {
                                            Glide.with(getApplicationContext()).load(HttpClient.getPictureBaseUrl + user.avatarURL).into(avatarImage);
                                        } else {
                                            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(avatarImage);
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
                .from(ProfileSettingActivity.this)
                .rowCount(3)
                .pickMode(SImagePicker.MODE_IMAGE)
                .forResult(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PROFILE_IMAGE_CODE) {
            final ArrayList<String> pathList =
                    data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            final boolean original =
                    data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
            if (pathList.size() == 1) {
                if (requestCode == SELECT_PROFILE_IMAGE_CODE) {
                    Glide.with(this).load(pathList.get(0)).into(avatarImage);
                    this.selectedProfileImagePath = pathList.get(0);
                }
            }
        }
    }

    @OnClick(R.id.modify_profile_btn)
    public void submitChange() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                user.setSignature(signatureEditText.getText().toString());
                user.setPhone(phoneEditText.getText().toString());
                user.setWechat(wechatEditText.getText().toString());
                user.setQq(qqEditText.getText().toString());

                if (!selectedProfileImagePath.equals("")) {
                    // 上传用户头像以获取对应的uri
                    String res = HttpClient.getInstance().upload(selectedProfileImagePath);
                    if (res != null) {
                        user.setAvatarURL(res.replace("\n", ""));
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
                                        Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PROFILE_IMAGE_CODE: {
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
