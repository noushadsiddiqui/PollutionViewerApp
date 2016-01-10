package com.example.azuga.pollutionviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 10-01-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progress;

    protected void showProgressBar(Context ctx, String msg) {
        if (progress != null) return;
        progress = new ProgressDialog(ctx);
        progress.setMessage(msg);
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    protected void hideProgressDialog() {
        if (progress == null) return;
        progress.dismiss();
    }
}
