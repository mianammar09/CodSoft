package com.imianammar.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method will be called when the alarm triggers
        // You can put your snooze/dismiss logic here

        String action = intent.getStringExtra("action");

        if (action != null) {
            if (action.equals("snooze")) {
                handleSnooze(context);
            } else if (action.equals("dismiss")) {
                handleDismiss(context);
            }
        }
    }

    private void handleSnooze(Context context) {
        // Placeholder for snooze action
        // You should implement your actual snooze logic here
        Toast.makeText(context, "Snooze pressed", Toast.LENGTH_SHORT).show();

        // You may want to reschedule the alarm for a short time in the future
        // using AlarmManager here
    }

    private void handleDismiss(Context context) {
        // Placeholder for dismiss action
        // You should implement your actual dismiss logic here
        Toast.makeText(context, "Dismiss pressed", Toast.LENGTH_SHORT).show();

        // Stop any ongoing alarm sound
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}

