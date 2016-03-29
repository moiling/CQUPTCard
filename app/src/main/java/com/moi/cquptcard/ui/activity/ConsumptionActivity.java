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
import com.moi.cquptcard.ui.widget.DividerItemDecoration;

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
    // 是否正在加载
    private boolean isLoading = false;
    // 是否滑到最底
    private boolean isBottom = false;
    // 时候向底端滑动
    private boolean isSlidingToEnd = false;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ConsumptionActivity.class);
        intent.putExtra("id", id);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consumptionPresenter.onRelieveView();
    }

    private void initContent() {
        mAdapter = new ConsumptionAdapter(this, consumptionBeans);
        mConsumptionList.setLayoutManager(new LinearLayoutManager(this));
        mConsumptionList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mConsumptionList.setAdapter(mAdapter);
        mConsumptionList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) mConsumptionList.getLayoutManager();
                int lastPosition = lm.findLastVisibleItemPosition();
                // 倒数第五个开始就加载后续，这样实现自动加载
                if (lastPosition >= lm.getItemCount() - 5 && !isLoading && isSlidingToEnd) {
                    consumptionPresenter.load(id, page + "");
                }
                isBottom = lastPosition == mAdapter.getItemCount() - 1;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToEnd = (dx > 0 || dy > 0);
            }
        });
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.accent_color));
    }

    private void initToolbar() {
        mToolbar.setTitle("花钱如流水");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onProcess() {
        // 为了自动加载的感觉，去掉进度条
        isLoading = true;
    }

    @Override
    public void onFail(RetrofitError e) {
        isLoading = false;
        mSwipeRefreshWidget.setRefreshing(false);
        e.printStackTrace();
        // 滑到底部再弹出错误信息，不要一直弹！
        if (isBottom) {
            Snackbar.make(mToolbar, "信息未能正确GET", Snackbar.LENGTH_LONG)
                    .setAction("OK", null)
                    .setActionTextColor(getResources().getColor(R.color.accent_color))
                    .show();
        }
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions, String page) {
        isLoading = false;
        mSwipeRefreshWidget.setRefreshing(false);
        // 如果是第一页(表示刷新，或者初始进入)就清空之前的数据
        if (page.equals("1"))
            consumptionBeans.clear();
        consumptionBeans.addAll(consumptions);
        mAdapter.notifyDataSetChanged();
        this.page++;
    }

    @Override
    public void onRefresh() {
        // 刷新的话……就请求最新数据吧
        if (!id.isEmpty()) {
            consumptionPresenter.load(id, 1 + "");
            // 还原page
            page = 1;
        } else
            mSwipeRefreshWidget.setRefreshing(false);
    }
}
