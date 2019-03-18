package com.mmiranda96.procastinationKiller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.adapters.TaskListAdapter;
import com.mmiranda96.procastinationKiller.asyncTasks.GetTasksAsyncTask;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTasksAsyncTask.RequestListener {
    private static final int ADD_TASK_ACTIVITY_CODE = 0;

    private ListView taskList;
    private TaskListAdapter adapter;
    private ArrayList<Task> tasks;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.taskList = findViewById(R.id.listViewMainActivityTaskList);

        this.tasks = new ArrayList<>();
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
        String username = intent.getStringExtra("Username");
        String password = intent.getStringExtra("Password");
        this.currentUser = new User(username, password);
        this.getTasks();

        Toast.makeText(getApplicationContext(), "Hello "+currentUser, Toast.LENGTH_SHORT).show();
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
                    getTasks();
            }
        }
    }

    public void getTasks() {
        GetTasksAsyncTask task = new GetTasksAsyncTask(Server.URL, this);
        task.execute(this.currentUser);
    }

    @Override
    public void getTasksRequestDone(ArrayList<Task> result) {
        Log.i("Task", result.toString());
        this.tasks = new ArrayList<>(result);
        adapter.updateTasks(this.tasks);
        taskList.setAdapter(adapter);
        // TODO: fix UI reload
    }
}
