package com.example.vacationschedulemichaelr.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.vacationschedulemichaelr.R;

public class MyReceiverExcursion extends BroadcastReceiver {

    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent3) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String message = intent3.getStringExtra("mulch");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.baseline_airplane_ticket_24)
                .setContentText(intent3.getStringExtra("mulch"))
                .setContentTitle("Notification").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = "myChannelName";
        String description = "myChannelDescription";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}