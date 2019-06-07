package com.carolsum.jingle.ui.activity;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carolsum.jingle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterSuccessActivity extends AppCompatActivity {

  @BindView(R.id.jump_to_home_btn)
  MaterialButton button;
  Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_success);
    unbinder = ButterKnife.bind(this);
  }

  @OnClick(R.id.jump_to_home_btn)
  public void jumpToHome(){
    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }
}
