package com.winit.haldiram;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.winit.common.util.LogUtils;

/**
 * Created by Srikanth on 18-05-2017.
 */
public class GCMInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "GCMInstanceIDLS";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        LogUtils.debug(TAG,"onTokenRefresh - Strated");
        Intent intent = new Intent(this, RegisterGCMIntentService.class);
        startService(intent);
        LogUtils.debug(TAG, "onTokenRefresh - Ended");
    }
}
