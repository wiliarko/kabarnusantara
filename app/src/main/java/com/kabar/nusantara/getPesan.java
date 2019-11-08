package com.kabar.nusantara;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

public class getPesan extends FirebaseMessagingService {
    private static final String TAG="TEST TEST";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData()!=null){
            sendNotif(remoteMessage);
        }
    }

    private void sendNotif(RemoteMessage remoteMessage){
        Map<String,String>data=remoteMessage.getData();
        String title = data.get("title");
        String content=data.get("message");
        String link=data.get("link");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "id1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(content);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("link",link);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notiBuilder = new  NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID);
        notiBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setColor(24714829);
        notificationManager.notify(1,notiBuilder.build());
    }
}