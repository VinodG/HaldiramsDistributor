package com.winit.haldiram.module.messageconversation;

import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IMessageConversationView extends IBaseView {
    public void onMessageConversation(List<MessageDO> messageDOs);
}
