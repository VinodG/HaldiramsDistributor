package com.winit.haldiram.module.messageconversation.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.MessagesDA;
import com.winit.haldiram.dataaccesslayer.SalesEcoDA;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessageConversationInteractor extends DBBaseInteractor implements IMessageConversationInteractor {
    private OnMessageConversationListener listener;
    private  MessageDO messageDO;

    @Override
    public void fetchMessages(OnMessageConversationListener listener,MessageDO messageDO) {
        this.listener = listener;
        this.messageDO = messageDO;
//        HttpService httpService = new HttpService();
//        httpService.executeAsyncTask(ServiceUrls.ServiceAction.COLLECTIONS, "", this);
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<MessageDO> messageDOs = new MessagesDA().getMessages(messageDO.fromId,messageDO.toId);

        listener.onSuccess(messageDOs);
    }

    @Override
    public void onError() {
//        listener.onError("No Collections Found.");
    }

}
