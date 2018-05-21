package com.winit.haldiram.module.newmessage.interactors;

import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface INewMessageInteractor extends IBaseInteractor {
    void fetchUsers(final INewMessageInteractor.OnNewMessageListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnNewMessageListener {
        void onError(String Message);
        void onSuccess(List<UserDO> users);
    }
}
