package com.fb.finstagram;

import android.app.Application;

import com.fb.finstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbustagramId")
                .clientKey("3234Rt5")
                .server("http://fbustagram.herokuapp.com/parse")
                .build();

        // this will set up parse server therefore on log activity it won't crash and will make network call
        Parse.initialize(configuration);
        // when you set a parse class name and extend ParseObject, you must let Parse know that you are creating a custom parse model
        ParseObject.registerSubclass(Post.class);

    }
}
