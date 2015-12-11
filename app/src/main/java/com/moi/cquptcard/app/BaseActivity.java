package com.moi.cquptcard.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.moi.cquptcard.R;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将当前的Activity加入数组中
        APP.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将当前的Activity从数组中移除
        APP.getInstance().removeActivity(this);
    }

    public void showProgress(String title) {
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(this.getResources().getColor(R.color.primary_color))
                .backgroundColor(this.getResources().getColor(R.color.white))
                .positiveColor(this.getResources().getColor(R.color.primary_color))
                .content(APP.getContext().getResources().getString(R.string.please_wait))
                .theme(Theme.LIGHT)
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    public void dismissProgress() {
        if (dialog != null) if (dialog.isShowing()) dialog.dismiss();
    }
}
