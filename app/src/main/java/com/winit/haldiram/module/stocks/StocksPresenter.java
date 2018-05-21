package com.winit.haldiram.module.stocks;

import com.winit.haldiram.dataobject.StockDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.stocks.interactors.StocksInteractor;
import com.winit.haldiram.module.stocks.interactors.IStocksInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class StocksPresenter extends BasePresenter implements IStocksPresenter,IStocksInteractor.OnStocksListener {
    private StocksInteractor interactor;
    private IStocksView view;
    public StocksPresenter(IStocksView view){
        this.view = view;
        this.interactor = new StocksInteractor();
    }

    @Override
    public void getStocks() {
        interactor.fetchStocks(StocksPresenter.this);
    }

    @Override
    public void onError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onSuccess(final HashMap<String,ArrayList<StockDO>> hmStocks) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onStocks(hmStocks);
            }
        });
    }

    @Override
    public void loadData() {

    }
}

