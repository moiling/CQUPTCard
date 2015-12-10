package com.moi.cquptcard.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class APP extends Application {

    private static Context context;
    private static APP instance;
    private static List<Activity> activities = new ArrayList<>();

    public APP() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static APP getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void exit() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
