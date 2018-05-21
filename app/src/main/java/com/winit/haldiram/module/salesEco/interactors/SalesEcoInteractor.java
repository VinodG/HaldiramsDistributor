package com.winit.haldiram.module.salesEco.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.SalesEcoDA;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class SalesEcoInteractor extends DBBaseInteractor implements ISalesEcoInteractor {
    private OnSalesListener listener;

    @Override
    public void fetchSales(OnSalesListener listener) {
        this.listener = listener;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<SalesEcoDO> salesEcoDOs = new SalesEcoDA().getSales();
//        for(int i=0;i<5;i++){
//            SalesEcoDO salesEcoDO = new SalesEcoDO();
//            salesEcoDO.dsmName = "custCode"+i;
//            salesEcoDO.uob = (50+i)+"%";
//            salesEcoDO.eco = (40+i)+"%";
//            salesEcoDO.ulc = (35+i)+"";
//            salesEcoDO.zeroSales = (25+i)+"";
//            salesEcoDO.newOutlet = (30+i)+"";
//            salesEcoDOs.add(salesEcoDO);
//        }

        listener.onSuccess(salesEcoDOs);
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
