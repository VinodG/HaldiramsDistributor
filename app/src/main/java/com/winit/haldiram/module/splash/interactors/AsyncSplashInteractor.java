package com.winit.haldiram.module.splash.interactors;

/**
 *  Created by Girish Velivela on 5/11/15.
 */

public class AsyncSplashInteractor implements IAsyncSplashInteractor {

    @Override
    public void holdSplash(final OnSplashFinishedListener listener) {
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            listener.onSuccess(new Object());
        }
    }
}
