package com.imianammar.taskbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText, taskDetailsEditText;
    private Button saveButton;
    private TaskDatabaseHelper dbHelper;
    private boolean updateMode = false;
    private Task taskToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        taskDetailsEditText = findViewById(R.id.taskDetailsEditText);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new TaskDatabaseHelper(this);

        if (getIntent().getBooleanExtra("UPDATE_MODE", false)) {
            int taskId = getIntent().getIntExtra("TASK_ID", -1);
            if (taskId != -1) {
                taskToUpdate = dbHelper.getTask(taskId);
                if (taskToUpdate != null) {
                    taskNameEditText.setText(taskToUpdate.getName());
                    taskDetailsEditText.setText(taskToUpdate.getDetails());
                    updateMode = true;
                }
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameEditText.getText().toString();
                String taskDetails = taskDetailsEditText.getText().toString();

                if (updateMode && taskToUpdate != null) {
                    taskToUpdate.setName(taskName);
                    taskToUpdate.setDetails(taskDetails);
                    dbHelper.updateTask(AddTaskActivity.this, taskToUpdate); // Pass the context
                } else {
                    Task task = new Task(taskName, taskDetails);
                    dbHelper.addTask(task);
                }

                finish();
            }
        });
    }
}
