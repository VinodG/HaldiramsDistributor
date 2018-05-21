package com.winit.haldiram.ui.activities;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.util.CustomBuilder;
import com.winit.common.util.NetworkUtility;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.newmessage.INewMessagePresenter;
import com.winit.haldiram.module.newmessage.INewMessageView;
import com.winit.haldiram.module.newmessage.NewMessagePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awaneesh on 5/12/2017.
 */

public class NewMessageActivity extends BaseActivity implements INewMessageView {

    @Nullable
    @Bind(R.id.tv_select_user)
    TextView tvSelectUser;

    @Nullable
    @Bind(R.id.et_message)
    EditText etMessage;

    @Nullable
    @Bind(R.id.btn_send)
    Button btnSend;

    @Nullable
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    private INewMessagePresenter iNewMessagePresenter;
    private List<UserDO> users;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.new_message, flBody, true);
        ButterKnife.bind(this);


        tvSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show custom builder
                CustomBuilder builder = new CustomBuilder(NewMessageActivity.this, "Select DSM Name", true);
                builder.setSingleChoiceItems(users, tvSelectUser.getTag()!=null?tvSelectUser.getTag():-1, new CustomBuilder.OnClickListener()
                {
                    @Override
                    public void onClick(CustomBuilder builder, Object selectedObject)
                    {
                        builder.dismiss();
                        UserDO userDO = (UserDO) selectedObject;

                        tvSelectUser.setText(userDO.userId);
                        tvSelectUser.setTag(userDO);
                    }
                });
                builder.show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                String user = tvSelectUser.getText().toString();
                if(TextUtils.isEmpty(user)){
                    showCustomDialog(NewMessageActivity.this, getString(R.string.warning), getString(R.string.select_user), getString(R.string.OK), null, "");
//                    etMessage.requestFocus();
                }
                else if(TextUtils.isEmpty(message)){
                    showCustomDialog(NewMessageActivity.this, getString(R.string.warning), getString(R.string.enter_message), getString(R.string.OK), null, "");
                    etMessage.requestFocus();
                }
                else if(NetworkUtility.isNetworkConnectionAvailable(NewMessageActivity.this)){
                    showLoader(getString(R.string.please_wait));
                    iNewMessagePresenter.sendMessage(preference.getStringFromPreference(Preference.USER_ID,""),user,message);
                }
                else
                    showCustomDialog(NewMessageActivity.this, getString(R.string.warning), ""+getString(R.string.connection_not_available), getString(R.string.OK), null, "");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iNewMessagePresenter = new NewMessagePresenter(this);
        //showLoader
        iNewMessagePresenter.getUsers();
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onNewMessage(List<UserDO> users) {
        //hide Loader
        this.users = users;
    }

    @Override
    public void showAlert(String message) {
        hideLoader();
//        if(message.contains("Success"))
            showCustomDialog(NewMessageActivity.this, getString(R.string.alert), message, getString(R.string.OK), null, "finish");
//        else
//            showCustomDialog(NewMessageActivity.this, getString(R.string.alert), message, getString(R.string.OK), null, "");
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onButtonYesClick(String from){
        if(from.equalsIgnoreCase("finish"))
            finish();;
    }
}
