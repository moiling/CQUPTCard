package com.moi.cquptcard.model.impl;

import android.content.Context;

import com.moi.cquptcard.model.IConsumptionModel;
import com.moi.cquptcard.model.bean.ConsumptionBean;

import java.util.List;

import retrofit.Callback;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class ConsumptionModel extends BaseModel implements IConsumptionModel {

    public ConsumptionModel(Context context) {
        super(context);
    }

    @Override
    public void loadConsumption(String userID, String page, Callback<List<ConsumptionBean>> callback) {
        getExperApi().loadConsumption(userID, page, callback);
    }
}
