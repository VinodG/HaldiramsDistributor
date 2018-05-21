package com.winit.haldiram.dataobject.response;

/**
 * Created by Gufran.Khan on 5/8/2017.
 */

public class Response {

    private int Status;
    private String Message;
    private String ServerTime;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String ServerTime) {
        this.ServerTime = ServerTime;
    }

}
