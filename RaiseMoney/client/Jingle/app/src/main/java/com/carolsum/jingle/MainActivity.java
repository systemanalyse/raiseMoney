package com.carolsum.jingle;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.carolsum.jingle.event.LoginEvent;
import com.carolsum.jingle.ui.activity.AcceptListActivity;
import com.carolsum.jingle.ui.activity.HomeActivity;
import com.carolsum.jingle.ui.activity.NotificationActivity;
import com.carolsum.jingle.ui.activity.RegisterActivity;
import com.carolsum.jingle.ui.activity.SponsorListActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username_input)
    TextInputEditText username;
    @BindView(R.id.pwd_input)
    TextInputEditText pwd;
    @BindView(R.id.login_btn)
    MaterialButton loginBtn;
    @BindView(R.id.clear_btn)
    MaterialButton clearBtn;
    @BindView(R.id.register_btn)
    MaterialButton registerBtn;

    @BindView(R.id.notification_btn)
    Button btn;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }


    @OnClick(R.id.login_btn)
    public void login() {
        if (username.getText().toString().equals("") || pwd.getText().toString().equals("")) {
            return;
        }
        Toast.makeText(MainActivity.this, "登录成功趴~", Toast.LENGTH_SHORT).show();
        // todo@lijiehong 登录后保存token到本地 下次进入直接验证token
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.register_btn)
    public void gotoRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.clear_btn)
    public void clearInput() {
        username.setText("");
        pwd.setText("");
        username.clearFocus();
        pwd.clearFocus();
    }

    @OnClick(R.id.notification_btn)
    public void gotoNotification() {
      Intent intent = new Intent(MainActivity.this, AcceptListActivity.class);
      startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
