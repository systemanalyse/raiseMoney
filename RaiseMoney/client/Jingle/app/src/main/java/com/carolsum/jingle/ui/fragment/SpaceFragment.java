package com.carolsum.jingle.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
    View placeholder;
    @BindView(R.id.space_running_mission)
    TextView runningMissionTitle;
    @BindView(R.id.space_accept_list_length)
    TextView acceptListLength;
    @BindView(R.id.space_publish_list_length)
    TextView publishListLength;

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
        Assignment assignment = new Assignment("求好心人帮拿快递！", 1, -5, "待接单", "17:40", "明6邮局", "至善园2号123", 12, 20);
        Assignment assignment1 = new Assignment("帮我评论点个赞？谢啦", 0, -10, "进度：20/35", "17:40", "明6邮局", "至善园2号123", 20, 35);
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

    @OnClick(R.id.space_username)
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
}
