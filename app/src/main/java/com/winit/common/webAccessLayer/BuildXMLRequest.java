package com.winit.common.webAccessLayer;

import java.net.URLEncoder;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class BuildXMLRequest {
    private final static String SOAP_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>";

    private final static String SOAP_FOOTER="</soap:Body>"+
            "</soap:Envelope>";

    public static String loginRequest(String UserName, String password, String gcmId,String deviceId)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<CheckLogin xmlns=\"http://tempuri.org/\">")
                    .append("<UserName>").append(UserName).append("</UserName>" )
                    .append("<Password>").append(password).append("</Password>" )
                    .append("<GCMKey>").append(gcmId).append("</GCMKey>" )
                    .append("<DeviceId>").append(deviceId).append("</DeviceId>" )
                    .append("</CheckLogin>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String masterRequest(String userCode, String lsd, String lst) {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<GetDistributorDataFile xmlns=\"http://tempuri.org/\">")
                    .append("<UserCode>").append(userCode).append("</UserCode>" )
                    .append("<lsd>").append(lsd).append("</lsd>" )
                    .append("<lst>").append(lst).append("</lst>" )
                    .append("</GetDistributorDataFile>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String syncDataRequest(String userCode, String lsd, String lst) {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<GetDistributorDataSync xmlns=\"http://tempuri.org/\">")
                    .append("<UserCode>").append(userCode).append("</UserCode>" )
                    .append("<lsd>").append(lsd).append("</lsd>" )
                    .append("<lst>").append(lst).append("</lst>" )
                    .append("</GetDistributorDataSync>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String insertMessageRequest(String fromId, String toId,String message)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<InsertMessage xmlns=\"http://tempuri.org/\">")
                    .append("<FromId>").append(fromId).append("</FromId>" )
                    .append("<ToId>").append(toId).append("</ToId>" )
                    .append("<Message>").append(URLEncoder.encode("" + message,"UTF-8")).append("</Message>" )
                    .append("</InsertMessage>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String sendMessageRequest(String userCode, String lsd,String lst)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<getMessagesForApp xmlns=\"http://tempuri.org/\">")
                    .append("<UserCode>").append(userCode).append("</UserCode>" )
                    .append("<lsd>").append(lsd).append("</lsd>" )
                    .append("<lst>").append(lst).append("</lst>" )
                    .append("</getMessagesForApp>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String sendMessageRequestNew(String userCode, String date)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append(SOAP_HEADER)
                    .append("<getMessages xmlns=\"http://tempuri.org/\">")
                    .append("<UserId>").append(userCode).append("</UserId>" )
                    .append("<LastSyncDate>").append(date).append("</LastSyncDate>" )
                    .append("</getMessages>")
                    .append(SOAP_FOOTER);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String registerGCMOnServer(String usedId, String gcmId)
    {
        StringBuilder strXML = new StringBuilder();
        strXML.append(SOAP_HEADER)
                .append("<updateDeviceId  xmlns=\"http://tempuri.org/\">")
                .append("<UserCode>").append(usedId).append("</UserCode>")
                .append("<GCMKey>").append(gcmId).append("</GCMKey>")
                .append("</updateDeviceId>")
                .append(SOAP_FOOTER) ;


        return strXML.toString();
    }
}
