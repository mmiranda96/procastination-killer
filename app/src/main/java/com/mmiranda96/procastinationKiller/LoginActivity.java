package com.mmiranda96.procastinationKiller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    // TODO: handle password EditText
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.username = findViewById(R.id.editTextLoginUsername);
    }

    public void login(View v) {
        String username = this.username.getText().toString();
        Toast.makeText(getApplicationContext(), "Hello "+username, Toast.LENGTH_SHORT).show();
    }
}
