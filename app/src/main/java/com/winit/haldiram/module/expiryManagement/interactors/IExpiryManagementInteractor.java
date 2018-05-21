package com.winit.haldiram.module.expiryManagement.interactors;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface IExpiryManagementInteractor extends IBaseInteractor {
    void fetchCollections(final IExpiryManagementInteractor.OnCollectionListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnCollectionListener {
        void onError(String Message);
        void onSuccess(List<CollectionsDO> collectionsDOs);
    }
}
