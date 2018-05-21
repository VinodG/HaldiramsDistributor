package com.winit.common.webAccessLayer;

import com.winit.common.util.CalendarUtil;
import com.winit.common.util.LogUtils;
import com.winit.haldiram.parser.BaseHandler;

import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class HttpService
{
    private HttpListener httpListener;
    private ServiceUrls.ServiceAction serviceAction;
    private String requestUrl;
    private String request;

    public void executeAsyncTask(ServiceUrls.ServiceAction soapAction, String request, HttpListener httpListener){
        this.serviceAction = soapAction;
        this.request = request;
        this.httpListener = httpListener;
        Thread  httpThread = new Thread(new HttpRunnable());
        httpThread.start();
    }

    public void executeTask(ServiceUrls.ServiceAction soapAction, String request, HttpListener httpListener){
        this.serviceAction = soapAction;
        this.request = request;
        this.httpListener = httpListener;
        execute();
    }

    public void executeTask(String requestUrl,HttpListener httpListener){
        this.requestUrl = requestUrl;
        this.httpListener = httpListener;
        execute();
    }

    private class HttpRunnable implements Runnable{
        @Override
        public void run() {
            execute();
        }
    }

    private void execute() {
        Response response             = new Response("Failure","Unable to connect server, please try again.",null);
        try {
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ "+ CalendarUtil.getDate(new Date(),CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
            LogUtils.infoLog("Request Format : ", "" + request);
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ "+ CalendarUtil.getDate(new Date(),CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));

            Response httpResponseDO = (Response) new RestCilent().processRequest(serviceAction,request);
            if (httpResponseDO != null && httpResponseDO.data != null){
                BaseHandler baseHandler = BaseHandler.getHandler(serviceAction);
                if(baseHandler != null) {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    saxParser.parse((InputStream)httpResponseDO.data, baseHandler);
                    response = (Response) baseHandler.getData();
                }else
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "BaseHandler  null");
            }else
                LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, " InputStream is NULL ");
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.errorLog(LogUtils.SERVICE_LOG_TAG, e.getMessage());
        }finally {
            if(httpListener != null)
                httpListener.onResponseReceived(response);
        }
    }

    public interface HttpListener {
        void onResponseReceived(Response response);
    }

}