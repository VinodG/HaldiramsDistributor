package com.winit.haldiram.module.newmessage.interactors;

import com.winit.haldiram.module.base.interactors.IBaseInteractor;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface ISendNewMessageInteractor extends IBaseInteractor {
    void sendMessage(String fromId,String toId,String message);
}
