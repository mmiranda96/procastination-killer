package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;
import com.mmiranda96.procastinationKiller.sources.user.UserSourceFactory;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.Listener {
    private User user;

    private EditText username;
    private EditText password;

    private UserSource userSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.username = findViewById(R.id.editTextLoginUsername);
        this.password = findViewById(R.id.editTextLoginPassword);

        // TODO: use a dependency injection framework. Meanwhile, change REMOTE to FAKE if needed
        this.userSource = UserSourceFactory.newSource(UserSourceFactory.REMOTE, Server.URL);
    }

    public void login(View v) {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        this.user = new User(username, password);

        LoginAsyncTask asyncTask = this.userSource.newLoginAsyncTask(this);
        asyncTask.execute(this.user);
    }

    @Override
    public void loginAsyncTaskDone(Integer result) {
        switch (result) {
            case LoginAsyncTask.SUCCESS:
                launchMainActivity();
                break;
            case LoginAsyncTask.FAILURE:
                displayFailureMessage();
                break;
            default:
                displayErrorMessage();
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(IntentExtras.USER, this.user);
        startActivity(intent);
    }

    private void displayFailureMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "Invalid email and/or password", Toast.LENGTH_SHORT).show();
    }

    private void displayErrorMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "An error occurred. Please try later.", Toast.LENGTH_SHORT).show();
    }
}
