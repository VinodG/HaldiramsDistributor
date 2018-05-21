package com.winit.haldiram.module.messages.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.constant.AppConstants;
import com.winit.common.databaseaccess.WinitDB;
import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataaccesslayer.SyncLogDA;
import com.winit.haldiram.dataobject.CustomerMessageDO;
import com.winit.haldiram.dataobject.SyncLogDO;
import com.winit.haldiram.module.base.interactors.HttpBaseInteractor;

import java.util.Vector;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class GetMessageInteractor extends HttpBaseInteractor implements IGetMessageInteractor {
    private OnGetNewMessageListener listener;
    private SyncLogDO lastSyncDO;

    public GetMessageInteractor(OnGetNewMessageListener listener){
        this.listener = listener;
    }
    @Override
    public void getMessage(String userId){

        WinitDB.getDefaultInstance().executeTransaction(new WinitDB.DBTaskListener() {
            @Override
            public void executeTask(SQLiteDatabase sqLiteDatabase) {
                lastSyncDO = new SyncLogDA().getSyncLog(ServiceUrls.GET_MESSAGE);
            }

            @Override
            public void onError() {
//                stopSelf();
            }
        });
        if(lastSyncDO!=null){
            HttpService httpService = new HttpService();
            httpService.executeAsyncTask(ServiceUrls.ServiceAction.GET_MESSAGE, BuildXMLRequest.sendMessageRequestNew(userId, lastSyncDO.timeStamp),this);
        }
    }

    @Override
    public void onResponseReceived(Response response) {
        Vector<CustomerMessageDO> vecCustomerMessages = (Vector<CustomerMessageDO>)response.data;
        if(vecCustomerMessages!=null && vecCustomerMessages.size()>0) {
            listener.onGetSuccess(AppConstants.getMessage);
        }
        else
            listener.onGetError(AppConstants.noNewMessage);
    }

    public interface OnGetNewMessageListener {
        void onGetError(String message);
        void onGetSuccess(String message);
    }
}
