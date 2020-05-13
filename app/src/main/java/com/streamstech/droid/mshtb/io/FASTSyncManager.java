package com.streamstech.droid.mshtb.io;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.streamstech.droid.mshtb.activity.LoginActivity;
import com.streamstech.droid.mshtb.activity.NewMainActivity;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.UpdateModels;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Location;
import com.streamstech.droid.mshtb.data.persistent.LocationDao;
import com.streamstech.droid.mshtb.data.persistent.Outcome;
import com.streamstech.droid.mshtb.data.persistent.OutcomeDao;
import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReviewDao;
import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptomDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupEndDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.PETSymptomDao;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrderDao;
import com.streamstech.droid.mshtb.data.persistent.PETTestResultDao;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStartDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.ScreeningDao;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathologyDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmearDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPert;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPertDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRayDao;
import com.streamstech.droid.mshtb.data.persistent.Treatment;
import com.streamstech.droid.mshtb.data.persistent.TreatmentDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.SyncMessageUpdateListener;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.GzipSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AKASH-LAPTOP on 5/21/2018.
 */

public class FASTSyncManager extends AsyncTask<Void, String, Boolean> {

    final Context context;
    final SyncMessageUpdateListener syncMessageUpdateListener;

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build();

    public FASTSyncManager(Context context, SyncMessageUpdateListener syncMessageUpdateListener) {
        this.context = context;
        this.syncMessageUpdateListener = syncMessageUpdateListener;
    }

    private void sync() {
        downloadLocation();
        upload();
        download();
    }

