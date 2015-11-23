package com.moi.cquptcard.model;

import com.moi.cquptcard.model.bean.ConsumptionBean;

import java.util.List;

import retrofit.Callback;

/**
 *
 * Created by moi on 11/23/2015.
 */
public interface IConsumptionModel {

    void loadConsumption(String userID, String page, Callback<List<ConsumptionBean>> callback);

}
