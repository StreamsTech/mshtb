//package com.streamstech.droid.mshtb.io;
//
//import android.os.Handler;
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//import java.io.File;
//import java.io.StringReader;
//import java.lang.reflect.Type;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import com.streamstech.droid.mshtb.data.ServerResponse;
//import com.streamstech.droid.mshtb.util.FileUtils;
//
///**
// * Created by User on 2/28/2016.
// */
//public class BackgroundUploader {
//
//    private final long mInterval = 2 * 60 * 1000;
//    private final long mUpdateInterval = 5 * 60 * 1000;
//    private Handler handler;
//    private Handler dataUpdateHandler;
//    private static BackgroundUploader instance;
//    boolean running = false;
//
//    public static BackgroundUploader getInstance() {
//        if (instance == null) {
//            instance = new BackgroundUploader();
//            instance.handler = new Handler();
//            instance.dataUpdateHandler = new Handler();
//            instance.pendingFeedbackChecker.run();
//            instance.backgroundDataUpdater.run();
//        }
//        return instance;
//    }
//
//    private BackgroundUploader() {
//        instance = this;
//    }
//
//    public void destroy() {
//        handler.removeCallbacks(pendingFeedbackChecker);
//        dataUpdateHandler.removeCallbacks(backgroundDataUpdater);
//
//        pendingFeedbackChecker = null;
//        backgroundDataUpdater = null;
//        handler = null;
//        dataUpdateHandler = null;
//        instance = null;
//    }
//
//    Runnable pendingFeedbackChecker = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                if (running)
//                    return;
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        checkandUpload();
//                        //checkandUploadTask();
//                    }
//                }).start();
//
//            } finally {
//                handler.postDelayed(pendingFeedbackChecker, mInterval);
//            }
//        }
//    };
//
//    Runnable backgroundDataUpdater = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        BackgroundDownloader.getInstance().updateProduct(true);
////                        BackgroundDownloader.getInstance().updateTasks(false);
//                    }
//                }).start();
//
//            } finally {
//                dataUpdateHandler.postDelayed(backgroundDataUpdater, mUpdateInterval);
//            }
//        }
//    };
//
//    private void checkandUpload() {
//        running = true;
//
//        final FeedbackDao dao = DatabaseManager.getInstance().getSession().getFeedbackDao();
//        List<Feedback> feedback = dao.queryBuilder()
//                .orderDesc(FeedbackDao.Properties.Id)
//                .where(FeedbackDao.Properties.Uploaded.eq(0))
//                .list();
//
//        for (final Feedback f : feedback) {
//
//            // Create a DateFormatter object for displaying date in specified format.
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            // Create a calendar object that will convert the date and time value in milliseconds to date.
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(f.getCreatedtime());
//            String dt = formatter.format(calendar.getTime());
//
//            System.out.println("Uploading feedback: " + f.getId());
//            MultipartBody.Builder buildernew = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("sessionid", DatabaseManager.getInstance().getServerSession())
////                    .addFormDataPart("productid", String.valueOf(f.getProductid()))
//                    .addFormDataPart("feedback_timestamp", dt)
//                    .addFormDataPart("description", f.getDescription());
//
//            List<String> lstFiles = new ArrayList<String>();
//            // All all those 5 images
//            if (!f.getImageurlone().trim().equalsIgnoreCase("")) {
//                File file = new File(f.getImageurlone());
//                if (file.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlone())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[0]", file.getName(), RequestBody.create(MEDIA_TYPE, file));
//                    lstFiles.add(f.getImageurlone());
//                }
//            }
//            if (!f.getImageurltwo().trim().equalsIgnoreCase("")) {
//                File file2 = new File(f.getImageurltwo());
//                if (file2.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurltwo())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[1]", file2.getName(), RequestBody.create(MEDIA_TYPE, file2));
//                    //lstFiles.add(file2);
//                    lstFiles.add(f.getImageurltwo());
//                }
//            }
//            // Maximum 2 images
//            if (!f.getImageurlthree().trim().equalsIgnoreCase("")) {
//                File file3 = new File(f.getImageurlthree());
//                if (file3.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlthree())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[2]", file3.getName(), RequestBody.create(MEDIA_TYPE, file3));
//                    //lstFiles.add(file3);
//                    lstFiles.add(f.getImageurlthree());
//                }
//            }
//            if (!f.getImageurlfour().trim().equalsIgnoreCase("")) {
//                File file4 = new File(f.getImageurlfour());
//                if (file4.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfour())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[3]", file4.getName(), RequestBody.create(MEDIA_TYPE, file4));
//                    //lstFiles.add(file4);
//                    lstFiles.add(f.getImageurlfour());
//                }
//            }
//            if (!f.getImageurlfive().trim().equalsIgnoreCase("")) {
//                File file5 = new File(f.getImageurlfive());
//                if (file5.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfive())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[4]", file5.getName(), RequestBody.create(MEDIA_TYPE, file5));
//                    //lstFiles.add(file5);
//                    lstFiles.add(f.getImageurlfive());
//                }
//            }
//
////            File files[] = new File[lstFiles.size()];
////            for (int j = 0; j < lstFiles.size(); j++)
////            {
////                files[j] = new File(lstFiles.get(j));
////            }
//
//
//            //RequestBody requestBody =
//            //        RequestBody.create(MediaType.parse("multipart/form-data"), lstFiles);
//
//            //buildernew.
//            //buildernew.addPart(new Re).addFormDataPart("files", lstFiles);
//
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(90000, TimeUnit.SECONDS)
//                    .readTimeout(90000, TimeUnit.SECONDS)
//                    .build();
//
////            client.interceptors().add(new Interceptor() {
////                @Override
////                public Response intercept(Chain chain) throws IOException {
////                    Request request = chain.request();
////
////                    long t1 = System.nanoTime();
////                    Log.d("OkHttp", String.format("Sending request %s on %s%n%s",
////                            request.url(), chain.connection(), request.headers()));
////
////                    Response response = chain.proceed(request);
////
////                    long t2 = System.nanoTime();
////                    Log.d("OkHttp", String.format("Received response for %s in %.1fms%n%s",
////                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
////
////                    return response;
////                }
////            });
////
//            RequestBody requestBody = buildernew.build();
//            Request request = new Request.Builder()
//                    .url(Paths.SERVER_URL + Paths.PRODUCT_FEEDBACK)
//                    .post(requestBody).build();
//
//            try {
////                AsyncHttpClient client = new AsyncHttpClient();
////                client.setLoggingEnabled(true);
////                RequestParams params = new RequestParams();
////                params.put("sessionid", DatabaseManager.getInstance().getServerSession());
////                params.put("productid", String.valueOf(f.getProductid()));
////                params.put("description", f.getDescription());
////                params.put("lati", -999);
////                params.put("longi", -999);
////                params.put("files", files);
//
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("Feedback Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//                            f.setUploaded(1L);
//                            dao.update(f);
//                        }
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                } else {
//                    System.out.println(response.message());
//                }
//
////                client.post(Paths.SERVER_URL + Paths.PRODUCT_FEEDBACK, params, new AsyncHttpResponseHandler() {
////                    @Override
////                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
////                        System.out.println("Response: " + new String(responseBody));
////                        //f.setUploaded(1L);
////                        dao.deleteByKey(f.getId());
////                    }
////
////                    @Override
////                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
////                        System.out.println("Response: " + new String(responseBody));
////                    }
////                });
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        }
//        running = false;
//    }
//
//    private void checkandUploadTask() {
//        running = true;
//
//        final TaskFeedbackDao dao = DatabaseManager.getInstance().getSession().getTaskFeedbackDao();
//        List<TaskFeedback> feedback = dao.queryBuilder()
//                .orderDesc(TaskFeedbackDao.Properties.Id)
//                .where(TaskFeedbackDao.Properties.Status.eq("PENDING"))
//                .list();
//
//        for (final TaskFeedback f : feedback) {
//
//            // Create a DateFormatter object for displaying date in specified format.
//            System.out.println("Uploading task feedback id: " + f.getId());
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            // Create a calendar object that will convert the date and time value in milliseconds to date.
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(f.getCreatedtime());
//            String dt = formatter.format(calendar.getTime());
//
//            System.out.println("Uploading Task feedback: " + f.getId());
//            MultipartBody.Builder buildernew = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("sessionid", DatabaseManager.getInstance().getServerSession())
//                    .addFormDataPart("taskid", String.valueOf(f.getTaskid()))
//                    .addFormDataPart("feedback_timestamp", dt)
//                    .addFormDataPart("lati", String.valueOf(f.getLatitude()))
//                    .addFormDataPart("longi", String.valueOf(f.getLongitude()))
//                    .addFormDataPart("description", f.getDescription());
//
//            List<String> lstFiles = new ArrayList<String>();
//            // All all those 5 images
//            if (!f.getImageurlone().trim().equalsIgnoreCase("")) {
//                File file = new File(f.getImageurlone());
//                if (file.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlone())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[0]", file.getName(), RequestBody.create(MEDIA_TYPE, file));
//                    lstFiles.add(f.getImageurlone());
//                }
//            }
//            if (!f.getImageurltwo().trim().equalsIgnoreCase("")) {
//                File file2 = new File(f.getImageurltwo());
//                if (file2.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurltwo())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[1]", file2.getName(), RequestBody.create(MEDIA_TYPE, file2));
//                    //lstFiles.add(file2);
//                    lstFiles.add(f.getImageurltwo());
//                }
//            }
//            // Maximum 2 images
//            if (!f.getImageurlthree().trim().equalsIgnoreCase("")) {
//                File file3 = new File(f.getImageurlthree());
//                if (file3.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlthree())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[2]", file3.getName(), RequestBody.create(MEDIA_TYPE, file3));
//                    //lstFiles.add(file3);
//                    lstFiles.add(f.getImageurlthree());
//                }
//            }
//            if (!f.getImageurlfour().trim().equalsIgnoreCase("")) {
//                File file4 = new File(f.getImageurlfour());
//                if (file4.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfour())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[3]", file4.getName(), RequestBody.create(MEDIA_TYPE, file4));
//                    //lstFiles.add(file4);
//                    lstFiles.add(f.getImageurlfour());
//                }
//            }
//            if (!f.getImageurlfive().trim().equalsIgnoreCase("")) {
//                File file5 = new File(f.getImageurlfive());
//                if (file5.exists()) {
//                    final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfive())
//                            .endsWith("png") ? "image/png" : "image/jpeg");
//                    buildernew.addFormDataPart("files[4]", file5.getName(), RequestBody.create(MEDIA_TYPE, file5));
//                    //lstFiles.add(file5);
//                    lstFiles.add(f.getImageurlfive());
//                }
//            }
//
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(90000, TimeUnit.SECONDS)
//                    .readTimeout(90000, TimeUnit.SECONDS)
//                    .build();
//
//            RequestBody requestBody = buildernew.build();
//            Request request = new Request.Builder()
//                    .url(Paths.SERVER_URL + Paths.TASK_FEEDBACK)
//                    .post(requestBody).build();
//
//            try {
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("Task Feedback Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
////                            DatabaseManager.getInstance().getSession().getTaskDao().delete(new Task(f.getTaskid()));
//                            DatabaseManager.getInstance().getSession().getTaskFeedbackDao().delete(f);
//                        } else {
//                            if (serverResponse.getMessage().equalsIgnoreCase("not allowed"))
//                                dao.delete(f);
//                            else {
//                                f.setStatus("PENDING");
//                                DatabaseManager.getInstance().getSession().getTaskFeedbackDao().update(f);
//                            }
//                        }
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                } else {
//                    System.out.println(response.message());
//                }
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        }
//        running = false;
//    }
//
//    public void uploadTask(TaskFeedback f) {
//
//        System.out.println("Force uploading task feedback id: " + f.getId());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // Create a calendar object that will convert the date and time value in milliseconds to date.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(f.getCreatedtime());
//        String dt = formatter.format(calendar.getTime());
//
//        System.out.println("Force uploading Task feedback: " + f.getId());
//        MultipartBody.Builder buildernew = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("sessionid", DatabaseManager.getInstance().getServerSession())
//                .addFormDataPart("taskid", String.valueOf(f.getTaskid()))
//                .addFormDataPart("feedback_timestamp", dt)
//                .addFormDataPart("lati", String.valueOf(f.getLatitude()))
//                .addFormDataPart("longi", String.valueOf(f.getLongitude()))
//                .addFormDataPart("description", f.getDescription());
//
//        List<String> lstFiles = new ArrayList<String>();
//        // All all those 5 images
//        if (!f.getImageurlone().trim().equalsIgnoreCase("")) {
//            File file = new File(f.getImageurlone());
//            if (file.exists()) {
//                final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlone())
//                        .endsWith("png") ? "image/png" : "image/jpeg");
//                buildernew.addFormDataPart("files[0]", file.getName(), RequestBody.create(MEDIA_TYPE, file));
//                lstFiles.add(f.getImageurlone());
//            }
//        }
//        if (!f.getImageurltwo().trim().equalsIgnoreCase("")) {
//            File file2 = new File(f.getImageurltwo());
//            if (file2.exists()) {
//                final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurltwo())
//                        .endsWith("png") ? "image/png" : "image/jpeg");
//                buildernew.addFormDataPart("files[1]", file2.getName(), RequestBody.create(MEDIA_TYPE, file2));
//                //lstFiles.add(file2);
//                lstFiles.add(f.getImageurltwo());
//            }
//        }
//        // Maximum 2 images
//        if (!f.getImageurlthree().trim().equalsIgnoreCase("")) {
//            File file3 = new File(f.getImageurlthree());
//            if (file3.exists()) {
//                final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlthree())
//                        .endsWith("png") ? "image/png" : "image/jpeg");
//                buildernew.addFormDataPart("files[2]", file3.getName(), RequestBody.create(MEDIA_TYPE, file3));
//                //lstFiles.add(file3);
//                lstFiles.add(f.getImageurlthree());
//            }
//        }
//        if (!f.getImageurlfour().trim().equalsIgnoreCase("")) {
//            File file4 = new File(f.getImageurlfour());
//            if (file4.exists()) {
//                final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfour())
//                        .endsWith("png") ? "image/png" : "image/jpeg");
//                buildernew.addFormDataPart("files[3]", file4.getName(), RequestBody.create(MEDIA_TYPE, file4));
//                //lstFiles.add(file4);
//                lstFiles.add(f.getImageurlfour());
//            }
//        }
//        if (!f.getImageurlfive().trim().equalsIgnoreCase("")) {
//            File file5 = new File(f.getImageurlfive());
//            if (file5.exists()) {
//                final MediaType MEDIA_TYPE = MediaType.parse(FileUtils.getExtension(f.getImageurlfive())
//                        .endsWith("png") ? "image/png" : "image/jpeg");
//                buildernew.addFormDataPart("files[4]", file5.getName(), RequestBody.create(MEDIA_TYPE, file5));
//                //lstFiles.add(file5);
//                lstFiles.add(f.getImageurlfive());
//            }
//        }
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(90000, TimeUnit.SECONDS)
//                .readTimeout(90000, TimeUnit.SECONDS)
//                .build();
//
//        RequestBody requestBody = buildernew.build();
//        Request request = new Request.Builder()
//                .url(Paths.SERVER_URL + Paths.TASK_FEEDBACK)
//                .post(requestBody).build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            if (response.isSuccessful()) {
//                String strResponse = response.body().string();
//                System.out.println("Task Feedback Response" + strResponse);
//                try {
//                    ServerResponse serverResponse = (ServerResponse) new Gson()
//                            .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                    if (serverResponse.isSuccess()) {
////                        DatabaseManager.getInstance().getSession().getTaskDao().delete(new Task(f.getTaskid()));
//                        DatabaseManager.getInstance().getSession().getTaskFeedbackDao().delete(f);
//                    } else {
//                        if (serverResponse.getMessage().equalsIgnoreCase("not allowed")) {
//                            DatabaseManager.getInstance().getSession().getTaskFeedbackDao().delete(f);
//                        } else {
//                            f.setStatus("PENDING");
//                            DatabaseManager.getInstance().getSession().getTaskFeedbackDao().update(f);
//                        }
//                    }
//                } catch (Exception exp) {
//                    exp.printStackTrace();
//                }
//            } else {
//                System.out.println(response.message());
//            }
//        } catch (Exception exp) {
//            exp.printStackTrace();
//        }
//    }
//}
