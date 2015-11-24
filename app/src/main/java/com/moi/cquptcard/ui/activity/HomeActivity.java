package com.moi.cquptcard.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.moi.cquptcard.R;
import com.moi.cquptcard.app.BaseActivity;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.presenter.ConsumptionPresenter;
import com.moi.cquptcard.ui.view.IConsumptionVu;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;

public class HomeActivity extends BaseActivity implements IConsumptionVu {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list_home_card)
    RecyclerView mCardList;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @Bind(R.id.tv_home_empty)
    TextView mCardEmptyText;
    @Bind(R.id.home_fab)
    FloatingActionButton mFab;

    private ConsumptionPresenter consumptionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        consumptionPresenter = new ConsumptionPresenter(this);
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consumptionPresenter.onRelieveView();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProcess() {
        showProgress("加载中");
    }

    @Override
    public void onFail(RetrofitError e) {
        dismissProgress();
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions) {
        dismissProgress();
    }
}
