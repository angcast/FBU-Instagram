package com.fb.finstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    // the keys that represent a post
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_IMAGE="image";
    public static final String KEY_USER="user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKES = "likes";


    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    // used when you create a post
    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }

    public int getLikes(){
        return getInt(KEY_LIKES);
    }

    public void setLikes(int amt){
        put(KEY_LIKES, amt);
    }




    public static class Query extends ParseQuery<Post> {
        public Query(){
            super(Post.class);
        }

        public Query getTop(){
            setLimit(20);
            // builder pattern- allows users to chain these methods
            return this;
        }
        public Query withUser(){
            include(KEY_USER);
            return this;
        }
    }


}
