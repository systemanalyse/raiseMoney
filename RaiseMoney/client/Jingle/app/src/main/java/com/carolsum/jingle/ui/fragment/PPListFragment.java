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
    nearbyAssignmentAdapter = new HomeAssignmentAdapter(getContext(), nearbyList);
    ppNearbyRV.setAdapter(nearbyAssignmentAdapter);

    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
    ppMoreRV.setLayoutManager(layoutManager1);
    moreAssignmentAdapter = new HomeAssignmentAdapter(getContext(), moreList);
    ppMoreRV.setAdapter(moreAssignmentAdapter);

    nearbyAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AssignmentDetailActivity.class);
        intent.putExtra("assignment", nearbyList.get(position));
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

    moreAssignmentAdapter.setOnItemClickListener(new HomeAssignmentAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AssignmentDetailActivity.class);
        intent.putExtra("assignment", moreList.get(position));
        startActivity(intent);
      }
    });

  }

  @Override
  public void onResume() {
    super.onResume();
    fetchPPList();
  }

  @Override
  protected void initEvent() {

  }

  @Override
  protected void initData() {
    nearbyList.clear();
    moreList.clear();
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

  private void fetchPPList() {
    nearbyList.clear();
    moreList.clear();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String res;
          SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share",MODE_PRIVATE);
          String userId = sharedPreferences.getString("userid","");

          res = HttpClient.getInstance().get("/user/" + userId + "/Privary");
          User user = gson.fromJson(res, User.class);

          res = HttpClient.getInstance().get("/task/PP");
          List<Assignment> assignmentList = gson.fromJson(res, new TypeToken<List<Assignment>>(){}.getType());
          Log.i("data", "PP: " + res);

          for (Assignment assignment : assignmentList) {
            if (assignment.getTaskType() == 0) {
              if (assignment.getPublishorInfo().getDormitory().equals(user.getDormitory())) {
                nearbyList.add(assignment);
              } else {
                moreList.add(assignment);
              }
            }
          }

          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              nearbyAssignmentAdapter.notifyDataSetChanged();
              moreAssignmentAdapter.notifyDataSetChanged();
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
