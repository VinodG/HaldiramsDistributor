package com.winit.haldiram.module.login.interactors;


import com.winit.common.util.FileUtils;
import com.winit.haldiram.dataobject.LoginUserInfo;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

/**
 *  on 9/25/2016.
 */

public interface IAsyncMasterTableInteractor extends IBaseInteractor {
    void loadSqliteFile(LoginUserInfo loginUserInfo, final FileUtils.DownloadListner downloadListner);
}
