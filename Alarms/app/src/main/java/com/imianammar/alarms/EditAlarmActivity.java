package com.imianammar.alarms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditAlarmActivity extends AppCompatActivity {

    private EditText alarmNameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        alarmNameEditText = findViewById(R.id.alarmNameEditText);
        saveButton = findViewById(R.id.saveButton);

        // Get the alarm name from the intent (if it was passed)
        String alarmName = getIntent().getStringExtra("alarmName");
        alarmNameEditText.setText(alarmName);

// Assuming alarmNameEditText and saveButton are defined in your layout XML
        EditText alarmNameEditText = findViewById(R.id.alarmNameEditText);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {

            String updatedAlarmName = alarmNameEditText.getText().toString();
            AlarmManagementActivity Am = new AlarmManagementActivity();
            Am.addNewAlarm(updatedAlarmName, true);


            SharedPreferences sharedPreferences = getSharedPreferences("AlarmPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("alarmName", updatedAlarmName);
            editor.apply();

            finish();
        });

    }
}
