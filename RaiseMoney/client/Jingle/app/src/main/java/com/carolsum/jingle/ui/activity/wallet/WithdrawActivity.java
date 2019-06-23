package com.carolsum.jingle.ui.activity.wallet;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WithdrawActivity extends AppCompatActivity {

    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.withdraw_toolbar)
    Toolbar toolbar;
    @BindView(R.id.withdraw_user_deposit)
    TextView depositText;
    @BindView(R.id.withdraw_number)
    TextInputEditText editText;

    private Unbinder unbinder;
    private User user;

    private int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        unbinder = ButterKnife.bind(this);

        user = (User) getIntent().getSerializableExtra("user");
        this.money = user.getJin();
        setTotalMoney();


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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = HttpClient.getInstance().get("/user/" + user.getUserid() + "/Wallet");
                    if (res != null && !res.equals("")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                money = Integer.parseInt(res);
                                setTotalMoney();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void setTotalMoney() {
        depositText.setText("剩余 " + money + " JIN币 = " + money / 10.0f + " RMB");
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

    @OnClick(R.id.withdraw_confirm_button)
    public void withdraw() {
        // 体现
        double remain = money - Double.parseDouble(editText.getText().toString()) * 10;
        if (remain >= 0) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        JSONObject object = new JSONObject();
                        object.put("Jin", remain);

                        HttpClient.getInstance().put("/user/" + user.getUserid() + "/Wallet", object.toString(), new Callback() {
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
                                            Toast.makeText(getApplicationContext(), "提现成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "提现失败", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.withdraw_all_btn)
    public void withdrawAll() {
        editText.setText(Integer.toString(money / 10));
    }
}
