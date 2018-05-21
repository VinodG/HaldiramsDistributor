package com.winit.haldiram.module.salesEco;

import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface ISalesEcoView extends IBaseView {
    public void onSalesEco(List<SalesEcoDO> salesEcoDOs);
}
