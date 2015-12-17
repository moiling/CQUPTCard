package com.moi.cquptcard.presenter;

import android.content.Context;

import com.moi.cquptcard.model.IConsumptionModel;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.model.impl.ConsumptionModel;
import com.moi.cquptcard.ui.view.IConsumptionVu;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class ConsumptionPresenter {

    private IConsumptionModel consumptionModel;
    private IConsumptionVu v;

    public ConsumptionPresenter(IConsumptionVu v) {
        this.v = v;
        consumptionModel = new ConsumptionModel((Context)v);
    }

    public void load(final String userID, final String page) {
        if (v != null) v.onProcess();
        consumptionModel.loadConsumption(userID, page, new Callback<List<ConsumptionBean>>() {
            @Override
            public void success(List<ConsumptionBean> consumptionBeans, Response response) {
                if (v != null) v.onSuccess(consumptionBeans, page);
            }

            @Override
            public void failure(RetrofitError error) {
                if (v != null) v.onFail(error);
            }
        });
    }

    public void onRelieveView() {
        if (v != null) v = null;
    }

}
