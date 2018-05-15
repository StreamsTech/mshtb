package com.streamstech.droid.mshtb.gcm;

public class GCMSettings {
    public static int BADGE_NUMBER = 0;
    public static final String GCM_SENDER_ID = "640754305730";
    public static final String GCM_TO_ALERT = "GCM_TO_ALERT";
    public static final String NOTIFICATION_PAYLOAD = "productqr";
    public static String TOKEN = null;
    public static final int WAKE_UP_TIME_MILIS = 5000;

    static {
        BADGE_NUMBER = 0;
        TOKEN = "";
    }
}
