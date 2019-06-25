package com.carolsum.jingle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.ui.activity.AssignmentDetailActivity;
import com.carolsum.jingle.ui.adapters.HomeAssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PPListFragment extends BaseFragment {
  private View fragmentView;
  @BindView(R.id.rv_pp_nearby)
  RecyclerView ppNearbyRV;
  @BindView(R.id.rv_pp_more)
  RecyclerView ppMoreRV;

  private Unbinder unbinder;

  final int REQUEST_CODE = 0;

  private List<Assignment> nearbyList = new ArrayList<>();
  private List<Assignment> moreList = new ArrayList<>();

  private HomeAssignmentAdapter nearbyAssignmentAdapter;
  private HomeAssignmentAdapter moreAssignmentAdapter;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    ppNearbyRV.setLayoutManager(layoutManager);
    nearbyAssignmentAdapter = new HomeAssignmentAdapter(nearbyList);
    ppNearbyRV.setAdapter(nearbyAssignmentAdapter);

    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
    ppMoreRV.setLayoutManager(layoutManager1);
    moreAssignmentAdapter = new HomeAssignmentAdapter(moreList);
    ppMoreRV.setAdapter(moreAssignmentAdapter);

    nearbyAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable("AssignmentDetail", nearbyAssignmentAdapter.getItem(position));
        Intent intent = new Intent(getContext(), AssignmentDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

    moreAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable("AssignmentDetail", nearbyAssignmentAdapter.getItem(position));
        Intent intent = new Intent(getContext(), AssignmentDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

  }

  @Override
  protected void initEvent() {

  }

  @Override
  protected void initData() {
    nearbyList.clear();
    moreList.clear();
    for(int i = 0; i < 3; i++) {
//      Assignment assignment = new Assignment("求好心人帮拿快递！", 0, 5, 1, "17:40", "明6邮局", "至善园2号123", 12, 20);
//      Assignment assignment1 = new Assignment("求好心人帮拿外卖！", 0, 2, 1, "17:40", "明6邮局", "至善园2号123", 20, 35);
      Assignment assignment = new Assignment(1, 1, 5, 0, 1,1,"17:40", "2", "求好心人帮拿快递！？谢啦", "aaaaaa", new ArrayList<String>(), new ArrayList<User>(),  "明6邮局", "至善园2号123", "123", 12, 20);
      Assignment assignment1 = new Assignment(2, 1, 5, 1, 0,2,"17:40", "5", "求好心人帮拿外卖", "aaaaaa", new ArrayList<String>(), new ArrayList<User>(),  "明6邮局", "至善园2号123", "123", 20, 30);
      nearbyList.add(assignment);
      moreList.add(assignment1);
    }
  }



  @Override
  protected View initView() {
    fragmentView =  View.inflate(getActivity(), R.layout.fragment_pp_list, null);
    unbinder = ButterKnife.bind(this, fragmentView);

    return fragmentView;
  }

  @Override
  public void onDestroyView() {
    unbinder.unbind();
    super.onDestroyView();
  }

}
