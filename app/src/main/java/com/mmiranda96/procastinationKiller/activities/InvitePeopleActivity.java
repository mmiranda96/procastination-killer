package com.mmiranda96.procastinationKiller.activities;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.AddUserToTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.TaskSourceFactory;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

import static android.content.RestrictionsManager.RESULT_ERROR;

public class InvitePeopleActivity extends AppCompatActivity implements AddUserToTaskAsyncTask.Listener{
    private TextView textViewTaskTitle;
    private EditText editTextUserEmail;
    private Button buttonGoBack, buttonInvite;
    private Integer taskId;
    private String userEmail;
    private Task task;
    private User user;
    private TaskSource taskSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);

        this.textViewTaskTitle = findViewById(R.id.textViewInvitePeopleActivityTaskTitle);
        this.editTextUserEmail = findViewById(R.id.editTextInvitePeopleActivityEmail);
        this.buttonGoBack = findViewById(R.id.buttonInvitePeopleActivityBack);
        this.buttonInvite = findViewById(R.id.buttonInvitePeopleActivityOK);

        Intent intent = getIntent();
        this.task = (Task) intent.getSerializableExtra(IntentExtras.TASK);
        this.user = (User) intent.getSerializableExtra(IntentExtras.USER);

        this.textViewTaskTitle.setText(this.task.getTitle());
        this.taskId = this.task.getId();
        this.taskSource = TaskSourceFactory.newSource(TaskSourceFactory.REMOTE, this.user, Server.URL);
    }

    public void addUserToTask(View view){
        if (this.editTextUserEmail.getText().toString().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "A user email is required", Toast.LENGTH_SHORT).show();
            return;
        }
        this.userEmail = this.editTextUserEmail.getText().toString();
        AddUserToTaskAsyncTask asyncTask = taskSource.newAddUserToTaskAsyncTask(this);
        asyncTask.execute(this.taskId.toString(), this.userEmail);
    }

    public void goToMainActivity(View view){
        finish();
    }

    @Override
    public void addUserToTaskAsyncTaskDone(Integer result) {
        finishActivity(result);
    }

    private void finishActivity(Integer result) {
        Intent intent = getIntent();
        switch (result) {
            case AddUserToTaskAsyncTask.SUCCESS:
                Toast.makeText(getApplicationContext(), "A user has been added to your task", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK, intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "An error ocurred", Toast.LENGTH_SHORT).show();
                setResult(RESULT_ERROR);
                break;
        }
        finish();
    }
}
