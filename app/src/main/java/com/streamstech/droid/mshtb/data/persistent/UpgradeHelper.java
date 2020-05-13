package com.streamstech.droid.mshtb.data.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * Created by AKASH-LAPTOP on 8/2/2018.
 */

public class UpgradeHelper extends DaoMaster.OpenHelper {
    public UpgradeHelper(Context context, String name) {
        super(context, name);
    }

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
//        dropAllTables(db, true);
//        onCreate(db);
        MigrationHelper.migrate(db,
                PatientDao.class,
                PETEligibilitySymptomDao.class,
                PETEnrollmentDao.class,
                PETTreatmentStartDao.class,
                PETAdverseEventFollowupDao.class);
    }
}
