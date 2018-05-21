package com.winit.common.constant;

import android.graphics.Typeface;
import android.os.Environment;

import com.winit.common.application.HaldiramsDistributorApplication;

/*
*
* This class has constants that used thourgh out the application.
*
* There are group constants with commented title.
* Declare a new constant in that group.
*
*  */
public class AppConstants 
{
	//Database Constants Start
	public static String DATABASE_PATH 	= "";
	public static String DATABASE_NAME 	= "DistributorSqlite.sqlite";
	public static final int DB_INSERT_SUCCESS=1;
	public static final int DB_INSERT_FAIL=-1;

	public static String ACTION_MENUCLICK			=	"com.winit.haldiramsdistributor.ACTION_MENUCLICK";
	public static String ACTION_NEW_MESSAGE			=	"com.winit.haldiramsdistributor.ACTION_NEW_MESSAGE";

	public static int DEVICE_WIDTH= 0;
	public static int DEVICE_HEIGHT= 0;
	//Database Constants End

	// Typface Constant Start
	public static Typeface REGULAR, BOLD, MEDIUM, LIGHT, THIN;
	// Typface Constant End


	public static String STORAGE_PATH					= Environment.getExternalStorageDirectory() + "/.EConnect";
	public static String CACHE_DIR_PATH					= Environment.getExternalStorageDirectory() + "/.EConnectFiles/Cache";

	public static String INTERNET_ERROR = "internet_error";
	public static String getMessage = "getMessage";
	public static String noNewMessage = "noNewMessage";

	public static void initializeTypeFace() {
		try {
			REGULAR = Typeface.createFromAsset(HaldiramsDistributorApplication.mContext.getAssets(), "Roboto_Regular.ttf");
			BOLD = Typeface.createFromAsset(HaldiramsDistributorApplication.mContext.getAssets(), "Roboto_Bold.ttf");
			MEDIUM = Typeface.createFromAsset(HaldiramsDistributorApplication.mContext.getAssets(), "Roboto_Medium.ttf");
			LIGHT = Typeface.createFromAsset(HaldiramsDistributorApplication.mContext.getAssets(), "Roboto_Light.ttf");
			THIN = Typeface.createFromAsset(HaldiramsDistributorApplication.mContext.getAssets(), "Roboto_Thin.ttf");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
