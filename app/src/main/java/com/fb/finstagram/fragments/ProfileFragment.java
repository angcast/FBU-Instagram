package com.fb.finstagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fb.finstagram.R;
import com.fb.finstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends HomeFragment {

 Button uploadNewImage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvPosts.setLayoutManager(gridLayoutManager);
    }

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
