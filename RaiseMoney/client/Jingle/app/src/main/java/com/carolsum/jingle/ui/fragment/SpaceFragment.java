package com.carolsum.jingle.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carolsum.jingle.MainActivity;
import com.carolsum.jingle.R;
import com.carolsum.jingle.event.UserEvent;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.User;
import com.carolsum.jingle.net.HttpClient;
import com.carolsum.jingle.ui.activity.AcceptListActivity;
import com.carolsum.jingle.ui.activity.NotificationActivity;
import com.carolsum.jingle.ui.activity.SettingsActivity;
import com.carolsum.jingle.ui.activity.SponsorListActivity;
import com.carolsum.jingle.ui.activity.wallet.WalletActivity;
import com.carolsum.jingle.ui.adapters.SpaceAssignmentAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.carolsum.jingle.net.HttpClient.gson;

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
    @BindView(R.id.space_user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.space_username)
    TextView username;
    @BindView(R.id.space_gender)
    ImageView genderImage;
    @BindView(R.id.space_address)
    TextView address;
    @BindView(R.id.space_signature)
    TextView signature;
    @BindView(R.id.space_accept_number)
    TextView acceptNum;
    @BindView(R.id.space_publish_number)
    TextView publishNum;
    @BindView(R.id.space_jin_number)
    TextView jinNum;

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
    private User currentUser;
    private List<Assignment> acceptList = new ArrayList<>();
    private List<Assignment> publishList = new ArrayList<>();

    private SpaceAssignmentAdapter acceptAdapter;
    private SpaceAssignmentAdapter publishAdapter;

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
      acceptAdapter = new SpaceAssignmentAdapter(acceptList);
      acceptRV.setAdapter(acceptAdapter);

      LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
      publishRV.setLayoutManager(layoutManager1);
      publishAdapter = new SpaceAssignmentAdapter(publishList);
      publishRV.setAdapter(publishAdapter);

      setupListBoard();
    }

    private void fetchUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
                String userid = sharedPreferences.getString("userid","");
                if (!userid.equals("")) {
                    try {
                        String res = HttpClient.getInstance().get("/user/" + userid + "/Privary");
                        User user = gson.fromJson(res, User.class);

                        if (user != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setUserInfo(user);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void fetchRunningTasks() {
        if (currentUser == null) return;
        publishList.clear();
        acceptList.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = HttpClient.getInstance().get("/user/" + currentUser.getUserid() + "/tasks/Running");
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                    ArrayList<Assignment> assignments = new ArrayList<>();
                    for (JsonElement element : jsonArray) {
                        Assignment assignment = gson.fromJson(element, Assignment.class);
                        assignments.add(assignment);
                    }

                    for (int i = 0 ; i < assignments.size(); i++) {
                        // 接受列表
                        if (assignments.get(i).origin == 1) {
//                            publishList.add()
//                            publishAdapter.notifyDataSetChanged();
                        } else {
//                            acceptList.add()
//                            acceptAdapter.notifyDataSetChanged();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchUserInfo();
//        fetchRunningTasks();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserEvent event) {
        currentUser = event.user;
        setUserInfo(currentUser);
    }

    public void setUserInfo(User user) {
        currentUser = user;
        if (currentUser != null &&  currentUser.avatarURL != null && !currentUser.avatarURL.equals("")) {
            // 加载用户头像
            Glide.with(this).load(HttpClient.getPictureBaseUrl + currentUser.avatarURL).into(userAvatar);
        } else {
            // 加载默认头像
            Glide.with(this).load(R.drawable.register_profile_image).into(userAvatar);
        }
        username.setText(currentUser.getName());
        address.setText(currentUser.getDormitory());
        if (currentUser.getGender() == 1) {
            genderImage.setImageResource(R.drawable.man);
        } else {
            genderImage.setImageResource(R.drawable.woman);
        }
        signature.setText(currentUser.getSignature());
        acceptNum.setText(Integer.toString(currentUser.getAcceptNum()));
        publishNum.setText(Integer.toString(currentUser.getPublishNum()));
        jinNum.setText(Integer.toString(currentUser.getJin()));
    }

    @Override
    protected View initView() {
        View view =  View.inflate(getActivity(), R.layout.fragment_space, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
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
      intent.putExtra("user", currentUser);
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
