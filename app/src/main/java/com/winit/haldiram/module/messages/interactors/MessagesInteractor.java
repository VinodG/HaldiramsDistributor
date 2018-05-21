package com.winit.haldiram.module.messages.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.MessagesDA;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessagesInteractor extends DBBaseInteractor implements IMessagesInteractor {
    private OnMessageListener listener;
    private String userId;

    @Override
    public void fetchMessages(OnMessageListener listener,String userId) {
        this.listener = listener;
        this.userId = userId;
        //Call DA class
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
//        List<MessageDO> messages = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            MessageDO messageDO = new MessageDO();
////            collectionsDO.customerCode = "custCode"+i;
////            collectionsDO.totalOutStanding = (50+i)+"";
////            collectionsDO.noOfBillsOutstanding = (40+i)+"";
////            collectionsDO.noOfBillsOverdue = (35+i)+"";
//            messages.add(messageDO);
//        }
        HashMap<String,ArrayList<MessageDO>> hmMessages = new MessagesDA().getMessages(userId);
//        HashMap<String,ArrayList<MessageDO>> hmMessages = new HashMap<>();
//        ArrayList<MessageDO> arrMessages = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            MessageDO messageDO = new MessageDO();
//            messageDO.messageId = "Id1";
//            messageDO.fromId = "hrched001";
//            messageDO.toId = "girish";
//            messageDO.message = "Hi "+i;
//            messageDO.messageDate = "2017-08-05";
//            messageDO.title = "title";
//            arrMessages.add(messageDO);
//        }
//        hmMessages.put("girish",arrMessages);
//
//        ArrayList<MessageDO> arrMessages1 = new ArrayList<>();
//        for(int i=6;i<9;i++){
//            MessageDO messageDO = new MessageDO();
//            messageDO.messageId = "Id1";
//            messageDO.fromId = "hrched001";
//            messageDO.toId = "rajesh";
//            messageDO.message = "Hi "+i;
//            messageDO.messageDate = "2017-08-05";
//            messageDO.title = "title";
//            arrMessages1.add(messageDO);
//        }
//        hmMessages.put("rajesh",arrMessages1);
//
//        ArrayList<MessageDO> arrMessages2 = new ArrayList<>();
//        for(int i=15;i<20;i++){
//            MessageDO messageDO = new MessageDO();
//            messageDO.messageId = "Id1";
//            messageDO.fromId = "hrched001";
//            messageDO.toId = "anurag";
//            messageDO.message = "Hi "+i;
//            messageDO.messageDate = "2017-08-05";
//            messageDO.title = "title";
//            arrMessages2.add(messageDO);
//        }
//        hmMessages.put("anurag",arrMessages2);

        listener.onSuccess(hmMessages);
    }

    @Override
    public void onError() {
        listener.onError("No Messages Found.");
    }

}
