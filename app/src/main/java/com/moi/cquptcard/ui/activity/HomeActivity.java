package com.moi.cquptcard.ui.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.moi.cquptcard.R;
import com.moi.cquptcard.app.BaseActivity;
import com.moi.cquptcard.model.bean.Card;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.presenter.CardPresenter;
import com.moi.cquptcard.presenter.ConsumptionPresenter;
import com.moi.cquptcard.ui.adapter.CardsAdapter;
import com.moi.cquptcard.ui.view.ICardVu;
import com.moi.cquptcard.ui.view.IConsumptionVu;
import com.moi.cquptcard.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;

public class HomeActivity extends BaseActivity implements IConsumptionVu, ICardVu, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    private CardPresenter cardPresenter;
    private CardsAdapter mCardsAdapter;
    private List<Card> cards = new ArrayList<>();
    private String tempName;
    private String tempId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        consumptionPresenter = new ConsumptionPresenter(this);
        cardPresenter = new CardPresenter(this);
        initToolbar();
        initContent();
        initData();
    }

    private void initData() {
        // 从数据库中读取所有一卡通信息
        cards.addAll(DatabaseUtils.loadCards());
        controlList();
        mCardsAdapter.notifyItemRangeInserted(0, cards.size());
        cardPresenter.refresh(cards);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consumptionPresenter.onRelieveView();
        cardPresenter.onRelieveView();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    private void initContent() {
        mCardsAdapter = new CardsAdapter(this, cards);
        mCardList.setLayoutManager(new LinearLayoutManager(this));
        mCardList.setAdapter(mCardsAdapter);
        // 根据时候有一卡通来判断是否显示List
        controlList();
        mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mFab.setOnClickListener(this);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.accent_color));
    }

    private void controlList() {
        if (cards.isEmpty()) {
            hideList();
        } else {
            showList();
        }
    }

    private void hideList() {
        mSwipeRefreshWidget.setVisibility(View.GONE);
        mCardList.setVisibility(View.GONE);
        mCardEmptyText.setVisibility(View.VISIBLE);
    }

    private void showList() {
        mSwipeRefreshWidget.setVisibility(View.VISIBLE);
        mCardList.setVisibility(View.VISIBLE);
        mCardEmptyText.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onProcess() {
        if (!mSwipeRefreshWidget.isRefreshing())
            showProgress("加载中");
    }

    @Override
    public void onFail(RetrofitError e) {
        dismissProgress();
        e.printStackTrace();
        Snackbar.make(mFab, "我的天！居然出错了", Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .setActionTextColor(getResources().getColor(R.color.accent_color))
                .show();
    }

    @Override
    public void onSuccess(List<ConsumptionBean> consumptions, String page) {
        dismissProgress();
        String name = consumptions.get(0).getName();
        String money = consumptions.get(0).getBalance();
        String time = consumptions.get(0).getTime();
        // 验证信息时候匹配
        if (name.equals(tempName)) {
            // 验证信息时候重复
            boolean hasThisCard = false;
            for (Card card : cards) {
                if (card.getCardId().equals(tempId)) {
                    Snackbar.make(mFab, "不要重复添加一卡通", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .setActionTextColor(getResources().getColor(R.color.accent_color))
                            .show();
                    hasThisCard = true;
                    break;
                }
            }
            if (!hasThisCard) {
                Card card = new Card(tempName, tempId).setMoney(money).setTime(time);
                cards.add(card);
                controlList();
                mCardsAdapter.notifyItemInserted(cards.size());
                // 保存到数据库中
                DatabaseUtils.saveCard(card);
            }
        } else {
            Snackbar.make(mFab, "信息不匹配，不要乱试啊！", Snackbar.LENGTH_LONG)
                    .setAction("OK", null)
                    .setActionTextColor(getResources().getColor(R.color.accent_color))
                    .show();
        }
    }

    @Override
    public void onItemRefreshSuccess(List<ConsumptionBean> consumptions, int position) {
        mSwipeRefreshWidget.setRefreshing(false);
        String money = consumptions.get(0).getBalance();
        String time = consumptions.get(0).getTime();
        cards.set(position, cards.get(position).setMoney(money).setTime(time));
        mCardsAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemRefreshFail(RetrofitError e, int position) {
        mSwipeRefreshWidget.setRefreshing(false);
        e.printStackTrace();
        cards.set(position, cards.get(position).setMoney("0").setTime("数据请求出错"));
        mCardsAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_fab:
                View addView  = LayoutInflater.from(this).inflate(R.layout.view_add_card, null);
                final AppCompatEditText edName = (AppCompatEditText) addView.findViewById(R.id.ed_name);
                final AppCompatEditText edId = (AppCompatEditText) addView.findViewById(R.id.ed_id);
                new MaterialDialog.Builder(this)
                        .title("添加一卡通")
                        .customView(addView, false)
                        .positiveText("确定")
                        .positiveColor(getResources().getColor(R.color.accent_color))
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                tempName = edName.getText().toString();
                                tempId = edId.getText().toString();
                                if (tempName.isEmpty() || tempId.isEmpty()) {
                                    Snackbar.make(mFab, "不能为空呀！", Snackbar.LENGTH_LONG)
                                            .setAction("OK", null)
                                            .setActionTextColor(getResources().getColor(R.color.accent_color))
                                            .show();
                                } else {
                                    // 传1表示每次获取最新一页数据，然后获取第一条就可以了
                                    consumptionPresenter.load(tempId, 1 + "");
                                }
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (cards.size() > 0) {
            mCardsAdapter.notifyItemRangeChanged(0, cards.size());
            cardPresenter.refresh(cards);
        } else
            mSwipeRefreshWidget.setRefreshing(false);
    }
}
