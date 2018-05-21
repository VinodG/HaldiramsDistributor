package com.winit.haldiram.module.salesEco.interactors;

import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface ISalesEcoInteractor extends IBaseInteractor {
    void fetchSales(final ISalesEcoInteractor.OnSalesListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnSalesListener {
        void onError(String Message);
        void onSuccess(List<SalesEcoDO> salesEcoDOs);
    }
}
