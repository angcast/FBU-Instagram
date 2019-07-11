package com.fb.finstagram.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fb.finstagram.HomeAdapter;
import com.fb.finstagram.R;
import com.fb.finstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends AppCompatActivity {

    ArrayList<Post> arraylist_posts;
    HomeAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        arraylist_posts = new ArrayList<>();
        adapter = new HomeAdapter(arraylist_posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        setUpSwipeContainer();

        showPosts();

    }

    public void setUpSwipeContainer(){
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make sure you call swipeContainer.setRefreshing(false) once the network request has completed successfully.
                arraylist_posts.clear(); // TODO: test: if u don't clear this, will it repopulate images twice?
                adapter.clear();
                showPosts();
                swipeContainer.setRefreshing(false);

            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void showPosts(){
        Post.Query posts = new Post.Query();
        posts.getTop().withUser();
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
