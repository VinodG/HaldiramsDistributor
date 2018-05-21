package com.winit.haldiram.module.login;


import com.winit.common.Preference;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.common.util.FileUtils;
import com.winit.haldiram.dataobject.LoginUserInfo;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.login.interactors.AsyncLoginInteractor;
import com.winit.haldiram.module.login.interactors.AsyncMasterInteractor;
import com.winit.haldiram.module.login.interactors.IAsyncLoginInteractor;
import com.winit.haldiram.module.login.interactors.IAsyncMasterTableInteractor;

/**
 *  on 9/25/2016.
 */

public class LoginPresenter extends BasePresenter implements ILoginPresenter,AsyncLoginInteractor.OnLoginFinishedListener,FileUtils.DownloadListner {

    private IAsyncLoginInteractor iAsyncLoginInteractor;
    private IAsyncMasterTableInteractor iAsyncMasterTableInteractor;
    private ILoginView view;
    private LoginUserInfo loginUserInfo;

    public LoginPresenter(ILoginView view){
        this.view = view;
        this.iAsyncLoginInteractor = new AsyncLoginInteractor(this);
        this.iAsyncMasterTableInteractor = new AsyncMasterInteractor(this);
    }

    @Override
    public void validateLogin(String username, String password) {
        iAsyncLoginInteractor.validateUser(username,password);
    }

    @Override
    public void onProgress(final int count) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onProgress(count);
            }
        });
    }

    @Override
    public void onComplete() {
        savePreference();
        DatabaseHelper.closedatabase();
        DatabaseHelper.clearDatabase();
        view.navigateToDashboard();
    }

    @Override
    public void onError(String Message) {

    }

    private void savePreference(){
        Preference preference = Preference.getInstance();
        preference.saveStringInPreference(Preference.USER_ID, loginUserInfo.strUserId);
        preference.saveStringInPreference(Preference.USER_NAME, loginUserInfo.strUserName);
        preference.saveBooleanInPreference(Preference.IS_LOGGED_IN, true);
    }

    @Override
    public void onSuccess(Object object) {
        this.loginUserInfo = (LoginUserInfo) object;
        Preference preference = Preference.getInstance();
        String userId = preference.getStringFromPreference(Preference.USER_ID,"");
//        String userId = "";
        if(userId.equalsIgnoreCase(loginUserInfo.strUserId)){
            savePreference();
            view.startSync();
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    view.showMasterProgessBar();
                }
            });
            iAsyncMasterTableInteractor.loadSqliteFile(loginUserInfo,this);
        }
    }

    @Override
    public void loadData() {

    }
}
