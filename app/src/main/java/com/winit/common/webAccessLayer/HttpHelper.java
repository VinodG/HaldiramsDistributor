package com.winit.common.webAccessLayer;

import com.winit.common.util.LogUtils;
import com.winit.common.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class HttpHelper {

    private int TIMEOUT_CONNECT_MILLIS = (1*60*1000);
    private int TIMEOUT_READ_MILLIS = (3*60*1000);

    /*
    * It's always better to send response object not inputstream because there will be scenario that
    * we will need some response properties like content length etc.
    */

    public Object sendRequest(String requestUrl, String method, Map<String,String> headers, String postData){
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "sendRequest - Started");
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "requestUrl - "+requestUrl);
        URL url;
        HttpURLConnection connection  = null;

        try {
            url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(TIMEOUT_CONNECT_MILLIS);
            connection.setReadTimeout(TIMEOUT_READ_MILLIS);
            connection.setDoInput(true);
            if(headers != null && headers.size() >0){
                Set<String> keySet = headers.keySet();
                for(String key : keySet)
                    connection.setRequestProperty(key,headers.get(key));
            }
            if(!StringUtils.isEmpty(postData)){
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();
            }

            int httpCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"Response Code - "+httpCode);
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"Response Message - "+resMsg);
            Response response = new Response();
            response.contentLength = connection.getContentLength();
            response.data = connection.getInputStream();
            return response;
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"sendRequest - Ended");
        }
        return null;
    }

    public void close(InputStream inputStream){
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "close - Started");
        if(inputStream != null){
            try {
                inputStream.close();
            }catch (Exception e){
                LogUtils.errorLog(LogUtils.HTTP_HELPER_TAG, e.toString());
                e.printStackTrace();
            }
        }
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "close - Ended");
    }

}
