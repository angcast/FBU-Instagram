package com.fb.finstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbustagramId")
                .clientKey("3234Rt5")
                .server("http://fbustagram.herokuapp.com/parse")
                .build();

        // this will set up parse serve therefore on log activity it won't crash and will make
        // network call
        Parse.initialize(configuration);

    }
}
