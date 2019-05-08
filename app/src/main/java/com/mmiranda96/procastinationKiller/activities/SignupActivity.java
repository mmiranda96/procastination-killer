package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;
import com.mmiranda96.procastinationKiller.sources.user.UserSourceFactory;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

public class SignupActivity extends AppCompatActivity implements SignupAsyncTask.Listener {

    private EditText email, password, confirmPassword, name;
    private UserSource userSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.email = findViewById(R.id.editTextSignupActivityEmail);
        this.password = findViewById(R.id.editTextSignupActivityPassword);
        this.confirmPassword = findViewById(R.id.editTextSignupActivityConfirmPassword);
        this.name = findViewById(R.id.editTextSignupActivityName);
        this.userSource = UserSourceFactory.newSource(UserSourceFactory.REMOTE, Server.URL);
    }

    public void signup(View view) {
        if (this.email.getText().toString().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "An email is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.password.getText().toString().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "A password is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.password.getText().toString().compareTo(this.confirmPassword.getText().toString()) != 0) {
            Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.name.getText().toString().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "A name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String name = this.name.getText().toString();
        User user = new User(email, password, name);

        SignupAsyncTask asyncTask = this.userSource.newSignupAsyncTask(this);
        asyncTask.execute(user);

    }

    @Override
    public void signupAsyncTaskDone(Integer result) {
        switch (result) {
            case SignupAsyncTask.SUCCESS:
                String email = this.email.getText().toString();
                String password = this.password.getText().toString();
                String name = this.name.getText().toString();

                User user = new User(email, password, name);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(IntentExtras.USER, user);
                startActivity(intent);
                return;
            case SignupAsyncTask.FAILURE:
                Toast.makeText(getApplicationContext(), "Email is already in use", Toast.LENGTH_SHORT).show();
                return;
            default:
                Toast.makeText(getApplicationContext(), "An error ocurred", Toast.LENGTH_SHORT).show();
        }
    }
}
