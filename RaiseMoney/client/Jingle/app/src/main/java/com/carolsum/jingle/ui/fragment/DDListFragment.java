package com.carolsum.jingle.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.activity.AssignmentDetailActivity;
import com.carolsum.jingle.ui.adapters.HomeAssignmentAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.carolsum.jingle.net.HttpClient.gson;

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
    ddAssignmentAdapter = new HomeAssignmentAdapter(getContext(), ddList);
    ddRV.setAdapter(ddAssignmentAdapter);

    ddAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), AssignmentDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

    fetchDDList();
  }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
      ddList.clear();
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

  private void fetchDDList() {
    ddList.clear();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String res;
          SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share",MODE_PRIVATE);
          String userId = sharedPreferences.getString("userid","");

          res = HttpClient.getInstance().get("/user/" + userId + "/Privary");
          User user = gson.fromJson(res, User.class);

          res = HttpClient.getInstance().get("/task/DD");
          List<Assignment> assignmentList = gson.fromJson(res, new TypeToken<List<Assignment>>(){}.getType());

          for (Assignment assignment : assignmentList) {
            ddList.add(assignment);
          }

          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ddAssignmentAdapter.notifyDataSetChanged();
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  }
