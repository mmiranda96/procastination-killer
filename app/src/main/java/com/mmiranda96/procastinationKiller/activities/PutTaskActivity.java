package com.mmiranda96.procastinationKiller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.adapters.SubtaskListAdapter;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.TaskSourceFactory;
import com.mmiranda96.procastinationKiller.sources.task.UpdateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

import java.util.ArrayList;
import java.util.Date;

// TODO: this is broken, implement logic with CreateTaskAsyncTask
public class PutTaskActivity extends AppCompatActivity implements CreateTaskAsyncTask.Listener, UpdateTaskAsyncTask.Listener {
    private static final int RESULT_ERROR = 2;

    private EditText taskTitle, taskDescription, subtask;
    private ListView subtaskList;
    private Button addSubtask, create;
    private ArrayList<String> subtaskArrayList;
    private SubtaskListAdapter adapter;
    private TaskSource taskSource;
    private Task task;
    private boolean isNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_task);

        this.taskTitle = findViewById(R.id.editTextAddTaskActivityTitle);
        this.taskDescription = findViewById(R.id.editTextAddTaskActivityDescription);
        this.subtask = findViewById(R.id.editTextAddTaskActivitySubtask);
        this.subtaskList = findViewById(R.id.listViewAddTaskActivitySubtasks);
        this.addSubtask = findViewById(R.id.buttonAddTaskActivityAddSubtask);
        this.create = findViewById(R.id.buttonAddTaskActivityCreateTask);

        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra(IntentExtras.USER);
        this.taskSource = TaskSourceFactory.newSource(TaskSourceFactory.REMOTE, user, Server.URL);

        this.task = (Task) intent.getSerializableExtra(IntentExtras.TASK);
        if (this.task != null) {
            this.isNewTask = false;
            this.taskTitle.setText(this.task.getTitle());
            this.taskDescription.setText(this.task.getDescription());
            this.subtaskArrayList = new ArrayList<>(task.getSubtasks());
            this.create.setText("Update");
        } else {
            this.isNewTask = true;
            this.subtaskArrayList = new ArrayList<>();
            this.create.setText("Create");
        }

        this.adapter = new SubtaskListAdapter(this, subtaskArrayList);
        this.subtaskList.setAdapter(adapter);
    }

    public void addSubtask(View view) {
        String subtask = this.subtask.getText().toString();
        this.subtaskArrayList.add(subtask);
        this.adapter.update(this.subtaskArrayList);
        this.subtask.setText("");
    }

    public void putTask(View view) {
        String title = this.taskTitle.getText().toString();
        String description = this.taskDescription.getText().toString();
        Date due = new Date(); // TODO: take user input
        //ArrayList<String> subtasks =

        if (this.isNewTask) {
            Task task = new Task(title, description, due, this.subtaskArrayList);
            CreateTaskAsyncTask asyncTask = taskSource.newCreateTaskAsyncTask(this);
            asyncTask.execute(task);
        } else {
            Task task = new Task(this.task.getId(), title, description, due, this.subtaskArrayList);
            UpdateTaskAsyncTask asyncTask = taskSource.newUpdateTaskAsyncTask(this);
            asyncTask.execute(task);
        }
    }

    @Override
    public void createTaskAsyncTaskDone(Integer result) {
        finishActivity(result);
    }

    @Override
    public void updateTaskAsyncTaskDone(Integer result) {
        finishActivity(result);
    }

    private void finishActivity(Integer result) {
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
