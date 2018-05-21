package com.winit.haldiram.module.newmessage;

import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface INewMessageView extends IBaseView {
    public void onNewMessage(List<UserDO> users);
}
