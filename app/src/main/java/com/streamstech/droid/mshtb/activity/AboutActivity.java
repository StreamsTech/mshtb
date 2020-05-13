package com.streamstech.droid.mshtb.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AboutActivity extends AppCompatActivity {

    String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        try {
            this.version = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
            ((TextView) findViewById(R.id.version_txt)).setText(" v" + this.version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.version = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
            ((TextView) findViewById(R.id.version_txt)).setText(" v" + this.version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final TextView lblLastBlock = (TextView)findViewById(R.id.lblLastBlock);
        lblLastBlock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                registerForContextMenu(lblLastBlock);
                return false;
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String exportDB() {

        String backupDBPath = "";
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//com.streamstech.droid.mshtb//databases//epharmacy-android-db";

                backupDBPath = "msh-db-" + MSHTBApplication.getInstance().getSettings(DBColumnKeys.USER_NAME) + "_"  + Util.getDbExportFileSuffix() + ".sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(this, "Database exported as " + backupDBPath, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backupDBPath;
    }

    private void simulate() {
        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        for (int i = 0; i < 10; i++ ) {
            String pid = Util.generatePatientID(patientDao);
            patientDao.insert(new Patient(null, pid, MSHTBApplication.getInstance().getSettings(DBColumnKeys.SCREENER_ID), //  String.valueOf(Constant.SCREENER_ID),
                    false, false, -1, -1, "Name-" + i, i,
                    i % 2 == 0 ? "M" : "F", (i * i) + "",
                    new Date().getTime(), false, false, 0.0, 0.0, "", false));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Hidden Menu");
        menu.add(0, 1, 0, "Auto test data");
        menu.add(0, 2, 0, "Export database");
//        menu.add(0, 3, 0, "Email database");
        menu.add(0, 4, 0, "Import database");
        menu.add(0, 5, 0, "Reset Sync Status");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
//            simulate();
        } else if (item.getItemId() == 2) {
            exportDB();
        }  else if (item.getItemId() == 3) {
            sendFileToEmail(exportDB());
        }  else if (item.getItemId() == 4) {
            startActivity(new Intent(this, FileBrowserActivity.class));
        } else if (item.getItemId() == 5){
            resetSyncProperty();
        }
        return true;
    }

    public void resetSyncProperty() {

        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Reset Sync Status")
                .setContentText("Are you sure you want to reset sync status to FALSE? This might unstable your application")
                .setConfirmText("Yes!")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        if (MSHTBApplication.getInstance().getSettingsBoolean(DBColumnKeys.SYNC_RUNNING)) {
                            MSHTBApplication.getInstance().saveSetting(DBColumnKeys.SYNC_RUNNING, String.valueOf(false));
                        }
                    }
                })
                .show();
    }

    public void sendFileToEmail_(String path) {
        System.out.println(path);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setType("text/plain");
        emailIntent.setType("text/xml");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.setData(Uri.parse("mailto: akash@streamstech.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MSH TB Mobile Database");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Database backup");
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("File can't read");
            return;
        }
        Uri uri = Uri.fromFile(file);
//        Uri uri = Uri.parse("file://" + file.getAbsolutePath());
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
//        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }

    public void sendFileToEmail(String path) {

        File f = new File(path);
        String subject = "MSH TB Mobile Database";
//        ArrayList<Uri> attachments = new ArrayList<>();
//        attachments.add(Uri.fromFile(f));

//        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", f);
//        attachments.add(uri);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("application/x-sqlite3");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setData(Uri.parse("mailto: akash@streamstech.com")); // only email apps should handle this
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments);
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//        intent.setType("text/plain");
//        intent.setType("text/xml");

//        intent.setClassName("com.android.email", "com.android.mail.compose.ComposeActivity");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
