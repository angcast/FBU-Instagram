package com.fb.finstagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

public class PostDetails extends AppCompatActivity {

    String description;
    ParseFile image;
    ImageView ivImage;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivImage = (ImageView) findViewById(R.id.postImage);
        tvDescription = (TextView) findViewById(R.id.postDescription);

        description = getIntent().getStringExtra("description");
        image = (ParseFile) getIntent().getExtras().get("image");

        settingUpView();
    }

    public void settingUpView(){
        tvDescription.setText(description);
        Glide.with(this)
                .load(image.getUrl())
                .into(ivImage);

    }




}
