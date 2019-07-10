package com.fb.finstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_username);
    }

    public void onClickNext(View view){
        EditText newUsername = (EditText) findViewById(R.id.email);
        Intent i = new Intent(SignUpUsername.this, SignUpPassword.class);
        i.putExtra("newUsername",newUsername.getText().toString());
        startActivity(i);
    }

}
