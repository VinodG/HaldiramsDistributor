package com.winit.haldiram.module.dashboard.interactors;


import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public interface IDashboardInteractor extends IBaseInteractor {
    void fetchDashboard(final OnDashboardListener listener);

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    interface OnDashboardListener {
        void onError(String Message);
        void onSuccess(List<DashboardDO> dashboardDOs,HashMap<String,ArrayList<MonthlyDO>> hmSales);
    }
}
