package com.mmiranda96.procastinationKiller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.models.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    // TODO: handle password EditText
    private EditText username;
    private EditText password;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.db = new DBHelper(getApplicationContext());
        this.username = findViewById(R.id.editTextLoginUsername);
        this.password = findViewById(R.id.editTextLoginPassword);

        // user test
        this.db.createUser(new User("Rosa", "123"));
    }

    public void login(View v) {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        ArrayList<User> users = this.db.getUsers();
        for( int i = 0; i < users.size(); i++ ){
            if( users.get(i).getUsername().equals(username) ){
                if( users.get(i).getPassword().equals(password) ){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                }
            }
        }
    }


}
