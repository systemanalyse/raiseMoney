package com.carolsum.jingle.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.carolsum.jingle.R;
import com.carolsum.jingle.model.Assignment;
import com.carolsum.jingle.model.Receipt;
import com.carolsum.jingle.ui.adapters.AcceptorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReceiptActivity extends AppCompatActivity {
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_receipt_unconfirm)
    RecyclerView rvReceiptUnconfirm;
    @BindView(R.id.rv_receipt_finish)
    RecyclerView rvReceiptFinish;

    private Unbinder unbinder;

    private List<Receipt> unconfirmList = new ArrayList<>();
    private List<Receipt> finishList = new ArrayList<>();

    private AcceptorAdapter unconfirmAdapter;
    private AcceptorAdapter finishAdapter;

    final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        unbinder = ButterKnife.bind(this);


        // 获取状态栏高度 更新toolbar的marginTop
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) appBarLayout.getLayoutParams();
            lp.setMargins(0, getResources().getDimensionPixelSize(resourceId), 0, 0);
            appBarLayout.setLayoutParams(lp);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        // 待确认
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvReceiptUnconfirm.setLayoutManager(layoutManager);
        unconfirmAdapter = new AcceptorAdapter(getApplicationContext(), unconfirmList);
        rvReceiptUnconfirm.setAdapter(unconfirmAdapter);

        // 已确认
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvReceiptFinish.setLayoutManager(layoutManager1);
        finishAdapter = new AcceptorAdapter(getApplicationContext(), finishList);
        rvReceiptFinish.setAdapter(finishAdapter);

//        unconfirmAdapter.setOnItemClickListener(new AcceptorAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("AssignmentDetail", unconfirmAdapter.getItem(position));
//                Intent intent = new Intent(ReceiptActivity.this, AssignmentDetailActivity.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, REQUEST_CODE);
//                Toast.makeText(ReceiptActivity.this, "跳转到名片页",Toast.LENGTH_SHORT).show();
//                // TODO: 页面跳转
//            }
//        });

//        finishAdapter.setOnItemClickListener(new AcceptorAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("AssignmentDetail", finishAdapter.getItem(position));
//                Intent intent = new Intent(ReceiptActivity.this, AssignmentDetailActivity.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, REQUEST_CODE);
//                Toast.makeText(ReceiptActivity.this, "跳转到名片页",Toast.LENGTH_SHORT).show();
//                // TODO: 页面跳转
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
