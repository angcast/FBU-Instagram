package com.fb.finstagram.fragments;

import android.util.Log;
import android.widget.Button;

import com.fb.finstagram.ProfileAdapter;
import com.fb.finstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends HomeFragment {

    Button uploadNewImage;
    ArrayList<Post> myPosts;
    ProfileAdapter profileAdapter;

    @Override
    protected void showPosts() {
        Post.Query posts = new Post.Query();
        posts.getTop().withUser();
        posts.addDescendingOrder(Post.KEY_CREATED_AT);
        posts.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        posts.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0 ; i<objects.size(); i++){
                        arraylist_posts.add(objects.get(i));
                        adapter.notifyItemInserted(objects.size()-1);
                    }
                }
                else{
                    Log.d("Home Fragment","Loading pictures failed");
                }
            }
        });
    }


}
