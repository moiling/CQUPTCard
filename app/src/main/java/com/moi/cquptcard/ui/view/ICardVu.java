package com.moi.cquptcard.ui.view;

import com.moi.cquptcard.model.bean.ConsumptionBean;

import java.util.List;

import retrofit.RetrofitError;

/**
 *
 * Created by moi on 15/12/12.
 */
public interface ICardVu {

    void onItemRefreshSuccess(List<ConsumptionBean> consumptions, int position);

    void onItemRefreshFail(RetrofitError e, int position);
}
