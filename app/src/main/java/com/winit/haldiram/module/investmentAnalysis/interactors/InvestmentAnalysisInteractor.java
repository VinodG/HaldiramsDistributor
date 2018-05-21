package com.winit.haldiram.module.investmentAnalysis.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.InvestmentAnalysisDA;
import com.winit.haldiram.dataobject.InvestmentAnalysisDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class InvestmentAnalysisInteractor extends DBBaseInteractor implements IInvestmentAnalysisInteractor {
    private OnInvesmentAnalysisListener listener;

    @Override
    public void fetchInvesmentAnalysis(OnInvesmentAnalysisListener listener) {
        this.listener = listener;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<InvestmentAnalysisDO> investmentAnalysisDOs = new InvestmentAnalysisDA().getInvestments();

        listener.onSuccess(investmentAnalysisDOs);
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
