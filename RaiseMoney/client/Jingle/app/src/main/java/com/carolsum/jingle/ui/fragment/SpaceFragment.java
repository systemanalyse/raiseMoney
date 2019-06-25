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

import static android.content.Context.MODE_PRIVATE;
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
    private String userId = "";
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

      LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
      acceptRV.setLayoutManager(layoutManager);
      acceptAdapter = new SpaceAssignmentAdapter(acceptList);
      acceptRV.setAdapter(acceptAdapter);

      LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
      publishRV.setLayoutManager(layoutManager1);
      publishAdapter = new SpaceAssignmentAdapter(publishList);
      publishRV.setAdapter(publishAdapter);
    }

    private void fetchUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", MODE_PRIVATE);
                String userid = sharedPreferences.getString("userid","");
                if (!userid.equals("")) {
                    try {
                        String res = HttpClient.getInstance().get("/user/" + userid + "/Privary");

                        if (res.startsWith("{")) {
                            User user = gson.fromJson(res, User.class);
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
        if (userId == null || userId.equals("")) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = HttpClient.getInstance().get("/task/" + "Publish/" + userId);
                    if (res != null &&  !res.equals("")) {
                        // 对返回的json串进行解析
//                        String tempRes = "[{\"origin\":1,\"userid\":12,\"taskid\":1,\"taskStatus\":1,\"taskType\":1,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":10,\"acceptor\":[],\"finishor\":[]},{\"origin\":1,\"userid\":12,\"taskid\":2,\"taskStatus\":1,\"taskType\":0,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":1,\"acceptor\":[],\"finishor\":[]}]";

                        //Json的解析类对象
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(res).getAsJsonArray();
                        ArrayList<Assignment> assignments = new ArrayList<>();
                        for (JsonElement assignment : jsonArray) {
                          Assignment item = gson.fromJson(assignment, Assignment.class);
                          assignments.add(item);
                        }
                        publishList.clear();
                        for (Assignment assignment : assignments) {
                            // 如果这个发布的任务正在进行中
                            if (assignment.getTaskStatus() == 1) {
                                publishList.add(assignment);
                            }
                        }
                    }

                    String res2 = HttpClient.getInstance().get("/task/" + "Received/" + userId);
                    if (res2 != null &&  !res2.equals("")) {
                        // 对返回的json串进行解析
//                        String tempRes = "[{\"origin\":1,\"userid\":12,\"taskid\":1,\"taskStatus\":1,\"taskType\":1,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":10,\"acceptor\":[],\"finishor\":[]},{\"origin\":1,\"userid\":12,\"taskid\":2,\"taskStatus\":1,\"taskType\":0,\"statusCode\":0,\"beginTime\":\"100\",\"value\":\"10\",\"title\":\"test\",\"desc\":\"test\",\"startPosition\":\"test\",\"endPosition\":\"test\",\"ddl\":\"100\",\"finishNum\":0,\"totalNum\":1,\"acceptor\":[],\"finishor\":[]}]";

                        //Json的解析类对象
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(res2).getAsJsonArray();
                        ArrayList<Assignment> assignments = new ArrayList<>();
                        for (JsonElement assignment : jsonArray) {
                            Assignment item = gson.fromJson(assignment, Assignment.class);
                            assignments.add(item);
                        }

                        acceptList.clear();
                        for (Assignment assignment : assignments) {
                            // 如果这个接受的任务正在进行中
                            if (assignment.getTaskStatus() == 1) {
                                acceptList.add(assignment);
                            }
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            acceptAdapter.notifyDataSetChanged();
                            publishAdapter.notifyDataSetChanged();
                            setupListBoard();
                        }
                    });
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
        fetchRunningTasks();
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
            Glide.with(this).load(R.drawable.default_avatar).into(userAvatar);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share",MODE_PRIVATE);
        userId = sharedPreferences.getString("userid","");
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
      if (acceptList.size() == 0 && publishList.size() == 0) {
        // 显示占位图 隐藏列表
        placeholder.setVisibility(View.VISIBLE);
        missionListWrapper.setVisibility(View.GONE);
      } else {
        placeholder.setVisibility(View.GONE);
        missionListWrapper.setVisibility(View.VISIBLE);
        int totalNum = acceptList.size() + publishList.size();
        runningMissionTitle.setText("正在进行中的任务（" + Integer.toString(totalNum) + "）");
        if (acceptList.size() == 0 && publishList.size() != 0) {
          listTypeNumber.setText("共 1 项");
          showList(1, true);
        } else if (acceptList.size() != 0 && publishList.size() == 0) {
          listTypeNumber.setText("共 1 项");
          showList(0, true);
        } else {
          listTypeNumber.setText("共 2 项");
          showList(0, false);
        }
      }
    }

    // type: 0 显示接受列表, 1 显示发起列表
    private void showList(int type, boolean forceHideOther) {
      switch (type) {
        case 0:
          accpetListBtn.setBackgroundResource(R.drawable.textview_bg_active);
          accpetListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
          if (forceHideOther) {
            publishListBtn.setVisibility(View.GONE);
          } else {
            publishListBtn.setBackgroundResource(R.drawable.textview_bg_normal);
            publishListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_6));
          }

          acceptListLayout.setVisibility(View.VISIBLE);
          publishListLayout.setVisibility(View.GONE);
          break;
        case 1:
          if (forceHideOther) {
            accpetListBtn.setVisibility(View.GONE);
          } else {
            accpetListBtn.setBackgroundResource(R.drawable.textview_bg_normal);
            accpetListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_6));
          }

          publishListBtn.setBackgroundResource(R.drawable.textview_bg_active);
          publishListBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

          acceptListLayout.setVisibility(View.GONE);
          publishListLayout.setVisibility(View.VISIBLE);
          break;
      }
    }

    @OnClick(R.id.accept_list_btn)
    public void showAcceptList() {
      showList(0, false);
    }

    @OnClick(R.id.published_list_btn)
    public void showPublishedListBtn() {
      showList(1, false);
    }

}
