package com.imianammar.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SetAlarmActivity extends AppCompatActivity {

    private TimePicker alarmTimePicker;
    private Button selectToneButton;
    private Button saveAlarmButton;

    private Uri selectedRingtoneUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        alarmTimePicker = findViewById(R.id.alarmTimePicker);
        selectToneButton = findViewById(R.id.selectToneButton);
        saveAlarmButton = findViewById(R.id.saveAlarmButton);

        selectToneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ringtone picker to select alarm tone
                openRingtonePicker();
            }
        });

        saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the alarm and navigate back to MainActivity
                saveAlarm();
            }
        });
    }

    private void openRingtonePicker() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectedRingtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        }
    }

    private void saveAlarm() {
        // Get the selected time from the time picker
        int hour = alarmTimePicker.getCurrentHour();
        int minute = alarmTimePicker.getCurrentMinute();

        // Convert the selected time to a format suitable for your alarm logic
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Set up an AlarmManager to schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("action", "alarm"); // Add an action to differentiate from other intents
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Use calendar.getTimeInMillis() to set the alarm at the specified time
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Store alarm details in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("Alarms", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("hour", hour);
        editor.putInt("minute", minute);
        editor.apply();
        Intent alarm = new Intent(SetAlarmActivity.this, AlarmManagementActivity.class);
        startActivity(alarm);
        Toast.makeText(this, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }

}

