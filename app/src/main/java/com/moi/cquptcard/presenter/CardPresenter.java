package com.moi.cquptcard.presenter;

import android.content.Context;

import com.moi.cquptcard.model.IConsumptionModel;
import com.moi.cquptcard.model.bean.Card;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.model.impl.ConsumptionModel;
import com.moi.cquptcard.ui.view.ICardVu;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * Created by moi on 15/12/12.
 */
public class CardPresenter {

    private IConsumptionModel consumptionModel;
    private ICardVu v;

    public CardPresenter(ICardVu v) {
        this.v = v;
        consumptionModel = new ConsumptionModel((Context) v);
    }

    public void refresh(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            final int finalI = i;
            // 这里默认第一页是1……
            consumptionModel.loadConsumption(cards.get(i).getCardId(), String.valueOf(1), new Callback<List<ConsumptionBean>>() {
                @Override
                public void success(List<ConsumptionBean> consumptionBeen, Response response) {
                    if (v != null) v.onItemRefreshSuccess(consumptionBeen, finalI);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (v != null) v.onItemRefreshFail(error, finalI);
                }
            });
        }
    }

    public void onRelieveView() {
        if (v != null) v = null;
    }

}
