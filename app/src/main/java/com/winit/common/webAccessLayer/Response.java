package com.winit.common.webAccessLayer;

/**
 * Created by Girish Velivela on 12/6/2016.
 */

public class Response {
    public static final String SUCCESS = "Success";
    public String status = "";
    public String message = "";
    public int contentLength;
    public Object data = "";
    public ServiceUrls.ServiceAction method;

    public Response(){

    }

    public Response(String status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
