package com.winit.common.webAccessLayer;


import android.widget.Toast;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.util.LogUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class ServiceUrls {


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";



//    localser
//    public static final String MAIN_URL                     = "http://10.20.53.80/Napco/Service/NapCoServices.asmx";

//    TEST
//    public static String MAIN_URL 	 	 				= 	"http://haldiramchennai.winitsoftware.com/Services.asmx";
//
//    public static final String MAIN_GLOBAL_URL 			= 	"http://haldiramchennai.winitsoftware.com/Services.asmx";
//    public static String IMAGE_GLOBAL_URL      			=	"http://haldiramchennai.winitsoftware.com/";
//    public static String IMAGE_GLOBAL_URL_FULL 			=	"http://haldiramchennai.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
//    public static final String SURVEY_HOST_URL 			= 	"http://haldiramchennai.winitsoftware.com/api/survey/";
//    public static String IMAGE_GLOBAL_URL_FOR_SURVEY	=	"http://haldiramchennai.winitsoftware.com/uploadfile/upload.aspx";
//    public static final String SERVER_IP_ADDRESS 		= 	"http://haldiramchennai.winitsoftware.com/";
//    public static final String EMPTY_SQLITE_URL 		= 	"http://haldiramchennai.winitsoftware.com/Data/SqliteDb/SalesMan.zip";
//    public static final String UPLOAD_DB 				=   "http://haldiramchennai.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
//    public static final String VERSION_MANAGEMENT		=	"http://haldiramchennai.winitsoftware.com/Data/APKFiles/";
/*
    public static String MAIN_URL 	 	 				= 	"http://haldiramchennailive.winitsoftware.com/Services.asmx";

    public static final String MAIN_GLOBAL_URL 			= 	"http://haldiramchennailive.winitsoftware.com/Services.asmx";
    public static String IMAGE_GLOBAL_URL      			=	"http://haldiramchennailive.winitsoftware.com/";
    public static String IMAGE_GLOBAL_URL_FULL 			=	"http://haldiramchennailive.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
    public static final String SURVEY_HOST_URL 			= 	"http://haldiramchennailive.winitsoftware.com/api/survey/";
    public static String IMAGE_GLOBAL_URL_FOR_SURVEY	=	"http://haldiramchennailive.winitsoftware.com/uploadfile/upload.aspx";
    public static final String SERVER_IP_ADDRESS 		= 	"http://haldiramchennailive.winitsoftware.com/";
    public static final String EMPTY_SQLITE_URL 		= 	"http://haldiramchennailive.winitsoftware.com/Data/SqliteDb/SalesMan.zip";
    public static final String UPLOAD_DB 				=   "http://haldiramchennailive.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
    public static final String VERSION_MANAGEMENT		=	"http://haldiramchennailive.winitsoftware.com/Data/APKFiles/";
*/
//--------------------Test dehli-------------------
  public static String MAIN_URL 	 	 				= 	"http://haldiramsfa.winitsoftware.com/services.asmx";

    public static final String MAIN_GLOBAL_URL 			= 	"http://haldiramsfa.winitsoftware.com/Services.asmx";
    public static String IMAGE_GLOBAL_URL      			=	"http://haldiramsfa.winitsoftware.com/";
    public static String IMAGE_GLOBAL_URL_FULL 			=	"http://haldiramsfa.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
    public static final String SURVEY_HOST_URL 			= 	"http://haldiramsfa.winitsoftware.com/api/survey/";
    public static String IMAGE_GLOBAL_URL_FOR_SURVEY	=	"http://haldiramsfa.winitsoftware.com/uploadfile/upload.aspx";
    public static final String SERVER_IP_ADDRESS 		= 	"http://haldiramsfa.winitsoftware.com/";
    public static final String EMPTY_SQLITE_URL 		= 	"http://haldiramsfa.winitsoftware.com/Data/SqliteDb/SalesMan.zip";
    public static final String UPLOAD_DB 				=   "http://haldiramsfa.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
    public static final String VERSION_MANAGEMENT		=	"http://haldiramsfa.winitsoftware.com/Data/APKFiles/";

 //---------------------------live Delhi
////
// public static String MAIN_URL 	 	 				= 	"http://haldiramlive.winitsoftware.com/services.asmx";
//
//    public static final String MAIN_GLOBAL_URL 			= 	"http://haldiramlive.winitsoftware.com/Services.asmx";
//    public static String IMAGE_GLOBAL_URL      			=	"http://haldiramlive.winitsoftware.com/";
//    public static String IMAGE_GLOBAL_URL_FULL 			=	"http://haldiramlive.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
//    public static final String SURVEY_HOST_URL 			= 	"http://haldiramlive.winitsoftware.com/api/survey/";
//    public static String IMAGE_GLOBAL_URL_FOR_SURVEY	=	"http://haldiramlive.winitsoftware.com/uploadfile/upload.aspx";
//    public static final String SERVER_IP_ADDRESS 		= 	"http://haldiramlive.winitsoftware.com/";
//    public static final String EMPTY_SQLITE_URL 		= 	"http://haldiramlive.winitsoftware.com/Data/SqliteDb/SalesMan.zip";
//    public static final String UPLOAD_DB 				=   "http://haldiramlive.winitsoftware.com/uploadfile/upload.aspx?Module=%s";
//    public static final String VERSION_MANAGEMENT		=	"http://haldiramlive.winitsoftware.com/Data/APKFiles/";


    //    Live URL's
    public static final String LIVE_MAIN_URL              = ""; //http://172.16.9.23/NapCoSupervisorSolution/Service/NapCoServices.asmx
//    public static final String CLIENT_TEST_MAIN_URL       = "http://bodyo.winitsoftware.com/";
/*    public static final String GLOBAL_MAIN_URL            = "http://172.16.9.23/NapCoSupervisorSolution";
    public static final String MAIN_URL                   = "http://172.16.9.23/NapCoSupervisorSolution/Service/NapCoServices.asmx";*/

    public static final String GET_MESSAGE 				=	"getMessages";
    public static final String GetDistributorDataSync 	=	"GetDistributorDataSync";
    // always order need to be followed
    public enum ServiceAction {
        NONE("",false,"",""),
        LOGIN("CheckLogin",false,METHOD_POST),
        MASTER_TABLE("GetDistributorDataFile",false,METHOD_POST),
        SYNC_TABLE("GetDistributorDataSync",false,METHOD_POST),
        INSERT_MESSAGE("InsertMessage",false,METHOD_POST),
        GET_MESSAGE("getMessages",false,METHOD_POST),
        UPDATE_DEVICE_ID("updateDeviceId",false,METHOD_POST);

        private String value;
        private String url;
        private String method;
        private boolean isPostEnable;
        private String ACTION_URI                   = "http://tempuri.org/";

        ServiceAction(String value, boolean isPostEnable,String url,String method) {
            this.value = value;
            this.url = url;
            this.method = method;
            this.isPostEnable = isPostEnable;
        }

        ServiceAction(String value, boolean isPostEnable,String method) {
            this.value = value;
            this.method = method;
            this.isPostEnable = isPostEnable;
        }

        public boolean getIsPostEnable() {
            return isPostEnable;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public String getSoapAction() {
            return ACTION_URI+value;
        }

        private static final Map<String, ServiceAction> hmSoapActions = new LinkedHashMap<>();

        static {
            for(ServiceAction soapAction : ServiceAction.values()){
                if(LogUtils.isLogEnable && hmSoapActions.containsKey(soapAction.value)){
                    Toast.makeText(HaldiramsDistributorApplication.mContext,"Duplicate SoapAction "+soapAction.value,Toast.LENGTH_LONG).show();
                }
                hmSoapActions.put(soapAction.value,soapAction);
            }
        }
    }

}
