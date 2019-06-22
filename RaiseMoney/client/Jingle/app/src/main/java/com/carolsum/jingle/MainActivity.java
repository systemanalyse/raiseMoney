package com.carolsum.jingle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.activity.GuideActivity;
import com.carolsum.jingle.ui.activity.HomeActivity;
import com.carolsum.jingle.ui.activity.RegisterActivity;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.carolsum.jingle.net.HttpClient.gson;

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

    private final String TAG = "MAIN_ACTIVITY";

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
              } else {
                  SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
                  String userId = sharedPreferences.getString("userid","");
                  if (!userId.equals("")) {
                    runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                        // 跳过登录进入主页
                        Toast.makeText(MainActivity.this, "欢迎回来~", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                      }
                    });
                  }
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
      try {
        JSONObject obj = new JSONObject();
        obj.put("email", username.getText().toString());
        obj.put("password", pwd.getText().toString());

        HttpClient.getInstance().post("/login", obj.toString(), new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
            e.printStackTrace();
          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {
            String res = response.body().string();
            Log.d(TAG, res);
            try {
              User user = gson.fromJson(res, User.class);
              // 将 userid 存到 sharePreferences 中
              SharedPreferences.Editor editor = getSharedPreferences("share", MODE_PRIVATE).edit();
              editor.putString("userid", Integer.toString(user.getUserId()));
              editor.commit();
              loginRes(res, user);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      } catch (JSONException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void loginRes(String res, User user) {
      // 切回主线程
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Log.d(TAG, user.toString());
          if (user != null) {
            Toast.makeText(MainActivity.this, "登录成功趴~", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
          }
        }
      });
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
