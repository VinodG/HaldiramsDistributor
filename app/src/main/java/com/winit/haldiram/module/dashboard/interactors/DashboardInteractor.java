package com.winit.haldiram.module.dashboard.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.DashboardDA;
import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class DashboardInteractor extends DBBaseInteractor implements IDashboardInteractor {

    private OnDashboardListener listener;

    @Override
    public void fetchDashboard(OnDashboardListener listener) {
        this.listener = listener;
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<DashboardDO> dashboardDOs = new DashboardDA().getAchieved();
        HashMap<String,ArrayList<MonthlyDO>> hmSales = new DashboardDA().getGraphData();

        listener.onSuccess(dashboardDOs,hmSales);
    }

    @Override
    public void onError() {
        listener.onError("No Records Found.");
    }
}
