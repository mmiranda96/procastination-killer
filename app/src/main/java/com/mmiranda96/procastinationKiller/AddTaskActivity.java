package com.mmiranda96.procastinationKiller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private DBHelper db;
    private EditText taskTitle, taskDescription, subtask;
    private ListView subtaskList;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        this.db = new DBHelper(getApplicationContext());

        this.taskTitle = findViewById(R.id.editTextAddTaskActivityTitle);
        this.taskDescription = findViewById(R.id.editTextAddTaskActivityDescription);
        this.subtask = findViewById(R.id.editTextAddTaskActivitySubtask);
        this.subtaskList = findViewById(R.id.listViewAddTaskActivitySubtasks);

        this.data = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(
            getApplicationContext(),
            android.R.layout.simple_list_item_1,
            data
        );
        this.subtaskList.setAdapter(adapter);
    }

    public void addSubtask(View view) {
        String subtask = this.subtask.getText().toString();
        this.data.add(subtask);
        this.adapter.notifyDataSetChanged();
    }

    public void createTask(View view) {
        String title = this.taskTitle.getText().toString();
        String description = this.taskDescription.getText().toString();
        Date due = new Date();
        Task task = new Task(title, description, due, this.data);
        this.db.createTask(task);

        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
