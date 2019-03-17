package com.mmiranda96.procastinationKiller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.adapters.TaskListAdapter;
import com.mmiranda96.procastinationKiller.asyncTasks.GetTasks;
import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements GetTasks.RequestListener {
    private static final int ADD_TASK_ACTIVITY_CODE = 0;

    private DBHelper db;
    private ListView taskList;
    private TaskListAdapter adapter;
    private ArrayList<Task> tasks;
    private String currentUser;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new DBHelper(getApplicationContext());
        this.taskList = findViewById(R.id.listViewMainActivityTaskList);
        this.tasks = this.db.getTasks();

        this.adapter = new TaskListAdapter(this, this.tasks);
        this.taskList.setAdapter(adapter);
        this.taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra("enabled", false);
                intent.putExtra("task", tasks.get(position));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("Username");
        currentPassword = intent.getStringExtra("Password");
        Toast.makeText(getApplicationContext(), "Hello "+currentUser, Toast.LENGTH_SHORT).show();
    }

    private void refreshTaskList() {
        this.tasks = this.db.getTasks();
        this.adapter.updateTasks(tasks);
    }

    public void addActivity(View view){
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivityForResult(intent, ADD_TASK_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TASK_ACTIVITY_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    refreshTaskList();
            }
        }
    }

    public void doRequest(View v){
        GetTasks task = new GetTasks(this);
        task.execute("10.0.2.2:8080", currentUser, currentPassword);
    }

    @Override
    public void getTasksRequestDone(ArrayList<Task> result) {
        for(int i = 0; i<result.size(); i++){
            Log.wtf("Task", result.get(i).toString());
        }
        Toast.makeText(getApplicationContext(),result.get(0).toString(), Toast.LENGTH_SHORT);
    }
}
