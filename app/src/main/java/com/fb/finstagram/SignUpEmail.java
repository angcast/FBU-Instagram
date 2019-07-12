package com.fb.finstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpEmail extends AppCompatActivity {

    String newUsername;
    String newPassword;

    EditText etEmail;

    ParseUser user = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        newUsername = getIntent().getStringExtra("newUsername");
        newPassword = getIntent().getStringExtra("newPassword");
        etEmail = (EditText) findViewById(R.id.email);


    }

    public void onClickNext(View view){ ;
        userSignUp();
    }

    public void userSignUp(){
        String email = etEmail.getText().toString();
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Intent i = new Intent(SignUpEmail.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }

}
