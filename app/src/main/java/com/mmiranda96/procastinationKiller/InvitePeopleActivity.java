package com.mmiranda96.procastinationKiller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mmiranda96.procastinationKiller.models.Task;

public class InvitePeopleActivity extends AppCompatActivity {
    private TextView taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);

        this.taskTitle = findViewById(R.id.textViewInvitePeopleActivityTaskTitle);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        this.taskTitle.setText(task.getTitle());
    }

    public void ok(View view) {
        finish();
    }
}
