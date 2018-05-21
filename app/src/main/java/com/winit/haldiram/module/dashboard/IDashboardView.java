package com.winit.haldiram.module.dashboard;


import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public interface IDashboardView extends IBaseView {
    public void onAchieved(List<DashboardDO> dashboardDOs,HashMap<String,ArrayList<MonthlyDO>> hmSales);
}
