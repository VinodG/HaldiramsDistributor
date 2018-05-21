package com.winit.haldiram;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.winit.common.Preference;
import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.RestCilent;
import com.winit.common.webAccessLayer.ServiceUrls;

import java.io.IOException;

/**
 * Created by Srikanth on 18-05-2017.
 */
public class RegisterGCMIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegisterGCMIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = "";
        int i=0;
        while (true){
            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken(getString(R.string.gcm_defaultSenderIdNew), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//                token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(i == 3){
                    break;
                }
                if(!TextUtils.isEmpty(token)){//fLDCs-8BvFs:APA91bF-AtTkEfGB36PCSbGB2XSzh_5cfYdWUqrGB3lNRoIu4MKM2P-W5fBC-Tvvs1-39KEdYKAgdT0p8TszUATAa6cdKzuWsYlkR6nyTgBx56mTxsLUdG8NJr1q05MxxhX8NtEiqVxS
                    Preference preference = Preference.getInstance();
                    preference.saveStringInPreference(Preference.gcmId,token);
                    sendRegistrationToServer(token);
                    break;
                }
            }
            i++;
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        Preference preference = Preference.getInstance();
        String userID = preference.getStringFromPreference(Preference.USER_ID, "");
        if (!userID.equalsIgnoreCase("")) {
            new RestCilent().processRequest(ServiceUrls.ServiceAction.UPDATE_DEVICE_ID, BuildXMLRequest.registerGCMOnServer(userID, token));
//            new ConnectionHelper(null).sendRequest(this,BuildXMLRequest.registerGCMOnServer(userID, token),ServiceURLs.updateDeviceId,new Preference(this));
        }
    }

}
