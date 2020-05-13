package com.streamstech.droid.mshtb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;

/**
 * Created by User on 2/24/2016.
 */
public class SplashActivity extends Activity {
    private long SPLASH_TIME_OUT = 3000;
    String version;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        requestWindowFeature(1);
        setContentView(R.layout.activity_splash);
        try {
            this.version = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
            ((TextView) findViewById(R.id.version_txt)).setText(" v" + this.version);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.version = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
            ((TextView) findViewById(R.id.version_txt)).setText(" v" + this.version);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        DatabaseManager.getInstance().init(this);
        startSplashTimer();
    }

    private void startSplashTimer() {
        new Thread(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_TIME_OUT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startLandingActivity();
            }
        })).start();
    }

    private void startLandingActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onBackPressed() {
    }
}