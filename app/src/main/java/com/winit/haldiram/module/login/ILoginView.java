package com.winit.haldiram.module.login;


import com.winit.haldiram.module.base.IBaseView;

/**
 *  on 9/25/2016.
 */

public interface ILoginView extends IBaseView{
    void showMasterProgessBar();
    void startSync();
    void onProgress(final int count) ;
    void navigateToDashboard();
}
