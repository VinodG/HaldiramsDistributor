package com.winit.haldiram.module.newmessage;

import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.newmessage.interactors.INewMessageInteractor;
import com.winit.haldiram.module.newmessage.interactors.NewMessageInteractor;
import com.winit.haldiram.module.newmessage.interactors.SendNewMessageInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class NewMessagePresenter extends BasePresenter implements INewMessagePresenter,INewMessageInteractor.OnNewMessageListener,SendNewMessageInteractor.OnSendNewMessageListener {
    private NewMessageInteractor interactor;
    private SendNewMessageInteractor sendNewMessageInteractor;
    private INewMessageView view;
    public NewMessagePresenter(INewMessageView view){
        this.view = view;
        this.interactor = new NewMessageInteractor();
        this.sendNewMessageInteractor = new SendNewMessageInteractor(this);
    }

    @Override
    public void getUsers() {
        interactor.fetchUsers(NewMessagePresenter.this);
    }

    @Override
    public void sendMessage(String fromId,String toId,String message) {
        sendNewMessageInteractor.sendMessage(fromId,toId,message);
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
    public void onSuccess(final List<UserDO> users) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onNewMessage(users);
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

