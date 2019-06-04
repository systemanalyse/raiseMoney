package com.carolsum.jingle;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.carolsum.jingle.ui.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText pwd;
    private MaterialButton loginBtn;
    private MaterialButton clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindEvent();
    }

    private void init() {
        username = findViewById(R.id.username_input);
        pwd = findViewById(R.id.pwd_input);
        loginBtn = findViewById(R.id.login_btn);
        clearBtn = findViewById(R.id.clear_btn);
    }

    private void bindEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("") || pwd.getText().toString().equals("")) {
                    return;
                }
                Toast.makeText(MainActivity.this, "登录成功趴~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                pwd.setText("");
                username.clearFocus();
                pwd.clearFocus();
            }
        });
    }
}
