package com.carolsum.jingle.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carolsum.jingle.MainActivity;
import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.ui.activity.AcceptListActivity;
import com.carolsum.jingle.ui.activity.NotificationActivity;
import com.carolsum.jingle.ui.activity.SettingsActivity;
import com.carolsum.jingle.ui.activity.SponsorListActivity;
import com.carolsum.jingle.ui.activity.wallet.WalletActivity;
import com.carolsum.jingle.ui.adapters.SpaceAssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceFragment extends BaseFragment {

    @BindView(R.id.space_accept_list)
    RecyclerView acceptRV;
    @BindView(R.id.space_publish_list)
    RecyclerView publishRV;
    @BindView(R.id.no_running_mission_image)
    LinearLayout placeholder;
    @BindView(R.id.mission_list_wrapper)
    LinearLayout missionListWrapper;

    // 进行中的任务列表相关
    @BindView(R.id.space_running_mission)
    TextView runningMissionTitle;
    @BindView(R.id.space_accept_list_layout)
    LinearLayout acceptListLayout;
    @BindView(R.id.space_publish_list_layout)
    LinearLayout publishListLayout;
    @BindView(R.id.accept_list_btn)
    TextView accpetListBtn;
    @BindView(R.id.published_list_btn)
    TextView publishListBtn;
    @BindView(R.id.list_type_number)
    TextView listTypeNumber;

    private Unbinder unbinder;
    private List<Assignment> acceptList = new ArrayList<>();
    private List<Assignment> publishList = new ArrayList<>();

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
      acceptList.clear();
      publishList.clear();
      for(int i = 0; i < 3; i++) {
        Assignment assignment = new Assignment("帮我评论点个赞？谢啦", 1, -5, 1, "17:40", "明6邮局", "至善园2号123", 12, 20);
        Assignment assignment1 = new Assignment("求好心人帮拿快递！", 0, -10, 0, "17:40", "明6邮局", "至善园2号123", 20, 35);
        acceptList.add(assignment);
        publishList.add(assignment1);

//        publishList.add(assignment);
//        publishList.add(assignment1);
      }

      LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
      acceptRV.setLayoutManager(layoutManager);
      SpaceAssignmentAdapter adapter = new SpaceAssignmentAdapter(acceptList);
      acceptRV.setAdapter(adapter);

      LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
      publishRV.setLayoutManager(layoutManager1);
      SpaceAssignmentAdapter adapter1 = new SpaceAssignmentAdapter(publishList);
      publishRV.setAdapter(adapter1);

      setupListBoard();
    }

    @Override
    protected View initView() {
        View view =  View.inflate(getActivity(), R.layout.fragment_space, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.space_notification_btn)
    public void gotoNotification() {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.space_setting_btn)
    public void gotoSettings() {
      Intent intent = new Intent(getContext(), SettingsActivity.class);
      startActivity(intent);
    }

    @OnClick(R.id.space_accept_number)
    public void gotoAcceptList() {
      Intent intent = new Intent(getContext(), AcceptListActivity.class);
      startActivity(intent);
    }

    @OnClick(R.id.space_publish_number)
    public void gotoPublishList() {
      Intent intent = new Intent(getContext(), SponsorListActivity.class);
      startActivity(intent);
    }

    @OnClick(R.id.space_jin_number)
    public void gotoWallet() {
      Intent intent = new Intent(getContext(), WalletActivity.class);
      startActivity(intent);
    }

    private void setupListBoard() {
      int totalNum = acceptList.size() + publishList.size();
      runningMissionTitle.setText("正在进行中的任务（" + Integer.toString(totalNum) + "）");
      if (totalNum == 0) {
        // 显示占位图 隐藏列表
        placeholder.setVisibility(View.VISIBLE);
        missionListWrapper.setVisibility(View.GONE);
      } else {
        placeholder.setVisibility(View.GONE);
        missionListWrapper.setVisibility(View.VISIBLE);

        if (acceptList.size() > 0) {
          showList(0);
        } else {
          showList(1);
        }
      }
    }

    // type: 0 显示接受列表, 1 显示发起列表
    private void showList(int type) {
      switch (type) {
        case 0:
          accpetListBtn.setBackgroundResource(R.drawable.textview_bg_active);
          accpetListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
//          this.getResources().getColor(R.color.colorAccent)
          publishListBtn.setBackgroundResource(R.drawable.textview_bg_normal);
          publishListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_6));

          acceptListLayout.setVisibility(View.VISIBLE);
          publishListLayout.setVisibility(View.GONE);
          break;
        case 1:
          accpetListBtn.setBackgroundResource(R.drawable.textview_bg_normal);
          accpetListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_6));
          publishListBtn.setBackgroundResource(R.drawable.textview_bg_active);
          publishListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

          acceptListLayout.setVisibility(View.GONE);
          publishListLayout.setVisibility(View.VISIBLE);
          break;
      }
    }

    @OnClick(R.id.accept_list_btn)
    public void showAcceptList() {
      showList(0);
    }

    @OnClick(R.id.published_list_btn)
    public void showPublishedListBtn() {
      showList(1);
    }

}
