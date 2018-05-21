package com.winit.haldiram.module.splash;


import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.splash.interactors.AsyncSplashInteractor;
import com.winit.haldiram.module.splash.interactors.OnSplashFinishedListener;;

/**
 *  Created by Girish Velivela on 4/11/16.
 */

public class SplashPresenter extends BasePresenter implements ISplashPresenter,OnSplashFinishedListener {

    private AsyncSplashInteractor interactor;
    private ISplashView view;

    public SplashPresenter(ISplashView view){
        this.view = view;
        this.interactor = new AsyncSplashInteractor();
    }

    @Override
    public void holdSplash() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.holdSplash(SplashPresenter.this);
            }
        }).start();

    }

    @Override
    public void onError(String Message) {

    }

    @Override
    public void onSuccess(Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
              /*  if(preference.getbooleanFromPreference(Preference.IS_LOGGED_IN,false))
                    view.moveToDashboard();
                else*/
                    view.moveToLogin();
            }
        });
    }

    @Override
    public void loadData() {

    }
}
