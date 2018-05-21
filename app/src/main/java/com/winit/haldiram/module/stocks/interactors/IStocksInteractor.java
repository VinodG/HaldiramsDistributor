package com.winit.haldiram.module.stocks.interactors;

import com.winit.haldiram.dataobject.StockDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IStocksInteractor extends IBaseInteractor {
    void fetchStocks(final IStocksInteractor.OnStocksListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnStocksListener {
        void onError(String Message);
        void onSuccess(HashMap<String,ArrayList<StockDO>> hmStocks);
    }
}
