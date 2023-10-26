package com.imianammar.alarms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class CustomAlarmAdapter extends ArrayAdapter<AlarmItem> {

    private Context context;
    private List<AlarmItem> alarmList;

    public CustomAlarmAdapter(Context context, int resource, List<AlarmItem> alarmList) {
        super(context, resource, alarmList);
        this.context = context;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.alarm_item_layout, parent, false);
        }

        TextView alarmNameText = convertView.findViewById(R.id.alarmNameText);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        AlarmItem alarm = alarmList.get(position);

        alarmNameText.setText(alarm.getName());

        editButton.setOnClickListener(v -> {
            // Handle edit button click
            // For example, you can open an edit activity with the alarm details
            Intent intent = new Intent(context, EditAlarmActivity.class);
            intent.putExtra("alarmName", alarm.getName());
            context.startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            // Handle delete button click
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Alarm")
                    .setMessage("Are you sure you want to delete this alarm?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete the alarm and update the list
                        deleteAlarm(position);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        return convertView;
    }

    private void deleteAlarm(int position) {
        // Remove the alarm at the specified position
        alarmList.remove(position);

        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }

}
