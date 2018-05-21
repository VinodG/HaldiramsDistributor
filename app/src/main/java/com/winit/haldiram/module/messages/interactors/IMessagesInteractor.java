package com.winit.haldiram.module.messages.interactors;

import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IMessagesInteractor extends IBaseInteractor {
    void fetchMessages(final IMessagesInteractor.OnMessageListener listener,String userId);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnMessageListener {
        void onError(String Message);
//        void onSuccess(List<MessageDO> messages);
        void onSuccess(HashMap<String,ArrayList<MessageDO>> hmMessages);
    }
}
