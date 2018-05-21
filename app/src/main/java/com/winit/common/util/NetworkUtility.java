package com.winit.common.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description Class : Checking Network Connections
 * @author Neeraj
 *
 */
public class NetworkUtility 
{
	/**
	 * Method to check Network Connections 
	 * @param context
	 * @return boolean value
	 */
	public static boolean isNetworkConnectionAvailable(Context context)
	{
		boolean isNetworkConnectionAvailable = false;
		
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(activeNetworkInfo != null) 
		{
		    isNetworkConnectionAvailable = activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
		}
		return isNetworkConnectionAvailable;
	}
	
	public static boolean isWifiConnected(Context context)
	{
		boolean isNetworkConnectionAvailable = false;
		ConnectivityManager connManager = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			isNetworkConnectionAvailable = true;
		}
		return isNetworkConnectionAvailable;
	}


	public static boolean isGPSEnable(Context context)
	{
		LocationManager lm 			= 	(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try
		{
			boolean gps_enabled		=	lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

			if(!gps_enabled)
				return false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return true;
	}
	
	
}
