package com.winit.common.application;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.winit.common.constant.AppConstants;
import com.winit.common.httpimage.FileSystemPersistence;
import com.winit.common.httpimage.HttpImageManager;
import com.winit.haldiram.R;


public class HaldiramsDistributorApplication extends MultiDexApplication {
	public static String MyLock = "Lock";
	public static Context mContext;

	private HttpImageManager mHttpImageManager;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mContext == null)
			mContext = this;
		mHttpImageManager = new HttpImageManager(
				HttpImageManager.createDefaultMemoryCache(),
				new FileSystemPersistence(AppConstants.CACHE_DIR_PATH));
	}


	public HttpImageManager getHttpImageManager() {
		return mHttpImageManager;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@BindingAdapter("android:src")
	public static void setImageUrl(ImageView imageView, String url) {
		url = "http://bodyo.winitsoftware.com/data/project/" +url;
		final Uri uri = Uri.parse(url);
		Bitmap bitmap=null;
		if (uri!=null){
			bitmap = ((HaldiramsDistributorApplication)mContext).mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(uri, imageView,url));
			if (bitmap!=null){
				imageView.setImageBitmap(bitmap);
			}
			else {
				imageView.setImageResource(R.drawable.popup_bg);
			}
		}
		else{
			imageView.setImageResource(R.drawable.popup_bg);
		}
	}
}
