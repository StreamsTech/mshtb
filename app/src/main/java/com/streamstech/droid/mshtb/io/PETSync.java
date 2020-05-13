//package com.streamstech.droid.mshtb.io;
//
//import android.content.Context;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import com.streamstech.droid.mshtb.data.Constant;
//import com.streamstech.droid.mshtb.data.UpdateModels;
//import com.streamstech.droid.mshtb.data.UpdateModelsPET;
//import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
//import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
//import com.streamstech.droid.mshtb.data.persistent.Outcome;
//import com.streamstech.droid.mshtb.data.persistent.OutcomeDao;
//import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
//import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
//import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
//import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowupDao;
//import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
//import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
//import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
//import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReviewDao;
//import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptom;
//import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptomDao;
//import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
//import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
//import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
//import com.streamstech.droid.mshtb.data.persistent.PETFollowupDao;
//import com.streamstech.droid.mshtb.data.persistent.PETFollowupEnd;
//import com.streamstech.droid.mshtb.data.persistent.PETFollowupEndDao;
//import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
//import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
//import com.streamstech.droid.mshtb.data.persistent.PETSymptom;
//import com.streamstech.droid.mshtb.data.persistent.PETSymptomDao;
//import com.streamstech.droid.mshtb.data.persistent.PETTestOrder;
//import com.streamstech.droid.mshtb.data.persistent.PETTestOrderDao;
//import com.streamstech.droid.mshtb.data.persistent.PETTestResult;
//import com.streamstech.droid.mshtb.data.persistent.PETTestResultDao;
//import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
//import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStartDao;
//import com.streamstech.droid.mshtb.data.persistent.Settings;
//import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
//import com.streamstech.droid.mshtb.util.SyncMessageUpdateListener;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by AKASH-LAPTOP on 7/8/2018.
// */
//
//public class PETSync {
//
//    final private Context context;
//    final SyncMessageUpdateListener syncMessageUpdateListener;
//    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .build();
//
//    public PETSync(Context context, SyncMessageUpdateListener syncMessageUpdateListener) {
//        this.context = context;
//        this.syncMessageUpdateListener = syncMessageUpdateListener;
//    }
//
//    public void sync() {
//        upload();
//        download();
//        if (syncMessageUpdateListener != null) {
//            syncMessageUpdateListener.syncCompleted();
//        }
//    }
//
//    private void upload() {
//        uploadRegistration();
//        uploadEnrollment();
//        uploadSymptom();
//        uploadTestOrder();
//        uploadTestResult();
//        uploadEligibility();
//        uploadTreatmentStart();
//        uploadAdherence();
//        uploadFollowup();
//        uploadFollowupClinician();
//        uploadFollowupClinicianTB();
//        uploadFollowupAdverse();
//        uploadFollowupEnd();
//    }
//
//    private void download() {
//
//        String lastUdateTime = null;
//        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
//                .getSettingsDao();
//        Settings settings = settingsDao.queryBuilder()
//                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LAST_UPDATE_TIME))
//                .unique();
//        if (settings == null) {
//            lastUdateTime = "1970-01-01 00:00:00";
//            settingsDao.save(new Settings(null, DBColumnKeys.LAST_UPDATE_TIME, lastUdateTime));
//        } else {
//            lastUdateTime = settings.getValue();
//        }
//        System.out.println("Last Update Time: " + lastUdateTime);
//        System.out.println("Token: " + Constant.TOKEN);
//
//        Retrofit client = new Retrofit.Builder()
//                .baseUrl(Paths.SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        EndpointInterface service = client.create(EndpointInterface.class);
//        retrofit2.Call<CommonResponse> call = service.syncpetdata(Constant.TOKEN, lastUdateTime);
//
//        call.enqueue(new retrofit2.Callback<CommonResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
//
//                System.out.println(response.body().toString());
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    Type type = new TypeToken<List<UpdateModelsPET>>() {
//                    }.getType();
//                    List<UpdateModelsPET> updateModels = new GsonBuilder()
//                            .create()
//                            .fromJson(new Gson()
//                                    .toJson(response.body()
//                                            .getResults()), type);
//                    saveSyncedData(updateModels);
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
//                System.out.println("Error in download");
//            }
//        });
//    }
//
//    private void saveSyncedData(List<UpdateModelsPET> updateModels) {
//        if (updateModels.isEmpty()) {
//            return;
//        }
//        UpdateModelsPET model = updateModels.get(0);
//
//        PETRegistrationDao registrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
//        for (PETRegistration patient : model.getRegistrationList()) {
//            PETRegistration pLocal = registrationDao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(patient.getQr())).unique();
//            if (pLocal == null) {
//                registrationDao.insert(new PETRegistration(null, patient.getLocationid(), patient.getScreenerid(),
//                        patient.getTbtype(),patient.getName(), patient.getAge(), patient.getGender(), patient.getQr(),
//                        patient.getNid(), patient.getDiagnosistype(), patient.getTrnumber(), patient.getTotalhouseholdmember(),
//                        patient.getAddress(), patient.getContantno(), patient.getCreatedtime(),
//                        true, patient.getLongitude(), patient.getLatitude()));
//            } else {
//                pLocal.setTbtype(patient.getTbtype());
//                pLocal.setContantno(patient.getContantno());
//                pLocal.setAddress(patient.getAddress());
//                pLocal.setName(patient.getName());
//                pLocal.setTrnumber((patient.getTrnumber()));
//                registrationDao.update(pLocal);
//            }
//        }
//
//        PETEnrollmentDao enrollmentDao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
//        for (PETEnrollment screening : model.getEnrollmentList()) {
//            PETEnrollment sLocal = enrollmentDao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(screening.getQr())).unique();
//            if (sLocal == null) {
//                enrollmentDao.insert(new PETEnrollment(null, screening.getLocationid(), screening.getScreenerid(),
//                        screening.getName(), screening.getAge(), screening.getNid(), screening.getGender(),
//                        screening.getQr(), screening.getIndexpatientqr(), screening.getWeight(),
//                        screening.getHeight(), screening.getMuac(), screening.getBmi(),
//                        screening.getPregnancy(), screening.getRelation(), screening.getRelationother(),
//                        screening.getCreatedtime(), true, screening.getLongitude(), screening.getLatitude()));
//            }
//        }
//
//        PETSymptomDao symptomDao = DatabaseManager.getInstance().getSession().getPETSymptomDao();
//        for (PETSymptom obj : model.getSymptomList()) {
//            PETSymptom sLocal = symptomDao.queryBuilder().where(PETSymptomDao.Properties.Contactqr.eq(obj.getContactqr())).unique();
//            if (sLocal == null) {
//                symptomDao.insert(new PETSymptom(null, obj.getContactqr(), obj.getScreenerid(),
//                        obj.getQ1(), obj.getQ2(), obj.getQ3(), obj.getQ4(), obj.getQ5(), obj.getQ6(),
//                        obj.getQ7(), obj.getQ8(), obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//            }
//        }
//
//        PETTestOrderDao testOrderDao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
//        for (PETTestOrder obj : model.getTestOrderList()) {
//            PETTestOrder sLocal = testOrderDao.queryBuilder().where(PETTestOrderDao.Properties.Contactqr.eq(obj.getContactqr())).unique();
//            if (sLocal == null) {
//                testOrderDao.insert(new PETTestOrder(null, obj.getContactqr(), obj.getScreenerid(),
//                        obj.getXray(), obj.getSmear(), obj.getXpert(), obj.getTstmt(), obj.getCbcesr(),
//                        obj.getHistopathology(), obj.getHistopathology_sample(), obj.getCtmri(),
//                        obj.getUltrasound(), obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//            }
//        }
//
//        PETTestResultDao testResultDao = DatabaseManager.getInstance().getSession().getPETTestResultDao();
//        for (PETTestResult obj : model.getTestResultList()) {
//            PETTestResult sLocal = testResultDao.queryBuilder().where(PETTestResultDao.Properties.Contactqr.eq(obj.getContactqr())).unique();
//            if (sLocal == null) {
//                testResultDao.insert(new PETTestResult(null, obj.getContactqr(), obj.getScreenerid(),
//                        obj.getXray(), obj.getSmear(), obj.getXpert(), obj.getTstmt(), obj.getCbchb(),
//                        obj.getCbctlc(), obj.getCbcmonocytes(), obj.getCbcplatelets(), obj.getCbcesr(),
//                        obj.getHistopathology(), obj.getHistopathology_sample(), obj.getCtmri(),
//                        obj.getUltrasound(), obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//            }
//        }
//
//        PETEligibilitySymptomDao eligibilitySymptomDao = DatabaseManager.getInstance().getSession().getPETEligibilitySymptomDao();
//        for (PETEligibilitySymptom obj : model.getEligibilitySymptomList()) {
//            PETEligibilitySymptom sLocal = eligibilitySymptomDao.queryBuilder().where(PETEligibilitySymptomDao.Properties.Contactqr.eq(obj.getContactqr())).unique();
//            if (sLocal == null) {
//                eligibilitySymptomDao.insert(new PETEligibilitySymptom(null, obj.getContactqr(), obj.getScreenerid(),
//                        obj.getTbdiagnosed(), obj.getTbdiagnosedhow(), obj.getTbtype2(),
//                        obj.getTbtype3(), obj.getTbtype(), obj.getReferred(), obj.getTbruledout(),
//                        obj.getTbruledouthow(), obj.getPeteligible(), obj.getFacilityname(),
//                        obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//            }
//        }
//
//        PETTreatmentStartDao petTreatmentStartDao = DatabaseManager.getInstance().getSession().getPETTreatmentStartDao();
//        for (PETTreatmentStart obj : model.getTreatmentStartList()) {
//            PETTreatmentStart sLocal = petTreatmentStartDao.queryBuilder().where(PETTreatmentStartDao.Properties.Contactqr.eq(obj.getContactqr())).unique();
//            if (sLocal == null) {
//                petTreatmentStartDao.insert(new PETTreatmentStart(null, obj.getContactqr(), obj.getScreenerid(),
//                        obj.getRegimen(), obj.getDrugone(), obj.getDrugtwo(), obj.getTreatmentstartdate(),
//                        obj.getSupportername(), obj.getSupporternumber(),
//                        obj.getSupporterrelation(), obj.getSupporterother(),
//                        obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//            }
//        }
//
//        PETAdherenceDao petAdherenceDao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
//        for (PETAdherence obj : model.getAdherenceList()) {
//            petAdherenceDao.insert(new PETAdherence(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getMonthoftreatment(), obj.getWeekoftreatment(), obj.getAdrerence(),
//                    obj.getAdverseeffect(), obj.getSeriousadverse(),
//                    obj.getComment(), obj.getDoctornotified(),
//                    obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//        }
//
//        PETFollowupDao petFollowupDao = DatabaseManager.getInstance().getSession().getPETFollowupDao();
//        for (PETFollowup obj : model.getFollowupList()) {
//            petFollowupDao.insert(new PETFollowup(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getMonthoftreatment(), obj.getAnysymptom(), obj.getTbsymptoms(),
//                    obj.getTbsymptomdescription(), obj.getSideeffects(),
//                    obj.getOthersideeffects(), obj.getConsistentcomoplains(), obj.getMisseddoses(),
//                    obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//        }
//
//        PETClinicianFollowupDao petClinicianFollowupDao = DatabaseManager.getInstance().getSession().getPETClinicianFollowupDao();
//        for (PETClinicianFollowup obj : model.getClinicianFollowupList()) {
//            petClinicianFollowupDao.insert(new PETClinicianFollowup(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getMonthoftreatment(), obj.getWeight(), obj.getHeight(),
//                    obj.getMuac(), obj.getBmi(), obj.getAnysymptom(), obj.getTbsymptoms(),
//                    obj.getTbsymptomdescription(), obj.getSideeffects(), obj.getOthersideeffects(),
//                    obj.getConsistentcomoplains(), obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//        }
//
//        PETClinicianTBReviewDao petClinicianTBReviewDao = DatabaseManager.getInstance().getSession().getPETClinicianTBReviewDao();
//        for (PETClinicianTBReview obj : model.getClinicianTBReviewList()) {
//            petClinicianTBReviewDao.insert(new PETClinicianTBReview(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getAdherence(), obj.getLabtests(), obj.getPlans(),
//                    obj.getNewinstructions(), obj.getReturnvisitdate(),
//                    obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//        }
//
//        PETAdverseEventFollowupDao petAdverseEventFollowupDao = DatabaseManager.getInstance().getSession().getPETAdverseEventFollowupDao();
//        for (PETAdverseEventFollowup obj : model.getPetAdverseEventFollowupList()) {
//            petAdverseEventFollowupDao.insert(new PETAdverseEventFollowup(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getMonthoftreatment(), obj.getNonseriousevent(), obj.getNonseriousevents(),
//                    obj.getSeriousevent(), obj.getSeriousevents(), obj.getOthers(),
//                    obj.getConsistentcomoplains(), obj.getSymptomaction(), obj.getComment(),
//                    obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//        }
//
//        PETFollowupEndDao petFollowupEndDao = DatabaseManager.getInstance().getSession().getPETFollowupEndDao();
//        for (PETFollowupEnd obj : model.getFollowupEndList()) {
//            petFollowupEndDao.save(new PETFollowupEnd(null, obj.getContactqr(), obj.getScreenerid(),
//                    obj.getMonthoftreatment(), obj.getReasontoend(), obj.getEnddescription(),
//                    obj.getComment(), obj.getCreatedtime(), true, obj.getLongitude(), obj.getLatitude()));
//
//        }
//        updateLastUpdateTime(model.getLastupdatetimestring());
//    }
//
//    private void updateLastUpdateTime(String value) {
//        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
//                .getSettingsDao();
//        Settings settings = settingsDao.queryBuilder()
//                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LAST_UPDATE_TIME))
//                .unique();
//        if (settings != null) {
//            settings.setValue(value);
//            settingsDao.update(settings);
//        }
//    }
//
//    private void uploadRegistration() {
//        final PETRegistrationDao dao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
//        List<PETRegistration> list = dao.queryBuilder()
//                .where(PETRegistrationDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETRegistration obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadregistration(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading registration data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadEnrollment() {
//        final PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
//        List<PETEnrollment> list = dao.queryBuilder()
//                .where(PETEnrollmentDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETEnrollment obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadenrollment(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading enrollment data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadSymptom() {
//        final PETSymptomDao dao = DatabaseManager.getInstance().getSession().getPETSymptomDao();
//        List<PETSymptom> list = dao.queryBuilder()
//                .where(PETSymptomDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETSymptom obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadsymptom(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading symptom data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
//
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadTestOrder() {
//        final PETTestOrderDao dao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
//        List<PETTestOrder> list = dao.queryBuilder()
//                .where(PETTestOrderDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETTestOrder obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadtestorder(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading test order data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadTestResult() {
//        final PETTestResultDao dao = DatabaseManager.getInstance().getSession().getPETTestResultDao();
//        List<PETTestResult> list = dao.queryBuilder()
//                .where(PETTestResultDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETTestResult obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadtestresult(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading test result data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadEligibility() {
//        final PETEligibilitySymptomDao dao = DatabaseManager.getInstance().getSession().getPETEligibilitySymptomDao();
//        List<PETEligibilitySymptom> list = dao.queryBuilder()
//                .where(PETEligibilitySymptomDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETEligibilitySymptom obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadeligibility(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading eligibility data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadTreatmentStart() {
//        final PETTreatmentStartDao dao = DatabaseManager.getInstance().getSession().getPETTreatmentStartDao();
//        List<PETTreatmentStart> list = dao.queryBuilder()
//                .where(PETTreatmentStartDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETTreatmentStart obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadtreatmentstart(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading treatment data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadAdherence() {
//        final PETAdherenceDao dao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
//        List<PETAdherence> list = dao.queryBuilder()
//                .where(PETAdherenceDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETAdherence obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadadherence(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading adherence data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadFollowup() {
//        final PETFollowupDao dao = DatabaseManager.getInstance().getSession().getPETFollowupDao();
//        List<PETFollowup> list = dao.queryBuilder()
//                .where(PETFollowupDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETFollowup obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadfollowup(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading followup data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadFollowupClinician() {
//        final PETClinicianFollowupDao dao = DatabaseManager.getInstance().getSession().getPETClinicianFollowupDao();
//        List<PETClinicianFollowup> list = dao.queryBuilder()
//                .where(PETClinicianFollowupDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETClinicianFollowup obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadfollowupclinician(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading clinician followup data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadFollowupClinicianTB() {
//        final PETClinicianTBReviewDao dao = DatabaseManager.getInstance().getSession().getPETClinicianTBReviewDao();
//        List<PETClinicianTBReview> list = dao.queryBuilder()
//                .where(PETClinicianTBReviewDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETClinicianTBReview obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadfollowupcliniciantb(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading TB followup data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadFollowupAdverse() {
//        final PETAdverseEventFollowupDao dao = DatabaseManager.getInstance().getSession().getPETAdverseEventFollowupDao();
//        List<PETAdverseEventFollowup> list = dao.queryBuilder()
//                .where(PETAdverseEventFollowupDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETAdverseEventFollowup obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadadverse(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading adverse data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            }
//            catch (Exception exp) {
//                exp.printStackTrace();
//            }
////            call.enqueue(new retrofit2.Callback<CommonResponse>() {
////                @Override
////                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
////                    if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
////                        obj.setUploaded(true);
////                        dao.update(obj);
////                    }
////                }
////                @Override
////                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
////                }
////            });
//        }
//    }
//
//    private void uploadFollowupEnd() {
//        final PETFollowupEndDao dao = DatabaseManager.getInstance().getSession().getPETFollowupEndDao();
//        List<PETFollowupEnd> list = dao.queryBuilder()
//                .where(PETFollowupEndDao.Properties.Uploaded.eq(false))
//                .list();
//        if (list.isEmpty())
//            return;
//
//        for (final PETFollowupEnd obj : list) {
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.uploadfollowupend(Constant.TOKEN, obj);
//            syncMessageUpdateListener.messageUpdated("Uploading followup end data");
//            try {
//                retrofit2.Response<CommonResponse> response = call.execute();
//                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
//                    obj.setUploaded(true);
//                    dao.update(obj);
//                }
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        }
//    }
//}
