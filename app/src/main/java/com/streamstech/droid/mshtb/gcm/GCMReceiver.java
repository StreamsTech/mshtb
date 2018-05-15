//package com.streamstech.droid.mshtb.gcm;
//
//import android.app.ActivityManager;
//import android.app.ActivityManager.RunningTaskInfo;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.os.Build;
//import android.os.Build.VERSION;
//import android.os.PowerManager;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.ContextCompat;
//import android.telephony.TelephonyManager;
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.lang.reflect.Type;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.OkHttpClient.Builder;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
////import com.streamstech.droid.mshtb.activity.MainActivity;
//import com.streamstech.droid.mshtb.R;
//import com.streamstech.droid.mshtb.data.ServerResponse;
////import com.streamstech.droid.mshtb.data.persistent.ProductDao;
////import com.streamstech.droid.mshtb.io.BackgroundDownloader;
//import com.streamstech.droid.mshtb.io.Paths;
//
//public class GCMReceiver extends BroadcastReceiver {
//    private Context context;
//    private String registrationId;
//
//    public void onReceive(Context context, Intent intent) {
//        this.context = context;
//        String actionString = intent.getAction();
//        if (actionString.equals("com.google.android.c2dm.intent.REGISTRATION")) {
//            this.registrationId = intent.getStringExtra("registration_id");
//            if (this.registrationId != null) {
//                System.out.println("TOKEN: " + this.registrationId);
//                registerDeviceToken(this.registrationId);
//            }
//        } else if (actionString.equals("com.google.android.c2dm.intent.RECEIVE")) {
//            String ff = intent.getStringExtra(GCMSettings.NOTIFICATION_PAYLOAD);
//            System.out.println("Push Received: " + ff);
//            PushMessage message = new PushMessage();
//            message.parse(ff);
//            if (message.isSuccess())
//            {
//                showNotification(message);
//                if (message.getOperation().equalsIgnoreCase(PushMessage.OperationType.ADD.name()))
//                {
////                    BackgroundDownloader.getInstance().getSingleProduct(message.getId());
//                }
//                else if (message.getOperation().equalsIgnoreCase(PushMessage.OperationType.UPDATE.name()))
//                {
////                    BackgroundDownloader.getInstance().getSingleProduct(message.getId());
//                }
//                else if (message.getOperation().equalsIgnoreCase(PushMessage.OperationType.DELETE.name()))
//                {
////                    ProductDao dao = DatabaseManager.getInstance(context).getSession()
////                            .getProductDao();
////                    dao.deleteByKey(message.getId());
////                    BackgroundDownloader.getInstance().notifyUpdate();
//                }
//                else if (message.getOperation().equalsIgnoreCase(PushMessage.OperationType.RELOAD.name()))
//                {
////                    BackgroundDownloader.getInstance().updateProduct(true);
//                }
//                //BackgroundDownloader.getInstance().updateProduct(false);
//            }
//            //showNotification(message);
//        }
//    }
//
//    private void registerDeviceToken(String token) {
//        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
//        String jsonURL = Paths.SERVER_URL + Paths.DEVICE_REGISTER;
//        String release = VERSION.RELEASE;
//        int sdkVersion = VERSION.SDK_INT;
//        OkHttpClient client = new Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//
//        String IMEI = "NOT_PERMITTED";
//        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
//            IMEI = telephonyManager.getDeviceId();
//
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("sessionid", DatabaseManager.getInstance().getServerSession())
//                .add("identifier", token)
//                .add("brandname", Build.MANUFACTURER)
//                .add("modelname", Build.MODEL)
//                .add("deviceos", sdkVersion + " (" + release + ")")
//                .add("devicefirmware", VERSION.RELEASE)
//                .add("platform", "android")
//                .add("imei", IMEI)
//                .build();
//        System.out.println("Device Registration Request: " + formBody.toString());
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("Device Registration Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public static String getLauncherClassName(Context context) {
//        PackageManager pm = context.getPackageManager();
//        Intent intent = new Intent("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//        for (ResolveInfo resolveInfo : pm.queryIntentActivities(intent, 0)) {
//            if (resolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
//                return resolveInfo.activityInfo.name;
//            }
//        }
//        return null;
//    }
//
//    private boolean isApplicationBroughtToBackground() {
//        List<RunningTaskInfo> tasks = ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
//        if (tasks.isEmpty() || ((RunningTaskInfo) tasks.get(0)).topActivity.getPackageName().equals(this.context.getPackageName())) {
//            return false;
//        }
//        return true;
//    }
//
//    private String getTopActivityName() {
//        List<RunningTaskInfo> tasks = ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
//        if (tasks.isEmpty()) {
//            return null;
//        }
//        return ((RunningTaskInfo) tasks.get(0)).topActivity.getShortClassName().toString();
//    }
//
//
//    private void showNotification(PushMessage msg) {
//        Intent intent = new Intent(this.context, NewMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        intent.putExtra(GCMSettings.GCM_TO_ALERT, true);
//        PendingIntent pIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        if (VERSION.SDK_INT >= 16) {
//            Notification notification = new Notification.Builder(this.context)
//                    .setContentTitle("SILTROCK QR")
//                    .setContentText(msg.getDisplayMessage())
//                    .setSmallIcon(R.drawable.qr)
//                    .setNumber(GCMSettings.BADGE_NUMBER++)
//                    .setContentIntent(pIntent)
//                    .build();
//
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            notification.defaults = Notification.DEFAULT_ALL;;
//            notification.flags |= Notification.FLAG_AUTO_CANCEL;
//            notificationManager.notify(0, notification);
//
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
//            wl.acquire(GCMSettings.WAKE_UP_TIME_MILIS);
//        }
//        else
//        {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context)
//                    .setContentTitle("SILTROCK QR")
//                    .setContentText(msg.getDisplayMessage())
//                    .setSmallIcon(R.drawable.qr)
//                    .setContentIntent(pIntent)
//                    .setNumber(GCMSettings.BADGE_NUMBER++)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(true);
//
//            NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, builder.build());
//
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
//            wl.acquire(GCMSettings.WAKE_UP_TIME_MILIS);
//        }
//    }
//}
