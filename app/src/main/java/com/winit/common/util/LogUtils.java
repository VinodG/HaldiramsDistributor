package com.winit.common.util;

import android.os.Environment;
import android.util.Log;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.ServiceUrls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

public class LogUtils
{
	public static boolean isLogEnable = true;
	public static String LOG_TAG = "Napco";
	public static String SERVICE_LOG_TAG = "--SERVICE_LOG_TAG--";
	public static String HTTP_HELPER_TAG = "--HTTP_HELPER_TAG--";
	public static String USER_PLANOGRAM_PRESENTER = "--UserPlanogramPresenter--";

	static {
		isLogEnable = !(ServiceUrls.LIVE_MAIN_URL.equalsIgnoreCase(ServiceUrls.MAIN_URL));
	}

	public static void errorLog(String tag, String msg){
		if(isLogEnable)
			Log.e(""+tag, ""+msg);
		if(tag.equalsIgnoreCase("vin"))
			convertRequestToFile(tag+" "+msg);
	}

	public static void infoLog(String tag, String msg){
		if(isLogEnable)
			Log.i(tag, msg);
		if(tag.equalsIgnoreCase("vin"))
			convertRequestToFile(tag+" "+msg);
	}

	public static void debug(String tag, String msg) {
		if(isLogEnable)
			Log.d(tag, msg);
		if(tag.equalsIgnoreCase("vin"))
			convertRequestToFile(tag + " " + msg);

	}

	public static void printMessage(String msg){
		if(isLogEnable)
			System.out.println(msg);
	}

	public static void setLogEnable(boolean isEnable)
	{
		isLogEnable = isEnable;
	}

	/**
	 * This method stores InputStream data into a file at specified location
	 * @param is
	 */
	public static void convertResponseToFile(InputStream is) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(is);
		FileOutputStream fos = new FileOutputStream("/sdcard/Response.xml");
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		byte byt[] = new byte[1024];
		int noBytes;

		while((noBytes = bis.read(byt)) != -1)
			bos.write(byt,0,noBytes);

		bos.flush();
		bos.close();
		fos.close();
		bis.close();
	}

	public static void exactDatabase(){
		if(LogUtils.isLogEnable){
			try {
				InputStream inputStream = new FileInputStream(new File(AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME));
				File dir = new File("/sdcard");
				dir.mkdirs();
				OutputStream outputStream = new FileOutputStream(new File(dir,AppConstants.DATABASE_NAME));
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method stores data in String into a file at specified location
	 * @param is
	 */
	public static void convertRequestToFile(String is){
		try {
			File file = new File("/sdcard/DedugLogs.txt");
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n");
			bw.write(is);
			bw.close();
			fw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/** This method will read data from the inputStream and return as StringBuffer
	 * @param inpStrData
	 */
	public static StringBuffer getDataFromInputStream(InputStream inpStrData){
		if(inpStrData != null)
		{
			try
			{
				BufferedReader brResp = new BufferedReader(new InputStreamReader(inpStrData));
				String stringTemporaryVariable;
				StringBuffer sbResp = new StringBuffer();

				//Converts response as a StringBuffer
				while((stringTemporaryVariable = brResp.readLine()) != null)
					sbResp.append(stringTemporaryVariable);

				brResp.close();
				inpStrData.close();

				return sbResp;
			}
			catch(Exception e)
			{
				LogUtils.errorLog("LogUtils", "Exception occurred in getDataFromInputStream(): "+e.toString());
			}
		}
		return null;
	}

	public static void writeIntoLog(String str,String type){
		try
		{
			File connectionFolder = new File(Environment.getExternalStorageDirectory().toString()+"/type");
			if(!connectionFolder.exists())
				connectionFolder.mkdir();
			FileOutputStream fos = new FileOutputStream(new File(connectionFolder,type+"_"+CalendarUtil.getCurrentDate()+".txt"), true);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(("************************************ "+ CalendarUtil.getDate(new Date(),CalendarUtil.DATE_PATTERN_dd_MMM_YYYY)).getBytes());
			bos.write(str.getBytes());
			bos.write(("************************************ "+ CalendarUtil.getDate(new Date(),CalendarUtil.DATE_PATTERN_dd_MMM_YYYY)).getBytes());

			bos.flush();
			bos.close();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
