package com.winit.haldiram.module.messageconversation;

import com.winit.common.Preference;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.messageconversation.interactors.IMessageConversationInteractor;
import com.winit.haldiram.module.messageconversation.interactors.MessageConversationInteractor;
import com.winit.haldiram.module.messages.interactors.GetMessageInteractor;
import com.winit.haldiram.module.newmessage.interactors.SendNewMessageInteractor;
import com.winit.haldiram.module.salesEco.interactors.ISalesEcoInteractor;
import com.winit.haldiram.module.salesEco.interactors.SalesEcoInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessageConversationPresenter extends BasePresenter implements IMessageConversationPresenter,IMessageConversationInteractor.OnMessageConversationListener
        ,GetMessageInteractor.OnGetNewMessageListener,SendNewMessageInteractor.OnSendNewMessageListener {
    private MessageConversationInteractor interactor;
    private GetMessageInteractor getMessageInteractor;
    private SendNewMessageInteractor sendNewMessageInteractor;
    private IMessageConversationView view;
    private Preference preference;
    public MessageConversationPresenter(IMessageConversationView view){
        this.view = view;
        this.interactor = new MessageConversationInteractor();
        this.sendNewMessageInteractor = new SendNewMessageInteractor(this);
        this.getMessageInteractor = new GetMessageInteractor(this);
        preference = Preference.getInstance();
    }

    @Override
    public void getMessageConversation(MessageDO messageDO) {
        interactor.fetchMessages(MessageConversationPresenter.this,messageDO);
    }

    @Override
    public void sendMessage(String fromId,String toId,String message) {
        sendNewMessageInteractor.sendMessage(fromId,toId,message);
    }

    @Override
    public void getMessageService(String userId) {
        getMessageInteractor.getMessage(userId);
    }

    @Override
    public void onError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onSuccess(final List<MessageDO> messageDOs) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onMessageConversation(messageDOs);
            }
        });
    }

    @Override
    public void onGetError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onGetSuccess(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onSendError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onSendSuccess(final String message) {
        getMessageInteractor.getMessage(preference.getStringFromPreference(Preference.USER_ID,""));
    }

    @Override
    public void loadData() {

    }
}

