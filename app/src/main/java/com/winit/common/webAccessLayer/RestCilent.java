package com.winit.common.webAccessLayer;

import java.util.TreeMap;

/**
 * Created by Girish Velivela on 8/18/2016.
 *
 * Class used
 *  1) Decides the Request is post or Get service
 *  2) Form an Url that need to request
 *  3) Prepares an headers of
 *
 */
public class RestCilent {

    public Object processRequest(ServiceUrls.ServiceAction serviceAction, String data){

        HttpHelper httpHelper = new HttpHelper();
        String baseUrl = ServiceUrls.MAIN_URL;
        String methodType = serviceAction.getMethod();
        String request = null;
        TreeMap<String,String> headers = null;

        switch (serviceAction) {
            default:
                headers = new TreeMap<>();
                request = data;
                headers.put("Content-type", "text/xml; charset=utf-8");
                headers.put("SOAPAction", serviceAction.getSoapAction());
                break;
        }

        return httpHelper.sendRequest(baseUrl, methodType, headers, request);
    }

}
