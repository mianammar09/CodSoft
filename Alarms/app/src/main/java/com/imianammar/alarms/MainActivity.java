package com.imianammar.alarms;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currentTimeText;
    private Button setAlarmButton, list;
    private final Handler handler = new Handler();
    private final int delay = 1000; //milliseconds

    private final Runnable updateTimeTask = new Runnable() {
        public void run() {
            updateCurrentTime();
            handler.postDelayed(this, delay);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTimeText = findViewById(R.id.currentTimeText);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        list = findViewById(R.id.list); // Initialize the 'list' button

        handler.postDelayed(updateTimeTask, delay);

        updateCurrentTime();

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmManagementActivity.class);
                startActivity(intent);
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the SetAlarmActivity
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateCurrentTime() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format the time to a string
        String currentTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        // Set the formatted time to the TextView
        currentTimeText.setText(currentTime);
    }

}
