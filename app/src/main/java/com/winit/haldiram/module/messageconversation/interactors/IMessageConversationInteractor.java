package com.winit.haldiram.module.messageconversation.interactors;

import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IMessageConversationInteractor extends IBaseInteractor {
    void fetchMessages(final IMessageConversationInteractor.OnMessageConversationListener listener,MessageDO messageDO);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnMessageConversationListener {
        void onError(String Message);
        void onSuccess(List<MessageDO> messageDOs);
    }
}
