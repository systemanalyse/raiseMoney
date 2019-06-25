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

public class DDListFragment extends BaseFragment {
  private View fragmentView;
  @BindView(R.id.rv_dd)
  RecyclerView ddRV;

  private Unbinder unbinder;

  private List<Assignment> ddList = new ArrayList<>();

  private HomeAssignmentAdapter ddAssignmentAdapter;

  final int REQUEST_CODE = 0;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    ddRV.setLayoutManager(layoutManager);
    ddAssignmentAdapter = new HomeAssignmentAdapter(ddList);
    ddRV.setAdapter(ddAssignmentAdapter);

    ddAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
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
      ddList.clear();
      for(int i = 0; i < 3; i++) {
        Assignment assignment = new Assignment(1, 1, 5, 1, 1,2,"17:40", "10", "帮我评论点个赞？谢啦", "aaaaaa", new ArrayList<String>(), new ArrayList<User>(),  "明6邮局", "至善园2号123", "123", 20, 30);
        ddList.add(assignment);
      }
    }



    @Override
    protected View initView() {
      fragmentView =  View.inflate(getActivity(), R.layout.fragment_dd_list, null);
      unbinder = ButterKnife.bind(this, fragmentView);
      return fragmentView;
    }

    @Override
    public void onDestroyView() {
      unbinder.unbind();
      super.onDestroyView();
    }

  }
