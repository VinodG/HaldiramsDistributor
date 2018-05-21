package com.winit.haldiram.dataobject;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class SyncLogDO {
    public String lsd = "";
    public String lst = "";
    public String entity = "";
    public String action = "";
    public String timeStamp = "";
    public SyncLogDO(){

    }
    public SyncLogDO(String lsd, String lst){
        this.lsd = lsd;
        this.lst = lst;
    }
}
