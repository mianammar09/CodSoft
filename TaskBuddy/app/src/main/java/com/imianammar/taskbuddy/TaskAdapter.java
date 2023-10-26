package com.imianammar.taskbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private TaskDatabaseHelper dbHelper;
    private OnTaskClickListener onTaskClickListener;
    private OnSwipeListener onSwipeListener;

    public TaskAdapter(List<Task> tasks, TaskDatabaseHelper dbHelper, OnTaskClickListener onTaskClickListener, OnSwipeListener onSwipeListener) {
        this.tasks = tasks;
        this.dbHelper = dbHelper;
        this.onTaskClickListener = onTaskClickListener;
        this.onSwipeListener = onSwipeListener;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final Task task = tasks.get(position);
        holder.taskNameTextView.setText(task.getName());
        holder.taskDetailsTextView.setText(task.getDetails());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dbHelper.deleteTask(task.getId());
                tasks.remove(task);
                notifyDataSetChanged();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskClickListener.onTaskClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Task getTaskAtPosition(int position) {
        if (position >= 0 && position < tasks.size()) {
            return tasks.get(position);
        }
        return null;
    }

    interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    interface OnSwipeListener {
        void onSwipeLeft(int position);
        void onSwipeRight(int position);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        TextView taskDetailsTextView;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            taskDetailsTextView = itemView.findViewById(R.id.taskDetailsTextView);
        }
    }
}
