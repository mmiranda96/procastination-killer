package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.RequestResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;
import com.mmiranda96.procastinationKiller.sources.user.UserSourceFactory;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

public class RequestResetPasswordActivity extends AppCompatActivity implements RequestResetPasswordAsyncTask.Listener {
    private String userEmail;
    private EditText username;
    private User user;

    private UserSource userSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_reset_password);

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra(IntentExtras.USER);

        this.username = findViewById(R.id.editTextUsername);
        // TODO: use a dependency injection framework. Meanwhile, change REMOTE to FAKE if needed
        this.userSource = UserSourceFactory.newSource(UserSourceFactory.REMOTE, Server.URL);
    }

    public void request(View view) {
        this.userEmail = this.username.getText().toString();

        RequestResetPasswordAsyncTask asyncTask = this.userSource.newRequestResetPasswordAsyncTask(this);
        asyncTask.execute(this.userEmail);
    }

    @Override
    public void requestResetPasswordAsyncTaskDone(Integer result) {
        switch (result) {
            case RequestResetPasswordAsyncTask.SUCCESS:
                requestReset();
                break;
            case RequestResetPasswordAsyncTask.FAILURE:
                displayFailureMessage();
                break;
            default:
                displayErrorMessage();
        }
    }

    private void displayFailureMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
    }

    private void displayErrorMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "An error occurred. Please try later.", Toast.LENGTH_SHORT).show();
    }

    public void requestReset() {
        Toast.makeText(getApplicationContext(), "A link to recover your account has been sent to your email.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
