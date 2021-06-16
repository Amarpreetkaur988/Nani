package com.omninos.nani.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.omninos.nani.R;
import com.omninos.nani.activity.StartActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;
    NotificationChannel mChannel;
    Notification notification;
    Uri defaultSound, uri;
    Intent intent;
    String title, MessageSubTitle, messageType, categoryId, categoryTitle;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        title = remoteMessage.getData().get("title");
        messageType = remoteMessage.getData().get("message");
        MessageSubTitle = remoteMessage.getData().get("subtitle");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setOreoNotification(title, MessageSubTitle, messageType);
        } else {
            showNotification(title, MessageSubTitle, messageType);
        }
    }

    private void showNotification(String title, String MessageSubTitle, String messageType) {
        intent = new Intent(this, StartActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification = new NotificationCompat.Builder(this)
                .setContentText(MessageSubTitle)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.drawable.logo_splash)
                .setSound(defaultSound)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void setOreoNotification(String title, String MessageSubTitle, String messageType) {
        intent = new Intent(this, StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Sets an ID for the notification, so it can be updated.
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "nani_app";// The user-visible name of the channel.

        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        // Create a notification and set the notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(MessageSubTitle)
                    .setSmallIcon(R.drawable.logo_splash)
                    .setSound(defaultSound)
                    .setStyle(new Notification.BigTextStyle())
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();
        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

        // Issue the notification.
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

}