    public static long getPendingCount() {
        long count = 0;
        count += DatabaseManager.getInstance().getSession().getPatientDao().queryBuilder()
                .whereOr(PatientDao.Properties.Uploaded.eq(false), PatientDao.Properties.Dirty.eq(true)).count();
        count += DatabaseManager.getInstance().getSession().getScreeningDao().queryBuilder()
                .where(ScreeningDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTestIndicationDao().queryBuilder()
                .where(TestIndicationDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTestResultXRayDao().queryBuilder()
                .where(TestResultXRayDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTestResultXPertDao().queryBuilder()
                .where(TestResultXPertDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTestResultSmearDao().queryBuilder()
                .where(TestResultSmearDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao().queryBuilder()
                .where(TestResultHistopathologyDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getTreatmentDao().queryBuilder()
                .where(TreatmentDao.Properties.Uploaded.eq(false)).count();
        count += DatabaseManager.getInstance().getSession().getOutcomeDao().queryBuilder()
                .where(OutcomeDao.Properties.Uploaded.eq(false)).count();
        return count;
    }

    private void upload() {
        uploadPatient();
        uploadScreening();
        uploadTestIndication();
        uploadTestResultXray();
        uploadTestResultXpert();
        uploadTestResultSmear();
        uploadTestResultHisto();
        uploadTreatmentInitiation();
        uploadTreatmentOutcome();
    }

    public void downloadLocation() {
        final LocationDao locationDao = DatabaseManager.getInstance().getSession().getLocationDao();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(Paths.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointInterface service = client.create(EndpointInterface.class);
        retrofit2.Call<CommonResponse> call = service.location(Constant.TOKEN);

        try {
            publishProgress("Downloading location data");
            retrofit2.Response<CommonResponse> response = call.execute();
            if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                Type type = new TypeToken<List<Location>>() {
                }.getType();
                List<Location> list = new GsonBuilder()
                        .create()
                        .fromJson(new Gson()
                                .toJson(response.body()
                                        .getResults()), type);
                locationDao.deleteAll();
                for (Location location : list) {
                    locationDao.insert(new Location(null, location.getName(), location.getGeneratedid(), location.getCreatedtime()));
                }
            } else {
                System.out.println("Location download failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        call.enqueue(new retrofit2.Callback<CommonResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    Type type = new TypeToken<List<Location>>() {
//                    }.getType();
//                    List<Location> list = new GsonBuilder()
//                            .create()
//                            .fromJson(new Gson()
//                                    .toJson(response.body()
//                                            .getResults()), type);
//                    locationDao.deleteAll();
//                    for (Location location : list) {
//                        locationDao.insert(new Location(null, location.getName(), location.getGeneratedid(), location.getCreatedtime()));
//                    }
//                }
//            }
//            @Override
//            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
//            }
//        });
    }

    private void download() {

        String lastUdateTime = null;
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
        System.out.println("Last Update Time: " + lastUdateTime);
        v2(lastUdateTime);
//
//        Retrofit client = new Retrofit.Builder()
//                .baseUrl(Paths.SERVER_URL)
////                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        long startTime = System.currentTimeMillis();
//        System.out.println(startTime);
//        EndpointInterface service = client.create(EndpointInterface.class);
//        retrofit2.Call<ResponseBody> call = service.syncfastdatav2(Constant.TOKEN, lastUdateTime);
////        retrofit2.Call<ResponseBody> call = service.syncfastdatav2(Constant.TOKEN, lastUdateTime);
//        try {
//            publishProgress("Downloading data from server");
//            retrofit2.Response<ResponseBody> response = call.execute();
//            if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                publishProgress("Data downloaded from server");
//
//                String content = Util.extractZip(response.body());
//                JSONObject jsonObject = new JSONObject(content);
////                JsonElement jelement = new JsonParser().parse(content);
//                Type type = new TypeToken<List<UpdateModels>>() {
//                }.getType();
//
//                List<UpdateModels> updateModels = new GsonBuilder()
//                        .create()
////                        .fromJson(jelement.getAsJsonObject().getAsJsonArray("results"), type);
//                        .fromJson(jsonObject.getJSONArray("results").toString(), type);
//                publishProgress("Saving in local database");
//                saveSyncedData(updateModels);
//            } else {
//                System.out.println("Previous data download failed");
//            }
//        }catch (Exception exp){
//            exp.printStackTrace();
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime);
//        System.out.println("Time Taken: " + (endTime - startTime));
    }

    private void v1(String lastUdateTime) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Paths.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointInterface service = client.create(EndpointInterface.class);
        retrofit2.Call<CommonResponse> call = service.syncfastdata(Constant.TOKEN, lastUdateTime);
        try {
            publishProgress("Downloading previous data");
            retrofit2.Response<CommonResponse> response = call.execute();
            if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                Type type = new TypeToken<List<UpdateModels>>() {
                }.getType();
                List<UpdateModels> updateModels = new GsonBuilder()
                        .create()
                        .fromJson(new Gson()
                                .toJson(response.body()
                                        .getResults()), type);
                saveSyncedData(updateModels);
            } else {
                System.out.println("Previous data download failed");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void v2(String lastUdateTime) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Paths.SERVER_URL)
                .client(okHttpClient)
                .build();

        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        EndpointInterface service = client.create(EndpointInterface.class);
        retrofit2.Call<ResponseBody> call = service.syncfastdatav2(Constant.TOKEN, lastUdateTime);
        try {
            publishProgress("Downloading data from server");
            retrofit2.Response<ResponseBody> response = call.execute();
            if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                publishProgress("Data downloaded from server");

                String content = Util.extractZip(response.body());
                JsonElement jelement = new JsonParser().parse(content);
                Type type = new TypeToken<List<UpdateModels>>() {
                }.getType();

                List<UpdateModels> updateModels = new GsonBuilder()
                        .create()
                        .fromJson(jelement.getAsJsonObject().getAsJsonArray("results"), type);
                publishProgress("Saving in local database");
                saveSyncedData(updateModels);
            } else {
                System.out.println("Previous data download failed");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void saveSyncedData(List<UpdateModels> updateModels) {
        if (updateModels.isEmpty()) {
            return;
        }
        UpdateModels model = updateModels.get(0);

        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        for (Patient patient : model.getPatientList()) {
            Patient pLocal = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (pLocal == null) {
                patientDao.insert(new Patient(null, patient.getPatientid(), MSHTBApplication.getInstance().getSettings(DBColumnKeys.SCREENER_ID)/*String.valueOf(Constant.SCREENER_ID)*/,
                        patient.getPresumtivetb(), patient.getTb(), patient.getSensivity(), patient.getSiteofdisease(),
                        patient.getName(), patient.getAge(), patient.getGender(), patient.getContantno(), patient.getCreatedtime(),
                        true, false, patient.getLongitude(), patient.getLatitude(), patient.getAddress(), patient.getIndexpatient()));
            } else {
                pLocal.setTb(patient.getTb());
                pLocal.setContantno(patient.getContantno());
                pLocal.setAddress(patient.getAddress());
                pLocal.setName(patient.getName());
                patientDao.update(pLocal);
            }
        }

        ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();
        for (Screening screening : model.getScreeningforms()) {
            Screening sLocal = screeningDao.queryBuilder().where(ScreeningDao.Properties.Patientid.eq(screening.getPatientid())).unique();
            if (sLocal == null) {
                screeningDao.insert(new Screening(null, screening.getPatientid(), screening.getQ3(),
                        screening.getQ3a(), screening.getQ3b(),
                        screening.getQ4(), screening.getQ5(),
                        screening.getQ6(), screening.getQ7(),
                        screening.getQ7a(), screening.getQ8(),
                        screening.getQ8a(), screening.getQ9(),
                        screening.getQ9a(), screening.getQ9aothers(), screening.getQ10(),
                        screening.getQ11(), -1, -1, screening.getCreatedtime(),
                        true, screening.getLongitude(), screening.getLatitude()));
            }
        }

        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        for (TestIndication testIndication : model.getTestindications()) {
            TestIndication localObj = testIndicationDao.queryBuilder().where(TestIndicationDao.Properties.Patientid.eq(testIndication.getPatientid())).unique();
            if (localObj == null) {
                testIndicationDao.save(new TestIndication(null, testIndication.getPatientid(), testIndication.getXray(),
                        testIndication.getXpert(), testIndication.getSmear(), testIndication.getUltrasound(),
                        testIndication.getHistopathology(), testIndication.getCtmri(), testIndication.getHistopathology_sample(),
                        testIndication.getOther(), testIndication.getCreatedtime(), true, testIndication.getLongitude(), testIndication.getLatitude()));
            } else {
                localObj.setXray(testIndication.getXray());
                localObj.setXpert(testIndication.getXpert());
                localObj.setSmear(testIndication.getSmear());
                localObj.setCtmri(testIndication.getCtmri());
                localObj.setHistopathology(testIndication.getHistopathology());
                testIndicationDao.update(localObj);
            }
        }

        TestResultXRayDao testResultXRayDao = DatabaseManager.getInstance().getSession().getTestResultXRayDao();
        for (TestResultXRay testResultXRay : model.getTestresultxrays()) {
            TestResultXRay localObj = testResultXRayDao.queryBuilder().where(TestResultXRayDao.Properties.Patientid.eq(testResultXRay.getPatientid())).unique();
            if (localObj == null) {
                testResultXRayDao.save(new TestResultXRay(null, testResultXRay.getPatientid(), testResultXRay.getOrderdate(),
                        testResultXRay.getChesrxray(), testResultXRay.getResultdate(), testResultXRay.getRadiologica_finding(),
                        testResultXRay.getRadiologica_diagnosis(), testResultXRay.getExtent_disease(),
                        testResultXRay.getCreatedtime(), true, testResultXRay.getLongitude(), testResultXRay.getLatitude()));
            }
        }

        TestResultXPertDao testResultXPertDao = DatabaseManager.getInstance().getSession().getTestResultXPertDao();
        for (TestResultXPert testResultXPert : model.getTexTestresultxperts()) {
            TestResultXPert localObj = testResultXPertDao.queryBuilder().where(TestResultXPertDao.Properties.Patientid.eq(testResultXPert.getPatientid())).unique();
            if (localObj == null) {
                testResultXPertDao.save(new TestResultXPert(null, testResultXPert.getPatientid(), testResultXPert.getOrderdate(),
                        testResultXPert.getSpecimen_type(), testResultXPert.getResultdate(), testResultXPert.getGenexpert_result(),
                        testResultXPert.getRif_result(), testResultXPert.getCreatedtime(), true, testResultXPert.getLongitude(), testResultXPert.getLatitude()));
            }
        }

        TestResultSmearDao testResultSmearDao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
        for (TestResultSmear testResultSmear : model.getTestresultsmears()) {
            TestResultSmear localObj = testResultSmearDao.queryBuilder().where(TestResultSmearDao.Properties.Patientid.eq(testResultSmear.getPatientid())).unique();
            if (localObj == null) {
                testResultSmearDao.save(new TestResultSmear(null, testResultSmear.getPatientid(), testResultSmear.getOrderdate(),
                        testResultSmear.getMonthof_treatment(), testResultSmear.getResultdate(), testResultSmear.getResult(),
                        testResultSmear.getCreatedtime(), true, testResultSmear.getLongitude(), testResultSmear.getLatitude()));
            }
        }

        TestResultHistopathologyDao testResultHistopathologyDao = DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
        for (TestResultHistopathology testResultHistopathology : model.getTestresulthistos()) {
            TestResultHistopathology localObj = testResultHistopathologyDao.queryBuilder().where(TestResultHistopathologyDao.Properties.Patientid.eq(testResultHistopathology.getPatientid())).unique();
            if (localObj == null) {
                testResultHistopathologyDao.save(new TestResultHistopathology(null, testResultHistopathology.getPatientid(),
                        testResultHistopathology.getOrderdate(), testResultHistopathology.getHistopathology(), testResultHistopathology.getHistopathology_site(),
                        testResultHistopathology.getResultdate(), testResultHistopathology.getResult(),
                        testResultHistopathology.getCreatedtime(), true, testResultHistopathology.getLongitude(), testResultHistopathology.getLatitude()));
            }
        }

        TreatmentDao treatmentDao = DatabaseManager.getInstance().getSession().getTreatmentDao();
        for (Treatment treatment : model.getTreatmentintiations()) {
            Treatment localObj = treatmentDao.queryBuilder().where(TreatmentDao.Properties.Patientid.eq(treatment.getPatientid())).unique();
            if (localObj == null) {
                treatmentDao.save(new Treatment(null, treatment.getPatientid(), treatment.getRegistrationdate(),
                        treatment.getCategory(), treatment.getReferred(), treatment.getTreatmentcenter(),
                        treatment.getRegistrationno(), treatment.getPatienttype(), treatment.getTreatmentstartdate(),
                        treatment.getCreatedtime(), true, treatment.getLongitude(), treatment.getLatitude()));
            }
        }

        OutcomeDao outcomeDao = DatabaseManager.getInstance().getSession().getOutcomeDao();
        for (Outcome outcome : model.getTreatmentoutcomes()) {
            Outcome localObj = outcomeDao.queryBuilder().where(OutcomeDao.Properties.Patientid.eq(outcome.getPatientid())).unique();
            if (localObj == null) {
                outcomeDao.save(new Outcome(null, outcome.getPatientid(), outcome.getTreatmentstartdate(),
                        outcome.getCategory(), outcome.getSiteofdisease(), outcome.getPatienttype(),
                        outcome.getTreatmentoutcome(), outcome.getTreatmentoutcomedate(),
                        outcome.getCreatedtime(), true, outcome.getLongitude(), outcome.getLatitude()));
            }
        }

        updateLastUpdateTime(model.getLastupdatetimestring());
    }

    private void updateLastUpdateTime(String value) {
        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                .getSettingsDao();
        Settings settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LAST_UPDATE_TIME))
                .unique();
        if (settings != null) {
            settings.setValue(value);
            settingsDao.update(settings);
        }
    }

    private void uploadPatient() {
        final PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .whereOr(PatientDao.Properties.Uploaded.eq(false),
                        PatientDao.Properties.Dirty.eq(true))
                .list();

        System.out.println("Total patient data to upload: " + patientList.size());
        if (patientList.isEmpty())
            return;

        for (final Patient patient : patientList) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadpatient(Constant.TOKEN, patient);
            publishProgress("Uploading patient data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    patient.setUploaded(true);
                    patient.setDirty(false);
                    patientDao.update(patient);
                } else if (!response.isSuccessful() && response.code() == HttpURLConnection.HTTP_NOT_ACCEPTABLE) { // Duplicate
                    patient.setUploaded(true);
                    patient.setDirty(false);
                    patientDao.update(patient);
                } else {
                    System.out.println("Exception " + response.message());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadScreening() {
        final ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();
        List<Screening> list = screeningDao.queryBuilder()
                .where(ScreeningDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

//        //UIUtil.showSweetProgress(context, "Uploading screening data...", false, null);
        for (final Screening screening : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadscreening(Constant.TOKEN, screening);
            publishProgress("Uploading screening data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    screening.setUploaded(true);
                    screeningDao.update(screening);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTestIndication() {
        final TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        List<TestIndication> list = testIndicationDao.queryBuilder()
                .where(TestIndicationDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final TestIndication testIndication : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadtestindication(Constant.TOKEN, testIndication);
            publishProgress("Uploading test indication data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    testIndication.setUploaded(true);
                    testIndicationDao.update(testIndication);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTestResultXray() {
        final TestResultXRayDao dao = DatabaseManager.getInstance().getSession().getTestResultXRayDao();
        List<TestResultXRay> list = dao.queryBuilder()
                .where(TestResultXRayDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final TestResultXRay obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadxrayresult(Constant.TOKEN, obj);
            publishProgress("Uploading XRay data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTestResultXpert() {
        final TestResultXPertDao dao = DatabaseManager.getInstance().getSession().getTestResultXPertDao();
        List<TestResultXPert> list = dao.queryBuilder()
                .where(TestResultXPertDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final TestResultXPert obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadxpertresult(Constant.TOKEN, obj);
            publishProgress("Uploading XPert data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTestResultSmear() {
        final TestResultSmearDao dao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
        List<TestResultSmear> list = dao.queryBuilder()
                .where(TestResultSmearDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final TestResultSmear obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadsmearresult(Constant.TOKEN, obj);
            publishProgress("Uploading smear data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTestResultHisto() {
        final TestResultHistopathologyDao dao = DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
        List<TestResultHistopathology> list = dao.queryBuilder()
                .where(TestResultHistopathologyDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final TestResultHistopathology obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadhistoresult(Constant.TOKEN, obj);
            publishProgress("Uploading histopathology data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTreatmentInitiation() {
        final TreatmentDao dao = DatabaseManager.getInstance().getSession().getTreatmentDao();
        List<Treatment> list = dao.queryBuilder()
                .where(TreatmentDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final Treatment obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadtreatmentinitiation(Constant.TOKEN, obj);
            publishProgress("Uploading treatment initiation data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void uploadTreatmentOutcome() {
        final OutcomeDao dao = DatabaseManager.getInstance().getSession().getOutcomeDao();
        List<Outcome> list = dao.queryBuilder()
                .where(OutcomeDao.Properties.Uploaded.eq(false))
                .list();
        if (list.isEmpty())
            return;

        for (final Outcome obj : list) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Paths.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            EndpointInterface service = client.create(EndpointInterface.class);
            retrofit2.Call<CommonResponse> call = service.uploadtreatmentoutcome(Constant.TOKEN, obj);
            publishProgress("Uploading treatment outcome data");
            try {
                retrofit2.Response<CommonResponse> response = call.execute();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    obj.setUploaded(true);
                    dao.update(obj);
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        sync();
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (syncMessageUpdateListener != null) {
            syncMessageUpdateListener.messageUpdated(values[0]);
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (syncMessageUpdateListener != null) {
            syncMessageUpdateListener.messageUpdated("Sync completed");
        }
        if (syncMessageUpdateListener != null) {
            syncMessageUpdateListener.syncCompleted();
        }
    }
}
