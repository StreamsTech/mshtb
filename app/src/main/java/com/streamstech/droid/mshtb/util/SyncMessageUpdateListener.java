package com.streamstech.droid.mshtb.util;

/**
 * Created by AKASH-LAPTOP on 9/17/2018.
 */

public interface SyncMessageUpdateListener {
    void messageUpdated(String message);
    void syncCompleted();
}
