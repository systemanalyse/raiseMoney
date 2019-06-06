package com.carolsum.jingle.ui.activity;

import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    private Unbinder unbinder;

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
      if (stepView.getCurrentStep() != stepView.getStepCount() - 1) {
        stepView.go(stepView.getCurrentStep() + 1, false);
      }
    }
}
