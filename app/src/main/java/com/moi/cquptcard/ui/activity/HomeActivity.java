package com.moi.cquptcard.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Bind(R.id.toolbar) Toolbar mToolbar;

    private ConsumptionPresenter consumptionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        consumptionPresenter = new ConsumptionPresenter(this);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        //mToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        Log.e("..", e.toString());
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions) {
        dismissProgress();
        Toast.makeText(this, consumptions.get(0).getName(), Toast.LENGTH_SHORT).show();
    }
}
