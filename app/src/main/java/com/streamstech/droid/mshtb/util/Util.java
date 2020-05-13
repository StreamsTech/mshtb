package com.streamstech.droid.mshtb.util;

import android.database.Cursor;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;

import org.greenrobot.greendao.query.CountQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

import static cz.msebera.android.httpclient.HttpHeaders.FROM;

/**
 * Created by AKASH-LAPTOP on 5/14/2018.
 */

public class Util {

    public static boolean isEmpty(TextView textView) {
        return textView.getText().toString().isEmpty();
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static String getFormattedDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static String getFormattedDate(long milliseonds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(milliseonds));
    }

    public static String getFormattedMonth(long milliseonds) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
        return formatter.format(new Date(milliseonds));
    }

    public static String getFormattedDateTime(long milliseonds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        return formatter.format(new Date(milliseonds));
    }

    public static String getDbExportFileSuffix() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return formatter.format(new Date(System.currentTimeMillis()));
    }

    public static boolean validQR(String qrtext) {
        if (qrtext == null || qrtext.trim().isEmpty()) {
            return false;
        }
        Pattern checkRegex = Pattern.compile(Constant.QR_REGEX);
        return checkRegex.matcher(qrtext).matches();
    }

    public static String _generatePatientID(PatientDao patientDao) {

//        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        long currentTime = System.currentTimeMillis();
        Date aDayAgo = new Date(currentTime - DateUtils.DAY_IN_MILLIS);
        Date inADay = new Date(currentTime + DateUtils.DAY_IN_MILLIS);
        CountQuery<Patient> countQuery = patientDao.queryBuilder().where(
                PatientDao.Properties.Createdtime.gt(aDayAgo.getTime()),
                PatientDao.Properties.Createdtime.lt(inADay.getTime()))
                .buildCount();
        long dayCount = countQuery.count();
//        System.out.println("Day Count: " + dayCount);
//        Constant.DAY_COUNT = countQuery.count() + 1;


//        long dayCount = Constant.DAY_COUNT++;
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        String pid = String.format("%s%s%s%s",
                String.format("%02d", MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.LOCATION_NO)/*Constant.HOSPITAL_ID*/),
                String.format("%02d", MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)  /*Constant.SCREENER_ID*/),
                dateFormat.format(Constant.DATE_FORMAT, new Date()),
                String.format("%03d", dayCount + 1));
//        System.out.println("Day Count PID: " + pid);
        return pid;
    }

    public static String generatePatientID(PatientDao patientDao) {

        Cursor cursor = patientDao.getDatabase().rawQuery("SELECT count(PATIENTID) FROM PATIENT " +
                        "WHERE date(datetime(CREATEDTIME / 1000 , 'unixepoch', 'localtime')) = date('now', 'localtime')", null);

        if (cursor == null || !cursor.moveToFirst()) {
            return "";
        }
        int dayCount = cursor.getInt(0);
        cursor.close();
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        String pid = String.format("%s%s%s%s",
                String.format("%02d", MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.LOCATION_NO)),
                String.format("%02d", MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)),
                dateFormat.format(Constant.DATE_FORMAT, new Date()),
                String.format("%03d", dayCount + 1));
        return pid;
    }

    public static String extractZip(ResponseBody responseBody) throws IOException {
        String content = "";
        // read gzip
        ByteArrayInputStream bais = null;
        try {
//            bais = new ByteArrayInputStream(responseBody.byteStream().bytes());
            InputStream gzis = new GZIPInputStream(responseBody.byteStream());
            InputStreamReader reader = new InputStreamReader(gzis);
            BufferedReader in = new BufferedReader(reader);

            String readed;
            StringBuilder gzipResponseString = new StringBuilder();
            while ((readed = in.readLine()) != null) {
                gzipResponseString.append(readed); //write gzip data in StringBuilder
            }
            content = gzipResponseString.toString();
            in.close();
            reader.close();
            gzis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean isQRAvailable(String qr) {
        PETEnrollmentDao petEnrollmentDao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
        long count = petEnrollmentDao.queryBuilder()
                .whereOr(PETEnrollmentDao.Properties.Qr.eq(qr), PETEnrollmentDao.Properties.Indexpatientqr.eq(qr))
                .count();
        if (count > 0) {
            return false;
        }

        PETRegistrationDao petRegistrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
        count = petRegistrationDao.queryBuilder()
                .where(PETRegistrationDao.Properties.Qr.eq(qr))
                .count();

        if (count > 0) {
            return false;
        }
        return true;
    }

    public static void setDate (long date, DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static int getBMIIndex (EditText weight, EditText height) {
        if (("".equalsIgnoreCase(getText(weight)) || "".equalsIgnoreCase(getText(height)))) {
            return -1;
        }

        double dWeight = Double.parseDouble(getText(weight));
        double dHeight = Double.parseDouble(getText(height));
        double bmi = dWeight / (Math.pow((dHeight / 100), 2));
        if (bmi < 18.5) {
            return 0;
        } else if (bmi < 24.9) {
            return 1;
        } else if (bmi < 29.9) {
            return 2;
        } else if (bmi < 35.9) {
            return 3;
        } else  {
            return 4;
        }
    }
}
