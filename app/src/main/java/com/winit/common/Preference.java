package com.winit.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.winit.common.application.HaldiramsDistributorApplication;


@SuppressLint("CommitPrefEdits") public class Preference
{

	private static SharedPreferences preferences;
	private static  SharedPreferences.Editor edit;
	private static Preference preference;

	public static final String DEVICE_DISPLAY_WIDTH 			=	"DEVICE_DISPLAY_WIDTH";
	public static final String DEVICE_DISPLAY_HEIGHT 			=	"DEVICE_DISPLAY_HEIGHT";
	public static final String IS_LOGGED_IN 					=	"IS_LOGGED_IN";
	public static final String USER_ID	 						=	"USER_ID";
	public static final String PASSWORD	 						=	"PASSWORD";
	public static final String USER_NAME 						=	"USER_NAME";
	public static final String LANGUAGE 						=	"LANGUAGE";
	public static final String REMEMBER_ME 						=	"REMEMBER_ME";
	public static final String TEMP_EMP_NO 						=	"TEMP_EMP_NO";
	public static final String EMP_NO 							=	"EMP_NO";
	public static final String SYNC_STATUS 						=	"SYNC_STATUS";
	public static final String LAST_SYNC_TIME 					=	"LAST_SYNC_TIME";
	public static final String SQLITE_DATE 						=	"SQLITE_DATE";

	public static String gcmId 							=	"gcmId";


	private Preference(Context context){
		if(preferences ==  null)
			preferences		=	PreferenceManager.getDefaultSharedPreferences(context);
		if(edit ==  null)
			edit			=	preferences.edit();
	}

	public static Preference getInstance(){
		if(preference == null)
			return preference = new Preference(HaldiramsDistributorApplication.mContext);
		else
			return preference;
	}

	public void saveStringInPreference(String strKey,String strValue){
		edit.putString(strKey, strValue);
		commitPreference();
	}

	public void saveIntInPreference(String strKey,int value){
		edit.putInt(strKey, value);
		commitPreference();
	}

	public void saveBooleanInPreference(String strKey,boolean value){
		edit.putBoolean(strKey, value);
		commitPreference();
	}

	public void saveLongInPreference(String strKey,Long value){
		edit.putLong(strKey, value);
		commitPreference();
	}

	public void saveDoubleInPreference(String strKey,String value){
		edit.putString(strKey, value);
		commitPreference();
	}

	public void removeFromPreference(String strKey){
		edit.remove(strKey);
		commitPreference();
	}

	private void commitPreference()
	{
		edit.commit();
	}

	public String getStringFromPreference(String strKey,String defaultValue ){
		return preferences.getString(strKey, defaultValue);
	}

	public boolean getbooleanFromPreference(String strKey,boolean defaultValue){
		return preferences.getBoolean(strKey, defaultValue);
	}

	public int getIntFromPreference(String strKey,int defaultValue){
		return preferences.getInt(strKey, defaultValue);
	}

	public double getDoubleFromPreference(String strKey,double defaultValue){
		return	Double.parseDouble(preferences.getString(strKey, ""+defaultValue));
	}

	public long getLongInPreference(String strKey){
		long value = 0;
		try{
			value = preferences.getLong(strKey, 0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}

	public void clearPreferences() {
		edit.clear();
		edit.commit();
	}

}
