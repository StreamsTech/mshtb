package com.streamstech.droid.mshtb.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;
import java.util.Date;
import java.util.List;

import com.streamstech.droid.mshtb.data.State;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.data.persistent.Treatment;
import com.streamstech.droid.mshtb.data.persistent.TreatmentDao;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

public class MSHTBApplication extends Application {

    private static MSHTBApplication instance;

    public MSHTBApplication() {
        QueryBuilder.LOG_SQL = false;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MSHTBApplication getInstance() {
        return instance;
    }

    public void hideKeyboard(Context context)
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


    public void saveSetting(String key, String value){
        SettingsDao dao = DatabaseManager.getInstance().getSession().getSettingsDao();
        Settings settings = dao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(key))
                .unique();
        if (settings == null) {
            dao.insert(new Settings(null, key, value));
        } else {
            settings.setValue(value);
            dao.update(settings);
        }
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

    public int getSettingsInt(String key){
        SettingsDao dao = DatabaseManager.getInstance().getSession().getSettingsDao();
        Settings settings = dao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(key))
                .unique();
        if (settings == null) {
            return -1;
        }
        return Integer.valueOf(settings.getValue());
    }

    public boolean getSettingsBoolean(String key){
        SettingsDao dao = DatabaseManager.getInstance().getSession().getSettingsDao();
        Settings settings = dao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(key))
                .unique();
        if (settings == null) {
            return false;
        }
        return Boolean.valueOf(settings.getValue());
    }

    public List<Patient> getPatientForResultInput(Property property) {
        //Property property
        // http://greenrobot.org/greendao/documentation/joins/
        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        QueryBuilder<Patient> queryBuilder = patientDao.queryBuilder();
        Join test = queryBuilder.join(PatientDao.Properties.Patientid, TestIndication.class, TestIndicationDao.Properties.Patientid)
                .where(property.eq(0));
        return queryBuilder
                .list();
    }

    public List<Patient> getTreatmentInput() {
        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        QueryBuilder<Patient> queryBuilder = patientDao.queryBuilder();
        return queryBuilder
                .where(PatientDao.Properties.Tb.eq(true))
                .list();
    }

    public List<Patient> getTreatmentOutput() {
        //Property property
        // http://greenrobot.org/greendao/documentation/joins/
        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        QueryBuilder<Patient> queryBuilder = patientDao.queryBuilder();
        Join test = queryBuilder.join(PatientDao.Properties.Patientid, Treatment.class, TreatmentDao.Properties.Patientid);
        return queryBuilder
                .list();
    }
}
