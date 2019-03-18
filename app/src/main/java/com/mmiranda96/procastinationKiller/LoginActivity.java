package com.mmiranda96.procastinationKiller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.asyncTasks.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.models.User;

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.RequestListener {
    private User user;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.username = findViewById(R.id.editTextLoginUsername);
        this.password = findViewById(R.id.editTextLoginPassword);
    }

    public void login(View v) {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        this.user = new User(username, password);

        LoginAsyncTask loginTask = new LoginAsyncTask(Server.URL, this);
        loginTask.execute(this.user);
    }

    @Override
    public void loginRequestDone(Integer result) {
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
        intent.putExtra("User", this.user);
        startActivity(intent);
    }

    private void displayFailureMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "Invalid email and/or password", Toast.LENGTH_SHORT).show();
    }

    private void displayErrorMessage() {
        // TODO: proper message
        Toast.makeText(getApplicationContext(), "An error ocurred. Please try later.", Toast.LENGTH_SHORT).show();
    }
}
