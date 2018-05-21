package com.winit.haldiram.module.login.interactors;


import com.winit.haldiram.module.base.interactors.IBaseInteractor;

/**
 *  on 9/25/2016.
 */

public interface IAsyncLoginInteractor extends IBaseInteractor {
    void validateUser(String username, String password);
}
