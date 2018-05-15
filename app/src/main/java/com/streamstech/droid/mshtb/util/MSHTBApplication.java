package com.streamstech.droid.mshtb.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;
import java.util.Date;

import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;

public class MSHTBApplication extends Application {

    private static MSHTBApplication instance;

    public MSHTBApplication() {
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MSHTBApplication getInstance() {
        return instance;
    }

    public static void hideKeyboard(Context context)
    {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity)context).getWindow().getDecorView().getWindowToken(), 0);
    }

    public String getApplicationVersion()
    {
        try {
            return getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "UNKNOWN";
        }
    }

    public String getTime(long date, String format)
    {
        return DateFormat.format(format, new Date(date)).toString();
    }

    public String getSettings(String key){
        SettingsDao dao = DatabaseManager.getInstance().getSession().getSettingsDao();
        Settings settings = dao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(key))
                .unique();
        if (settings == null) {
            return "";
        }
        return settings.getValue();
    }
}
