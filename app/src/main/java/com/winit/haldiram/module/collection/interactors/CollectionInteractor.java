package com.winit.haldiram.module.collection.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.CollectionDA;
import com.winit.haldiram.dataaccesslayer.UserDA;
import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class CollectionInteractor extends DBBaseInteractor implements ICollectionInteractor{
    private OnCollectionListener listener;

    @Override
    public void fetchCollections(CollectionInteractor.OnCollectionListener listener) {
        this.listener = listener;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
//        List<CollectionsDO> collections = new CollectionDA().getCollections();
        Object[] object = new CollectionDA().getHMCollections();
        HashMap<String,Vector<CollectionsDO>> hmCollections = (HashMap<String,Vector<CollectionsDO>>)object[0];
        Double totalDue = (Double)object[1];
        List<UserDO> users = new UserDA().getUsers();

        listener.onSuccess(hmCollections,users,totalDue);
    }

    @Override
    public void onError() {
        listener.onError("No Collections Found.");
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
