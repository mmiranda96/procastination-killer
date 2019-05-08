package com.mmiranda96.procastinationKiller.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.ResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;
import com.mmiranda96.procastinationKiller.sources.user.UserSourceFactory;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordAsyncTask.Listener {
    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    private User user;

    private String token;
    private String dataToParse;
    private Uri data;


    private UserSource userSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        this.username = findViewById(R.id.editTextUsername);
        this.password = findViewById(R.id.editTextPassword);
        this.confirmPassword = findViewById(R.id.editTextConfirm);

        Intent intent = getIntent();
        data = intent.getData();
        this.user = (User) intent.getSerializableExtra(IntentExtras.USER);
        dataToParse = data.toString();

        token = "";
        for (int i = 34; i < dataToParse.length(); i++) {
            token = token + dataToParse.charAt(i);
        }
        Log.d("value", token);

        // TODO: use a dependency injection framework. Meanwhile, change REMOTE to FAKE if needed
        this.userSource = UserSourceFactory.newSource(UserSourceFactory.REMOTE, Server.URL);
    }

    public void resetPassword(View view) {
        if (this.username.getText().toString().compareTo("") == 0) {
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

        String email = this.username.getText().toString();
        String password = this.password.getText().toString();
        Log.d("value", this.token);

        ResetPasswordAsyncTask asyncTask = this.userSource.newResetPasswordAsyncTask(this, token);
        asyncTask.execute(email, password, token);
    }

    @Override
    public void resetPasswordAsyncTaskDone(Integer result) {
        switch (result) {
            case ResetPasswordAsyncTask.SUCCESS:
                String email = this.username.getText().toString();
                String password = this.password.getText().toString();
                User user = new User(email, password);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(IntentExtras.USER, user);
                startActivity(intent);
                return;
            case ResetPasswordAsyncTask.FAILURE:
                Toast.makeText(getApplicationContext(), "Email is already in use", Toast.LENGTH_SHORT).show();
                return;
            default:
                Toast.makeText(getApplicationContext(), "An error ocurred", Toast.LENGTH_SHORT).show();
        }
    }

}