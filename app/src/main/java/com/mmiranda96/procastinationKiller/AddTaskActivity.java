package com.mmiranda96.procastinationKiller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mmiranda96.procastinationKiller.asyncTasks.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

// TODO: this is broken, implement logic with CreateTaskAsyncTask
public class AddTaskActivity extends AppCompatActivity implements CreateTaskAsyncTask.RequestListener {
    private EditText taskTitle, taskDescription, subtask;
    private ListView subtaskList;
    private Button addSubtask, create;
    private ArrayList<String> subtaskArrayList;
    private ArrayAdapter<String> adapter;
    private boolean readOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Intent intent = getIntent();
        this.readOnly = intent.getBooleanExtra("readOnly", true);
        this.taskTitle = findViewById(R.id.editTextAddTaskActivityTitle);
        this.taskDescription = findViewById(R.id.editTextAddTaskActivityDescription);
        this.subtask = findViewById(R.id.editTextAddTaskActivitySubtask);
        this.subtaskList = findViewById(R.id.listViewAddTaskActivitySubtasks);
        this.addSubtask = findViewById(R.id.buttonAddTaskActivityAddSubtask);
        this.create = findViewById(R.id.buttonAddTaskActivityCreateTask);
        this.subtaskArrayList = new ArrayList<>();

        if (!this.readOnly) {
            Task task = (Task) intent.getSerializableExtra("task");
            if (task != null) {
                this.taskTitle.setText(task.getTitle());
                this.taskDescription.setText(task.getDescription());
                this.subtaskArrayList = new ArrayList<>(task.getSubtasks());

                this.taskTitle.setEnabled(false);
                this.taskTitle.setEnabled(false);
                this.taskDescription.setEnabled(false);
                this.subtask.setEnabled(false);
                this.subtask.setText("");
                this.subtaskList.setEnabled(false);
                this.addSubtask.setEnabled(false);

                // TODO: take this from a strings resource
                this.create.setText("Go back");
            }
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
    }

    public void createTask(View view) {
        if (this.readOnly) {
            String title = this.taskTitle.getText().toString();
            String description = this.taskDescription.getText().toString();
            Date due = new Date(); // TODO: take user input
            Task task = new Task(title, description, due, this.subtaskArrayList);
            // TODO: execute CreateTaskAsyncTask
        }

        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void createTaskRequestDone(Integer result) {
        // TODO: implement
        switch (result) {
            case CreateTaskAsyncTask.SUCCESS:
            default:
        }
    }
}
