package com.winit.haldiram.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.NetworkUtility;
import com.winit.haldiram.R;
import com.winit.haldiram.adapter.MessageConversationAdapter;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.messageconversation.IMessageConversationPresenter;
import com.winit.haldiram.module.messageconversation.IMessageConversationView;
import com.winit.haldiram.module.messageconversation.MessageConversationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awaneesh on 5/12/2017.
 */

public class MessageConversationDetail extends  BaseActivity implements IMessageConversationView{
    @Nullable
    @Bind(R.id.rv_message_conversations)
    RecyclerView rvMessageConversations;

    @Nullable
    @Bind(R.id.et_conversation_message)
    EditText etMessage;

    @Nullable
    @Bind(R.id.btn_send_conversation)
    Button btnSend;

    private MessageConversationAdapter adapter;
    private MessageDO messageDO;
    private ArrayList<MessageDO> arrMessages;
    BroadcastReceiver newMessageReceiver;
    private IMessageConversationPresenter iMessageConversationPresenter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.message_conversation, flBody, true);
        ButterKnife.bind(this);
        iMessageConversationPresenter = new MessageConversationPresenter(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            if(extras.containsKey("messageDO"))
                messageDO = (MessageDO) extras.get("messageDO");
            if(extras.containsKey("arrMessages"))
                arrMessages = (ArrayList<MessageDO>) extras.get("arrMessages");
            else
                arrMessages = new ArrayList<>();
        }
        newMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Srikanth: ","Broadcast received");
//                iMessageConversationPresenter.getMessageConversation(messageDO);
                if(NetworkUtility.isNetworkConnectionAvailable(MessageConversationDetail.this)){
//                    showLoader(getString(R.string.please_wait));
                    iMessageConversationPresenter.getMessageService(preference.getStringFromPreference(Preference.USER_ID, ""));
                }
            }
        };

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                String user = "";
                if(messageDO.fromId.equalsIgnoreCase(preference.getStringFromPreference(Preference.USER_ID,"")))
                    user = messageDO.toId;
                else
                    user = messageDO.fromId;
                if(TextUtils.isEmpty(message)){
                    showCustomDialog(MessageConversationDetail.this, getString(R.string.warning), getString(R.string.enter_message), getString(R.string.OK), null, "");
                    etMessage.requestFocus();
                }
                else if(NetworkUtility.isNetworkConnectionAvailable(MessageConversationDetail.this)){
                    showLoader(getString(R.string.please_wait));
                    iMessageConversationPresenter.sendMessage(preference.getStringFromPreference(Preference.USER_ID,""),user,message);
                }
                else
                    showCustomDialog(MessageConversationDetail.this, getString(R.string.warning), ""+getString(R.string.connection_not_available), getString(R.string.OK), null, "");
            }
        });
        
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MessageConversationDetail.this);
        rvMessageConversations.setLayoutManager(mLayoutManager);
        rvMessageConversations.setItemAnimator(new DefaultItemAnimator());
        rvMessageConversations.setAdapter(adapter = new MessageConversationAdapter(MessageConversationDetail.this,arrMessages,preference.getStringFromPreference(Preference.USER_ID, "")));

    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onMessageConversation(List<MessageDO> messageDOs) {
        adapter.refresh(messageDOs);
    }

    @Override
    public void showAlert(String message) {
        hideLoader();
        if(message.equalsIgnoreCase("error"))
            showCustomDialog(MessageConversationDetail.this, getString(R.string.warning), ""+getString(R.string.error_sending_message), getString(R.string.OK), null, "");
        else if(message.equalsIgnoreCase(AppConstants.getMessage))
            iMessageConversationPresenter.getMessageConversation(messageDO);
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filtersMessage = new IntentFilter();
        filtersMessage.addAction(AppConstants.ACTION_NEW_MESSAGE);
        registerReceiver(newMessageReceiver, filtersMessage);
    }
    @Override
    protected void onStop() {
        try {
            unregisterReceiver(newMessageReceiver);
        }
        catch (IllegalArgumentException e) {
        }
        catch (Exception e) {
        }
        super.onStop();
    }
}
