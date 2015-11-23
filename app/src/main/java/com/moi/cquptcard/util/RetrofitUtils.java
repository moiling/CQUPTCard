package com.moi.cquptcard.util;

import android.content.Context;
import android.util.Config;

import com.moi.cquptcard.config.Api;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class RetrofitUtils {

    private static RestAdapter singleton;

    public static <T> T createApi(Context context, Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    RestAdapter.Builder builder = new RestAdapter.Builder();
                    builder.setEndpoint(Api.END_POINT);
                    builder.setConverter(new GsonConverter(GsonUtils.newInstance()));
                    builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));
                    builder.setLogLevel(
                            Config.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }
}
