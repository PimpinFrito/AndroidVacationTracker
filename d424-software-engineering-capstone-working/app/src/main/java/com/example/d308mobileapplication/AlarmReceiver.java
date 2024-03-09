package com.example.d308mobileapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.d308mobileapplication.Activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String channelID = "Channel_ID_Notification";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelID);

        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        builder.setSmallIcon(R.drawable.baseline_calendar_today_24);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            NotificationChannel channel = notificationManager.getNotificationChannel(channelID);
            if(channel == null){
                int priority = NotificationManager.IMPORTANCE_HIGH;
                channel = new NotificationChannel(channelID
                        ,"Channel for date notifications"
                        , priority);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }
        }

        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        notificationManager.notify(101, builder.build());
    }
}
