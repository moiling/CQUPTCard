package com.moi.cquptcard.ui.view;

import com.moi.cquptcard.model.bean.ConsumptionBean;

import java.util.List;

import retrofit.RetrofitError;

/**
 *
 * Created by moi on 11/23/2015.
 */
public interface IConsumptionVu {

    void onProcess();

    void onFail(RetrofitError e);

    void onSuccess(List<ConsumptionBean> consumptions, String page);

}
