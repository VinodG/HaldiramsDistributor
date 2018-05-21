package com.winit.haldiram.module.stocks.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class StocksInteractor extends DBBaseInteractor implements IStocksInteractor {
    private OnStocksListener listener;

    @Override
    public void fetchStocks(OnStocksListener listener) {
        this.listener = listener;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
//        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {

//        HashMap<String,ArrayList<StockDO>> hmStocks = new StocksDA().getStocks(sqLiteDatabase);
//        HashMap<String,ArrayList<StockDO>> hmStocks = new HashMap<>();
//        ArrayList<StockDO> arrStocks = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            StockDO stockDO = new StockDO();
//            stockDO.itemCode = "custCode"+i;
//            stockDO.itemName = "custName"+i;
//            stockDO.productNorm = (50+i)+"%";
//            stockDO.stockQty = (40+i)+"%";
//            stockDO.noOfDays = (35+i)+"";
//            arrStocks.add(stockDO);
//        }
//        hmStocks.put("Model Stock",arrStocks);
//        ArrayList<StockDO> arrStocks1 = new ArrayList<>();
//        for(int i=6;i<9;i++){
//            StockDO stockDO = new StockDO();
//            stockDO.itemCode = "custCode"+i;
//            stockDO.itemName = "custName"+i;
//            stockDO.productNorm = (50+i)+"%";
//            stockDO.stockQty = (40+i)+"%";
//            stockDO.noOfDays = (35+i)+"";
//            arrStocks1.add(stockDO);
//        }
//        hmStocks.put("Over Stock",arrStocks1);
//        ArrayList<StockDO> arrStocks2 = new ArrayList<>();
//        for(int i=10;i<15;i++){
//            StockDO stockDO = new StockDO();
//            stockDO.itemCode = "custCode"+i;
//            stockDO.itemName = "custName"+i;
//            stockDO.productNorm = (50+i)+"%";
//            stockDO.stockQty = (40+i)+"%";
//            stockDO.noOfDays = (35+i)+"";
//            arrStocks2.add(stockDO);
//        }
//        hmStocks.put("OOS",arrStocks2);

//        listener.onSuccess(hmStocks);
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
