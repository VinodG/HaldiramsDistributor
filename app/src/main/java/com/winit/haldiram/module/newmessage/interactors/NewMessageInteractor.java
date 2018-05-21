package com.winit.haldiram.module.newmessage.interactors;

import android.database.sqlite.SQLiteDatabase;

import com.winit.common.databaseaccess.WinitDB;
import com.winit.haldiram.dataaccesslayer.UserDA;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.interactors.DBBaseInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class NewMessageInteractor extends DBBaseInteractor implements INewMessageInteractor {
    private OnNewMessageListener listener;

    @Override
    public void fetchUsers(OnNewMessageListener listener) {
        this.listener = listener;
        WinitDB.getDefaultInstance().executeTransactionAsync(this);
    }

    @Override
    public void executeTask(SQLiteDatabase sqLiteDatabase) {
        List<UserDO> users = new UserDA().getUsers();
        listener.onSuccess(users);
    }

    @Override
    public void onError() {
        listener.onError("No Messages Found.");
    }

}
