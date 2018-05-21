package com.winit.haldiram.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.LogUtils;
import com.winit.common.util.NetworkUtility;
import com.winit.haldiram.R;
import com.winit.haldiram.RegisterGCMIntentService;
import com.winit.haldiram.module.splash.ISplashPresenter;
import com.winit.haldiram.module.splash.ISplashView;
import com.winit.haldiram.module.splash.SplashPresenter;

import java.util.ArrayList;

/**
 * Created by Girish Velivela on 11/4/2016.
 */

public class SplashActivity extends BaseActivity implements ISplashView {

    private ISplashPresenter splashPresenter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.splash_activity, flBody, true);
        toolbar.setVisibility(View.GONE);
        splashPresenter = new SplashPresenter(this);
        AppConstants.DATABASE_PATH = getApplication().getFilesDir().toString() + "/";
        initializeControls();
        if(checkPermission())
            initializeSplash();
        if(NetworkUtility.isNetworkConnectionAvailable(SplashActivity.this)) {
            String token = preference.getStringFromPreference(Preference.gcmId, "");
            if (TextUtils.isEmpty(token)) {
                Intent intent = new Intent(SplashActivity.this, RegisterGCMIntentService.class);
                startService(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lockDrawer();
    }

    @Override
    protected void setTypeFace() {

    }

    protected void initializeControls() {
        int width = preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH,0);
        int height = preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT,0);
        if(width == 0 || height == 0){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displaymetrics.widthPixels);
            preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displaymetrics.heightPixels);
            AppConstants.DEVICE_WIDTH 		= 	displaymetrics.widthPixels;
            AppConstants.DEVICE_HEIGHT 		= 	displaymetrics.heightPixels;
        }
        AppConstants.initializeTypeFace();
    }

    private void initializeSplash() {
//        if(ServiceUrls.CLIENT_TEST_MAIN_URL.equalsIgnoreCase(ServiceUrls.GLOBAL_MAIN_URL))
//            showCustomDialog(SplashActivity.this,"Alert","Client Test Environment","OK","","");
//        else if(LogUtils.isLogEnable)
//            showCustomDialog(SplashActivity.this,"Alert","Test Environment","OK","","");
        splashPresenter.holdSplash();
    }

    private final int REQUEST_CODE_ASK_PERMISSIONS = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LogUtils.debug(LogUtils.LOG_TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {
                    for(int grantResult :grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(SplashActivity.this, "All permissions are required.", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    initializeSplash();
                } else {
                    Toast.makeText(SplashActivity.this, "All permissions are required.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            final ArrayList<String> permissions = new ArrayList<>();
/*
*
*  CALL_PHONE
*  CAMERA
*  ACCESS_FINE_LOCATION
* WRITE_EXTERNAL_STORAGE
* */
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CALL_PHONE);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);
            }

            if (permissions.size() > 0) {

                int permissionLength = permissions.size();
                final String[] permissionArray = new String[permissionLength];
                for (int i = 0; i < permissionLength; i++) {
                    permissionArray[i] = permissions.get(i);
                }
                ActivityCompat.requestPermissions(SplashActivity.this, permissionArray, REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void moveToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//        Intent intent = new Intent(SplashActivity.this, DashboardActivtiy.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void moveToDashboard() {
        String locale = preference.getStringFromPreference(Preference.LANGUAGE,"");
        setLocale(locale);
        Intent intent = new Intent(SplashActivity.this, DashboardActivtiy.class);
        startActivity(intent);
        finish();
    }
}
