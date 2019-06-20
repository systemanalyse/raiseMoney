package com.carolsum.jingle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.carolsum.jingle.ui.activity.GuideActivity;
import com.carolsum.jingle.ui.activity.HomeActivity;
import com.carolsum.jingle.ui.activity.RegisterActivity;

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
    TextView clearBtn;
    @BindView(R.id.register_btn)
    MaterialButton registerBtn;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                  .getDefaultSharedPreferences(getBaseContext());
                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {
                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, GuideActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });
                  //  Make a new preferences editor
                  SharedPreferences.Editor e = getPrefs.edit();
                  //  Edit preference to make it false because we don't want this to run again
                  e.putBoolean("firstStart", false);
                  //  Apply changes
                  e.apply();
              }
            }
        });
        // Start the thread
        t.start();
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

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
