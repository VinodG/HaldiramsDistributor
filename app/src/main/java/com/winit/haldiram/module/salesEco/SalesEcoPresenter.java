package com.winit.haldiram.module.salesEco;

import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.salesEco.interactors.SalesEcoInteractor;
import com.winit.haldiram.module.salesEco.interactors.ISalesEcoInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class SalesEcoPresenter extends BasePresenter implements ISalesEcoPresenter,ISalesEcoInteractor.OnSalesListener {
    private SalesEcoInteractor interactor;
    private ISalesEcoView view;
    public SalesEcoPresenter(ISalesEcoView view){
        this.view = view;
        this.interactor = new SalesEcoInteractor();
    }

    @Override
    public void getSalesEco() {
        interactor.fetchSales(SalesEcoPresenter.this);
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
    public void onSuccess(final List<SalesEcoDO> salesEcoDOs) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onSalesEco(salesEcoDOs);
            }
        });
    }

    @Override
    public void loadData() {

    }
}

