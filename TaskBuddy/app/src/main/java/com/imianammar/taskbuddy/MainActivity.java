package com.imianammar.taskbuddy;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener,
        TaskAdapter.OnSwipeListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        AppCompatImageView addButton = findViewById(R.id.addButton);

        dbHelper = new TaskDatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(new ArrayList<Task>(), dbHelper, this, this);
        recyclerView.setAdapter(taskAdapter);

        taskAdapter.setTasks(dbHelper.getAllTasks());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        if (direction == ItemTouchHelper.LEFT) {
                            onSwipeLeft(position);
                        } else if (direction == ItemTouchHelper.RIGHT) {
                            onSwipeRight(position);
                        }
                    }

                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        // Set background colors based on the direction
                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            View itemView = viewHolder.itemView;
                            if (dX > 0) {
                                // Swiping right (blue background)
                                ColorDrawable background = new ColorDrawable(getResources().getColor(android.R.color.holo_red_light));
                                background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int) dX, itemView.getBottom());
                                background.draw(c);
                            } else {
                                // Swiping left (red background)
                                ColorDrawable background = new ColorDrawable(getResources().getColor(android.R.color.holo_blue_light));
                                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                                background.draw(c);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.setTasks(dbHelper.getAllTasks());
    }

    @Override
    public void onTaskClick(int position) {
        // Handle task item click (if needed)
    }

    @Override
    public void onSwipeLeft(int position) {
        Task task = taskAdapter.getTaskAtPosition(position);
        if (task != null) {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            intent.putExtra("UPDATE_MODE", true);
            startActivity(intent);
        }
    }

    @Override
    public void onSwipeRight(int position) {
        Task task = taskAdapter.getTaskAtPosition(position);
        if (task != null) {
            dbHelper.deleteTask(task.getId());
            taskAdapter.notifyItemRemoved(position);
            onResume();
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
