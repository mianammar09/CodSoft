package com.imianammar.alarms;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AlarmManagementActivity extends AppCompatActivity {

    private ListView alarmListView;
    private List<AlarmItem> alarmList;
    private CustomAlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_management);

        alarmListView = findViewById(R.id.alarmList);

        // Initialize the list of alarms
        alarmList = new ArrayList<>();
        // Add some sample alarms (you would fetch from a database or preferences)
        alarmList.add(new AlarmItem("Alarm 1", true));

        // Set up the list adapter
        adapter = new CustomAlarmAdapter(this, R.layout.alarm_item_layout, alarmList);
        alarmListView.setAdapter(adapter);
        alarmList = new ArrayList<>();

    }

    public void addNewAlarm(String alarmName, boolean isEnabled) {
        alarmList.add(new AlarmItem(alarmName, isEnabled));
        adapter.notifyDataSetChanged();
    }

    // Placeholder for editing alarms
    private void editAlarm(int position, String newAlarmName, boolean newIsEnabled) {
        AlarmItem alarm = alarmList.get(position);
        alarm.setName(newAlarmName);
        alarm.setEnabled(newIsEnabled);
        adapter.notifyDataSetChanged();
    }

    // Placeholder for deleting alarms
    private void deleteAlarm(int position) {
        alarmList.remove(position);
        adapter.notifyDataSetChanged();
    }
}
