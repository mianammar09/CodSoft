package com.imianammar.quoteland;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class QuoteNotificationService extends IntentService {

    public QuoteNotificationService() {
        super("QuoteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Code to generate a notification
        sendNotification();
    }

    private void sendNotification() {
        // Create an Intent to open the MainActivity when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "QuoteNotificationChannel")
                .setContentTitle("New Quote Available")
                .setContentText("Tap to view")
                .setSmallIcon(R.drawable.pen_book_learning_education_logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Check if Android version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("QuoteNotificationChannel",
                    "Quote Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for Daily Quotes");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());
    }
}

