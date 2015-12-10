package com.moi.cquptcard.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

public class ConsumptionActivity extends BaseActivity implements IConsumptionVu, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list_detail_card)
    RecyclerView mConsumptionList;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private ConsumptionPresenter consumptionPresenter;
    private ConsumptionAdapter mAdapter;
    private List<ConsumptionBean> consumptionBeans = new ArrayList<>();
    // 这接口的第一页居然page不是0……而是1
    private int page = 1;
    private String id;

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
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initToolbar() {
        mToolbar.setTitle("流水线");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onProcess() {
        // 避免双螺旋
        if (!mSwipeRefreshWidget.isRefreshing())
            showProgress("加载中");
    }

    @Override
    public void onFail(RetrofitError e) {
        dismissProgress();
        mSwipeRefreshWidget.setRefreshing(false);
        e.printStackTrace();
        Snackbar.make(mToolbar, "我的天！居然出错了", Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .setActionTextColor(getResources().getColor(R.color.accent_color))
                .show();
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions, String page) {
        dismissProgress();
        mSwipeRefreshWidget.setRefreshing(false);
        // 如果是第一页(表示刷新，或者初始进入)就清空之前的数据
        if (page.equals("1"))
            consumptionBeans.clear();
        consumptionBeans.addAll(consumptions);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        // 刷新的话……就请求最新数据吧
        if (!id.isEmpty())
            consumptionPresenter.load(id, 1 + "");
        else
            mSwipeRefreshWidget.setRefreshing(false);
    }
}