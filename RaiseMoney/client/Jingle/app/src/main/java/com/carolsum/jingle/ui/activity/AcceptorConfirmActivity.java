package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.Receipt;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.google.gson.JsonObject;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

public class AcceptorConfirmActivity extends AppCompatActivity {
  @BindView(R.id.appbarLayout)
  AppBarLayout appBarLayout;
  @BindView(R.id.detail_toolbar)
  Toolbar toolbar;

  @BindView(R.id.assignment_type)
  ImageView assignmentType;
  @BindView(R.id.assignment_title)
  TextView assignmentTitle;
  @BindView(R.id.user_avatar)
  ImageView userAvatar;
  @BindView(R.id.username)
  TextView username;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.receipt_desc)
  EditText receiptDesc;
  @BindView(R.id.image_pick)
  ImageView imagePick;
  @BindView(R.id.remove_image)
  ImageView removeImage;
  @BindView(R.id.image_1)
  ImageView image1;
  @BindView(R.id.image_num)
  TextView imageNum;
  @BindView(R.id.confirm)
  LinearLayout confirm;

  private Unbinder unbinder;

  String userId;
  Assignment assignment;
  Receipt receipt;


  private String selectedReceiptImagePath = "";
  private final int SELECT_RECEIPT_IMAGE = 10003;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_accpter_confirm);
    unbinder = ButterKnife.bind(this);

    SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
      .setImageLoader(new GlideImageLoader())
      .setToolbaseColor(getColor(R.color.colorPrimary))
      .build());

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

    SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
    userId = sharedPreferences.getString("userid","");

    assignment = (Assignment) getIntent().getSerializableExtra("assignment");
    receipt = new Receipt();

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
    if (assignment.getTaskType() == 0) {
      title.setText("通知已取");
      assignmentType.setImageResource(R.drawable.pao);

    } else {
      title.setText("通知已完成");
      assignmentType.setImageResource(R.drawable.dian);

    }

    assignmentTitle.setText(assignment.getTitle());
    if (assignment.getPublishorInfo().getAvatarURL() != null && !assignment.getPublishorInfo().getAvatarURL().equals("") || assignment.getPublishorInfo().getAvatarURL().equals("undefined")) {
      // 加载用户头像
      Glide.with(this).load(HttpClient.getPictureBaseUrl + assignment.getPublishorInfo().getAvatarURL()).into(userAvatar);
    } else {
      // 加载默认头像
      Glide.with(this).load(R.drawable.default_avatar).into(userAvatar);
    }
    username.setText(assignment.getPublishorInfo().getName());
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
      .from(AcceptorConfirmActivity.this)
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

  @OnClick(R.id.confirm)
  public void confirmChanges() {
    new Thread(new Runnable() {
      @Override
      public void run() {

        if (!selectedReceiptImagePath.equals("")) {
          // 上传截图以获取对应的url
          String res = HttpClient.getInstance().upload(selectedReceiptImagePath);
          ArrayList<String> photourl = new ArrayList<>();
          photourl.add(res.replace("\n", ""));
          receipt.setPhotourl(photourl);
        }
        receipt.setDesc(receiptDesc.getText().toString());

        try {
          if (assignment.getTaskType() == 0) {
            HttpClient.getInstance().put("/task/FinishPP/" + assignment.getTaskid() + "/" + userId, gson.toJson(receipt), new Callback() {
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
                      Log.i("data", "FinishPP: " + res);
                      assignment = gson.fromJson(res, Assignment.class);
                      /**
                       * api 返回没有 origin 字段
                       */
                      assignment.setOrigin(2);
                      Intent intent = new Intent(AcceptorConfirmActivity.this, ConfirmFeedbackActivity.class);
                      intent.putExtra("operation", "finishPP");
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
          } else {
            HttpClient.getInstance().put("/task/FinishDD/" + assignment.getTaskid() + "/" + userId, gson.toJson(receipt), new Callback() {
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
                      Log.i("data", "FinishDD: " + res);
                      assignment = gson.fromJson(res, Assignment.class);
                      assignment.setOrigin(2);

                      Intent intent = new Intent(AcceptorConfirmActivity.this, ConfirmFeedbackActivity.class);
                      intent.putExtra("operation", "finishDD");
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
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
