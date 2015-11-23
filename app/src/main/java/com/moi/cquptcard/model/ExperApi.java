package com.moi.cquptcard.model;

import com.moi.cquptcard.config.Api;
import com.moi.cquptcard.model.bean.ConsumptionBean;
import com.moi.cquptcard.model.bean.LibraryInfoBean;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 * Created by moi on 11/23/2015.
 */
public interface ExperApi {

    @GET(Api.API_YKT)
    void loadConsumption(@Query("UsrID") String userID, @Query("page") String page, Callback<List<ConsumptionBean>> callback);

    @GET(Api.API_JSCX)
    void loadLibraryInfo(@Query("UsrID") String userID, Callback<List<LibraryInfoBean>> callback);
}
