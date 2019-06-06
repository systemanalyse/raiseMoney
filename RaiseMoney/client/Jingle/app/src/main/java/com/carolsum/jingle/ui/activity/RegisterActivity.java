package com.carolsum.jingle.ui.activity;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    private int state = 0;
    private Unbinder unbinder;

    private static final String emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        setupStepView();
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
        Toast.makeText(this, "完成注册", Toast.LENGTH_SHORT).show();
      }
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
        return  false;
    }

    private boolean validateStep2() {
        return true;
    }

    private boolean validateStep3() {
        return true;
    }
}
