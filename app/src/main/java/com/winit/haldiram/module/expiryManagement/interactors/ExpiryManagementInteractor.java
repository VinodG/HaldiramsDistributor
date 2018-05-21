package com.winit.haldiram.module.expiryManagement.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class ExpiryManagementInteractor extends DBBaseInteractor implements IExpiryManagementInteractor {
    private OnCollectionListener listener;

    @Override
    public void fetchCollections(OnCollectionListener listener) {
        this.listener = listener;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<CollectionsDO> collections = new ArrayList<>();
        for(int i=0;i<5;i++){
            CollectionsDO collectionsDO = new CollectionsDO();
            collectionsDO.customerCode = "custCode"+i;
            collectionsDO.totalOutStanding = (50+i)+"";
            collectionsDO.noOfBillsOutstanding = (40+i)+"";
            collectionsDO.noOfBillsOverdue = (35+i)+"";
            collections.add(collectionsDO);
        }

        listener.onSuccess(collections);
    }

    @Override
    public void onError() {
        listener.onError("No Records Found.");
    }

//    @Override
//    public void onResponseReceived(Response response) {
//        if(response != null){
//            if(response.data != null){
//                FeedsResponse feedsResponse = (FeedsResponse) response.data;
//                if(feedsResponse.getStatus() == 200){
//                    if(feedsResponse.getFeeds() != null){
//                        for(int i =0; i<10;i++){
//                            feedsResponse.getFeeds().addAll(feedsResponse.getFeeds());
//                        }
////                        listener.onSuccess(feedsResponse.getFeeds());
//                    }else
//                        listener.onError("No Feeds Found.");
//                }
//                else
//                    listener.onError("No Feeds Found.");
//                return;
//            }
//        }
//        listener.onError("Unable to connect.");
//    }
}
