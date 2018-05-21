package com.winit.haldiram.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.winit.common.Preference;
import com.winit.common.util.NetworkUtility;
import com.winit.haldiram.R;
import com.winit.haldiram.adapter.MessagesAdapter;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.messages.IMessagesPresenter;
import com.winit.haldiram.module.messages.IMessagesView;
import com.winit.haldiram.module.messages.MessagesPresenter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessagesActivity extends BaseActivity implements IMessagesView {

    @Nullable
    @Bind(R.id.btn_new_message)
    Button btnNewMessage;

    @Nullable
    @Bind(R.id.rvMessages)
    RecyclerView rvMessages;

    private IMessagesPresenter iMessagesPresenter;
    private MessagesAdapter messagesAdapter;
    private ArrayList<UserDO> users;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.messages_activity, flBody, true);
        ButterKnife.bind(this);

        btnNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagesActivity.this, NewMessageActivity.class);
                startActivityForResult(intent, 500);
            }
        });

        rvMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MessagesActivity.this);
        rvMessages.setLayoutManager(mLayoutManager);
        rvMessages.setItemAnimator(new DefaultItemAnimator());

        rvMessages.setAdapter(messagesAdapter = new MessagesAdapter(MessagesActivity.this,null));

        iMessagesPresenter = new MessagesPresenter(this);
        //showLoader
//        if(NetworkUtility.isNetworkConnectionAvailable(MessagesActivity.this)){
//            showLoader(getString(R.string.please_wait));
//            iMessagesPresenter.getMessageService(preference.getStringFromPreference(Preference.USER_ID, ""));
//        }
//        else{
//            iMessagesPresenter.getMessages();
////            showCustomDialog(MessagesActivity.this, getString(R.string.warning), ""+getString(R.string.connection_not_available), getString(R.string.OK), null, "");
//        }
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
//    public void onMessages(List<MessageDO> messages) {
    public void onMessages(HashMap<String,ArrayList<MessageDO>> hmMessages) {
        //hide Loader
        messagesAdapter.refresh(hmMessages);
    }

    @Override
    public void showAlert(String message) {
        hideLoader();
//        if(!message.equalsIgnoreCase("Success"))
        {
//            iMessagesPresenter.getMessages();
//            showCustomDialog(MessagesActivity.this, getString(R.string.alert), message, getString(R.string.OK), null, "");//to use
        }
//        else
//            showCustomDialog(MessagesActivity.this, getString(R.string.warning), message, getString(R.string.OK), null, "");
        iMessagesPresenter.getMessages(preference.getStringFromPreference(Preference.USER_ID, ""));
    }

    @Override
    public void onLoadFailed() {


    }

    @Override
    public void onResume(){
        super.onResume();
        if(iMessagesPresenter!=null && preference!=null){
            showLoader("Loading....");
            if(NetworkUtility.isNetworkConnectionAvailable(MessagesActivity.this)){
                showLoader(getString(R.string.please_wait));
                iMessagesPresenter.getMessageService(preference.getStringFromPreference(Preference.USER_ID, ""));
            }
            else{
                iMessagesPresenter.getMessages(preference.getStringFromPreference(Preference.USER_ID, ""));
//            showCustomDialog(MessagesActivity.this, getString(R.string.warning), ""+getString(R.string.connection_not_available), getString(R.string.OK), null, "");
            }
        }
    }
}
