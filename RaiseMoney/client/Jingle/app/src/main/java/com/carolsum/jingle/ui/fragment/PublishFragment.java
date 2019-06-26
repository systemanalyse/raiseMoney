package com.carolsum.jingle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.ui.activity.AcceptorConfirmActivity;
import com.carolsum.jingle.ui.activity.AssignmentDetailActivity;
import com.carolsum.jingle.ui.activity.PublishDDActivity;
import com.carolsum.jingle.ui.activity.PublishPPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PublishFragment extends BaseFragment {
  private View fragmentView;
  private Unbinder unbinder;

  @BindView(R.id.ll_publish_pp)
  LinearLayout llPublishPP;
  @BindView(R.id.ll_publish_dd)
  LinearLayout llPublishDD;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }

  @Override
  protected void initEvent() {

  }

  @Override
  protected void initData() {

  }

  @Override
  protected View initView() {
    fragmentView =  View.inflate(getActivity(), R.layout.fragment_publish, null);
    unbinder = ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override
  public void onDestroyView() {
    unbinder.unbind();
    super.onDestroyView();
  }


  @OnClick(R.id.ll_publish_pp)
  public void publishPP() {
    Intent intent = new Intent(getContext(), PublishPPActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.ll_publish_dd)
  public void publishDD() {
    Intent intent = new Intent(getContext(), PublishDDActivity.class);
    startActivity(intent);
  }
}
