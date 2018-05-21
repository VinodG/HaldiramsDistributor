package com.winit.common.util;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.winit.common.Preference;
import com.winit.common.databaseaccess.WinitDB;
import com.winit.common.webAccessLayer.BuildXMLRequest;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.RestCilent;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataaccesslayer.SyncLogDA;
import com.winit.haldiram.dataobject.SyncLogDO;
import com.winit.haldiram.parser.AllDataSyncParser;

import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SyncData extends IntentService{private Preference preference;
	private static SyncProcessListner syncProcessListner;
//	private LastSyncDO lastSyncDO;
	public static boolean isRunning=false;

	public SyncData() {
		super("SyncDataService");
	}
	public static void setListner(SyncProcessListner listner){
		syncProcessListner=listner;
	}
	public static void removeListner(){
		syncProcessListner=null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		isRunning=true;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		preference 						 = 	Preference.getInstance();
		preference.saveIntInPreference(Preference.SYNC_STATUS, 0);
//		syncDeletedLogs();
		loadIncrementalData();
	}

	private SyncLogDO lastSyncDO;
	public void loadIncrementalData(){
		try {

			Preference preference = Preference.getInstance();
			WinitDB.getDefaultInstance().executeTransaction(new WinitDB.DBTaskListener() {
				@Override
				public void executeTask(SQLiteDatabase sqLiteDatabase) {
					lastSyncDO = new SyncLogDA().getSyncLog(ServiceUrls.GetDistributorDataSync);
				}

				@Override
				public void onError() {
					stopSelf();
				}
			});
			if(lastSyncDO != null) {
				AllDataSyncParser allDataSyncParser = new AllDataSyncParser( syncProcessListner);

			/** 1) For sync we not using the Http service because for parser we need to send SYNCPROCESSLISTNER
			* 2) Master table and sync both are having same service.*/

				String syncRequest = BuildXMLRequest.syncDataRequest(preference.getStringFromPreference(Preference.USER_ID,""), lastSyncDO.lsd+"",  lastSyncDO.lst+"");
//						BuildXMLRequest.syncDataRequest(preference.getStringFromPreference(Preference.USER_ID,""), lastSyncDO.lsd+"",  lastSyncDO.lst+"");
//						BuildXMLRequest.syncDataRequest(headerDO, 1+"",  1+"");
				LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
				LogUtils.infoLog("Request Format : ", "" + syncRequest);
				LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
				Response httpResponseDO = (Response) new RestCilent().processRequest(ServiceUrls.ServiceAction.SYNC_TABLE, syncRequest);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse((InputStream) httpResponseDO.data, allDataSyncParser);
				final Response response = (Response) allDataSyncParser.getData();
				if(response != null && response.status.equalsIgnoreCase(Response.SUCCESS)){
					if(response.data instanceof SyncLogDO) {
						WinitDB.getDefaultInstance().executeTransaction(new WinitDB.DBTaskListener() {
							@Override
							public void executeTask(SQLiteDatabase sqLiteDatabase) {
								SyncLogDO syncLogDO = (SyncLogDO)(response.data);
								syncLogDO.action="Success";
								syncLogDO.entity=ServiceUrls.GetDistributorDataSync;
								syncLogDO.timeStamp = CalendarUtil.getCurrentDateWithTime();
								 new SyncLogDA().insertSyncLog(syncLogDO);
							}

							@Override
							public void onError() {
								stopSelf();
							}
						});
					}
					if(syncProcessListner != null){
						syncProcessListner.end();
					}
				}else if(response != null && syncProcessListner != null){
					syncProcessListner.error(response.message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (syncProcessListner != null)
			syncProcessListner.error("");
	}

	public void syncDeletedLogs(){
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public interface SyncProcessListner{
		public void setProgress(String msg);
		public void error(String message);
		public void end();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning=false;
	}
}
