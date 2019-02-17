package com.mmiranda96.procastinationKiller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_TASK_ACTIVITY_CODE = 0;

    private DBHelper db;
    private TextView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new DBHelper(getApplicationContext());
        this.taskList= findViewById(R.id.textViewMainTaskList);
        refreshTaskList();

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        Toast.makeText(getApplicationContext(), "Hello "+username, Toast.LENGTH_SHORT).show();
    }

    private void refreshTaskList() {
        ArrayList<Task> tasks = this.db.getTasks();
        this.taskList.setText(tasks.toString());
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
}
