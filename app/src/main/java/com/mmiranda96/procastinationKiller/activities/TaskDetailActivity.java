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
import com.mmiranda96.procastinationKiller.util.IntentExtras;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView taskTitle = findViewById(R.id.textViewTaskDetailActivityTitle);
        TextView taskDescription = findViewById(R.id.textViewTaskDetailActivityTaskDescription);
        TextView taskDue = findViewById(R.id.textViewTaskDetailActivityDue);
        ListView subtaskList = findViewById(R.id.listViewTaskDetailActivitySubtasks);

        // TODO: add detail functionality here
        Intent intent = getIntent();
        this.task = (Task) intent.getSerializableExtra("task");
        if (task != null) {
            // add sub-tasks list
            ArrayList<String> subtaskArrayList = new ArrayList<>(task.getSubtasks());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    subtaskArrayList
            );
            subtaskList.setAdapter(adapter);
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());
            taskDue.setText("Due: " + CalendarActivity.DATE_FORMAT.format(task.getDue()));
        }
    }

    public void changeToMainActivity(View v) {
        finish();
    }

    public void changeToLocationMapActivity(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(IntentExtras.TASK, this.task);
        startActivity(intent);
    }
}
