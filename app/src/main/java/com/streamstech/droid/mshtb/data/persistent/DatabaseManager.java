package com.streamstech.droid.mshtb.data.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.streamstech.droid.mshtb.util.MSHTBApplication;


public class DatabaseManager {
    private static DatabaseManager instance;
    private final String DB_NAME;
    private Context context;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;

    public DatabaseManager() {
        this.DB_NAME = "epharmacy-android-db";
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void init(Context context) {
        this.context = context;
        this.db = new DaoMaster.DevOpenHelper(context, this.DB_NAME, null).getWritableDatabase();
        this.daoMaster = new DaoMaster(this.db);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        instance.context = context;
        return instance;
    }

    public DaoSession getSession() {
        if (this.context == null) {
            System.out.println("Context is NULL at DatabaseManager");
            context = MSHTBApplication.getInstance();
        }
        if (this.daoMaster == null) {
            this.db = new DaoMaster.DevOpenHelper(this.context, this.DB_NAME, null).getWritableDatabase();
            this.daoMaster = new DaoMaster(this.db);
        }
        return this.daoMaster.newSession();
    }

    public Settings getSettings(final String key) {
        if (this.context == null) {
            System.out.println("Context is NULL at DatabaseManager");
            context = MSHTBApplication.getInstance();
        }
        return getSession()
                .getSettingsDao().queryBuilder()
                .where(SettingsDao.Properties.Key.eq(key)).unique();
    }

//    public DigestParams getDigestParameters() {
//        List<Settings> lst = getSession()
//                .getSettingsDao()
//                .queryBuilder()
//                .whereOr(SettingsDao.Properties.Key.eq(SettingColumnKeys.DEVICE_PIN),
//                        SettingsDao.Properties.Key.eq(SettingColumnKeys.DEVICE_IDENTIFIER))
//                .list();
//        if (lst.isEmpty()) {
//            return null;
//        }
//
//        DigestParams param = new DigestParams();
//        for(Settings s: lst)
//        {
//            if (s.getKey().equals(SettingColumnKeys.DEVICE_PIN))
//                param.setPin(s.getValue());
//            else if (s.getKey().equals(SettingColumnKeys.DEVICE_IDENTIFIER))
//                param.setIdentifier(s.getValue());
//        }
//        return param;
//    }
}
