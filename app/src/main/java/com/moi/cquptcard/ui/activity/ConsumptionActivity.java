package com.moi.cquptcard.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.moi.cquptcard.R;
import com.moi.cquptcard.app.BaseActivity;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.presenter.ConsumptionPresenter;
import com.moi.cquptcard.ui.adapter.ConsumptionAdapter;
import com.moi.cquptcard.ui.view.IConsumptionVu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;

public class ConsumptionActivity extends BaseActivity implements IConsumptionVu {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list_detail_card)
    RecyclerView mConsumptionList;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private ConsumptionPresenter consumptionPresenter;
    private ConsumptionAdapter mAdapter;
    private List<ConsumptionBean> consumptionBeans = new ArrayList<>();
    private String id;
    private int page = 1;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ConsumptionActivity.class);
        intent.putExtra("id", id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        ButterKnife.bind(this);
        consumptionPresenter = new ConsumptionPresenter(this);
        id = getIntent().getStringExtra("id");
        initToolbar();
        initContent();
        if (!id.isEmpty())
            consumptionPresenter.load(id, page + "");
    }

    private void initContent() {
        mAdapter = new ConsumptionAdapter(this, consumptionBeans);
        mConsumptionList.setLayoutManager(new LinearLayoutManager(this));
        mConsumptionList.setAdapter(mAdapter);
    }

    private void initToolbar() {
        mToolbar.setTitle("流水线");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onProcess() {
        showProgress("加载中");
    }

    @Override
    public void onFail(RetrofitError e) {
        dismissProgress();
        e.printStackTrace();
        Toast.makeText(this, "我的天！居然出错了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions) {
        dismissProgress();
        consumptionBeans.clear();
        consumptionBeans.addAll(consumptions);
        mAdapter.notifyDataSetChanged();
    }
}
