package com.mmiranda96.procastinationKiller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private DBHelper db;
    private TextView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new DBHelper(getApplicationContext());
        this.taskList= findViewById(R.id.textViewMainTaskList);
        ArrayList<Task> tasks = this.db.getTasks();
        this.taskList.setText(tasks.toString());

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        Toast.makeText(getApplicationContext(), "Hello "+username, Toast.LENGTH_SHORT).show();
    }
}
