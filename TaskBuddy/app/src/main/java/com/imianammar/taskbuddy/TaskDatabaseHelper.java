package com.imianammar.taskbuddy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DETAILS = "details";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DETAILS + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DETAILS, task.getDetails());
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                task.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS)));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public Task getTask(long taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_DETAILS};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query(TABLE_TASKS, columns, selection, selectionArgs, null, null, null);

        Task task = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") String taskDetails = cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS));
            task = new Task((int) id, taskName, taskDetails);
            cursor.close();
        }

        return task;
    }

    public void updateTask(Context context, Task taskToUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, taskToUpdate.getName());
        values.put(COLUMN_DETAILS, taskToUpdate.getDetails());

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(taskToUpdate.getId()) };

        int count = db.update(TABLE_TASKS, values, selection, selectionArgs);

        db.close();

        if (count > 0) {
            Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
        } else {
            // An error occurred during the update
        }
    }
}
