package com.mmiranda96.procastinationKiller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_FILE = "database";

    public DBHelper(Context context){
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String queryTask =
            "CREATE TABLE task(" +
            "    id INTEGER PRIMARY KEY, " +
            "    title VARCHAR(64), " +
            "    description TEXT, " +
            "    due DATE" +
            ");" ;
        db.execSQL(queryTask);

        final String querySubtask =
            "CREATE TABLE subtask(" +
            "    id INTEGER," +
            "    task_id INTEGER," +
            "    description VARCHAR(64)," +
            "    PRIMARY KEY (id, task_id)," +
            "    FOREIGN KEY (task_id) REFERENCES task(id)" +
            ");";
        db.execSQL(querySubtask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String querySubtask =
            "DROP TABLE IF EXISTS subtask;";
        db.execSQL(querySubtask);

        final String queryTask =
            "DROP TABLE IF EXISTS task";
        db.execSQL(queryTask);

        onCreate(db);
    }

    public void createTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        final String queryTask =
            "INSERT INTO task(title, description, due)" +
            "VALUES ($1, $2, $3)";
        Object argsTask[] = {task.getTitle(), task.getDescription(), task.getDue()};
        db.execSQL(queryTask, argsTask);

        final String queryLastID =
            "SELECT last_insert_rowid()";
        Cursor cursor = db.rawQuery(queryLastID, new String[]{});
        cursor.moveToNext();
        int taskID = cursor.getInt(0);

        final String querySubtasks =
            "INSERT INTO subtask(task_id, description)" +
            "VALUES ($1, $2)";
        for (String subtask : task.getSubtasks()) {
            Object argsSubtasks[] = {taskID, subtask};
            db.execSQL(querySubtasks, argsSubtasks);
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        final String queryTasks =
            "SELECT id, title, description, due " +
            "FROM task";
        Cursor cursor = db.rawQuery(queryTasks, new String[]{});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            Date due = new Date(cursor.getString(3));
            Task newTask = new Task(id, title, description, due);

            final String querySubtask =
                "SELECT description " +
                    "FROM subtask " +
                    "WHERE task_id = $1";
            String args[] = {id+""};
            Cursor cursorSubtasks = db.rawQuery(querySubtask, args);
            while (cursorSubtasks.moveToNext()) {
                String subtask = cursorSubtasks.getString(0);
                newTask.addSubtask(subtask);
            }
            tasks.add(newTask);
        }
        return tasks;
    }
}