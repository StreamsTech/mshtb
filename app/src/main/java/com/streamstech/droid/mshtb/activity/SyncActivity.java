package com.streamstech.droid.mshtb.activity;

import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.State;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.io.PETSyncManager;
import com.streamstech.droid.mshtb.io.FASTSyncManager;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.SyncMessageUpdateListener;
import com.streamstech.droid.mshtb.util.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SyncActivity extends AppCompatActivity implements SyncMessageUpdateListener {

    @BindView(R.id.progressLoading)
    ContentLoadingProgressBar progressLoading;
    @BindView(R.id.lblStatus)
    TextView lblStatus;
    @BindView(R.id.lblLastSyncTime)
    TextView lblLastSyncTime;
    @BindView(R.id.lblCount)
    TextView lblCount;
    @BindView(R.id.lblMessage)
    TextView lblMessage;
    @BindView(R.id.btnSync)
    Button btnSync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        readLastSyncTime();
        if (MSHTBApplication.getInstance().getSettingsBoolean(DBColumnKeys.SYNC_RUNNING)) {
            btnSync.setText("SYNC IN PROGRESS");
            btnSync.setEnabled(false);
        } else {
            progressLoading.hide();
        }
        if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
            lblCount.setText(String.valueOf(FASTSyncManager.getPendingCount()));
        } else {
            lblCount.setText(String.valueOf(PETSyncManager.getPendingCount()));
        }
    }

    private void readLastSyncTime() {
        String lastUdateTime = "n/a";
        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                .getSettingsDao();
        Settings settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LAST_UPDATE_TIME))
                .unique();
        if (settings == null) {
            lastUdateTime = "1970-01-01 00:00:00";
            settingsDao.save(new Settings(null, DBColumnKeys.LAST_UPDATE_TIME, lastUdateTime));
        } else {
            lastUdateTime = settings.getValue();
        }
        lblLastSyncTime.setText(lastUdateTime);
    }

    @OnClick(R.id.btnSync)
    public void onSyncClicked() {

        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Sync with server")
                .setContentText("Are you sure you want to sync now? You need an active internet connection and it may take a while")
                .setConfirmText("Yes!")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        // Needs to login to upload
                        if (Constant.TOKEN.trim().isEmpty()) {
                            SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                                    .getSettingsDao();
                            Settings settings = settingsDao.queryBuilder()
                                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
                                    .unique();
                            if (settings != null) {
                                settings.setValue(State.LOGGED_OUT.name());
                                settingsDao.update(settings);
                            }
                            Intent intent = new Intent(SyncActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
//                        UIUtil.showSweetProgress(NewMainActivity.this, "Sync with server...", false, null);
                        lblMessage.setVisibility(View.VISIBLE);
                        btnSync.setEnabled(false);
                        progressLoading.show();
                        MSHTBApplication.getInstance().saveSetting(DBColumnKeys.SYNC_RUNNING, String.valueOf(true));
                        btnSync.setText("SYNC IN PROGRESS");
                        if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
                            new FASTSyncManager(SyncActivity.this, SyncActivity.this).execute((Void) null);
                        } else {
                            new PETSyncManager(SyncActivity.this, SyncActivity.this).execute((Void) null);
                        }
//                        mAuthTask.execute((Void) null);
                    }
                })
                .show();
    }

    @Override
    public void messageUpdated(String message) {
        lblStatus.setText(message);
    }

    @Override
    public void syncCompleted() {
        MSHTBApplication.getInstance().saveSetting(DBColumnKeys.SYNC_RUNNING, String.valueOf(false));
        btnSync.setText("SYNC");
        progressLoading.hide();
        UIUtil.hideSweetProgress();
        readLastSyncTime();
        lblCount.setText(String.valueOf(FASTSyncManager.getPendingCount()));
        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE, "Sync", "Sync completed");
        btnSync.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
