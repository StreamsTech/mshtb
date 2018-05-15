package com.streamstech.droid.mshtb.io;

import com.streamstech.droid.mshtb.gcm.PushMessage;

public interface BackgroundDataAvailable {
    void updateData(PushMessage pushMessage);
}
