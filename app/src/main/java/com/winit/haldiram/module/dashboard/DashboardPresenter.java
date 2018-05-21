package com.winit.haldiram.module.dashboard;



import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.dashboard.interactors.DashboardInteractor;
import com.winit.haldiram.module.dashboard.interactors.IDashboardInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class DashboardPresenter extends BasePresenter implements IDashboardPresenter,IDashboardInteractor.OnDashboardListener {

    private DashboardInteractor interactor;
    private IDashboardView view;

    public DashboardPresenter(IDashboardView view){
        this.view = view;
        this.interactor = new DashboardInteractor();
    }

    @Override
    public void getAchieved() {
        interactor.fetchDashboard(DashboardPresenter.this);
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
    public void onSuccess(final List<DashboardDO> dashboardDOs,final HashMap<String,ArrayList<MonthlyDO>> hmSales) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onAchieved(dashboardDOs,hmSales);
            }
        });
    }

    @Override
    public void loadData() {

    }
}
