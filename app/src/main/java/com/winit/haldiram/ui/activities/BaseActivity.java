package com.winit.haldiram.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.Flip3dAnimation;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.R;
import com.winit.haldiram.ui.dialog.CustomDialog;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Gufran.Khan on 3/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Bind(R.id.flBody) FrameLayout flBody;
    @Bind(R.id.drawer_layout)  DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)  NavigationView navigationView;

    protected TextView tvUserName;
    protected TextView tvUserId;
    // -------------------------- Tool Bar ------------------------
    @Bind(R.id.toolbar)  Toolbar toolbar;
    private TextView tvTitle;
    // -------------------------- Tool Bar End------------------------

    protected LayoutInflater inflater;
    protected Preference preference;
    private ProgressDialog progressdialog;

    protected String className = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(BaseActivity.this));
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);

        //*************************** Tool Bar intialisation **************************/
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        tvUserName = (TextView)header.findViewById(R.id.tvUserName);
        tvUserId = (TextView)header.findViewById(R.id.tvUserCode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.toplogo);
        //*************************** Tool Bar intialisation Ends **************************/

        //*************************** Common intialisation **************************/
        preference = Preference.getInstance();
        inflater = this.getLayoutInflater();
        //*************************** Common intialisation **************************/

        if(savedInstanceState != null){
            AppConstants.DATABASE_PATH = getApplication().getFilesDir().toString() + "/";
            AppConstants.initializeTypeFace();
        }

        setUpNavigationView();
        initialize();
        tvUserName.setText(preference.getStringFromPreference(Preference.USER_NAME,""));
        tvUserId.setText(preference.getStringFromPreference(Preference.USER_ID,""));
        setTypeFace(flBody);

        IntentFilter filtersNew = new IntentFilter();
        filtersNew.addAction(AppConstants.ACTION_MENUCLICK);
        registerReceiver(ActionMenuClick, filtersNew);

    }

    BroadcastReceiver ActionMenuClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((BaseActivity.this instanceof DashboardActivtiy) || (BaseActivity.this instanceof LoginActivity))
                ;
            else
                finish();
        }
    };

    public void menuClickBCRAction() {
        Intent intent = new Intent();
        intent.setAction(AppConstants.ACTION_MENUCLICK);
        sendBroadcast(intent);
    }

    protected abstract void initialize();

    //******************************************************************** App Bar Start **************************************************/

    public void setToolbarLogo(int navigationIcon, int logo){
        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setLogo(logo);
        toolbar.setTitle("");
    }

    public void setToolbarTitle(int navigationIcon, int title){
        toolbar.setNavigationIcon(navigationIcon);
        tvTitle.setText(title);
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void lockDrawer() {
        if(BaseActivity.this instanceof SplashActivity|BaseActivity.this instanceof LoginActivity)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.sales_eco:
                        if(!(BaseActivity.this instanceof SalesEcoActivity)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,SalesEcoActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.collections:
                        if(!(BaseActivity.this instanceof CollectionReportActivity)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,CollectionReportActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.investment_analysis:
                        if(!(BaseActivity.this instanceof InvestmentAnalysisActivity)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,InvestmentAnalysisActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.stock:
                        if(!(BaseActivity.this instanceof StockActivity)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,StockActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.messages:
                        if(!(BaseActivity.this instanceof MessagesActivity)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,MessagesActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.settings:
                        if(!(BaseActivity.this instanceof Settings)){
                            menuClickBCRAction();
                            intent = new Intent(BaseActivity.this,Settings.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.logout:
                        showCustomDialog(BaseActivity.this, getResources().getString(R.string.warning),
                                getResources().getString(R.string.do_you_want_to_logout), getResources().getString(R.string.Yes), getResources().getString(R.string.No), "LOGOUT");
                        break;

                }
//                if(intent != null) {
//                    if (BaseActivity.this instanceof DashboardActivtiy)
//                        startActivity(intent);
//                    else {
//                        finish();
//                        startActivity(intent);
//                    }
//                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    //******************************************************************** App Bar End **************************************************/

    //******************************************************************** TypeFace Start **************************************************/
    private void setTypeFace(ViewGroup group){
        setTypeFace(group,AppConstants.REGULAR);
        setTypeFace();
    }

    public void setTypeFace(ViewGroup group, Typeface typeface) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(typeface);
            else if (v instanceof ViewGroup)
                setTypeFace((ViewGroup) v,typeface);
        }
    }

    protected abstract void setTypeFace();
    //******************************************************************** TypeFace End **************************************************/


//*******************************************************************************************************


    public void showSnackMessage(String message){
        Snackbar snackbar = Snackbar.make(flBody,message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public boolean checkNetworkConnection() {
        if (!NetworkUtility.isNetworkConnectionAvailable(BaseActivity.this)) {
            showCustomDialog(BaseActivity.this, getString(R.string.alert), getString(R.string.No_Network), getString(R.string.OK), "", AppConstants.INTERNET_ERROR,false);
            return false;
        }
        return true;
    }

    public void hideKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clearPreference(){
        preference.removeFromPreference(Preference.IS_LOGGED_IN);
    }

    public Preference getPreference(){
        return preference;
    }

    //for status bar color
    public void setStatusBarColor(){
        Window window = BaseActivity.this.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(BaseActivity.this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void setLocale(String language){
        if(StringUtils.isEmpty(language))
            language = "en";
        preference.saveStringInPreference(Preference.LANGUAGE,language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }


    //******************************************************************** Loader And Dialog **************************************************/
    protected Dialog dialog;
    private Animation rotateXaxis;
    private ImageView ivOutsideImage, ImageView01;
    public boolean isCancelableLoader;
    public void showLoader(String msg) {
        runOnUiThread(new RunShowLoader(msg, ""));
    }

    public void showLoader(String msg, String title) {
        runOnUiThread(new RunShowLoader(msg, title));
    }

    /** For hiding progress dialog (Loader ). **/
//    public void hideLoader() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (progressdialog != null && progressdialog.isShowing())
//                        progressdialog.dismiss();
//                    progressdialog = null;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

//    /* Custom Runnable for showing loaders */
//    class RunShowLoader implements Runnable {
//        private String strMsg;
//        private String title;
//
//        public RunShowLoader(String strMsg, String title) {
//            this.strMsg = strMsg;
//            this.title = title;
//        }
//
//        @Override
//        public void run() {
//            try {
//                if (progressdialog == null
//                        || (progressdialog != null && !progressdialog
//                        .isShowing())) {
//                    progressdialog = ProgressDialog.show(BaseActivity.this,
//                            title, strMsg);
//                } else if (progressdialog == null
//                        || (progressdialog != null && progressdialog
//                        .isShowing())) {
//                    progressdialog.setMessage(strMsg);
//                }
//            } catch (Exception e) {
//                progressdialog = null;
//            }
//        }
//    }


    class RunShowLoader implements Runnable
    {
        private String title;
        private String strMsg;

        public RunShowLoader(String strMsg)
        {
            this.strMsg = strMsg;
        }
        public RunShowLoader(String strMsg, String title)
        {
            this.strMsg = strMsg;
            this.title = title;
        }

        @Override
        public void run()
        {
            try
            {
                if(dialog == null)
                    dialog = new Dialog(BaseActivity.this, R.style.Theme_Dialog_Translucent);

                dialog.setContentView(R.layout.loading);

                if(!isCancelableLoader)
                    dialog.setCancelable(false);
                else
                    dialog.setCancelable(true);


                try {
                    dialog.show();
                } catch(Exception e){
                    e.printStackTrace();
                }

                ivOutsideImage = (ImageView) dialog.findViewById(R.id.ivOutsideImage);
                ImageView01 = (ImageView) dialog.findViewById(R.id.ImageView01);

                TextView tvLoading = (TextView)dialog.findViewById(R.id.tvLoading);
                if(!strMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strMsg);
                else
                    tvLoading.setVisibility(View.GONE);
                rotateXaxis = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.rotate_x_axis);
                rotateXaxis.setInterpolator(new LinearInterpolator());

                ivOutsideImage.setAnimation(rotateXaxis);
                applyRotation(0, 360);
            }
            catch(Exception e)
            {}
        }
    }

    public void  hideLoader()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if(dialog != null && dialog.isShowing())
                        dialog.dismiss();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void applyRotation(final float start, final float end) {

        BitmapDrawable bd=(BitmapDrawable) BaseActivity.this.getResources().getDrawable(R.drawable.progress_mid_icon);
        float centerY = bd.getBitmap().getHeight()/2.0f;
        float centerX = bd.getBitmap().getWidth()/2.0f;

        final Flip3dAnimation rotation = new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new LinearInterpolator());
        ImageView01.startAnimation(rotation);
    }

    public void showAlert(String message, String from){
        showCustomDialog(BaseActivity.this, getString(R.string.alert), message,getString(R.string.OK), "", from);
    }

    /** Method to Show the alert dialog **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from){
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage,firstBtnName, secondBtnName, from, true));
    }

    /** Method to Show the alert dialog **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable){
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage,firstBtnName, secondBtnName, from, isCancelable));
    }

    // For showing Dialog message.
    private class ShowCustomDialogs implements Runnable{
        private CustomDialog customDialog;
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private boolean isCancelable=false;
        private View.OnClickListener posClickListener;
        private View.OnClickListener negClickListener;

        public ShowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable){
            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.firstBtnName 	= firstBtnName;
            this.secondBtnName	= secondBtnName;
            this.isCancelable 	= isCancelable;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @Override
        public void run() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
//                final AlertDialog alertDialog = builder.create();
//                alertDialog.setTitle(strTitle);
//                alertDialog.setMessage(strMessage);
//                alertDialog.setCancelable(isCancelable);
//                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, firstBtnName, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        onButtonYesClick(from);
//                    }
//                });
//                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
//                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, secondBtnName, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            onButtonNoClick(from);
//                        }
//                    });
//                }
//                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface arg0) {
//                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_color));
//                        Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//                        if(button != null) {
//                            button.setTextColor(getResources().getColor(R.color.app_color));
//                        }
//                    }
//                });
//                alertDialog.show();
//            } else
                {
                if (customDialog != null && customDialog.isShowing())
                    customDialog.dismiss();

                View view = inflater.inflate(R.layout.custom_common_popup, null);
                customDialog = new CustomDialog(BaseActivity.this, view, preference
                        .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                customDialog.setCancelable(isCancelable);
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitlePopup);
                View ivDivider = view.findViewById(R.id.ivDividerPopUp);
                View view_middle = view.findViewById(R.id.view_middle);
                TextView tvMessage = (TextView) view.findViewById(R.id.tvMessagePopup);
                Button btnYes = (Button) view.findViewById(R.id.btnYesPopup);
                Button btnNo = (Button) view.findViewById(R.id.btnNoPopup);

                tvTitle.setTypeface(AppConstants.MEDIUM);
                tvMessage.setTypeface(AppConstants.REGULAR);
                btnYes.setTypeface(AppConstants.MEDIUM);
                btnNo.setTypeface(AppConstants.MEDIUM);
                if (!StringUtils.isEmpty(strTitle)) {
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tvTitle.setText("" + strTitle);
                } else {
                    tvTitle.setVisibility(View.GONE);
                    ivDivider.setVisibility(View.GONE);
                }
                tvMessage.setText("" + strMessage);
                btnYes.setText("" + firstBtnName);

                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase(""))
                    btnNo.setText("" + secondBtnName);
                else {
                    btnNo.setVisibility(View.GONE);
                    view_middle.setVisibility(View.GONE);
                }

                if (posClickListener == null)
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            onButtonYesClick(from);
                        }
                    });
                else
                    btnYes.setOnClickListener(posClickListener);

                if (negClickListener == null)
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            onButtonNoClick(from);
                        }
                    });
                else
                    btnNo.setOnClickListener(negClickListener);
                try {
                    if (!customDialog.isShowing())
                        customDialog.showCustomDialog();
                } catch (Exception e) {
                }
            }
        }
    }

    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase("LOGOUT")){
            clearPreference();
            Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
            finish();
        }

    }

    public void onButtonNoClick(String from) {

    }

//    public void syncData(SyncProcessListner syncProcessListner){
//        if(!SyncData.isRunning){
//            SyncData.setListner(syncProcessListner);
//            Intent uploadTraIntent=new Intent(this,SyncData.class);
//            startService(uploadTraIntent);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(ActionMenuClick);
        }
        catch (IllegalArgumentException e) {
        }
        catch (Exception e) {
        }
    }


}
