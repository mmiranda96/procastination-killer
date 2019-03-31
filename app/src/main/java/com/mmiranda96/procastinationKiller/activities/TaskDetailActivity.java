package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskTitle;
    private TextView taskDescription;
    private ListView subtaskList;
    private ArrayList<String> subtaskArrayList;
    private Button goBack;
    private Button mapLocation;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        this.taskTitle = findViewById(R.id.textViewTaskDetailActivityTitle);
        this.taskDescription = findViewById(R.id.textViewTaskDetailActivityTaskDescription);
        this.subtaskList = findViewById(R.id.listViewTaskDetailActivitySubtasks);
        this.goBack = findViewById(R.id.buttonTaskDetailsActivityBack);
        this.mapLocation = findViewById(R.id.buttonTaskDetailActivityMap);

        // TODO: add detail functionality here
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task != null) {
            // add sub-tasks list
            this.subtaskArrayList = new ArrayList<>(task.getSubtasks());
            this.adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    subtaskArrayList
            );
            this.subtaskList.setAdapter(adapter);

            // add title
            this.taskTitle.setText(task.getTitle());

            // add description
            this.taskDescription.setText(task.getDescription());
        }
    }

    public void changeToMainActivity(View v) {
        finish();
    }

    public void changeToLocationMapActivity(View v) {
        //TODO go to activity
    }
}
