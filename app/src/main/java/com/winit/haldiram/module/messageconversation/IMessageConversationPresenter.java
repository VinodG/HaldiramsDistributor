package com.winit.haldiram.module.messageconversation;

import com.winit.haldiram.dataobject.MessageDO;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IMessageConversationPresenter {
    void getMessageConversation(MessageDO messageDO);
    void getMessageService(String message);
    void sendMessage(String fromId,String toId,String message);
}
