package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // TODO: add detail functionality here
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task != null) {
            // TODO: initialize fields
        }
    }
}
