package com.winit.haldiram.module.newmessage;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface INewMessagePresenter {
    void getUsers();
    void sendMessage(String fromId,String toId,String message);
}
