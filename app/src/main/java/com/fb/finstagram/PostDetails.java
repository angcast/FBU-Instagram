package com.fb.finstagram;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.Date;

public class PostDetails extends AppCompatActivity {

    String description;
    ParseFile image;
    Date createdAt;
    String username;
    ImageView ivImage;
    TextView tvDescription;
    TextView tvCreatedAt;
    TextView tvUsername;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivImage = (ImageView) findViewById(R.id.postImage);
        tvDescription = (TextView) findViewById(R.id.postDescription);
        tvCreatedAt = (TextView) findViewById(R.id.postTimestamp);
        tvUsername = (TextView) findViewById(R.id.postUsername);

        description = getIntent().getStringExtra("description");
        image = (ParseFile) getIntent().getExtras().get("image");
        createdAt = (Date) getIntent().getExtras().get("timestamp");
        username = getIntent().getStringExtra("username");
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        settingUpView();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    public void onClickLogout(MenuItem item){
        ParseUser.logOut();

    }

    public void settingUpView(){
        tvDescription.setText(description);
        tvUsername.setText(username);

        tvCreatedAt.setText(HomeAdapter.formatDate(createdAt));

        Glide.with(this)
                .load(image.getUrl())
                .into(ivImage);

    }




}
