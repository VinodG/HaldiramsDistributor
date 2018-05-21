package com.winit.haldiram.module.login.interactors;


import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataobject.LoginUserInfo;
import com.winit.haldiram.module.base.interactors.HttpBaseInteractor;

import java.util.Vector;

/**
 * on 9/25/2016.
 */

public class AsyncLoginInteractor extends HttpBaseInteractor implements IAsyncLoginInteractor {

    private OnLoginFinishedListener onLoginFinishedListener;

    public AsyncLoginInteractor(OnLoginFinishedListener onLoginFinishedListener){
        this.onLoginFinishedListener = onLoginFinishedListener;
    }

    @Override
    public void validateUser(String username, String password) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.LOGIN, BuildXMLRequest.loginRequest(username,password,"",""),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response.status.equalsIgnoreCase(Response.SUCCESS)) {
            onLoginFinishedListener.onSuccess(((Vector<LoginUserInfo>)response.data).get(0));
        }else
            onLoginFinishedListener.onError(response.message);
    }

    /**
     * Created by Girish Velivela on 5/11/15.
     */
    public static interface OnLoginFinishedListener {
        void onError(String Message);
        void onSuccess(Object object);
    }
}
