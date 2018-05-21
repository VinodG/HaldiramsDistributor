package com.winit.haldiram.module.collection.interactors;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface ICollectionInteractor extends IBaseInteractor {
    void fetchCollections(final ICollectionInteractor.OnCollectionListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnCollectionListener {
        void onError(String Message);
        void onSuccess(HashMap<String,Vector<CollectionsDO>> hmCollections, List<UserDO> users,Double totalDue);
    }
}
