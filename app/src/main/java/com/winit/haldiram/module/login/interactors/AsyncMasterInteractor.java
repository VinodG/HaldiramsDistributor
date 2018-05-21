package com.winit.haldiram.module.login.interactors;


import com.winit.common.constant.AppConstants;
import com.winit.common.util.FileUtils;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.HttpHelper;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataobject.LoginUserInfo;
import com.winit.haldiram.module.base.interactors.HttpBaseInteractor;

/**
 * on 9/25/2016.
 */

public class AsyncMasterInteractor extends HttpBaseInteractor implements IAsyncMasterTableInteractor {

    private FileUtils.DownloadListner downloadListner;

    public AsyncMasterInteractor(FileUtils.DownloadListner downloadListner) {
        this.downloadListner = downloadListner;
    }

    @Override
    public void loadSqliteFile(LoginUserInfo loginUserInfo, FileUtils.DownloadListner downloadListner) {
        HttpService httpService = new HttpService();
        httpService.executeAsyncTask(ServiceUrls.ServiceAction.MASTER_TABLE, BuildXMLRequest.masterRequest(loginUserInfo.strUserId,"0","0"),this);
    }

    @Override
    public void onResponseReceived(Response response) {
        if (response.status.equalsIgnoreCase(Response.SUCCESS))
        {
            if(response.data != null)
            {
                String masterTable = response.data+"";
                if(masterTable.contains("%s"))
                    masterTable = masterTable.replace("%s",ServiceUrls.IMAGE_GLOBAL_URL);
//                String masterTable = "";
//                masterTable = "http://transmedsfa.winitsoftware.com/DistributorSqlite.zip";
                if(StringUtils.isEmpty(masterTable))
                    downloadListner.onError("Error occurred while downloading master data file.");
                else {
                    if(!masterTable.contains(".zip"))
                        masterTable  = masterTable.replace("sqlite","zip");
                    Response responseDO = (Response) new HttpHelper().sendRequest(masterTable, ServiceUrls.METHOD_GET, null, null);
                    if (responseDO != null) {
                        FileUtils.downloadZip(responseDO, AppConstants.DATABASE_PATH, AppConstants.DATABASE_NAME, 0, downloadListner);
                    } else
                        downloadListner.onError("Error occurred while downloading master data file.");

                }
            }
            else
                downloadListner.onError("Error occurred while downloading master data file.");
        }
        else
            downloadListner.onError(response.message);
    }
}
