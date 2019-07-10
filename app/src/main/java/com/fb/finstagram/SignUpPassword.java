package com.fb.finstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPassword extends AppCompatActivity {

    String newUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_password);

        newUsername = getIntent().getStringExtra("newUsername");
        Toast.makeText(this, newUsername, Toast.LENGTH_LONG).show();
    }

    public void onClickNext(View view){
        Toast.makeText(this, newUsername, Toast.LENGTH_LONG).show();
        EditText newPassword = (EditText) findViewById(R.id.email);
        Intent i = new Intent(SignUpPassword.this, SignUpEmail.class);
        i.putExtra("newPassword", newPassword.getText().toString());
        i.putExtra("newUsername", newUsername);
        startActivity(i);
    }



}
