package com.winit.haldiram.module.newmessage.interactors;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.databaseaccess.WinitDB;
import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataaccesslayer.SyncLogDA;
import com.winit.haldiram.dataobject.SyncLogDO;
import com.winit.haldiram.module.base.interactors.HttpBaseInteractor;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class SendNewMessageInteractor extends HttpBaseInteractor implements ISendNewMessageInteractor {
    private OnSendNewMessageListener listener;
    private SyncLogDO lastSyncDO;

    public SendNewMessageInteractor(OnSendNewMessageListener listener){
        this.listener = listener;
    }
    @Override
    public void sendMessage(String fromId,String toId,String message){
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.INSERT_MESSAGE, BuildXMLRequest.insertMessageRequest(fromId,toId,message),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response.status.equalsIgnoreCase(Response.SUCCESS)) {
            listener.onSendSuccess("Message sent successfully");
        }
        else {
            listener.onSendError("error");
        }
    }

    public interface OnSendNewMessageListener {
        void onSendError(String message);
        void onSendSuccess(String message);
    }
}
