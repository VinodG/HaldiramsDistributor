package com.winit.haldiram.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.SyncData;
import com.winit.haldiram.R;
import com.winit.haldiram.module.login.ILoginPresenter;
import com.winit.haldiram.module.login.ILoginView;
import com.winit.haldiram.module.login.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gufran.Khan on 5/8/2017.
 */

public class LoginActivity extends BaseActivity implements ILoginView,SyncData.SyncProcessListner {

    @Nullable
    @Bind(R.id.etUserName)
    EditText etUserName;

    @Nullable
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Nullable
    @Bind(R.id.ivCheck_rememberMe)
    ImageView ivCheck_RememberMe;

    private String strUserName,strPassword;

    private TextView tvProgress;
    private ProgressBar progressBar;
    private Dialog dialogDownload;

    private ILoginPresenter iLoginPresenter;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.login, flBody, true);
        toolbar.setVisibility(View.GONE);
        iLoginPresenter = new LoginPresenter(this);
        ButterKnife.bind(this);

        etUserName.setText(preference.getStringFromPreference(Preference.USER_ID,""));
//        etPassword.setText(preference.getStringFromPreference(Preference.USER_ID,""));
        ivCheck_RememberMe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(preference.getbooleanFromPreference(Preference.REMEMBER_ME, false))
                {
                    ivCheck_RememberMe.setImageResource(R.drawable.remeber_me_uncheck_box);
                    preference.saveBooleanInPreference(Preference.REMEMBER_ME, false);
                }
                else
                {
                    ivCheck_RememberMe.setImageResource(R.drawable.remeber_me_box);
                    preference.saveBooleanInPreference(Preference.REMEMBER_ME, true);
                }
            }
        });

    }


    @Nullable
    @OnClick(R.id.btnLogin)
    public void performLogin(){

        strUserName		= 	etUserName.getText().toString();
        strPassword		=   etPassword.getText().toString();
        if(strUserName.equals("") || strPassword.equals(""))
        {
            if(strUserName.equals("") && strPassword.equals(""))
            {
                showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_username_password), getString(R.string.OK), null, "");
                etUserName.requestFocus();
            }
            else if(strUserName.equals(""))
            {
                showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_username), getString(R.string.OK), null, "");
                etUserName.requestFocus();
            }
            else if(strPassword.equals(""))
            {
                showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_password), getString(R.string.OK), null, "");
                etPassword.requestFocus();
            }
        }else if(NetworkUtility.isNetworkConnectionAvailable(LoginActivity.this)){
            showLoader(getString(R.string.please_wait));
            iLoginPresenter.validateLogin(strUserName.trim(), strPassword);
        }else
            showCustomDialog(LoginActivity.this, getString(R.string.warning), ""+getString(R.string.connection_not_available), getString(R.string.OK), null, "");
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        lockDrawer();
    }


    @Override
    public void showMasterProgessBar() {
        View v = inflater.inflate(R.layout.progressdialog, null);

        progressBar= (ProgressBar) v.findViewById(R.id.prgbar);
        tvProgress = (TextView) v.findViewById(R.id.tvprogress);

        if(dialogDownload==null)
        {
            dialogDownload = new Dialog(LoginActivity.this);
            dialogDownload.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogDownload.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogDownload.setCancelable(false);
        }
        int w = (int) (preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 600) * (2f/3f));
        dialogDownload.setContentView(v,new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialogDownload.getWindow().setGravity(Gravity.CENTER);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        tvProgress.setText("0 %");
        try {
            if( !LoginActivity.this.isFinishing())
                dialogDownload.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startSync() {
        showLoader("Syncing....");
        SyncData.setListner(LoginActivity.this);
        Intent intent = new Intent(LoginActivity.this,SyncData.class);
        startService(intent);
    }

    @Override
    public void onProgress(final int count) {
        if(dialogDownload!=null)
        {
            progressBar.setProgress(count);
            tvProgress.setText(count+" %");
        }
    }

    @Override
    public void navigateToDashboard() {
        hideLoader();
        if(dialogDownload!=null && dialogDownload.isShowing())
            dialogDownload.dismiss();
        Intent intent =new Intent(LoginActivity.this,DashboardActivtiy.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void setProgress(String msg) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void end() {
       navigateToDashboard();
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
