package com.moi.cquptcard.model.impl;

import android.content.Context;

import com.moi.cquptcard.model.ExperApi;
import com.moi.cquptcard.util.RetrofitUtils;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class BaseModel {

    private ExperApi mExperApi;

    public BaseModel(Context context) {
        mExperApi = createApi(ExperApi.class, context);
    }

    public ExperApi getExperApi() {
        return mExperApi;
    }

    public <T> T createApi(Class<T> cls, Context context) {
        return RetrofitUtils.createApi(context, cls);
    }

}
