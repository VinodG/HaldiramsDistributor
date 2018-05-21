package com.winit.haldiram.module.messages;

import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.messages.interactors.GetMessageInteractor;
import com.winit.haldiram.module.messages.interactors.MessagesInteractor;
import com.winit.haldiram.module.messages.interactors.IMessagesInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessagesPresenter extends BasePresenter implements IMessagesPresenter,IMessagesInteractor.OnMessageListener,GetMessageInteractor.OnGetNewMessageListener {
    private MessagesInteractor interactor;
    private GetMessageInteractor getMessageInteractor;
    private IMessagesView view;
    public MessagesPresenter(IMessagesView view){
        this.view = view;
        this.interactor = new MessagesInteractor();
        this.getMessageInteractor = new GetMessageInteractor(this);
    }

    @Override
    public void getMessages(String userId) {
        interactor.fetchMessages(MessagesPresenter.this,userId);
    }

    @Override
    public void getMessageService(String message) {
        getMessageInteractor.getMessage(message);
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
//    public void onSuccess(final List<MessageDO> messages) {
    public void onSuccess(final HashMap<String,ArrayList<MessageDO>> hmMessages) {
        handler.post(new Runnable() {
            @Override
            public void run() {
//                view.onMessages(messages);
                view.onMessages(hmMessages);
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
    public void loadData() {

    }
}

