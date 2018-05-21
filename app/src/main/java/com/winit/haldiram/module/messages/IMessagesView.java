package com.winit.haldiram.module.messages;

import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IMessagesView extends IBaseView {
//    public void onMessages(List<MessageDO> messages);
    public void onMessages(HashMap<String,ArrayList<MessageDO>> hmMessages);
}
