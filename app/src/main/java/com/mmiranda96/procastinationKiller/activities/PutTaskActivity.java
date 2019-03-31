package com.mmiranda96.procastinationKiller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.TaskSourceFactory;
import com.mmiranda96.procastinationKiller.util.Server;

import java.util.ArrayList;
import java.util.Date;

// TODO: this is broken, implement logic with CreateTaskAsyncTask
public class AddTaskActivity extends AppCompatActivity implements CreateTaskAsyncTask.Listener {
    private static final int RESULT_ERROR = 2;

    private EditText taskTitle, taskDescription, subtask;
    private ListView subtaskList;
    private Button addSubtask, create;
    private ArrayList<String> subtaskArrayList;
    private ArrayAdapter<String> adapter;
    private TaskSource taskSource;
    private Task task;
    private boolean isNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        this.taskTitle = findViewById(R.id.editTextAddTaskActivityTitle);
        this.taskDescription = findViewById(R.id.editTextAddTaskActivityDescription);
        this.subtask = findViewById(R.id.editTextAddTaskActivitySubtask);
        this.subtaskList = findViewById(R.id.listViewAddTaskActivitySubtasks);
        this.addSubtask = findViewById(R.id.buttonAddTaskActivityAddSubtask);
        this.create = findViewById(R.id.buttonAddTaskActivityCreateTask);
        this.subtaskArrayList = new ArrayList<>();

        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra("user");
        this.taskSource = TaskSourceFactory.newSource(TaskSourceFactory.REMOTE, user, Server.URL);

        this.task = (Task) intent.getSerializableExtra("task");
        if (this.task != null) {
            Task task = (Task) intent.getSerializableExtra("task");
            if (task != null) {
                this.isNewTask = false;
                this.taskTitle.setText(this.task.getTitle());
                this.taskDescription.setText(this.task.getDescription());
                this.subtaskArrayList = new ArrayList<>(task.getSubtasks());
            }
        } else {
            this.isNewTask = true;
        }

        this.adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                subtaskArrayList
        );
        this.subtaskList.setAdapter(adapter);
    }

    public void addSubtask(View view) {
        String subtask = this.subtask.getText().toString();
        this.subtaskArrayList.add(subtask);
        this.adapter.notifyDataSetChanged();
        this.subtask.setText("");
    }

    public void createTask(View view) {
        String title = this.taskTitle.getText().toString();
        String description = this.taskDescription.getText().toString();
        Date due = new Date(); // TODO: take user input
        Task task = new Task(title, description, due, this.subtaskArrayList);

        if (this.isNewTask) {
            CreateTaskAsyncTask asyncTask = taskSource.newCreateTaskAsyncTask(this);
            asyncTask.execute(task);
        } else {
            // TODO: execute UpdateTaskAsyncTask
        }
    }

    @Override
    public void createTaskAsyncTaskDone(Integer result) {
        Log.d("Server", result + "");
        // TODO: implement
        Intent intent = getIntent();
        switch (result) {
            case CreateTaskAsyncTask.SUCCESS:
                setResult(Activity.RESULT_OK, intent);
                break;
            default:
                setResult(RESULT_ERROR);
                break;
        }
        finish();
    }
}
