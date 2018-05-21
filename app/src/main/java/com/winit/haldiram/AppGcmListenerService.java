package com.winit.haldiram;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.gcm.GcmListenerService;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.ui.activities.MessagesActivity;

/**
 * Created by Srikanth on 18-05-2017.
 */
public class AppGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    public static int NOTIFICATION_ID = 10000;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString("title");
        String message = data.getString("message");
        int messageType = StringUtils.getInt(data.getString("MessageType"));
        title = title==null? "HAI":title;
        message = message==null? "":message;
        generateNotification(title, message,messageType);
    }

    private void generateNotification(String title,String message,int messageType) {
        ActivityManager activityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
        String className = componentName.getShortClassName();
        if(className.contains("MessageConversationDetail")){
            //refresh
            Intent intent = new Intent(AppConstants.ACTION_NEW_MESSAGE);
            sendBroadcast(intent);
        }
        else{
            //show notification
            Intent intent = new Intent(this, MessagesActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

            Notification noti = new Notification.Builder(this).setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject").setSmallIcon(R.drawable.app_logo)
                    .setContentIntent(pIntent)
                    .addAction(R.drawable.app_logo, "Call", pIntent)
                    .addAction(R.drawable.app_logo, "More", pIntent)
                    .addAction(R.drawable.app_logo, "And more", pIntent).build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);
        }
        if((message != null && !TextUtils.isEmpty(message)) || (title != null && !TextUtils.isEmpty(title)))
        {
//            Intent i = new Intent();
//            i.setClass(this, NotificationActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.putExtra("message", message);
//            i.putExtra("title", title);
//            i.putExtra("messageType", messageType);
//            startActivity(i);
        }

    }
}
