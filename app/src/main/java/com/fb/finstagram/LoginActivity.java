package com.fb.finstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.loginUsername);
        passwordInput = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);

        ParseUser user = ParseUser.getCurrentUser();
        checkIfUserLoggedIn(user);

    }

    public void checkIfUserLoggedIn(ParseUser user){
        if (user!=null){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }
    // TODO: research- final variables within a method- how do they work?
    public void onClickLogin(View view){
        final String username = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        login(username, password);
    }

    public void onClickSignUp(View view){
        Intent i = new Intent(LoginActivity.this, SignUpUsername.class);
        startActivity(i);
    }

    // NOTE: controls login functionality: do logInInBackground because logIn will run on main thread, and we
    // do not want to run a network call in main thread because it locks up the UI and make it seem
    // as the phone is not responding.
    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // no errors
                if (e==null){
                    Log.d("LoginActivity","Login successful!");
                    final Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    // takes you out of the application
                    finish();
                }
                else{
                    Log.d("LoginActivity","Login failure!");
                    e.printStackTrace();
                }
            }
        });
    }

}
